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

}
