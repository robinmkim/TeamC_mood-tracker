package com.teamc.moodtracker.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("cmlvo")
public class Comment_LikeDto {
    private int cmlike_id;
    private int m_id; // 좋아요 누른 사람
    private int cm_id; // 게시글/댓글 고유번호

}
