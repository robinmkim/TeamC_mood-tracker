package com.teamc.moodtracker.service.JH;

import com.teamc.moodtracker.dto.JH.JH_CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JH_CommentService {
    public int commentCount(int b_id) ;

    public int replyCount(int cm_id) ;

    public List<JH_CommentDto> getCommentList(int b_id) ;
    public List<JH_CommentDto> getCommentListDetail(int b_id) ;

    public int addComment(JH_CommentDto dto);

    public void delComment(int cm_id);


}
