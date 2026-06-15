package com.ssw.blog.service.impl;


import com.ssw.blog.exception.BlogException;
import com.ssw.blog.mapper.TypeMapper;
import com.ssw.blog.pojo.Type;
import com.ssw.blog.service.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;
      public void save(Type type){

         Type t= typeMapper.getByName(type.getTypeName());
           if(t!=null){
               throw  new BlogException("改分类已存在!");
           }
           typeMapper.insert(type);
      }

    @Override
    public List<Type> findAll() {
        return typeMapper.findAll();
    }

    @Override
    public List<Type> findTypeList() {
        return typeMapper.getTypeList();
    }

    @Override
    public void update(Type type) {
       typeMapper.update(type);
    }

    @Override
    public void deleteById(Integer id) {
        typeMapper.deleteById(id);
    }

    @Override
    public Type getById(Integer id) {
        return typeMapper.getById(id);
    }


}
