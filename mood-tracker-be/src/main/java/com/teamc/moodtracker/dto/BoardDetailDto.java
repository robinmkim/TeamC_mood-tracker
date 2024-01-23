package com.teamc.moodtracker.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter
@Setter
@Alias("bdvo")
public class BoardDetailDto {
    private int b_id;
    private int m_id;
    private String b_content ;
    private String b_sentiment;
    private String regdate;
    private List<MultipartFile> mediaList;
    private MemberDto member;
    private int countLike;
    private int countComments;
    private boolean isMyLike;
    public void setIsMyLike(boolean isMyLike) {
        this.isMyLike = isMyLike;
    }
}
