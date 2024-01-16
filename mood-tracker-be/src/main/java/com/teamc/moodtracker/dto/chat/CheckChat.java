package com.teamc.moodtracker.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
// 채팅방이 존재하는지 확인하기 위한 DTO
public class CheckChat {
    private int userId1;
    private int userId2;
}
