package com.hong.ad;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  3:53 下午 2020/8/23
 */
public class Demo {

    private static LongAdder count = new LongAdder();

    public static void main (String[] args) {
        List<Long> list = Arrays.asList(1L,2L,3L,4L);
        list.forEach(l ->{
            count.add(l);
        });
        System.out.println(count);
    }
}
