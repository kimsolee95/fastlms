package com.zerobase.fastlms.course.service.impl;

import com.zerobase.fastlms.admin.model.CourseInput;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.course.mapper.CourseMapper;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.repository.CourseRepository;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;


    private LocalDate getLocalDate(String value) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {
        }

        return null;
    }

    @Override
    public boolean add(CourseInput parameter) {

        //LocalDate formatting
        LocalDate saleEndDt = getLocalDate(parameter.getSaleEndDtText());

        Course course = Course.builder()
                .categoryId(parameter.getCategoryId())
                .subject(parameter.getSubject())
                .keyword(parameter.getKeyword())
                .summary(parameter.getSummary())
                .contents(parameter.getContents())
                .price(parameter.getPrice())
                .salePrice(parameter.getSalePrice())
                .saleEndDt(saleEndDt)
                .regDt(LocalDateTime.now())
                .build();
        courseRepository.save(course);

        return true;
    }

    @Override
    public List<CourseDto> list(CourseParam parameter) {

        long totalCount = courseMapper.selectListCount(parameter);

        List<CourseDto> list = courseMapper.selectList(parameter);

        if (!CollectionUtils.isEmpty(list)) {

            int i = 0;

            for (CourseDto course : list) {
                course.setTotalCount(totalCount);
                course.setSeq(totalCount - parameter.getPageStart() - i); //순번
                i++;
            }
        }

        return list;
    }

    @Override
    public CourseDto getById(long id) {

        CourseDto existCourse = courseRepository.findById(id).map(CourseDto::of).orElse(null);

        return existCourse;
    }

    @Override
    public boolean set(CourseInput parameter) {

        Optional<Course> optionalCourse = courseRepository.findById(parameter.getId());

        if (!optionalCourse.isPresent()) {
            return false;
        }


        //LocalDate formatting
        LocalDate saleEndDt = getLocalDate(parameter.getSaleEndDtText());

        Course course = optionalCourse.get();
        course.setCategoryId(parameter.getCategoryId());
        course.setSubject(parameter.getSubject());
        course.setKeyword(parameter.getKeyword());
        course.setSummary(parameter.getSummary());
        course.setContents(parameter.getContents());
        course.setPrice(parameter.getPrice());
        course.setSalePrice(parameter.getSalePrice());
        course.setSaleEndDt(saleEndDt);
        course.setUdtDt(LocalDateTime.now());
        courseRepository.save(course);

        return true;
    }

    @Override
    public boolean del(String idList) {

        if (idList != null && idList.length() > 0) {

            String[] ids = idList.split(",");

            for (String x : ids) {

                Long id = 0L;

                try {
                    id = Long.parseLong(x);
                } catch (Exception e) {
                }

                if (id > 0) {
                    courseRepository.deleteById(id);
                }
            }
        }

        return true;
    }

    @Override
    public List<CourseDto> frontList(CourseParam parameter) {

        if (parameter.getCategoryId() < 1) {
            List<Course> courseList = courseRepository.findAll();
            return CourseDto.of(courseList);
        }

        Optional<List<Course>> optionalCourseList = courseRepository.findByCategoryId(parameter.getCategoryId());
        if (optionalCourseList.isPresent()) {
            return  CourseDto.of(optionalCourseList.get());
        }

        return null;
    }

    @Override
    public CourseDto frontDetail(long courseId) {

        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalCourse.isPresent()) {
            return CourseDto.of(optionalCourse.get());
        }

        return null;
    }
}
