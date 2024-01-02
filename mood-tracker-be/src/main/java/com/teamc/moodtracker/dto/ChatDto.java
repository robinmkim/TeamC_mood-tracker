package com.teamc.moodtracker.dto;

import lombok.Getter;

@Getter
public class ChatDto {
    private int chatid;
    private String groupid; // 채팅방 고유번호
    private String content;
    private String sender;
    private String regdate;
}
