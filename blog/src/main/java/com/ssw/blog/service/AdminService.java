package com.ssw.blog.service;


import com.ssw.blog.pojo.Admin;

public interface AdminService {
   //根据用户名查询
    Admin getByUserName(String username);

    Admin getAdmin();

    void updateInfo(Admin admin);

    void updatePassword(Admin admin);

    Admin login(Admin admin);
}
