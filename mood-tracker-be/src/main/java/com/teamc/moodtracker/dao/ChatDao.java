package com.teamc.moodtracker.dao;

import java.util.List;

import com.teamc.moodtracker.dto.chat.ChatDto;
import com.teamc.moodtracker.dto.chat.ChatMessage;
import com.teamc.moodtracker.dto.chat.SaveChat;
import org.apache.ibatis.annotations.Mapper;

import com.teamc.moodtracker.dto.chat.ChatRoom;

@Mapper
public interface ChatDao {

    public List<ChatRoom> getChatRooms(Long memberId);

    public List<ChatMessage> getChatMessages(Long roomId);

    public void saveChatMessage(SaveChat saveChat);
}
