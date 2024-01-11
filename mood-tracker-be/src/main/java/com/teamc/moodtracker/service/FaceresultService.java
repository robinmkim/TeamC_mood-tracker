package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dto.FaceresultDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FaceresultService {
    //입력
    public void insertFaceResult(FaceresultDto faceresultDto);

    //조회
    public List<FaceresultDto> selectList();
    public List<FaceresultDto> selectMyList(int memberNum);
    public int selectIdOfMyLastFaceResult(int memberNum);
    public FaceresultDto selectFaceResultDetail(int ar_id);

    //삭제
    public void deleteFaceResult(int resultId);

    // 피드백 업데이트
    public void updateFeedbackBad(int ar_id);
    public void updateFeedbackGood(int ar_id);





}
