package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    public static MemberDto of(Member member) {

        return MemberDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                .phone(member.getPhone())
                .regDt(member.getRegDt())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthDt(member.getEmailAuthDt())
                .emailAuthKey(member.getEmailAuthKey())
                .resetPasswordKey(member.getResetPasswordKey())
                .resetPasswordLimitDt(member.getResetPasswordLimitDt())
                .adminYn(member.isAdminYn())
                .build();
    }


}
