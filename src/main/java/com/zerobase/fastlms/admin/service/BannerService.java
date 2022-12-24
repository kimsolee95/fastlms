package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.CommonParam;

import java.util.List;

public interface BannerService {


    /* 배너 등록 */
    boolean add(BannerInput parameter);

    List<BannerDto> selectBannerList(CommonParam parameter);

    long selectListCount();

}
