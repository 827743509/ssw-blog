package com.ssw.blog.utils;


import com.ssw.blog.enums.ResultEnum;
import com.ssw.blog.exception.BlogException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * user工具类
 * @author  zhouxq
 * @version 2020/5/10
 * @see     UserUtils
 * @since   从哪个版本开始有
 */
@Component
public class UserUtils
{
    /**
     * 获取登录中的用户
     *
     * @return
     */
    public static Object getLoginUser(HttpServletRequest httpServletRequest) {
        Object token = httpServletRequest.getSession().getAttribute("token");
        if(token==null){
            throw  new BlogException(ResultEnum.NOT_LOGIN);
        }

        return token ;
    }
    //退出登录
    public  static void logout(HttpServletRequest request){
         request.getSession().removeAttribute("token");
    }
}
