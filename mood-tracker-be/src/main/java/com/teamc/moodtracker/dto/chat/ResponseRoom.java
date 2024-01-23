package com.teamc.moodtracker.dto.chat;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 새로운 채팅방 생성 시 응답을 위한 DTO
public class ResponseRoom {
    private int roomId;
    private int newMemberId;
    private String newMemberName;
}
