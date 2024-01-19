package com.teamc.moodtracker.dao;

import java.util.List;

import com.teamc.moodtracker.dto.chat.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatDao {

    public List<ChatRoom> getChatRooms(int memberId);

    public List<ChatRoom> getMemberInfo(int memberId);

    public List<ChatMessage> getChatMessages(int roomId, int memberId);

    public void saveChatMessage(SaveChat saveChat);

    public Integer findExistingChatRoom(EnterDto memberId);

    public int newChatRoom();

    public void exitChatRoom(int memberId, int roomId);

    public int checkMemberInChatRoom(int memberId, int roomId);

    public String getLastMsgStatus(int roomId, int memberId);
}
