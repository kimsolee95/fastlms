package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.model.CourseInput;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AdminCourseController {

    private final CourseService courseService;

    @GetMapping("/admin/course/list.do")
    public String list(Model model) {



        return "admin/course/list";
    }

    @GetMapping("/admin/course/add.do")
    public String add(Model model) {

        return "admin/course/add";
    }


    @PostMapping("/admin/course/add.do")
    public String addSubmit(Model model, CourseInput parameter) {

        boolean result = courseService.add(parameter);

        return "redirect:/admin/course/list.do";
    }


}
