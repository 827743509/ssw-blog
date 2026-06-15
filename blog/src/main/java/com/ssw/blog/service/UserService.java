package com.ssw.blog.service;


import com.ssw.blog.pojo.User;
import com.ssw.blog.utils.Page;

import java.util.List;

public interface UserService {
    /**
     * 保存
     *
     * @param user
     */
    void save(User user);

    /**
     * 更新
     *
     * @param user
     */
    void update(User user);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    User getById(Integer id);

    /**
     * 根据id删除
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<User> getByPage(Page<User> page);

    void updateInfo(User user);

    void resetPwd(List<Integer> userIds);

    void register(User user);

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    User getByUsername(String username);

    User login(User user);
}
