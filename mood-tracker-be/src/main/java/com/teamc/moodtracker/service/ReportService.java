package com.teamc.moodtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamc.moodtracker.dao.ReportDao;
import com.teamc.moodtracker.dto.ReportDto;

import jakarta.transaction.Transactional;

@Service
public class ReportService {
    @Autowired
    private ReportDao reportDao;

    // 게시글
    public List<ReportDto> reportList() {
        return reportDao.reportList();
    };

    public ReportDto reportDetail(int report_id) {
        return reportDao.reportDetail(report_id);
    }

    // 댓글
    public List<ReportDto> reportCommentList() {
        return reportDao.reportCommentList();
    }

    public ReportDto reportCommentDetail(int comment_id) {
        return reportDao.reportCommentDetail(comment_id);
    }

    @Transactional
    public void addReport(ReportDto dto) {
        reportDao.addReport(dto);
    }
    // public List<Integer> reportList(int lastRowNum) {
    // Map<String, Object> params = new HashMap<>();
    // params.put("lastRowNum", lastRowNum);
    // return ReportDao.reportList(params);
    // }

}
