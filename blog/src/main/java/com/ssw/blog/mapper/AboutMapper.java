package com.ssw.blog.mapper;

import com.ssw.blog.pojo.About;
import com.ssw.blog.utils.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AboutMapper {
    void save(About about);

    void updateById(About about);

    About getById(Integer id);

    About getAbout();

    void deleteById(Integer id);

    void updateEnable(About about1);

    List<About> getByPage(Page<About> page);

    int getCountByPage(Page<About> page);
}
