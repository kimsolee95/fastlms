package com.zerobase.fastlms.course.model;

import lombok.Data;

@Data
public class TakeCourseInput {

    /* course id */
    long courseId;

    String userId;

    long takeCourseId;

}
