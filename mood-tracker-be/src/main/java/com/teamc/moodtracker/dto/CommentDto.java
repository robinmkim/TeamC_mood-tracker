package com.teamc.moodtracker.dto;

import lombok.Getter;

@Getter
public class CommentDto {
    private int commentId;
    private int boardId;
    private String writer;
    private  String content;
    private String regdate;
}
