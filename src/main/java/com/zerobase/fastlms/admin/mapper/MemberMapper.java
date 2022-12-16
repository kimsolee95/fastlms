package com.zerobase.fastlms.admin.mapper;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    /* admin 회원 조회 시 total num */
    Long selectListCount(MemberParam parameter);

    /* admin 회원 조회 */
    List<MemberDto> selectList(MemberParam parameter);


}
