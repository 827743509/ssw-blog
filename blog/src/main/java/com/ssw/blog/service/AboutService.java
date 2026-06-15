package com.ssw.blog.service;


import com.ssw.blog.pojo.About;
import com.ssw.blog.utils.Page;

public interface AboutService {

    void save(About about);

    void update(About about);

    About getById(Integer id);

    About read();

    void deleteById(Integer id);

    void enable(Integer id);

    void disable(Integer id);

    Page<About> getByPage(Page<About> page);
}
