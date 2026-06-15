package com.ssw.blog.controller.advice;

import com.ssw.blog.exception.BlogException;
import com.ssw.blog.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//统一异常处理类
@ControllerAdvice
@Slf4j
public class BlogExceptionAdvice {
    //统一处理blogexception
    @ExceptionHandler(BlogException.class)
    @ResponseBody
    public Result<Object> exceptionHandler(BlogException blogException){
        log.error("统一异常处理",blogException);
         return  new Result<>(blogException.getErrorCode(),blogException.getMessage());
    }
}
