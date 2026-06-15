package com.ssw.blog.mapper;


import com.ssw.blog.pojo.Blog;
import com.ssw.blog.pojo.Type;
import com.ssw.blog.utils.Page;
import com.ssw.blog.vo.BlogVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface BlogMapper {

     Blog getById(String id);

    void save(Blog blog);

    void update(Blog blog);

    void deleteById(String id);

    void deleteBlogTypes(String blogId);

    void saveBlogTypes(@Param("blogId") String blogId, @Param("typeIds") List<Integer> typeIds);

    List<Integer> getTypeIdsByBlogId(String blogId);

    List<Type> getTypesByBlogId(String blogId);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<BlogVo> getByPage(Page<BlogVo> page);

    int getCountByPage(Page<BlogVo> page);

    List<BlogVo> getTimeLine();
}
