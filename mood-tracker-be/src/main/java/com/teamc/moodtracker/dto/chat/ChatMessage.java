package com.teamc.moodtracker.dto.chat;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 채팅방 입장 시 기존 메시지를 불러오기 위한 DTO
public class ChatMessage {
    private Long memberId;
    private String message;
    private String sendTime;
}
