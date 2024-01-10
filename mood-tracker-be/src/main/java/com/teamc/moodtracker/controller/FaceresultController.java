package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.FaceresultDto;
import com.teamc.moodtracker.service.FaceresultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/faceresult")
public class FaceresultController {

    private final FaceresultService faceresultService;

    @PostMapping("/insert")
    public void insertFaceResult(FaceresultDto faceresultDto){
        faceresultService.insertFaceResult(faceresultDto);
    }

    @GetMapping("/list") // 관리자 : 모든 얼굴분석결과 리스트 조회 ( 추후 삭제 가능 )
    public List<FaceresultDto> selectList(){
        return faceresultService.selectList();
    }

    @GetMapping("/mylist") // 본인의 얼굴분석결과 리스트 조회
    public List<FaceresultDto> selectMyList(int memberNum){
        return faceresultService.selectMyList(memberNum);
    }

    @GetMapping("/lastResultId")
    public int selectIdOfMyLastFaceResult(@RequestParam int memberNum){
        return faceresultService.selectIdOfMyLastFaceResult(memberNum);
    }
    @GetMapping("/detail")
    public FaceresultDto selectFaceResultDetail(@RequestParam int ar_id){
        return faceresultService.selectFaceResultDetail(ar_id);
    }

    @GetMapping("/delete")
    public void deleteFaceResult(int resultId){
        faceresultService.deleteFaceResult(resultId);
    }

}
