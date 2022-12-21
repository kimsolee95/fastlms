package com.zerobase.fastlms.member.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberInput {

    private String userId;

    private String userName;

    private String password;

    private String phone;

    /* 비밀번호 변경 - 회원 */
    private String newPassword;

}
