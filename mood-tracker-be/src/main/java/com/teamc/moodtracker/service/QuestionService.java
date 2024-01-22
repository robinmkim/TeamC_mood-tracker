package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.QuestionDao;
import com.teamc.moodtracker.dto.QuestionDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService {
    @Autowired
    private QuestionDao dao;

    @Transactional
    public void addQuestionContent(QuestionDto dto) {
        dao.addQuestion(dto);

    }

}
