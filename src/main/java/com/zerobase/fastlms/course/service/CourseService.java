package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.admin.model.CourseInput;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;

import java.util.List;

public interface CourseService {


    /* 강좌 등록 */
    boolean add(CourseInput parameter);

    /* 강좌 목록 - 관리자 */
    List<CourseDto> list(CourseParam parameter);

    /* 강좌 상세정보 - 관리자 */
    CourseDto getById(long id);

    /* 강좌정보 수정 */
    boolean set(CourseInput parameter);

    /* 강좌정보 삭제 */
    boolean del(String idList);

    /*강좌 목록 - 이용자*/
    List<CourseDto> frontList(CourseParam parameter);

    /* 강좌 상세정보 - 이용자 */
    CourseDto frontDetail(long courseId);

    /* 수강신청 - 이용자 */
    ServiceResult req(TakeCourseInput parameter);
}
