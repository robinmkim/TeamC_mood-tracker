package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.Alert;
import com.teamc.moodtracker.dto.MemberDto;

import com.teamc.moodtracker.dto.chat.*;
import com.teamc.moodtracker.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/send")
    public void sendMessage(@Payload SaveChat dto) {
        ResponseMessage res = chatService.saveChatMessage(dto);
        messagingTemplate.convertAndSend("/topic/" + res.getRoomId(), res);

        // 알림
//        int room_id = dto.getRoomId();
//        // room_id로 조회하면 내아이디, 상대아이디가 나옴
//        List<Integer> rmlist = chatService.getMembersOfThisRoom(room_id);
//        // 그 중 내아이디=dto.getMemberId() 를 제외하면
//        // 알림을 받을 멤버 아이디 획득
//        int m_id_to = 0;
//        for (int i : rmlist){
//            if(i != dto.getMemberId()){
//                m_id_to = i;
//            }
//        }
//
//        Alert alert2 = Alert.builder()
//                .type("comment")
//                .m_id_to(m_id_to)             // 받는 사람
//                .m_id_from(dto.getMemberId()) // 보낸 사람
//                .m_content("TEST")
//                .build();
//        messagingTemplate.convertAndSend("/topic/notiChat/"+ m_id_to, alert2);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoom>> getChatRooms(@AuthenticationPrincipal MemberDto memberDto) {
        List<ChatRoom> chatRooms = chatService.getChatRooms(memberDto.getM_id());
        return ResponseEntity.ok(chatRooms);
    }

    @GetMapping("/rooms/{id}/messages")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable int id) {
        List<ChatMessage> chatMessages = chatService.getChatMessages(id);
        return ResponseEntity.ok(chatMessages);
    }

    // 새로운 채팅 시작 시 해당 유저와 이미 방이 존재하는지 확인
    @PostMapping("/rooms/check")
    public ResponseEntity<List<Integer>> checkChatRoom(@RequestBody CheckChat checkChat) {
        List<Integer> checkRes = chatService.checkChatRoom(checkChat);
        return ResponseEntity.ok(checkRes);
    }

    @PostMapping("/rooms/new")
    public ResponseEntity<ResponseRoom> newChatRoom(@RequestBody CheckChat checkChat) {
        ResponseRoom newRoomData = chatService.defaultChatRoom(checkChat);
        return ResponseEntity.ok(newRoomData);
    }


}
