package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.model.CourseInput;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping("/course")
    public String list(Model model, CourseParam parameter) {

        List<CourseDto> list = courseService.frontList(parameter);
        List<CategoryDto> categoryList = categoryService.frontList(CategoryDto.builder().build());

        int courseTotalCount = 0;

        if (categoryList != null) {
            for (CategoryDto x : categoryList) {
                courseTotalCount += x.getCourseCount();
            }
        }

        model.addAttribute("categoryList", categoryList); //강좌 카테고리 목록
        model.addAttribute("list", list); //강좌 정보
        model.addAttribute("courseTotalCount", courseTotalCount); //과정 총 개수
        return "course/index";
    }


    @GetMapping("/course/{id}")
    public String courseDetail(Model model, CourseParam parameter) {

        CourseDto detail = courseService.frontDetail(parameter.getId());
        model.addAttribute("detail", detail);
        return "course/detail";
    }


}
