package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.member.model.MemberInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    boolean register(MemberInput parameter);

    /**
     * uuir 해당 계정을 활성화함.
     * */
    boolean emailAuth(String uuid);

}
