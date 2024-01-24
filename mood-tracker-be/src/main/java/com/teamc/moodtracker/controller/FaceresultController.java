package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.FaceresultDto;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.service.FaceresultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/faceresult")
public class FaceresultController {

    private final FaceresultService faceresultService;


//     입력 (장고에서 처리함)
//    @PostMapping("/predictFace")
//    public String insertFaceResult(@AuthenticationPrincipal MemberDto memberDto, @RequestParam MultipartFile file1) {
//
////        faceresultService.insertFaceResult(faceresultDto);
//    }

    // 조회
    @GetMapping("/list") // 관리자 : 모든 얼굴분석결과 리스트 조회 ( 추후 삭제 가능 )
    public List<FaceresultDto> selectList(){
        return faceresultService.selectList();
    }
    @GetMapping("/mylist") // 본인의 얼굴분석결과 리스트 조회
    public List<FaceresultDto> selectMyList(int memberNum){
        return faceresultService.selectMyList(memberNum);
    }

    @GetMapping("/lastResultId")
    public int selectIdOfMyLastFaceResultp(@AuthenticationPrincipal MemberDto memberDto){
        return faceresultService.selectIdOfMyLastFaceResult(memberDto.getM_id());
    }
    @GetMapping("/detail")
    public FaceresultDto selectFaceResultDetail(@AuthenticationPrincipal MemberDto memberDto,@RequestParam int ar_id){
        return faceresultService.selectFaceResultDetail(ar_id);
    }

    // 삭제
    @PostMapping("/delete")
    public void deleteFaceResult(int resultId){
        faceresultService.deleteFaceResult(resultId);
    }

    // 피드백 업데이트
    @PostMapping(value = "/feedback/bad")
    public void updateFeedbackBad(@AuthenticationPrincipal MemberDto memberDto, @RequestBody Map<String, Integer> requestMap){
        //Map<String, Integer>는 json 형태
        Integer ar_id = requestMap.get("ar_id");
        faceresultService.updateFeedbackBad(ar_id);
    }
    @PostMapping("/feedback/good")
    public void updateFeedbackGood(@AuthenticationPrincipal MemberDto memberDto, @RequestBody Map<String, Integer> requestMap){
        Integer ar_id = requestMap.get("ar_id");
        faceresultService.updateFeedbackGood(ar_id);
    }



}
