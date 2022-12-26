package com.zerobase.fastlms.main;

import com.zerobase.fastlms.components.MailComponents;
import com.zerobase.fastlms.member.dto.FrontBannerDto;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MailComponents mailComponents;
    private final MemberService memberService;


    @RequestMapping("/")
    public String index(Model model) {

        List<FrontBannerDto> frontBannerList = memberService.selectFrontBannerList();

        model.addAttribute("frontBannerList", frontBannerList);
        return "index";
    }

    @RequestMapping("/error/denied")
    public String errorDenied() {

        return "error/denied";
    }

}
