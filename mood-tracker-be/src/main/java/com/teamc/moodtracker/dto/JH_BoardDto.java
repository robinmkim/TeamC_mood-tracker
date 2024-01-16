package com.teamc.moodtracker.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Alias("jh_bvo")
@Getter
@Setter
public class JH_BoardDto {
    private int b_id;
    private int m_id;
    private String b_content ;
    private String b_sentiment;
    private String regdate;
    private List<MultipartFile> mediaList;
    private JH_MemberDto member;
    private int countLike;
    private int countComments;
    private boolean isMyLike;
    public void setIsMyLike(boolean isMyLike) {
        this.isMyLike = isMyLike;
    }
}