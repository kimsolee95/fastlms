package com.zerobase.fastlms.admin.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseInput {

    long id;

    String subject;

    long categoryId;

    String keyword;

    String summary;

    String contents;

    long price;

    long salePrice;

    String saleEndDt;

    String saleEndDtText;

}
