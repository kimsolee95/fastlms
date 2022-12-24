package com.zerobase.fastlms.util;

import javax.servlet.http.HttpServletRequest;

public class LoginUserUtil {

    public static String getRemoteIP(HttpServletRequest request) {

        /*
        * vm option 추가 (ipv4형식으로 가져오기)
        * -Djava.net.preferIPv4Stack=true
        * */

        String ip = request.getHeader("X-FORWARDED-FOR");

        //PROXY
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        //WEB
        if (ip == null || ip.length() == 0) {

            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0) {
            ip = request.getRemoteAddr() ;
        }

        return ip;
    }

    public static String getUserAgent (HttpServletRequest request) {

        String userAgent = request.getHeader("User-Agent");
        return userAgent;
    }

}
