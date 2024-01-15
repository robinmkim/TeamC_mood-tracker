package com.teamc.moodtracker.dto;

import lombok.Getter;

@Getter
public class Board_LikeDto {
    private String liketype; // 게시글/댓글/대댓글 좋아요인지 구분
    private String memberid; // 좋아요 누른 사람
    private String targetno; // 게시글/댓글 고유번호
    private String regdate; // 좋아요 누른 시각

}
