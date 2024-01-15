package com.teamc.moodtracker.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("mdto")
@Getter
@Setter
public class MediaDto {
    private int md_id;
    private String md_name;
    private String md_path;
    private String md_type;
    private int b_id;
    private String regdate;
}


