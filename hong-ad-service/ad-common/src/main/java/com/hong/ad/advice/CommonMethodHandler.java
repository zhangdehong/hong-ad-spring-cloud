package com.hong.ad.advice;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create by  23:31 2022/9/6
 */
public class CommonMethodHandler implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType (MethodParameter returnType) {

        return false;
    }

    @Override
    public void handleReturnValue (Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

    }
}
