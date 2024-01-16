package com.teamc.moodtracker.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("jh_rvo")
@Getter
@Setter
public class JH_ReplyDto { // 대댓글 테이블
    private int re_id; // 대댓글 고유번호
    private int cm_id; // 댓글 고유번호
    private int m_id;
    private  String re_content;
    private String regdate;
    private JH_MemberDto member;


}
