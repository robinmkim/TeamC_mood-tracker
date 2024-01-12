package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.FaceresultDto;
import com.teamc.moodtracker.service.FaceresultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/faceresult")
public class FaceresultController {

    private final FaceresultService faceresultService;


    // 입력 (장고에서 처리함)
//    @PostMapping("/insert")
//    public void insertFaceResult(FaceresultDto faceresultDto){
//        faceresultService.insertFaceResult(faceresultDto);
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
//    @GetMapping("/lastResultId") //post되면 삭제 예정
//    public int selectIdOfMyLastFaceResult(@RequestParam int memberNum){
//        return faceresultService.selectIdOfMyLastFaceResult(memberNum);
//    }
    @PostMapping("/lastResultId")
    public int selectIdOfMyLastFaceResultp(@RequestBody Map<String, Integer> requestMap){
        Integer memberNum = requestMap.get("memberNum");
        return faceresultService.selectIdOfMyLastFaceResult(memberNum);
    }
    @GetMapping("/detail")
    public FaceresultDto selectFaceResultDetail(@RequestParam int ar_id){
        return faceresultService.selectFaceResultDetail(ar_id);
    }

    // 삭제
    @PostMapping("/delete")
    public void deleteFaceResult(int resultId){
        faceresultService.deleteFaceResult(resultId);
    }

    // 피드백 업데이트
//    @PostMapping("/feedback/bad")
    @PostMapping(value = "/feedback/bad")
    public void updateFeedbackBad(@RequestBody Map<String, Integer> requestMap){
        //Map<String, Integer>는 json 형태
        Integer ar_id = requestMap.get("ar_id");
        faceresultService.updateFeedbackBad(ar_id);
    }
    @PostMapping("/feedback/good")
    public void updateFeedbackGood(@RequestBody Map<String, Integer> requestMap){
        Integer ar_id = requestMap.get("ar_id");
        faceresultService.updateFeedbackGood(ar_id);
    }

}
