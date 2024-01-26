package com.teamc.moodtracker.controller.Authentication;

import com.teamc.moodtracker.dto.email.EmailCheckDto;
import com.teamc.moodtracker.dto.email.EmailRequestDto;
import com.teamc.moodtracker.service.EmailVerifyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
//CORS (Cross-Origin Resource Sharing) 요청을 허용함, 다른 도메인/포트에서 이 서비스에 접근할 수 있게 해준다.
@CrossOrigin
@RequestMapping("/auth")
@RequiredArgsConstructor
public class EmailVerifyController {

    private final EmailVerifyService emailVerifyService;

    @PostMapping ("/sendCode")
    public String mailSend(@RequestBody @Valid EmailRequestDto emailDto){
        if(emailVerifyService.checkEmailExists(emailDto.getEmail()) == 1){
            throw new NullPointerException("이미 가입된 이메일입니다.");
        }
        System.out.println("이메일 인증 요청이 들어옴");
        System.out.println("이메일 인증 이메일 :"+emailDto.getEmail());
        return emailVerifyService.joinEmail(emailDto.getEmail());
    }
    @PostMapping("/verifyCode")
    public String AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto){
        Boolean Checked=emailVerifyService.CheckAuthNum(emailCheckDto.getEmail(),emailCheckDto.getCode());
        if(Checked){
            return "ok";
        }
        else{
            throw new NullPointerException("뭔가 잘못!");
        }
    }
}