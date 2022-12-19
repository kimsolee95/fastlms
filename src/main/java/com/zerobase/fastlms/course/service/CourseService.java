package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.admin.model.CourseInput;

public interface CourseService {

    /* 강좌 등록 */
    boolean add(CourseInput parameter);

}
