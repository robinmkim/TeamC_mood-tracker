package com.teamc.moodtracker.dao;

import org.apache.ibatis.annotations.Mapper;

import com.teamc.moodtracker.dto.QuestionDto;

@Mapper
public interface QuestionDao {
    public void addQuestion(QuestionDto qvo);

}