package com.teamc.moodtracker.dto.chat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 채팅방 목록을 불러오기 위한 DTO
public class ChatRoom {
    private Long roomId;
    private Long memberId;
    private String message;
}
