package com.ssw.blog.controller;

import com.ssw.blog.enums.ResultEnum;
import com.ssw.blog.pojo.User;
import com.ssw.blog.service.UserService;
import com.ssw.blog.utils.Page;
import com.ssw.blog.utils.Result;
import com.ssw.blog.utils.UserUtils;
import com.ssw.blog.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: ssw
 * @Date: 2020/4/1 20:45
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 保存
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Object> save(@RequestBody User user) {
        userService.save(user);
        return new Result<>("添加成功！");
    }
      @GetMapping("/logout")
      public  Result<String> logout(HttpServletRequest request){
          UserUtils.logout(request);
          return  new Result<>("退出成功!");
      }
    /**
     * 更新
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody User user) {
        userService.update(user);
        return new Result<>("修改成功！");
    }

    /**
     * 修改个人信息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/updateInfo", method = RequestMethod.PUT)
    public Result<Object> updateInfo(@RequestBody User user) {
        userService.updateInfo(user);
        return new Result<>("修改成功！");
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<User> get(@PathVariable Integer id) {
        User user = userService.getById(id);
        return new Result<>(user);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<Object> delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return new Result<>("删除成功！");
    }

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<User>> getByPage(@RequestBody Page<User> page) {
        String sortColumn = page.getSortColumn();
        if (StringUtils.isNotBlank(sortColumn)) {
            // 排序列不为空
            String[] sortColumns = {"sex", "created_time", "update_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if (!sortList.contains(sortColumn.toLowerCase())) {
                return new Result<>(ResultEnum.PARAMS_ERROR.getCode(), "排序参数不合法！");
            }
        }
        page = userService.getByPage(page);
        return new Result<>(page);
    }

    /**
     * 重置密码
     * @param userIds
     * @return
     */
    @PutMapping("/resetPwd")
    public Result<Object> resetPwd(@RequestBody List<Integer> userIds) {
        userService.resetPwd(userIds);
        return new Result<>("重置成功");
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result<Object> register(@RequestBody User user) {
        userService.register(user);
        return new Result<>("註冊成功!");
    }
    /**
     * 用户登录
     * @author  zhouxq
     * @version 2020/5/10
     * @see     UserController
     * @since   从哪个版本开始有
     */
    @PostMapping("/login")
    public  Result<Object> login(@RequestBody User user,HttpServletRequest request){
        if(user==null|| StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())){
            return  new Result<>(ResultEnum.PARAMS_NULL.getCode(),"用户名或密码错误");
        }
        try {
               User useru=userService.login(user);
            //登录成功
            HttpSession session = request.getSession();
            session.setAttribute("token", useru);
            return new Result<>("登录成功!",useru);
        }catch (Exception e){

            return  new Result<>(ResultEnum.PARAMS_NULL.getCode(),"用户名或密码错误");
        }
    }
}
