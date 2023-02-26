package com.itheima.reggie.filter;


import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 张壮
 * @description TODO
 * @since 2023/2/25 11:26
 **/
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {


    //用于路径匹配
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;


        String remoteAddr = request.getRemoteAddr();
        String pathInfo = request.getPathInfo();
        String requestURI = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();

        log.info("<--- remoteAddr == {}", remoteAddr);
        log.info("pathInfo : {}", pathInfo);
        log.info("requestURI : {}", requestURI);
        log.info("requestURL : {}  --->", requestURL);

        // 定义不需要 拦截的路径
        String[] urls = new String[]{"/employee/login", "/employee/logout", "/backend/**", "/front"};

        boolean check = check(urls, requestURI);
        // 不需要拦截,直接放行
        if (check) {
            log.info("本次请求 {} 不需要处理",requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        // 需要拦截的页面进行 校验处理
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已登录,用户id为:{}", request.getSession().getAttribute("employee"));
            filterChain.doFilter(request, response);
            return;
        }

        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(Result.error("not_login")));

    }

    // 用于放行 不需要拦截url
    public boolean check(String[] urls, String uri) {
        for (String url : urls) {
            if (PATH_MATCHER.match(url, uri)) return true;
        }
        return false;
    }
}
