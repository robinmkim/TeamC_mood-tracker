package com.teamc.moodtracker.dto.JH;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;
@Alias("jh_mvo")
@Getter
@Setter
@ToString
public class JH_MemberDto {
    private int m_id;
    private String m_name;
    private String m_handle;
    private String m_email;
    private String m_pwd;
    private String m_bdate;
    private String regdate;
    private String m_gender;
    private String m_img_name;
    private String m_img_path;
    private String m_bio;
}
