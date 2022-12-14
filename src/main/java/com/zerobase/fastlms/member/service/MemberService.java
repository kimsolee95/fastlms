package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.admin.dto.LoginHistoryDto;
import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.member.dto.FrontBannerDto;
import com.zerobase.fastlms.member.model.LoginHistoryInput;
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

    /**
     *회원 상세 정보
     */
    MemberDto detail(String userId);

    /**
     * 각 회원 로그인 히스토리 목록 - 회원 상세정보 페이지
     * */
    List<LoginHistoryDto> loginHistoryList(MemberParam parameter);

    /**
     * 회원 상태 변경
     * */
    boolean updateStatus(String userId, String userStatus);

    /**
     * 회원 비밀번호 초기화
     * */
    boolean updatePassword(String userId, String password);

    /**
     * 회원 정보 - 비밀번호 변경 (회원)
     * */
    ServiceResult updateMemberPassword(MemberInput parameter);

    /**
     * 회원 정보 - 회원 정보 변경 (회원)
     * */
    ServiceResult updateMember(MemberInput parameter);

    /**
     * 회원 정보 - 회원 탈퇴 (회원)
     * */
    ServiceResult withdraw(String userId, String password);

    /**
     * 회원 로그인 히스토리 저장
     * */
    boolean insertLoginHistory(LoginHistoryInput parameter);


    /**
     * 프론트 배너 리스트 조회
     * */
    List<FrontBannerDto> selectFrontBannerList();
}
