package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.ChatDao;
import com.teamc.moodtracker.dao.MemberDao;
import com.teamc.moodtracker.dto.chat.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatDao chatDao;
    private final MemberDao memberDao;

    public List<ChatRoom> getChatRooms(int memberId) {
        List<ChatRoom> chatRooms = chatDao.getChatRooms(memberId);
        List<ChatRoom> chatMembers = chatDao.getMemberInfo(memberId);

        for(int i=0; i<chatRooms.size(); i++) {
            chatRooms.get(i).setOtherMemberName(chatMembers.get(i).getOtherMemberName());
            chatRooms.get(i).setOtherMemberId(chatMembers.get(i).getOtherMemberId());
        }
        return chatRooms;
    }

    public List<ChatMessage> getChatMessages(int id) {
        List<ChatMessage> chatMessages = chatDao.getChatMessages(id);
        return chatMessages;
    }

    public ResponseMessage saveChatMessage(SaveChat saveChat) {
        chatDao.saveChatMessage(saveChat);
        ResponseMessage responseMessage = ResponseMessage.builder()
                .roomId(saveChat.getRoomId())
                .memberId(saveChat.getMemberId())
                .message(saveChat.getMessage())
                .sendTime(LocalDateTime.now())
                .build();
        return responseMessage;
    }

    public List<Integer> checkChatRoom(CheckChat memberId) {
        List<Integer> checkRes = chatDao.checkChatRoom(memberId);
        if (checkRes == null) {
            return Collections.singletonList(-1);
        }
        return checkRes;
    }

    @Transactional
    public ResponseRoom defaultChatRoom(CheckChat checkChat) {
        int newRoomId = chatDao.newChatRoom();
        SaveChat user1 = SaveChat.builder()
                .roomId(newRoomId)
                .memberId(checkChat.getUserId1())
                .message("")
                .build();
        SaveChat user2 = SaveChat.builder()
                .roomId(newRoomId)
                .memberId(checkChat.getUserId2())
                .message("")
                .build();
        chatDao.saveChatMessage(user1);
        chatDao.saveChatMessage(user2);
        String newMemberName = memberDao.getMemberName(checkChat.getUserId2());
        ResponseRoom responseRoom = ResponseRoom.builder()
                .roomId(newRoomId)
                .newMemberId(checkChat.getUserId2())
                .newMemberName(newMemberName)
                .build();
        return responseRoom;
    }
}
