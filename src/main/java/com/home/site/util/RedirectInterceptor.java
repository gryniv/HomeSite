package com.home.site.util;

import org.springframework.web.servlet.*;
import org.springframework.web.servlet.handler.*;

import javax.servlet.http.*;

public class RedirectInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (modelAndView != null) {
            String args = request.getQueryString() != null ? request.getQueryString() : "";
            String url = request.getRequestURI() + "?" + args;
            response.setHeader("Turbolinks-Location", url);
        }
    }
}
