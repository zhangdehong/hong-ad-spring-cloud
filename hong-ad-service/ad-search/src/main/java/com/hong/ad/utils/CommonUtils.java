package com.hong.ad.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  9:19 PM 2020/1/30
 */
@Slf4j
public class CommonUtils {

    public static <K, V> V getOrCreate (K key, Map<K, V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

    public static String stringConcat (String... args) {
        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            builder.append(arg);
            builder.append("-");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static Date parseStringDate (String dateString) {

        try {
            DateFormat format = new SimpleDateFormat(
                    "EEE MM dd HH:mm:ss zzz yyyy",
                    Locale.US
            );
            return DateUtils.addHours(
                    format.parse(dateString),
                    -8);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}