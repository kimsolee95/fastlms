package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.admin.dto.LoginHistoryDto;
import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.mapper.BannerMapper;
import com.zerobase.fastlms.admin.mapper.MemberMapper;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.components.MailComponents;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.member.dto.FrontBannerDto;
import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.entity.MemberCode;
import com.zerobase.fastlms.member.exception.MemberNotEmailAuthException;
import com.zerobase.fastlms.member.exception.MemberStopUserException;
import com.zerobase.fastlms.member.model.LoginHistoryInput;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.repository.LoginHistoryRepository;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;
    private final MemberMapper memberMapper;
    private final LoginHistoryRepository loginHistoryRepository;
    private final BannerMapper bannerMapper;

    /*
    * ?????? ??????
    * */
    @Override
    public boolean register(MemberInput parameter) {

        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());

        if (optionalMember.isPresent()) {
            //?????? userId??? ???????????? ????????? ??????
            return false;
        }

        String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());

        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .phone(parameter.getPhone())
                .password(encPassword)
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .userStatus(Member.MEMBER_STATUS_REQ) // ?????? ?????? ??? default
                .build();
        memberRepository.save(member);

        String email = parameter.getUserId();
        String subject = "fastlms ????????? ????????? ??????????????????.";
        String text = "<p>fastlms ????????? ????????? ??????????????????.</p><p>?????? ????????? ??????????????? ????????? ???????????????</p>"
                + "<div><a href='http://localhost:8080/member/email-auth?id="
                + uuid
                + "'>?????? ?????? </a></div>";

        mailComponents.sendMail(email, subject, text);

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {

        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);

        if (!optionalMember.isPresent()) {
            return false;
        }

        Member member = optionalMember.get();

        //???????????? check
        if (member.isEmailAuthYn()) {
            return false;
        }

        member.setEmailAuthYn(true);
        member.setUserStatus(Member.MEMBER_STATUS_ING); // ?????? ?????? ????????? update
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean sendResetPassword(ResetPasswordInput parameter) {

        Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(parameter.getUserId(), parameter.getUserName());
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
        }

        Member member = optionalMember.get();

        String uuid = UUID.randomUUID().toString();

        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
        memberRepository.save(member);


        String email = parameter.getUserId();
        String subject = "[fastlms] ???????????? ????????? ???????????????.";
        String text = "<p>fastlms ???????????? ????????? ???????????????.</p><p>?????? ????????? ??????????????? ??????????????? ?????????????????????.</p>"
                + "<div><a href='http://localhost:8080/member/reset/password?id="
                + uuid
                + "'>???????????? ????????? ??????</a></div>";

        mailComponents.sendMail(email, subject, text);

        return true;
    }

    @Override
    public boolean resetPassword(String uuid, String password) {

        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
        }

        Member member = optionalMember.get();

        //????????? ?????? ????????? ??????
        if (member.getResetPasswordLimitDt() == null) {
            throw new RuntimeException("????????? ????????? ????????????.");
        }

        if (member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("????????? ????????? ????????????.");
        }

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {

        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if (!optionalMember.isPresent()) {
            return false;
        }

        Member member = optionalMember.get();

        //????????? ?????? ????????? ??????
        if (member.getResetPasswordLimitDt() == null) {
            throw new RuntimeException("????????? ????????? ????????????.");
        }

        if (member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("????????? ????????? ????????????.");
        }

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //email
        Optional<Member> optionalMember = memberRepository.findById(username);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
        }

        Member member = optionalMember.get();

        if (Member.MEMBER_STATUS_REQ.equals(member.getUserStatus())) {
            throw new MemberNotEmailAuthException("????????? ?????? ????????? ???????????? ????????????.");
        }

        if (Member.MEMBER_STATUS_STOP.equals(member.getUserStatus())) {
            throw new MemberStopUserException("????????? ???????????????.");
        }

        if (Member.MEMBER_STATUS_WITHDRAW.equals(member.getUserStatus())) {
            throw new MemberStopUserException("?????? ???????????????.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        //???????????? ??????, role ?????? 
        if (member.isAdminYn()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(member.getUserId(), member.getPassword(), grantedAuthorities); //id, pw, role
    }


    @Override
    public List<MemberDto> list(MemberParam parameter) {

        long totalCount = memberMapper.selectListCount(parameter);

        List<MemberDto> list = memberMapper.selectList(parameter);

        if (!CollectionUtils.isEmpty(list)) {

            int i = 0;

            for (MemberDto member : list) {
                member.setTotalCount(totalCount);
                member.setSeq(totalCount - parameter.getPageStart() - i); //??????
                i++;
            }
        }

        return list;
    }


    @Override
    public MemberDto detail(String userId) {

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            return null;
        }

        Member member = optionalMember.get();
        return  MemberDto.of(member); // Member -> MemberDto??? ?????? return
    }

    @Override
    public List<LoginHistoryDto> loginHistoryList(MemberParam parameter) {

        List<LoginHistoryDto> loginHistoryList = memberMapper.selectLoginHistoryList(parameter);

        return loginHistoryList;
    }


    @Override
    public boolean updateStatus(String userId, String userStatus) {

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
        }

        Member member = optionalMember.get();

        member.setUserStatus(userStatus);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean updatePassword(String userId, String password) {

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("??????????????? ???????????? ????????????.");
        }

        Member member = optionalMember.get();

        String ensPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        member.setPassword(ensPassword);
        memberRepository.save(member);

        return true;
    }

    @Override
    public ServiceResult updateMemberPassword(MemberInput parameter) {

        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, "?????? ????????? ???????????? ????????????.");
        }

        Member member = optionalMember.get();

        if (PasswordUtil.equals(parameter.getPassword(), member.getPassword())) {
            return new ServiceResult(false, "??????????????? ???????????? ????????????.");
        }

        String encPassword = PasswordUtil.encPassword(parameter.getNewPassword());

        member.setPassword(encPassword);
        memberRepository.save(member);

        return new ServiceResult(true);
    }

    @Override
    public ServiceResult updateMember(MemberInput parameter) {

        String userId = parameter.getUserId();

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, "?????? ????????? ???????????? ????????????.");
        }

        Member member = optionalMember.get();
        member.setPhone(parameter.getPhone());
        member.setUdtDt(LocalDateTime.now());
        member.setZipcode(parameter.getZipcode());
        member.setAddr(parameter.getAddr());
        member.setAddrDetail(parameter.getAddrDetail());
        memberRepository.save(member);

        return new ServiceResult(true);
    }

    @Override
    public ServiceResult withdraw(String userId, String password) {

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, "?????? ????????? ???????????? ????????????.");
        }

        Member member = optionalMember.get();

        if (!PasswordUtil.equals(password, member.getPassword())) {
            return new ServiceResult(false, "??????????????? ???????????? ????????????.");
        }

        member.setUserName("????????????");
        member.setPhone("");
        member.setPassword("");
        member.setRegDt(null);
        member.setUdtDt(null);
        member.setEmailAuthYn(false);
        member.setEmailAuthDt(null);
        member.setEmailAuthKey("");
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        member.setUserStatus(MemberCode.MEMBER_STATUS_WITHDRAW);
        member.setZipcode("");
        member.setAddr("");
        member.setAddrDetail("");
        memberRepository.save(member);

        return new ServiceResult(true);
    }

    @Override
    public boolean insertLoginHistory(LoginHistoryInput parameter) {

        LoginHistory loginHistory = LoginHistory.builder()
                .userId(parameter.getUserId())
                .userAgent(parameter.getUserAgent())
                .userIpAddr(parameter.getUserIpAddr())
                .loginDt(parameter.getLoginDt())
                .build();

        loginHistoryRepository.save(loginHistory);
        return true;
    }

    @Override
    public List<FrontBannerDto> selectFrontBannerList() {
        return bannerMapper.selectFrontBannerList();
    }

}
