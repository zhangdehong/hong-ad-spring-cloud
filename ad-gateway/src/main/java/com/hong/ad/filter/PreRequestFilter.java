package com.hong.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  1:25 PM 2019/11/17
 */
@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter {

    /**
     * filter类型 有四种
     *
     * @return
     */
    @Override
    public String filterType () {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 执行的优先级
     *
     * @return
     */
    @Override
    public int filterOrder () {
        return 0;
    }

    /**
     * 表示是否执行此过滤器，false 不执行  true 执行
     *
     * @return
     */
    @Override
    public boolean shouldFilter () {
        return true;
    }

    @Override
    public Object run () throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        context.set("startTime",System.currentTimeMillis());
        return null;
    }
}
