package com.zerobase.fastlms.member.dto;

import lombok.Data;

@Data
public class FrontBannerDto {


    private Long id;

    /* 배너명 */
    String subject;

    /* 링크 url */
    String linkUrl;

    /* 정렬순서 */
    long sortOrder;

    /* 공개여부 */
    boolean usingYn;

    /* 오픈방법 */
    String openWay;

    /* img file - url path */
    String urlFilename;



}
