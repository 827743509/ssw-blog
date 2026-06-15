package com.ssw.blog.service.impl;

import com.ssw.blog.enums.ResultEnum;
import com.ssw.blog.exception.BlogException;
import com.ssw.blog.mapper.UserMapper;
import com.ssw.blog.pojo.User;
import com.ssw.blog.service.UserService;
import com.ssw.blog.utils.Md5Utils;
import com.ssw.blog.utils.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void save(User user) {
        userMapper.save(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public Page<User> getByPage(Page<User> page) {
        List<User> userList = userMapper.getByPage(page);
        page.setList(userList);
        int totalCount = userMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public void updateInfo(User user) {
        userMapper.updateInfo(user);
    }

    @Override
    public void resetPwd(List<Integer> userIds) {
        userIds.forEach(e -> {
            User user = new User();
            user.setUserId(e);
            user.setPassword(Md5Utils.toMD5("123456"));
            userMapper.update(user);
        });
    }

    @Override
    public void register(User user) {
        User u = userMapper.getByUsername(user.getUsername());
        if (u != null) {
            throw new BlogException(ResultEnum.PARAMS_ERROR.getCode(), "用户已存在");
        }
        userMapper.save(user);
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    @Override
    public User login(User user) {
        User u = userMapper.getByUsername(user.getUsername());
        if (!user.getPassword().equals(u.getPassword())) {
            throw new BlogException(ResultEnum.PARAMS_ERROR);
        }
        u.setPassword("");
        u.setUpdateTime(null);
        return u;
    }
}
