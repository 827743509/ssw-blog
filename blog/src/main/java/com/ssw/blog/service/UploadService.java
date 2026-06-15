package com.ssw.blog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qiniu.common.QiniuException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    String uploadImage(MultipartFile file) ;
}
