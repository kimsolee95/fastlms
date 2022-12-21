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
public class MemberDto {

    //빌터 패턴 사용
    //기본생성자 -> NoArgsConstructor
    //전체 속성 컬럼 -> AllArgsConstructor

    String userId;
    String userName;
    String phone;
    String password;
    LocalDateTime regDt;
    LocalDateTime udtDt;

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

    /*page  컬럼*/
    long totalCount;

    /* 게시글 순번 */
    long seq;

    /* 회원 상태 */
    String userStatus;

    /* 우편번호 */
    String zipcode;

    /* 주소 */
    String addr;

    /* 상세주소 */
    String addrDetail;


    /* 날짜 컬럼 formatting */
    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return regDt != null ? regDt.format(formatter) : "";
    }
    public String getUdtDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return udtDt != null ? udtDt.format(formatter) : "";
    }

    public static MemberDto of(Member member) {

        return MemberDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                .phone(member.getPhone())
                .regDt(member.getRegDt())
                .udtDt(member.getUdtDt())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthDt(member.getEmailAuthDt())
                .emailAuthKey(member.getEmailAuthKey())
                .resetPasswordKey(member.getResetPasswordKey())
                .resetPasswordLimitDt(member.getResetPasswordLimitDt())
                .adminYn(member.isAdminYn())
                .userStatus(member.getUserStatus())
                .zipcode(member.getZipcode())
                .addr(member.getAddr())
                .addrDetail(member.getAddrDetail())
                .build();
    }


}
