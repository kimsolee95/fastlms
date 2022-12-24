package com.zerobase.fastlms.admin.mapper;

import com.zerobase.fastlms.admin.dto.BannerDto;

import com.zerobase.fastlms.admin.model.CommonParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BannerMapper {

    long selectListCount();

    List<BannerDto> selectBannerList(CommonParam parameter);


}
