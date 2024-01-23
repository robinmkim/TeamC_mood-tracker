package com.teamc.moodtracker.dto.chat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 채팅 전송 시 DB에 저장하기 위한 DTO
public class SaveChat {
    private int roomId;
    private int memberId;
    private String message;
    private String status;
}
