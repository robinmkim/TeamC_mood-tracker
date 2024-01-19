package com.teamc.moodtracker.controller.JH;

import com.teamc.moodtracker.dto.Comment_LikeDto;
import com.teamc.moodtracker.dto.JH.JH_CommentDto;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.service.BoardService;
import com.teamc.moodtracker.service.JH.JH_CommentLikeService;
import com.teamc.moodtracker.service.JH.JH_CommentService;
import com.teamc.moodtracker.service.NotificationService;
import com.teamc.moodtracker.service.JH.JH_ReplyLikeService;
import com.teamc.moodtracker.service.JH.JH_ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/jh_comment")
public class JH_CommentController {
    @Autowired
    private JH_CommentService commentService;
    @Autowired
    private JH_CommentLikeService likeService;

    @Autowired
    private NotificationService notificationService; // 윤영호

    @Autowired
    private JH_ReplyService replyService;

    @GetMapping("/allCommentCount")
    public int allCommentCount(@RequestParam(value = "b_id") int b_id) {
        List<JH_CommentDto> comments = getCommentList(b_id);
        int replyCount = 0;
        for (JH_CommentDto dto : comments) {
            replyCount += commentService.replyCount(dto.getCm_id());
        }
        return commentService.commentCount(b_id) + replyCount;
    };

    @GetMapping("/getCommentList")
    public List<JH_CommentDto> getCommentList(int b_id) {
        return commentService.getCommentList(b_id);
    };

    @GetMapping("/getCommentListDetail")
    public List<JH_CommentDto> getCommentListDetail(@AuthenticationPrincipal MemberDto memberDto,
            @RequestParam("b_id") int b_id) {
        System.out.println("getCommentListDetail");
        System.out.println("b_id::::::::::::::::" + b_id);

        List<JH_CommentDto> comments = commentService.getCommentListDetail(b_id);

        Comment_LikeDto dto = new Comment_LikeDto();
        dto.setM_id(memberDto.getM_id());
        boolean isMyLike = false;
        for (JH_CommentDto comment : comments) {
            dto.setCm_id(comment.getCm_id());
            isMyLike = likeService.isMyLikeComment(dto);
            comment.setIsMyLike(isMyLike);
        }
        return comments;
    };

    @PostMapping("/addComment")
    public int addComment(@AuthenticationPrincipal MemberDto memberDto,
            @ModelAttribute JH_CommentDto dto) {
        System.out.println("addComment!!!!!!!!");
        // JH_CommentDto dto = new JH_CommentDto();
        dto.setM_id(memberDto.getM_id());
        // dto.setB_id(b_id);
        // dto.setCm_content(cm_content);
        System.out.println("m_id: " + dto.getM_id());
        System.out.println("b_id: " + dto.getB_id());
        System.out.println(dto.getCm_content());
        System.out.println(dto);

        commentService.addComment(dto);

        notificationService.addComment_SaveNotificationAndSendAlert(dto); // 알림 // 윤영호
        return 1;
    };

    @Transactional
    @GetMapping("/delComment")
    public void delComment(@RequestParam(value = "cm_id") int cm_id) {
        System.out.println("delComment");

        List<Integer> replyList = replyService.getRe_idList(cm_id);
        for (int reply : replyList) {
            System.out.println("re_id: " + reply);
            replyService.delReply(reply);
        }
        // if (likeService.commentLikeCount(cm_id) > 0) {
        // likeService.delCommentLike(cm_id);
        // }
        commentService.delComment(cm_id);
    }

    @GetMapping("/getCm_idList")
    public List<Integer> getCm_idList(@RequestParam("b_id") int b_id) {

        return commentService.getCm_idList(b_id);
    }

    @Transactional
    @GetMapping("/getCommentDetail")
    public JH_CommentDto getCommentDetail(@AuthenticationPrincipal MemberDto memberDto,
            @RequestParam("cm_id") int cm_id) {
        JH_CommentDto comment = commentService.getCommentDetail(cm_id);
        Comment_LikeDto dto = new Comment_LikeDto();
        dto.setM_id(memberDto.getM_id());
        dto.setCm_id(cm_id);
        comment.setIsMyLike(likeService.isMyLikeComment(dto));
        comment.setReply_count(replyService.replyCount(cm_id));
        comment.setLikeCount(likeService.commentLikeCount(cm_id));
        return comment;
    }

}
