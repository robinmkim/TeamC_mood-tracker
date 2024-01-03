package com.teamc.moodtracker.dto;

import lombok.Getter;

@Getter
public class MediaDto { //미디어 테이블
    private int postId;
    private String fileaddress; //파일주소
    private String type; // 이미지/비디오
}

