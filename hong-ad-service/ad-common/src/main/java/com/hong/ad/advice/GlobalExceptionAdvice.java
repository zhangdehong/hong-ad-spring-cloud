package com.hong.ad.advice;

import com.hong.ad.exception.AdException;
import com.hong.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  5:08 PM 2019/11/18
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 异常处理
     *
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException (HttpServletRequest request,
                                                      AdException exception) {

        CommonResponse<String> response = new CommonResponse<>(-1,
                "business error");
        response.setData(exception.getMessage());
        return response;
    }
}
