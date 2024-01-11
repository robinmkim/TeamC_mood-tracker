package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.chat.ChatMessage;
import com.teamc.moodtracker.dto.chat.ChatRoom;
import com.teamc.moodtracker.dto.chat.SaveChat;
import com.teamc.moodtracker.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/send")
    public void sendMessage(@Payload SaveChat dto) {
        SaveChat res = chatService.saveChatMessage(dto);
        messagingTemplate.convertAndSend("/topic/" + res.getRoomId(), res);
    }

    @GetMapping("/rooms/{memberId}")
    public ResponseEntity<List<ChatRoom>> getChatRooms(@PathVariable Long memberId) {
        // 이후 토큰에서 유저 정보를 얻어와 memberId를 통해 조회하는 방식으로 변경
        // @RequestHeader("Authorization") String token
        List<ChatRoom> chatRooms = chatService.getChatRooms(memberId);
        return ResponseEntity.ok(chatRooms);
    }

    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable Long roomId) {
        List<ChatMessage> chatMessages = chatService.getChatMessages(roomId);
        return ResponseEntity.ok(chatMessages);
    }

    // 새로운 채팅 시작
}
