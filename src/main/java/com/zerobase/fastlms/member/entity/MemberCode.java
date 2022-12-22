package com.zerobase.fastlms.member.entity;

public interface MemberCode {

    /**
     * 현재 가입 요청 중
     * */
    String MEMBER_STATUS_REQ = "REQ";

    /**
     * 현재 이용 중 상태
     * */
    String MEMBER_STATUS_ING = "ING";


    /**
     * 현재 정지 상태
     * */
    String MEMBER_STATUS_STOP = "STOP";

    /**
     * 탈퇴 회원
     * */
    String MEMBER_STATUS_WITHDRAW = "WITHDRAW";

}
