package com.teamc.moodtracker.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import java.util.Date;

@Alias("qvo")
@Getter
@Setter
public class QuestionDto {
    private int q_id;
    private String q_title;
    private String q_content;
    private Date regdate;
    private int q_hit;
    private int q_writer; // 사용자 아이디를 저장하는 변수

    // MemberDto에서 M_id 정보를 가져와서 q_writer에 설정하는 메서드
    public void setMemberDto(MemberDto memberDto) {
        this.q_writer = memberDto.getM_id();
    }
}
