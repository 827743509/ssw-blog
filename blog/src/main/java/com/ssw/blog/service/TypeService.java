package com.ssw.blog.service;


import com.ssw.blog.pojo.Type;

import java.util.List;

public interface TypeService {
     //添加类型
     void save(Type type);
     //后台查询所有
     List<Type> findAll();
     //前台查询所有
     List<Type> findTypeList();

     void update(Type type);
     //根据id启用
     //根据id弃用
     //根据id删除
     void deleteById(Integer id);
     //根据id查询
    Type getById(Integer id);
}
