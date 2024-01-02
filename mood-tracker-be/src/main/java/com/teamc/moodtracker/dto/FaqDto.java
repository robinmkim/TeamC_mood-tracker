package com.teamc.moodtracker.dto;

import lombok.Getter;

@Getter
public class FaqDto {
    private int faqno;
    private String title;
    private String content;
    private String regdate;  // 작성일
    private String modidate; // 수정일
    private int vcount; // 조회수
}
