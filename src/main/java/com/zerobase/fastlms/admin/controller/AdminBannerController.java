package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminBannerController {

    private final BannerService bannerService;

    @GetMapping("/admin/banner/add.do")
    public String add() {

        return "admin/banner/add";
    }

    @PostMapping("/admin/banner/add.do")
    public String addSubmit(HttpServletRequest request
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

        boolean result = bannerService.add(parameter);

        return "admin/banner/add";
    }

}
