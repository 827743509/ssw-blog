package com.ssw.blog.interceptor;

import com.ssw.blog.enums.ResultEnum;
import com.ssw.blog.exception.BlogException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    private static final String[] WHITE_LIST = {
            "/admin/login",
            "/user/login",
            "/user/register",
            "/link/list",
            "/music/getList",
            "/about/read",
            "/type/getList",
            "/blog/read",
            "/blog/getTimeLine",
            "/blog/getByPage",
            "/admin/getAdmin"
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (containsWhiteList(request.getRequestURI())) {
            return true;
        }
        if (request.getSession().getAttribute("token") != null) {
            return true;
        }
        throw new BlogException(ResultEnum.NOT_LOGIN);
    }

    private boolean containsWhiteList(String uri) {
        if (uri == null) {
            return false;
        }
        for (String url : WHITE_LIST) {
            if (uri.equals(url) || uri.startsWith(url + "/")) {
                return true;
            }
        }
        return false;
    }
}
