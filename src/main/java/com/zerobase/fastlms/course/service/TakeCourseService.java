package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseParam;

import java.util.List;

public interface TakeCourseService {

    /* 수강 목록 - 관리자 */
    List<TakeCourseDto> list(TakeCourseParam parameter);


    /* 수강내용 상태 변경 - 관리자  */
    ServiceResult updateStatus(long id, String status);

    /* 내 수강내역 목록 조회 - 회원 */
    List<TakeCourseDto> myCourseList(String userId);

    /* 수강 상세 정보 */
    TakeCourseDto detail(long id);

    /* 내 수강신청 취소하기 - 회원 */
    ServiceResult cancel(long id);
}
