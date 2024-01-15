package com.teamc.moodtracker.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@Alias("mvo")
@Getter
@Setter
@ToString
public class MemberDto implements UserDetails {
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
    private String m_role = "ROLE_USER";
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // role.name() 권한을 가져와서 , 스프링 시큐리티 타입의 Authority로 변환 해준다.
        // ADMIN -> 스프링에서 이미 설정되어 있는 ROLE의 이름으로 변경해서 돌려준다. ROLE_ADMIN
        return List.of(new SimpleGrantedAuthority(m_role));
    }

    @Override
    public String getPassword() {
        return this.m_pwd; // 사용자의 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return this.m_email; // 사용자 식별자(여기서는 이메일) 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않았음을 나타냄
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠겨 있지 않음을 나타냄
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 인증 정보(비밀번호)가 만료되지 않았음을 나타냄
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정이 활성화되어 있음을 나타냄
    }

}
