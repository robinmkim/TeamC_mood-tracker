package com.teamc.moodtracker.dto.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthenticationRequest {

    private String username;

    private String password;
}
