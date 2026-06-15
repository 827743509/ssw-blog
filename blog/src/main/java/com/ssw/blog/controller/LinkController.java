package com.ssw.blog.controller;

import com.ssw.blog.pojo.Link;
import com.ssw.blog.service.LinkService;
import com.ssw.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/link")
public class LinkController {
    @Autowired
    private LinkService linkService;
    //添加链接
    @PostMapping("/save")
    public Result<Object> save(@RequestBody Link link){
        linkService.save(link);
        return  new Result<>("添加成功!");
    }
    //修改
    @PutMapping("/update")
    public Result<Object> update(@RequestBody Link link){
        linkService.update(link);
        return  new Result<>("修改成功!");
    }
    //根据id查询
    @GetMapping("/get/{id}")
    public Result<Link> getById(@PathVariable("id") Integer id){
        Link link=linkService.getById(id);
        return  new Result<>(link);
    }
    //查询所有
    @GetMapping("/list")
    public Result<List<Link>> list(){
        List<Link> linkList=linkService.getAll();
        return  new Result<>(linkList);
    }
    //删除
    @DeleteMapping("/delete/{id}")
    public Result<Object> deleteById(@PathVariable("id") Integer id){
        linkService.deleteById(id);
        return  new Result<>("删除成功!");
    }
}
