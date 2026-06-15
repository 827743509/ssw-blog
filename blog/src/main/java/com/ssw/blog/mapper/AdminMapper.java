package com.ssw.blog.mapper;


import com.ssw.blog.pojo.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 管理员表Mapper
 * </p>
 *
 * @author 稽哥
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 */
@Component
public interface AdminMapper {

    Admin getByUserName(@Param("username") String username);

    Admin getAdmin();

    void updateInfo(Admin admin);
}
