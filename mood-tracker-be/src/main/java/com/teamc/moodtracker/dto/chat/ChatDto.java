package com.teamc.moodtracker.dto.chat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private int id;
    private int memberId;
    private String message;
    private LocalDateTime sendTime;

    public ChatDto(int id, int memberId, String message) {
        this.id = id;
        this.memberId = memberId;
        this.message = message;
        this.sendTime = LocalDateTime.now();
    }
}
