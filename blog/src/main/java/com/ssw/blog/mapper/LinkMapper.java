package com.ssw.blog.mapper;


import com.ssw.blog.pojo.Link;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 评论表Mapper
 * </p>
 *
 * @author 稽哥
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 */
@Component
public interface LinkMapper {

    void save(Link link);

    void update(Link link);

    Link getById(Integer id);

    List<Link> getAll();

    void deleteById(Integer id);
}
