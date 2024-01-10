package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dto.FaceresultDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FaceresultService {
    public void insertFaceResult(FaceresultDto faceresultDto);
    public List<FaceresultDto> selectList();
    public List<FaceresultDto> selectMyList(int memberNum);
    public int selectIdOfMyLastFaceResult(int memberNum);
    public FaceresultDto selectFaceResultDetail(int ar_id);
    public void deleteFaceResult(int resultId);


//    // 입력
//    public void createFaceResult(FaceresultDto faceresultDto);
//
//    // 조회
//    public List<FaceresultDto> selectList();
//    public List<FaceresultDto> selectMyList(String memdberId);
//
//    // 삭제
//    public void deleteFaceResult(int resultId);

}
