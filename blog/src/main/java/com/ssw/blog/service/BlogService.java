package com.ssw.blog.service;

import com.ssw.blog.pojo.Blog;
import com.ssw.blog.utils.Page;
import com.ssw.blog.vo.BlogVo;

import java.util.List;

public interface BlogService {

    void save(Blog blog);

    Blog getById(String id);

    void update(Blog blog);

    BlogVo readById(String id);

    void deleteById(String id);

    Page<BlogVo> getByPage(Page<BlogVo> page);

    List<BlogVo> getTimeLine();
}
