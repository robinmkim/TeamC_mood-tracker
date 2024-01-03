package com.teamc.moodtracker.dto;

import lombok.Getter;

@Getter
public class NotificationDto {
    private int notiId;      //알림 고유번호
    private String memberId;
    private String readstate; // 읽음/안읽음 상태
    private String regdate;
    private String content; // 내용
    private String url ; // 이동할 url

}
