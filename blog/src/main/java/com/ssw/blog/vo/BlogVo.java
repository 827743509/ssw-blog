package com.ssw.blog.vo;

import lombok.Data;

import com.ssw.blog.pojo.Type;

import java.io.Serializable;
import java.util.List;

@Data
public class BlogVo implements Serializable {

    private String blogId;

    private String blogTitle;

    private String blogImage;

    private String blogContent;

    private String typeName;

    private List<Type> type;

    private String blogMonth;

    private String createdTime;

    private String updateTime;
}
