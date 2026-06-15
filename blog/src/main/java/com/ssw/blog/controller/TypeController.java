package com.ssw.blog.controller;

import com.ssw.blog.pojo.Type;
import com.ssw.blog.service.TypeService;
import com.ssw.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeService typeService;
    //根据id查询
    @GetMapping("/get/{id}")
    public Result<Object> getById(@PathVariable("id") Integer id){
        Type type =typeService.getById(id);
        return  new Result<>(type);
    }
    //添加类型
    @PostMapping("/save")
    public Result<Object> save(@RequestBody Type type){
        typeService.save(type);
        return  new Result<>("添加成功!");
    }
    //后台查询所有
    @GetMapping("/listBack")
    public  Result<List<Type>> list(){
       List<Type> typeList= typeService.findAll();
       return   new Result<>(typeList);
    }
    //前台查询所有
    @GetMapping("/getList")
    public  Result<List<Type>> getList(){
        List<Type> typeList= typeService.findTypeList();
        return   new Result<>(typeList);
    }
    //更新分类
    @PutMapping("/update")
    public  Result<Object> update(@RequestBody  Type type){
             typeService.update(type);
        return   new Result<>("更新成功");
    }
    //删除
    @DeleteMapping("/delete/{id}")
    public Result<Object> deleteById(@PathVariable("id") Integer id){
        typeService.deleteById(id);
        return  new Result<>("删除成功");
    }
}
