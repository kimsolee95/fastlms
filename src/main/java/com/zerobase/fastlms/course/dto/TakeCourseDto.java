package com.zerobase.fastlms.course.dto;

import com.zerobase.fastlms.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

}
