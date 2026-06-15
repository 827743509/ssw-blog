package com.ssw.blog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ssw.blog.enums.ResultEnum;
import com.ssw.blog.pojo.Admin;
import com.ssw.blog.service.AdminService;
import com.ssw.blog.utils.Result;
import com.ssw.blog.utils.StringUtils;
import com.ssw.blog.utils.UserUtils;

@RestController
@RequestMapping("/admin")
public class AdminController
{
    @Autowired
    private AdminService adminService;
    private HttpSession session;

    @PostMapping("/login")
    public Result<Object> login(@RequestBody Admin admin, HttpServletRequest httpServletRequest)
    {
        if (admin == null || StringUtils.isBlank(admin.getUsername()) || StringUtils.isBlank(admin.getPassword()))
        {
            return new Result<>(ResultEnum.PARAMS_NULL.getCode(), "用户名或密码错误");
        }
        Admin adminUser = null;
        try
        {
            adminUser = adminService.login(admin);
            //登录成功
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("token", adminUser);
            Map map = new HashMap(2);
            map.put("token", session.getId());
            return new Result<>(map);
        } catch (Exception e)
        {
            return new Result<>(ResultEnum.PARAMS_NULL.getCode(), "用户名或密码错误");
        }
    }

    @GetMapping("/info")
    public Result<Admin> getLoginInfo(HttpServletRequest request)
    {
        Admin loginUser = (Admin) UserUtils.getLoginUser(request);
        loginUser.setPassword("");
        return new Result<Admin>(loginUser);
    }

    //查询管理员
    @GetMapping("/getAdmin")
    public Result<Admin> getAdmin()
    {
        Admin admin = adminService.getAdmin();
        return new Result<>(admin);
    }

    @GetMapping("/layout")
    public Result<String> layout(HttpServletRequest request)
    {
        UserUtils.logout(request);
        return new Result<>("退出成功");
    }

    //更新个人信息
    @PutMapping("/updateInfo")
    public Result<Object> updateInfo(@RequestBody Admin admin)
    {
        adminService.updateInfo(admin);
        return new Result<>("更新成功");
    }

    //更新密码
    @PutMapping("/updatePassword")
    public Result<Object> updatePassword(@RequestBody Admin admin)
    {
        adminService.updatePassword(admin);
        return new Result<>("更新成功");
    }
}
