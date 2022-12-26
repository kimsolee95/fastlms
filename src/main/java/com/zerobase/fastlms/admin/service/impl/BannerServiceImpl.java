package com.zerobase.fastlms.admin.service.impl;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.mapper.BannerMapper;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.CommonParam;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import com.zerobase.fastlms.admin.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;


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

    @Override
    public boolean set(BannerInput parameter) {

        Optional<Banner> optionalBanner = bannerRepository.findById(parameter.getId());
        if (!optionalBanner.isPresent()) {
            return false;
        }

        Banner banner = optionalBanner.get();
        banner.setSubject(parameter.getSubject());
        banner.setLinkUrl(parameter.getLinkUrl());
        banner.setSortOrder(parameter.getSortOrder());
        banner.setUsingYn(parameter.isUsingYn());
        banner.setOpenWay(parameter.getOpenWay());
        banner.setFilename(parameter.getFilename());
        banner.setUrlFilename(parameter.getUrlFilename());
        banner.setUdtDt(LocalDateTime.now());
        bannerRepository.save(banner);

        return true;
    }

    @Override
    public List<BannerDto> selectBannerList(CommonParam parameter) {

        List<BannerDto> bannerList = bannerMapper.selectBannerList(parameter);
        return bannerList;
    }

    @Override
    public long selectListCount() {
        return bannerMapper.selectListCount();
    }

    @Override
    public BannerDto getById(long id) {
        BannerDto existBanner = bannerRepository.findById(id).map(BannerDto::of).orElse(null);
        return existBanner;
    }
}
