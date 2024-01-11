package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.ChatDao;
import com.teamc.moodtracker.dao.FaceresultDao;
import com.teamc.moodtracker.dto.FaceresultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FaceresultServiceImpl implements FaceresultService{

    @Autowired
    private FaceresultDao faceresultDao;

    // 입력
//    @Override
//    public void insertFaceResult(FaceresultDto faceresultDto) {
//        faceresultDao.insertFaceResult(faceresultDto);
//    }

    // 조회
    @Override
    public List<FaceresultDto> selectList() {
        return faceresultDao.selectList();
    }
    @Override
    public List<FaceresultDto> selectMyList(int memberNum) {
        return faceresultDao.selectMyList(memberNum);
    }
    @Override
    public int selectIdOfMyLastFaceResult(int memberNum) {
        return faceresultDao.selectIdOfMyLastFaceResult(memberNum);
    }
    @Override
    public FaceresultDto selectFaceResultDetail(int ar_id) {
        return faceresultDao.selectFaceResultDetail(ar_id);
    }

    // 삭제
    @Override
    public void deleteFaceResult(int resultId) {
        faceresultDao.deleteFaceResult(resultId);
    }

    // 피드백 업데이트
    @Override
    public void updateFeedbackBad(int ar_id) {
        faceresultDao.updateFeedbackBad(ar_id);
    }
    @Override
    public void updateFeedbackGood(int ar_id) {
        faceresultDao.updateFeedbackGood(ar_id);
    }
}
