package com.teamc.moodtracker.dto;

import lombok.Getter;

@Getter
public class ReCommentDto { // 대댓글 테이블
    private int recommentId; // 대댓글 고유번호
    private String commentId; // 댓글 고유번호
    private String writer;
    private  String content;
    private String regdate;
}
