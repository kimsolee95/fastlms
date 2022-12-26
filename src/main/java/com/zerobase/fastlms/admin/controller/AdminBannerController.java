package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.CommonParam;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.course.controller.BaseController;
import com.zerobase.fastlms.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminBannerController extends BaseController {

    private final BannerService bannerService;

    @GetMapping("/admin/banner/list.do")
    public String list(Model model, CommonParam parameter) {

        parameter.init();

        List<BannerDto> bannerList = bannerService.selectBannerList(parameter);
        long totalCount = bannerService.selectListCount();

        String queryString = parameter.getQueryString();
        String pageHtml = super.getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("bannerList", bannerList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pageHtml);

        return "admin/banner/list";
    }


    @GetMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String add(Model model
                    , HttpServletRequest request
                    , BannerInput parameter) {

        boolean editMode = request.getRequestURI().contains("/edit.do");

        BannerDto detail = new BannerDto();

        if (editMode) {

            long id = parameter.getId();
            BannerDto existBanner = bannerService.getById(id);

            if (existBanner == null) {
                //error
                model.addAttribute("message", "배너 정보가 존재하지 않습니다.");
                return "common/error";
            }

            detail = existBanner;
        }

        model.addAttribute("detail", detail);
        model.addAttribute("editMode", editMode);

        return "admin/banner/add";
    }

    @PostMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String addSubmit(HttpServletRequest request
                            , Model model
                            , BannerInput parameter
                            , MultipartFile file) {


        String saveFilename = ""; //local path
        String urlFilename = ""; // url path

        if (file != null) {

            String originalFilename = file.getOriginalFilename();

            String baseLocalPath =
                    System.getProperty("user.dir") + File.separator
                            + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "img" + File.separator + "banner-img";
            String baseUrlPath = File.separator + "img" + File.separator + "banner-img";

            String[] arrFilename = FileUploadUtil.getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);

            saveFilename = arrFilename[0];
            urlFilename = arrFilename[1];

            try {

                File newFile = new File(saveFilename);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
        parameter.setFilename(saveFilename);
        parameter.setUrlFilename(urlFilename);

        boolean editMode = request.getRequestURI().contains("/edit.do");

        if (editMode) {

            long id = parameter.getId();
            BannerDto existBanner = bannerService.getById(id);

            if (existBanner == null) {
                //error
                model.addAttribute("message", "배너 정보가 존재하지 않습니다.");
                return "common/error";
            }

            boolean result = bannerService.set(parameter);

        } else {

            boolean result = bannerService.add(parameter);
        }

        return "redirect:/admin/banner/list.do";
    }

    @PostMapping("/admin/banner/delete.do")
    public String del (HttpServletRequest request
                        , Model model
                        , BannerInput parameter) {


        boolean result = bannerService.del(parameter.getIdList());
        return "redirect:/admin/banner/list.do";
    }

}
