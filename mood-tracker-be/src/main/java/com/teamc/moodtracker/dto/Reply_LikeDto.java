package com.teamc.moodtracker.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("relvo")
public class Reply_LikeDto {
    private int relike_id;
    private int m_id; // 좋아요 누른 사람
    private int re_id; // 게시글/댓글 고유번호

    private int likeCount;

}
