package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.CommonParam;

import java.util.List;

public interface BannerService {


    /* 배너 등록 */
    boolean add(BannerInput parameter);

    /* 배너 수정 */
    boolean set(BannerInput parameter);

    /* 배너 삭제 */
    boolean del(String idList);

    /* 배너 리스트 조회 */
    List<BannerDto> selectBannerList(CommonParam parameter);

    /* 배너 리스트 총 개수 */
    long selectListCount();

    /* 배너 상세정보 - 관리자 */
    BannerDto getById(long id);

}
