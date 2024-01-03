package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.ChatDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatDao {
    public List<ChatDto> selectList();
    public void sendChat(ChatDto chatDto);
}
