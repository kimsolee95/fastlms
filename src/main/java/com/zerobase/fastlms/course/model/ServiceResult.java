package com.zerobase.fastlms.course.model;

import com.zerobase.fastlms.admin.model.CommonParam;
import lombok.Data;

@Data
public class ServiceResult extends CommonParam {

        /* service return value */
        boolean result;

        /* detail message */
        String message;
}
