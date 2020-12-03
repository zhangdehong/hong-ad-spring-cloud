package com.hong.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hong.ad.vo.CommonResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  8:28 下午 2020/5/30
 */
public class Test {

    static String str = "{\"content\":[\"admin\"],\"count\":1,\"statusCode\":\"API-COMMON-INF-OK\",\"statusMessage\":\"成功\"}";
    static CommonResponse<List<String>> response = new CommonResponse<>();
    static List<String> list = new ArrayList<>();
    static {
        list.add("admin");
        response.setCode(1);
        response.setMessage("成功");
        response.setData(list);
    }
    public static void main (String[] args) {
        //JSONObject jsonObject = JSONObject.parseObject(str);
        //Object content = jsonObject.get("content");
        //JSONArray jsonArray = JSONArray.parseArray(content.toString());
        //for (Object object : jsonArray) {
        //    System.out.println(object);
        //}
        System.out.println(123456);
        List<String> str = new ArrayList<>();
        List<String> data = response.getData();
        Properties system = System.getProperties();
    }
}
