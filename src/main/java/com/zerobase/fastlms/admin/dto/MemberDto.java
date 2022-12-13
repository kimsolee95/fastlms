package com.zerobase.fastlms.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDto {

    String userId;
    String userName;
    String phone;
    String password;
    LocalDateTime regDt;

    /*이메일 인증 여부*/
    boolean emailAuthYn;

    /*이메일 인증 날짜*/
    LocalDateTime emailAuthDt;

    /*이메일 인증 키*/
    String emailAuthKey;

    /* 초기화 비밀번호 키 */
    String resetPasswordKey;

    /* 초기화 비밀번호 날짜 제한 */
    LocalDateTime resetPasswordLimitDt;

    /* 관리자 여부 */
    boolean adminYn;


}
