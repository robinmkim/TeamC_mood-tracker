package com.teamc.moodtracker.dto;

import lombok.Getter;

@Getter
public class ReportDto { //신고
    private int reportId;
    private String url;
    private String content; //신고내용
    private String type; //신고종류
    private String regdate; // 신고일

}
