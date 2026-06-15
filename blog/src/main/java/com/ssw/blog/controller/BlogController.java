package com.ssw.blog.controller;

import com.ssw.blog.enums.ResultEnum;
import com.ssw.blog.pojo.Blog;
import com.ssw.blog.service.BlogService;
import com.ssw.blog.utils.Page;
import com.ssw.blog.utils.Result;
import com.ssw.blog.utils.StringUtils;
import com.ssw.blog.vo.BlogVo;
import com.ssw.blog.vo.TimeLineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping("/save")
    public Result<Object> save(@RequestBody Blog blog) {
        blogService.save(blog);
        return new Result<>("添加成功!");
    }

    @GetMapping("/get/{id}")
    public Result<Blog> getById(@PathVariable("id") String id) {
        Blog blog = blogService.getById(id);
        if (blog == null) {
            return new Result<>(ResultEnum.DATA_NOT_FOUND);
        }
        return new Result<>(blog);
    }

    @PutMapping("/update")
    public Result<Object> update(@RequestBody Blog blog) {
        blogService.update(blog);
        return new Result<>("更新成功!");
    }

    @GetMapping("/read/{id}")
    public Result<BlogVo> read(@PathVariable("id") String id) {
        BlogVo blog = blogService.readById(id);
        return new Result<>(blog);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Object> deleteById(@PathVariable("id") String id) {
        blogService.deleteById(id);
        return new Result<>("删除成功!");
    }

    @PostMapping("/getByPage")
    public Result<Page<BlogVo>> getByPage(@RequestBody Page<BlogVo> page) {
        String sortColumn = page.getSortColumn();
        if (!StringUtils.isBlank(sortColumn)) {
            String[] sortColumns = {"type_name", "created_time", "update_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if (!sortList.contains(sortColumn.toLowerCase())) {
                return new Result<>(ResultEnum.PARAMS_ERROR.getCode(), "排序参数不合法!");
            }
        }
        page = blogService.getByPage(page);
        return new Result<>(page);
    }

    @GetMapping("/getTimeLine")
    public Result<List<TimeLineVo>> getTimeLine() {
        List<TimeLineVo> timeList = new ArrayList<>();
        List<BlogVo> blogVoList = blogService.getTimeLine();
        blogVoList.forEach(e -> {
            String blogMonth = e.getBlogMonth();
            TimeLineVo timeLineVo = new TimeLineVo();
            timeLineVo.setMonth(blogMonth);
            if (timeList.contains(timeLineVo)) {
                TimeLineVo timeLine = getTimeLineForList(timeList, timeLineVo);
                List<BlogVo> list = timeLine.getList();
                if (list == null) {
                    list = new ArrayList<>(8);
                }
                list.add(e);
                timeLine.setList(list);
            } else {
                List<BlogVo> list = timeLineVo.getList();
                if (list == null) {
                    list = new ArrayList<>(8);
                }
                list.add(e);
                timeLineVo.setList(list);
                timeList.add(timeLineVo);
            }
        });
        return new Result<>(timeList);
    }

    private TimeLineVo getTimeLineForList(List<TimeLineVo> timeList, TimeLineVo timeLineVo) {
        for (TimeLineVo lineVo : timeList) {
            if (timeLineVo.getMonth().equals(lineVo.getMonth())) {
                return lineVo;
            }
        }
        return null;
    }
}
