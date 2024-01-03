package com.teamc.moodtracker.dto;

import lombok.Getter;

@Getter
public class PostDto {
    private int postId;
    private String memberId ;
    private String content;
    private String media;
    private String emotion; // 기분
    private String regdate;
}
