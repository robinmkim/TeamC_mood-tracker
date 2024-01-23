package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.*;
import com.teamc.moodtracker.service.BoardLikeService;
import com.teamc.moodtracker.service.BoardService;
import com.teamc.moodtracker.service.CommentService;
import com.teamc.moodtracker.service.ReplyService;
import com.teamc.moodtracker.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/post")
public class BoardController {
    @Autowired
    private BoardService service;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CommentController commentController;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private BoardLikeService likeService;

    String imageDirectory = "src/main/resources/static/images/";

    @PostMapping("/add")
    public int addBoardContent(@ModelAttribute BoardDetailDto dto,
                               @RequestParam(value = "mediaList", required = false) List<MultipartFile> mediaList) {
        List<MediaDto> mediaDtos = new ArrayList<>();
        System.out.print(dto);
        if (mediaList != null) {
            for (MultipartFile multipartFile : mediaList) {
                MediaDto mediaDto = new MediaDto();
                mediaDto.setMd_name(multipartFile.getOriginalFilename());
                mediaDto.setMd_path("images/");
                mediaDto.setMd_type(multipartFile.getContentType());
                mediaDtos.add(mediaDto);
                String filePath = imageDirectory + multipartFile.getOriginalFilename();
                System.out.println(filePath);
                uploadFile(multipartFile, filePath);
            }
        }

        service.addBoardContent(dto, mediaDtos);

        // 성공 시 적절한 상태 코드 반환
        return 1; // 예시: 성공 시 1 반환
    }

    public String uploadFile(MultipartFile file, String path) {
        if (!file.isEmpty()) {
            try (FileOutputStream writer = new FileOutputStream(path)) {
                writer.write(file.getBytes());
                return "File Uploaded Successfully!";
            } catch (Exception e) {
                log.error("Error while uploading file " + file.getOriginalFilename(), e);
                return "Error while uploading file" + file.getOriginalFilename();
            }
        } else {
            return "No File Selected!";
        }
    }

    @GetMapping("/get/{b_id}")
    public BoardDetailDto getBoardDetail(@AuthenticationPrincipal MemberDto memberDto,@PathVariable int b_id) {
        BoardDetailDto board = service.getBoardDetail(b_id);
        board.setCountComments(commentController.allCommentCount(b_id));
        BoardLikeDto dto = new BoardLikeDto();
        dto.setM_id(memberDto.getM_id());
        dto.setB_id(b_id);
        boolean isMyLike = likeService.isMyLike(dto);
        board.setIsMyLike(isMyLike);
        return board;
    }

    @GetMapping("/list")
    public List<Integer> getBoardList(@RequestParam(value = "lastRowNum") int lastRowNum) {
        return service.getBoardList(lastRowNum);
    }

    @Transactional
    @PostMapping(value = "/update", consumes = "multipart/form-data")
    public int updateBoardContent(@AuthenticationPrincipal MemberDto memberDto, @ModelAttribute BoardDetailDto dto,
                                  @RequestParam(value = "mediaList", required = false) List<MultipartFile> newMedias,
                                  @RequestParam(value = "mb_idList", required = false) String mb_idList) {
        System.out.println("update");
        System.out.println("mb_idList: "+ mb_idList);
        dto.setM_id(memberDto.getM_id());
        System.out.println("b_id: " + dto.getB_id());
        System.out.println("m_id: " + dto.getM_id());
        System.out.println("b_content: " + dto.getB_content());
        System.out.println("b_sentiment: " + dto.getB_sentiment());
        if (mb_idList.equals("isNull")) {

            String[] split_md_id = mb_idList.split(",");
            int[] md_ids = new int[split_md_id.length];
            for (int i = 0; i < split_md_id.length; i++) {
                md_ids[i] = Integer.parseInt(split_md_id[i]);
            }
            List<Integer> mediaIds = Arrays.stream(md_ids)
                    .boxed()
                    .collect(Collectors.toList());
            for (int i = 0; i < split_md_id.length; i++) {
                md_ids[i] = Integer.parseInt(split_md_id[i]);
            }
            List<MediaDto> oldMediaList = service.getMediaList(dto.getB_id());
            List<Integer> differentMediaIds = new ArrayList<>();
            for (MediaDto old : oldMediaList) {
                if (!mediaIds.contains(old.getMd_id())) {
                    differentMediaIds.add(old.getMd_id());
                }
            }
            if (differentMediaIds != null) {
                for (int md_id : differentMediaIds){
                    service.delMedia(md_id);
                }
            }
        }
        List<MediaDto> mediaDtos = new ArrayList<>();
        if (newMedias != null) {
//            oldMedias = service.getMediaList(dto.getB_id());
            for (MultipartFile multipartFile : newMedias) {
                MediaDto mediaDto = new MediaDto();
                mediaDto.setMd_name(multipartFile.getOriginalFilename());
                mediaDto.setMd_path("images/");
                mediaDto.setMd_type(multipartFile.getContentType());
                mediaDtos.add(mediaDto);
                String filePath = imageDirectory + multipartFile.getOriginalFilename();
                uploadFile(multipartFile, filePath);
            }
        }
        service.updateBoardContent(dto, mediaDtos);
        return 1;
    }
    @Transactional
    @GetMapping("/delPost")
    public void delPost(@RequestParam(value = "b_id") int b_id) {
        System.out.println("delPost");
        List<Integer> commentList = commentService.getCm_idList(b_id);
        for (int comment : commentList){
            List<Integer> replyList = replyService.getRe_idList(comment);
            for (int reply : replyList) {
                replyService.delReply(reply);
            }
            commentService.delComment(comment);
        }
        service.delPost(b_id);
    }

}
