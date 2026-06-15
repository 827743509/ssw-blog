package com.ssw.blog.mapper;


import com.ssw.blog.pojo.User;
import com.ssw.blog.utils.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 用户表Mapper
 * </p>
 *
 * @author 稽哥
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 */
@Component
public interface UserMapper {

    void save(User user);

    void update(User user);

    User getById(Integer id);

    void deleteById(Integer id);

    List<User> getByPage(Page<User> page);

    int getCountByPage(Page<User> page);

    void updateInfo(User user);


    User getByUsername(String username);
}
