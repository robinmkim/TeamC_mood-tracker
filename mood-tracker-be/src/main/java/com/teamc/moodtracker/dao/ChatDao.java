package com.teamc.moodtracker.dao;

import java.util.List;

import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.dto.chat.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatDao {

    public List<ChatRoom> getChatRooms(int memberId);

    public List<ChatRoom> getMemberInfo(int memberId);

    public List<ChatMessage> getChatMessages(int id);

    public void saveChatMessage(SaveChat saveChat);

    public List<Integer> checkChatRoom(CheckChat memberId);

    public int newChatRoom();

    List<Integer> getMembersOfThisRoom(int roomId); //윤영호
}
