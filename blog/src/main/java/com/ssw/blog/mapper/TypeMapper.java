package com.ssw.blog.mapper;


import com.ssw.blog.pojo.Type;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TypeMapper {
     List<Type> findAll();

    void insert(Type type);
    Type getByName(@Param("typeName") String typeName);

    List<Type> getTypeList();

    void update(Type type);

    Type getById(Integer id);

    void deleteById(Integer id);
}
