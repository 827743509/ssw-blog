package com.ssw.blog.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Blog implements Serializable {

    private static final long serialVersionUID = -559415810554538278L;

    private String blogId;

    private String blogTitle;

    private String blogImage;

    private String blogContent;

    private List<Integer> typeIds;

    private String createdTime;

    private String updateTime;

}
