package com.zerobase.fastlms.course.dto;

import com.zerobase.fastlms.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CourseDto {

    Long id;

    long categoryId;

    String imagePath;

    String keyword;

    String subject;

    String summary;

    String contents;

    long price;

    long salePrice;

    LocalDate saleEndDt;

    /* 등록일 */
    LocalDateTime regDt;

    /* 수정일 */
    LocalDateTime udtDt;

    long totalCount;

    long seq;

    /* img local path */
    String filename;

    /* img url path */
    String urlFilename;


    public static CourseDto of(Course course) {

        return CourseDto.builder()
                .id(course.getId())
                .categoryId(course.getCategoryId())
                .imagePath(course.getImagePath())
                .keyword(course.getKeyword())
                .subject(course.getSubject())
                .summary(course.getSummary())
                .contents(course.getContents())
                .price(course.getPrice())
                .salePrice(course.getSalePrice())
                .saleEndDt(course.getSaleEndDt())
                .regDt(course.getRegDt())
                .udtDt(course.getUdtDt())
                .filename(course.getFilename())
                .urlFilename(course.getUrlFilename())
                .build();
    }

    public static List<CourseDto> of(List<Course> courses) {

        //null check
        if (courses == null) {
            return null;
        }

        List<CourseDto> courseDtoList = new ArrayList<>();

        for (Course course : courses) {
            courseDtoList.add(CourseDto.of(course));
        }

        return courseDtoList;
    }

}
