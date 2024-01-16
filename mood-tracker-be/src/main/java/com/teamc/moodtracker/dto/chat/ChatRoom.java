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
    private int roomId;
    private int otherMemberId;
    private String otherMemberName;
    private String message;
}
