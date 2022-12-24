package com.zerobase.fastlms.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BannerDto {


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

    /* img file - local path */
    String filename;

    /* img file - url path */
    String urlFilename;

    /* 등록일 */
    LocalDateTime regDt;

    /* 수정일 */
    LocalDateTime udtDt;

}
