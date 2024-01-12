package com.teamc.moodtracker.dto;

import lombok.Getter;

@Getter
public class NotificationDto {
    private int n_id; // 알림 Id
    private int n_user; // 알람 받는 유저
    private int n_state; //알림_상태 [ 0=읽음, 1=안읽음 ]
    private String n_date; // 알림_날짜
    private String n_type; // 알림_종류 [follow/comment/like]
    private String n_user_from; // 알림 보낸 유저
    private String n_content; // 알림_내용
    private String n_url; // 알림_url


//    n_id NUMBER NOT NULL, /* 알림_id */
//    n_user NUMBER, /* 알림_받는유저 */
//    n_state NUMBER, /* 알림_상태 [ 0=읽음, 1=안읽음 ] */
//    n_date DATE, /* 알림_날짜 */
//    n_type  VARCHAR2(100), /* 알림_종류 [follow/comment/like] */
//    n_content VARCHAR2(100), /* 알림_내용 */
//    n_url VARCHAR2(200) /* 알림_url */


//    private int notiId;      //알림 고유번호
//    private String memberId;
//    private String readstate; // 읽음/안읽음 상태
//    private String regdate;
//    private String content; // 내용
//    private String url ; // 이동할 url

}
