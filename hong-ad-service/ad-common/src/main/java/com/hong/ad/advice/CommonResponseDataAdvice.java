package com.hong.ad.advice;

import com.hong.ad.annotation.IgnoreResponseAdvice;
import com.hong.ad.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  4:27 PM 2019/11/17
 */
@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 响应是否支持拦截
     *
     * @param methodParameter
     * @param converterType
     * @return
     */
    @Override
    @SuppressWarnings("all")
    public boolean supports (MethodParameter methodParameter,
                             Class<? extends HttpMessageConverter<?>> converterType) {
        if(methodParameter.getDeclaringClass().isAnnotationPresent(
                IgnoreResponseAdvice.class
        )){
            return false;
        }
        if(methodParameter.getMethod().isAnnotationPresent(
                IgnoreResponseAdvice.class
        )){
            return false;
        }
        return true;
    }

    /**
     * 响应写入之前
     *
     * @param body
     * @param methodParameter
     * @param selectedContentType
     * @param selectedConverterType
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite (Object body, MethodParameter methodParameter,
                                   MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                   ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        CommonResponse<Object> response = new CommonResponse<>(0,"");
        if(null == body){
            return response;
        }else if(body instanceof CommonResponse){
            response = (CommonResponse<Object>) body;
        }else {
            response.setData(body);
        }
        return response;
    }
}
