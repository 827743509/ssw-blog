package com.ssw.blog.controller;

import com.ssw.blog.enums.ResultEnum;
import com.ssw.blog.pojo.About;
import com.ssw.blog.service.AboutService;
import com.ssw.blog.utils.Page;
import com.ssw.blog.utils.Result;
import com.ssw.blog.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 关于我
 */
@RestController
@RequestMapping("/about")
public class AboutController {
    @Autowired
    AboutService aboutService;

    @PostMapping("/save")
    public Result<Object> save(@RequestBody  About about){
        aboutService.save(about);
        return  new Result<>("保存成功!");
    }

    /**
     * 更新
     * @param about
     * @return
     */
    @PutMapping("/update")
    public  Result<Object> update(@RequestBody  About about){
        aboutService.update(about);
        return  new Result<>("修改成功!");
    }
    @GetMapping("/get/{id}")
    public  Result<About> getById(@PathVariable("id") Integer id){
       About about= aboutService.getById(id);
        return new Result<>(about);
    }

    /**
     * 阅读
     * @return
     */
    @GetMapping("/read")
    public  Result<About> readById(){
        About about= aboutService.read();
        return new Result<>(about);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable("id") Integer id){
      aboutService.deleteById(id);
      return  new Result<>("删除成功!");
    }

    /**
     * 启用
     * @param id
     * @return
     */
    @PutMapping("/enable/{id}")
    public Result<Object> enable(@PathVariable("id") Integer id)  {
        aboutService.enable(id);
        return  new Result<>("启用成功!");
    }
    /**
     * 启用
     * @param id
     * @return
     */
    @PutMapping("/disable/{id}")
    public Result<Object> disable(@PathVariable("id") Integer id)  {
        aboutService.disable(id);
        return  new Result<>("禁用成功!");
    }
    @PostMapping("/getByPage")
    public Result<Page<About>>  getByPage(@RequestBody Page<About> page){
        String sortColumn = page.getSortColumn();
        if (StringUtils.isNotBlank(sortColumn)) {
            // 排序列不为空
            String[] sortColumns = {"about_read", "created_time", "update_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if (!sortList.contains(sortColumn.toLowerCase())) {
                return new Result<>(ResultEnum.PARAMS_ERROR.getCode(), "排序参数不合法！");
            }
        }
        page = aboutService.getByPage(page);
        return new Result<>(page);
    }
}
