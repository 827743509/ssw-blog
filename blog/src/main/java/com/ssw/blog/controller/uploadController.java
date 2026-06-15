package com.ssw.blog.controller;

import com.ssw.blog.service.UploadService;
import com.ssw.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传
 */
@RestController
@RequestMapping("/upload")
public class uploadController {
    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping("/uploadImage")
    public Result<String> uploadImage(MultipartFile file) {
       String url=uploadService.uploadImage(file);
       if (url==null){
           return  new Result<>("上传失败!");
       }
        return  new Result<>("上传成功",url);
    }
}
