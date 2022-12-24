package com.zerobase.fastlms.admin.service.impl;

import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import com.zerobase.fastlms.admin.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;


    @Override
    public boolean add(BannerInput parameter) {

        Banner banner = Banner.builder()
                .subject(parameter.getSubject())
                .linkUrl(parameter.getLinkUrl())
                .sortOrder(parameter.getSortOrder())
                .usingYn(parameter.isUsingYn())
                .openWay(parameter.getOpenWay())
                .filename(parameter.getFilename())
                .urlFilename(parameter.getUrlFilename())
                .regDt(LocalDateTime.now())
                .build();
        bannerRepository.save(banner);
        return true;
    }
}
