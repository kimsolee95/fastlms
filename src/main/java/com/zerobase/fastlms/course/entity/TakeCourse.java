package com.zerobase.fastlms.course.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class TakeCourse implements TakeCourseCode{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    long courseId;

    String userId;

    /* 결제 금액 */
    long payPrice;

    /* 상태(수강신청, 결제완료, 수강취소) */
    String status;

    /* 신청일 */
    LocalDateTime regDt;

}
