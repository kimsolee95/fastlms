package com.zerobase.fastlms.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Member implements MemberCode{

    @Id
    private String userId;

    private String userName;

    private String phone;

    private String password;

    /* 가입일 */
    private LocalDateTime regDt;

    /* 회원정보 수정일 */
    private LocalDateTime udtDt;

    /*이메일 인증 여부*/
    private boolean emailAuthYn;

    /*이메일 인증 날짜*/
    private LocalDateTime emailAuthDt;

    /*이메일 인증 키*/
    private String emailAuthKey;

    /* 초기화 비밀번호 키 */
    private String resetPasswordKey;

    /* 초기화 비밀번호 날짜 제한 */
    private LocalDateTime resetPasswordLimitDt;

    /* 관리자 여부 */
    private boolean adminYn;

    /* 회원 상태 {이용가능 상태, 정지 상태} */
    private String userStatus;

    /* 우편번호 */
    private String zipcode;

    /* 주소 */
    private String addr;

    /* 상세주소 */
    private String addrDetail;
}
