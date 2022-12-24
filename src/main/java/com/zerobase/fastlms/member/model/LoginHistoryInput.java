package com.zerobase.fastlms.member.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginHistoryInput {

    /* user id (email) */
    String userId;

    /* ipv4 */
    String userIpAddr;

    /* user-agent */
    String userAgent;
    
    /* 로그인 일시 */
    LocalDateTime loginDt;

}
