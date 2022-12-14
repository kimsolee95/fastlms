package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {

    /**
     * 회원가입
     */
    boolean register(MemberInput parameter);

    /**
     * 해당 계정을 활성화함.
     * */
    boolean emailAuth(String uuid);

    /**
     * 입력한 이메일로 비밀번호 초기화 정보 전송
     * */
    boolean sendResetPassword(ResetPasswordInput parameter);


    /**
     * 입력받은 uuid로 사용자의 password로 초기화함.
     * */
    boolean resetPassword(String uuid, String password);


    /**
     * 입력받은 uuid 값이 유효한지 확인
     * */
    boolean checkResetPassword(String uuid);


    /**
     * 회원 목록 return(관리자만 사용 가능 - ADMIN_ROLE)
     * */
    List<MemberDto> list(MemberParam parameter);

}
