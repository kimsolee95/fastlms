package com.zerobase.fastlms.admin.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BannerInput {

    /* 배너 ID */
    Long id;

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

    /* img file - local path */
    String filename;

    /* img file - url path */
    String urlFilename;

    /* 등록일 */
    LocalDateTime regDt;

    /* 수정일 */
    LocalDateTime udtDt;

}
