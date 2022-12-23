package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.model.CourseInput;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminCourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping("/admin/course/list.do")
    public String list(Model model, CourseParam parameter) {

        parameter.init();

        List<CourseDto> courseList = courseService.list(parameter);

        long totalCount = 0;

        if (!CollectionUtils.isEmpty(courseList)) {
            totalCount = courseList.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();

        String pageHtml = super.getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", courseList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pageHtml);

        return "admin/course/list";
    }

    @GetMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String add(Model model, HttpServletRequest request
                    , CourseInput parameter) {

        boolean editMode = request.getRequestURI().contains("/edit.do");

        CourseDto detail = new CourseDto();

        if (editMode) {

            long id = parameter.getId();

            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null) {
                //error
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }

            detail = existCourse;
        }

        model.addAttribute("categoryList",  categoryService.list());
        model.addAttribute("detail", detail);
        model.addAttribute("editMode", editMode);
        return "admin/course/add";
    }


    /**
     * 파일명 생성
     * */
    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename) {

        LocalDate now = LocalDate.now();
        String separator = System.getProperty("file.separator"); //os 맞춤 구분자

        //local path
        //origin source
//        String[] dirs = {
//                        String.format("%s/%d", baseLocalPath, now.getYear())
//                        , String.format("%s/%d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue())
//                        , String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};
        //수정 (os에 맞게끔 구분자 통일)
        String[] dirs = {
                        String.format("%s%s%d", baseLocalPath, separator, now.getYear())
                        , String.format("%s%s%d%s%02d%s", baseLocalPath, separator, now.getYear(), separator,  now.getMonthValue(), separator)
                        , String.format("%s%s%d%s%02d%s%02d%s", baseLocalPath, separator, now.getYear(), separator, now.getMonthValue(), separator,  now.getDayOfMonth(), separator)};

        //url path
        //origin source
//        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        //수정 (os에 맞게끔 구분자 통일)
        String urlDir = String.format("%s%s%d%s%02d%s%02d%s", baseUrlPath, separator, now.getYear(), separator, now.getMonthValue(), separator, now.getDayOfMonth(), separator);


        for (String dir : dirs) {

            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";

        if (originalFilename != null) {

            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFilename = String.format("%s%s", dirs[2], uuid); //local path
        String newUrlFilename = String.format("%s%s", urlDir, uuid); //url path

        if (fileExtension.length() > 0) {
            newFilename += "." + fileExtension;
            newUrlFilename += "." + fileExtension;
        }

        String[] returnFilename = {newFilename, newUrlFilename};

        return returnFilename;
    }


    @PostMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String addSubmit(Model model
                    , HttpServletRequest request
                    , CourseInput parameter
                    , MultipartFile file) {

        String saveFileName = "";
        String urlFilename = "";

        if (file != null) {

            String originalFilename = file.getOriginalFilename();

            //2022.12.23
            /*
            이미지 출력 문제...
            community intelij로 웹 리소스 폴더 지정이 제대로 인식이 안되는 관계로
            우선 기본적인 src/main/resources/static 하위 폴더로 사용하여 파일 업로드 기능을 구현
            "/src/main/resources/static/img";   //"C:\\lms-study\\fastlms\\files\\";
            * */

            String baseLocalPath =
                    System.getProperty("user.dir") + File.separator
                            + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "img";
            String baseUrlPath = File.separator + "img";

            String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);

            saveFileName = arrFilename[0]; //local path
            urlFilename = arrFilename[1]; //url path

            try {

                File newFile = new File(saveFileName);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (Exception e) {
                log.info("===============error=====================");
                log.info(e.getMessage());
            }
        }
        parameter.setFilename(saveFileName);
        parameter.setUrlFilename(urlFilename);

        boolean editMode = request.getRequestURI().contains("/edit.do");

        if (editMode) {
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);

            if (existCourse == null) {
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }

            boolean result = courseService.set(parameter);
        } else {

            boolean result = courseService.add(parameter);
        }

        return "redirect:/admin/course/list.do";
    }


    @PostMapping("/admin/course/delete.do")
    public String del(Model model, HttpServletRequest request
            , CourseInput parameter) {


        boolean result = courseService.del(parameter.getIdList());

        return "redirect:/admin/course/list.do";
    }


}
