package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Banner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    /* 날짜 컬럼 formatting */
    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return regDt != null ? regDt.format(formatter) : "";
    }

    public static BannerDto of (Banner banner) {

        return BannerDto.builder()
                .id(banner.getId())
                .subject(banner.getSubject())
                .linkUrl(banner.getLinkUrl())
                .sortOrder(banner.getSortOrder())
                .usingYn(banner.isUsingYn())
                .openWay(banner.getOpenWay())
                .filename(banner.getFilename())
                .urlFilename(banner.getUrlFilename())
                .regDt(banner.getRegDt())
                .udtDt(banner.getUdtDt())
                .build();
    }

}
