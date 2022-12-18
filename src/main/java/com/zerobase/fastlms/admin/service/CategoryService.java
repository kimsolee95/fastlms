package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.CategoryDto;

import java.util.List;


public interface CategoryService {

    /* 카테고리 목록 조회 - 관리자 */
    List<CategoryDto> list();

    /* 카테고리 신규 추가 - 관리자 */
    boolean add(String categoryName);

    /* 카테고리 수정 - 관리자 */
    boolean update(CategoryDto parameter);

    /* 카테고리 삭제 - 관리자 */
    boolean del(long id);
}
