package com.hong.ad.utils;

import com.hong.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  9:16 PM 2019/11/23
 */
public class CommonUtils {

    private static final String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    public static String md5 (String str) {
        return DigestUtils.md5Hex(str).toUpperCase();
    }

    public static Date parseStringDate (String dateString) throws AdException {
        try {
            return DateUtils.parseDate(dateString, parsePatterns);
        } catch (Exception ex) {
            throw new AdException(ex.getMessage());
        }
    }
}
