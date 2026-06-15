package com.ssw.blog.service;


import com.ssw.blog.pojo.Link;

import java.util.List;

public interface LinkService {
    //添加
    void save(Link link);
    //修改
    void update(Link link);
    //根据id查询
    Link getById(Integer id);
    //查询所有
    List<Link> getAll();

    void deleteById(Integer id);
}
