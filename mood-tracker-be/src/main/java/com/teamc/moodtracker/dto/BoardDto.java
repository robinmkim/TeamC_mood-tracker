package com.teamc.moodtracker.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Alias("bvo")
@Getter
@Setter
public class BoardDto {
    private int b_id;
    private int m_id;
    private String b_content ;
    private String b_sentiment;
    private String regdate;
    private List<MultipartFile> mediaList;
}