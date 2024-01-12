package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dto.JH_CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JH_CommentService {
    public int commentCount(int cm_bid) ;

    public int replyCount(int re_cmid) ;

    public List<JH_CommentDto> getCommentList(int cm_bid) ;
    public List<JH_CommentDto> getCommentListDetail(int cm_bid) ;


}
