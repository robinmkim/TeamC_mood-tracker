package com.teamc.moodtracker.controller;

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
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

//    @MessageMapping("/chat/send")
//    public void sendMessage(@Payload SendChat dto) {
//        String getLastMsgStatus = chatService.getLastMsgStatus(dto.getRoomId(), dto.getMemberId());
//        CheckChat checkChat = CheckChat.builder()
//                .userId1(dto.getMemberId())
//                .userId2(dto.getOtherMemberId())
//                .build();
//
//        // 상대방이 채팅방을 나갔을 경우 기존 채팅방에 상대방을 다시 추가
//        if (getLastMsgStatus == "LEFT") {
//            chatService.createChatRoom(checkChat, dto.getRoomId());
//        }
//
//        SaveChat saveChat = SaveChat.builder()
//                .roomId(dto.getRoomId())
//                .memberId(dto.getMemberId())
//                .message(dto.getMessage())
//                .build();
//        ResponseMessage res = chatService.saveChatMessage(saveChat);
//        messagingTemplate.convertAndSend("/topic/" + res.getRoomId(), res);
//    }

    @PostMapping("/send")
    public void sendChat(@RequestBody SendChat sendRequest) {
        // 상대방이 나갔는지 확인
        String getLastMsgStatus = chatService.getLastMsgStatus(sendRequest.getRoomId(), sendRequest.getOtherMemberId());
        System.out.println("getLastaaaaaaaaaaaMsgStatus: " + getLastMsgStatus);
        EnterDto enterDto = EnterDto.builder()
                .myMemberId(sendRequest.getOtherMemberId())
                .otherMemberId(sendRequest.getMemberId())
                .build();

        // 상대방이 채팅방을 나갔을 경우 기존 채팅방에 상대방을 다시 추가
        if (getLastMsgStatus.equals("LEFT")) {
            System.out.println("추가되야지?");
            chatService.joinExistingChatRoom(enterDto, sendRequest.getRoomId());
            System.out.println("추가됬나?");
        }
        System.out.println("지나갔어");

        SaveChat saveChat = SaveChat.builder()
                .roomId(sendRequest.getRoomId())
                .memberId(sendRequest.getMemberId())
                .message(sendRequest.getMessage())
                .build();
        ResponseMessage res = chatService.saveChatMessage(saveChat);
        messagingTemplate.convertAndSend("/topic/chat/" + sendRequest.getMemberId(), res);
        messagingTemplate.convertAndSend("/topic/chat/" + sendRequest.getOtherMemberId(), res);
    }
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoom>> getChatRooms(@AuthenticationPrincipal MemberDto memberDto) {
        List<ChatRoom> chatRooms = chatService.getChatRooms(memberDto.getM_id());
        return ResponseEntity.ok(chatRooms);
    }

    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@AuthenticationPrincipal MemberDto memberDto, @PathVariable int roomId) {
        List<ChatMessage> chatMessages = chatService.getChatMessages(memberDto.getM_id(), roomId);
        System.out.println("roomId: " + roomId);
        System.out.println("memberId: " + memberDto.getM_id());
        System.out.println("chatMessages: " + chatMessages.size());
        return ResponseEntity.ok(chatMessages);
    }

    // 새로운 채팅 시작 시 해당 유저와 이전에 채팅했던 적이 있는지 확인
    @PostMapping("/rooms/newRoom")
    public ResponseEntity<ResponseRoom> checkChatRoom(@RequestBody EnterDto memberIds) {
        Integer foundRoomId = chatService.findExistingChatRoom(memberIds);
        if (foundRoomId == null) {
            System.out.println("새로운 채팅방 생성");
            int newRoomId = chatService.newChatRoomId();
            ResponseRoom newRoomData = chatService.createChatRoom(memberIds, newRoomId);
            return ResponseEntity.ok(newRoomData);
        } else {
            // 존재하던 채팅방 번호에 대해 STATUS=JOIN인 데이터 추가
            System.out.println("기존 채팅방에 추가");
            ResponseRoom existingRoomData = chatService.joinExistingChatRoom(memberIds, foundRoomId);
            return ResponseEntity.ok(existingRoomData);
        }
    }

//    @PostMapping("/rooms/new")
//    public ResponseEntity<ResponseRoom> newChatRoom(@RequestBody CheckChat checkChat) {
//        int newRoomId = chatService.newChatRoomId();
//        ResponseRoom newRoomData = chatService.createChatRoom(checkChat, newRoomId);
//        return ResponseEntity.ok(newRoomData);
//    }

    @PostMapping("/rooms/exit")
    public ResponseEntity<Integer> deleteChatRoom(@AuthenticationPrincipal MemberDto memberDto, @RequestBody Map<String, Integer> requestBody) {
        int roomId = requestBody.get("roomId");
        int memberId = memberDto.getM_id();
        chatService.exitChatRoom(memberId, roomId);
        return ResponseEntity.ok(roomId);
    }
}
