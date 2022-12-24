package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginHistoryDto {

    private Long id;

    /* user id (email) */
    String userId;

    /* ipv4 */
    String userIpAddr;

    /* user-agent */
    String userAgent;

    /* 로그인일시 */
    LocalDateTime loginDt;

    /* 로그인 날짜 컬럼 formatting */
    public String getLoginDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return loginDt != null ? loginDt.format(formatter) : "";
    }


}
