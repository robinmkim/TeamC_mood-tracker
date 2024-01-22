package com.teamc.moodtracker.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.teamc.moodtracker.dto.ReportDto;

@Mapper
public interface ReportDao {

    public List<ReportDto> reportList(); // 신고 게시글 목록 조회

    public ReportDto reportDetail(int report_id);// 신고 게시글 상세 조회

    List<ReportDto> reportCommentList(); // 신고 댓글 목록 조회

    ReportDto reportCommentDetail(int comment_id); // 신고 댓글 상세 조회

    public void addReport(ReportDto dto); //
    // public List<Integer> reportList(Map<String, Object> params);
}
