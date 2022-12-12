package com.zerobase.fastlms.main;

import com.zerobase.fastlms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MailComponents mailComponents;


    @RequestMapping("/")
    public String index() {

//        String email = "ahhasolee@naver.com";
//        String subject = "제로베이스 mail send test";
//        String text = "<p>안녕하세요</p><p>test입니다.</p>";
//
//        mailComponents.sendMail(email, subject, text);

        return "index";
    }

    @RequestMapping("/error/denied")
    public String errorDenied() {

        return "error/denied";
    }

}
