package com.hong.ad.conf;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  8:07 PM 2019/11/18
 */
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters (List<HttpMessageConverter<?>> converters) {
        converters.clear();
        // 自定义消息转换器
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
