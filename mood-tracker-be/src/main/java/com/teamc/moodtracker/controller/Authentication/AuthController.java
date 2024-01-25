package com.teamc.moodtracker.controller.Authentication;

import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.service.MemberService;
import com.teamc.moodtracker.util.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.teamc.moodtracker.dto.auth.AuthenticationRequest;
import com.teamc.moodtracker.dto.auth.AuthenticationResponse;
import com.teamc.moodtracker.util.JwtTokenProvider;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
//CORS (Cross-Origin Resource Sharing) 요청을 허용함, 다른 도메인/포트에서 이 서비스에 접근할 수 있게 해준다.
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    MemberService service;

    @Autowired
    PasswordEncoder passwordEncoder;

    //authenticateUser
    //사용자가 제공한 로그인 정보(username, password)를 검증하고, 유효하면 JWT 토큰을 생성하여 반환
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
            @RequestBody AuthenticationRequest authenticationRequest) {
        System.out.println("authenticationRequest =>"+authenticationRequest.toString());
        //  UsernamePasswordAuthenticationToken을 생성해서 AuthenticationManager에 전달
        Authentication authentication = authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword())
        );
        // 인증 성공 시, SecurityContextHolder에 인증 정보를 설정한다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // JwtTokenProvider를 사용하여 JWT 토큰을 생성
        String jwt = jwtTokenProvider.createToken(authentication);
        System.out.print("jwt =>"+jwt);
        // AuthenticationResponse에 JWT 토큰을 담아 클라이언트에게 반환
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
    //사용자 로그아웃 처리
    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        // 실제로 JWT 토큰 기반 인증에서 로그아웃은 클라이언트 측에서 토큰을 삭제하는 것으로 처리되지만,
        // 서버 측에서는 SecurityContextHolder의 컨텍스트를 클리어 해야 한다.
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successful");
    }

    //http://localhost/springjwt1/api/auth/test
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("접근이 됨을 확인합니다.");
    }

    String imageDirectory = "src/main/resources/static/images/";
    @PostMapping("/signUp")
    public int signUpTest(@ModelAttribute MemberDto dto,
                          @RequestParam(value = "m_profile", required = false) MultipartFile m_profile) {


        if(m_profile != null) {
            dto.setM_img_name(m_profile.getOriginalFilename());
            dto.setM_img_path("images/");
            String filePath = imageDirectory + m_profile.getOriginalFilename();
            System.out.println(filePath);
            FileUpload.uploadFile(m_profile, filePath);
        }

        dto.setM_pwd(passwordEncoder.encode(dto.getM_pwd()));
        System.out.println(dto.toString());
        service.addMember(dto);
        return 1; // 예시: 성공 시 1 반환
    }
    
    @PostMapping("/checkHandle")
    public int checkHandle(@RequestBody Map<String, String> map){
        System.out.println("handle =>"+map.get("handle"));
        System.out.println("checkHandleExist =>"+service.checkHandleExist(map.get("handle")));
        if(service.checkHandleExist(map.get("handle")) == 1){

            throw new NullPointerException("이미 존재하는 이름입니다.");
        }
        return service.checkHandleExist(map.get("handle"));
    }
}
