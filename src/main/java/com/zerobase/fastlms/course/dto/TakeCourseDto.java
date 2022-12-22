package com.zerobase.fastlms.course.dto;

import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.course.entity.TakeCourse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TakeCourseDto {


    Long id;

    long courseId;

    String userId;

    /* 결제 금액 */
    long payPrice;

    /* 상태(수강신청, 결제완료, 수강취소) */
    String status;

    /* 신청일 */
    LocalDateTime regDt;

    /* join */
    String userName;

    String phone;

    String subject;

    /* 추가 컬럼 */
    long totalCount;

    long seq;

    /* view 에서 사용할 날짜 포맷 함수 */
    public String getRegDtText() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return regDt != null ? regDt.format(formatter) : "";
    }

    public static TakeCourseDto of (TakeCourse takeCourse) {

        return TakeCourseDto.builder()
                .id(takeCourse.getId())
                .courseId(takeCourse.getCourseId())
                .userId(takeCourse.getUserId())
                .payPrice(takeCourse.getPayPrice())
                .status(takeCourse.getStatus())
                .regDt(takeCourse.getRegDt())
                .build();
    }


}
