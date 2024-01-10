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


    @Override
    public void insertFaceResult(FaceresultDto faceresultDto) {
        faceresultDao.insertFaceResult(faceresultDto);
    }

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

    @Override
    public void deleteFaceResult(int resultId) {
        faceresultDao.deleteFaceResult(resultId);
    }
}
