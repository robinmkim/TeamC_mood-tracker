package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.ChatDao;
import com.teamc.moodtracker.dto.chat.ChatDto;
import com.teamc.moodtracker.dto.chat.ChatMessage;
import com.teamc.moodtracker.dto.chat.ChatRoom;
import com.teamc.moodtracker.dto.chat.SaveChat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatDao chatDao;

    public List<ChatRoom> getChatRooms(Long memberId) {
        List<ChatRoom> chatRooms = chatDao.getChatRooms(memberId);
        return chatRooms;
    }

    public List<ChatMessage> getChatMessages(Long roomId) {
        List<ChatMessage> chatMessages = chatDao.getChatMessages(roomId);
        return chatMessages;
    }

    public SaveChat saveChatMessage(SaveChat saveChat) {
        saveChat.setSendTime(LocalDateTime.now());
        chatDao.saveChatMessage(saveChat);
        return saveChat;
    }
}
