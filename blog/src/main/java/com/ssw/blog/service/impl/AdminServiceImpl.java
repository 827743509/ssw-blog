package com.ssw.blog.service.impl;


import com.ssw.blog.exception.BlogException;
import com.ssw.blog.mapper.AdminMapper;
import com.ssw.blog.pojo.Admin;
import com.ssw.blog.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Autowired
   private AdminMapper adminMapper;
    @Override
    public Admin getByUserName(String username) {

        return adminMapper.getByUserName(username);
    }

    @Override
    public Admin getAdmin() {

        return adminMapper.getAdmin();
    }

    @Override
    public void updateInfo(Admin admin) {
        adminMapper.updateInfo(admin);
    }

    @Override
    public void updatePassword(Admin admin) {
        Admin oldadmin = adminMapper.getAdmin();
        oldadmin.setPassword(admin.getPassword());
        adminMapper.updateInfo(oldadmin);
    }

    @Override
    public Admin login(Admin admin)
    {
        Admin adminu = adminMapper.getByUserName("admin");
        if(!admin.getPassword().equals(adminu.getPassword())){
            throw  new BlogException("用户名或密码错误");
        }
        adminu.setPassword("");
        return  adminu;
    }
}
