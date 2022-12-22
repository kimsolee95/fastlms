package com.zerobase.fastlms.course.model;

import com.zerobase.fastlms.admin.model.CommonParam;
import lombok.Data;

@Data
public class ServiceResult extends CommonParam {

        /* service return value */
        boolean result;

        /* detail message */
        String message;


        /* default constructor */
        public ServiceResult() {
        }

        public ServiceResult(boolean result) {
                this.result = result;
        }

        public ServiceResult(boolean result, String message) {
                this.result = result;
                this.message = message;
        }
}
