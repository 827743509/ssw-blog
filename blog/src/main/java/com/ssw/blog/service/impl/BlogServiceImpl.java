package com.ssw.blog.service.impl;

import com.ssw.blog.mapper.BlogMapper;
import com.ssw.blog.pojo.Blog;
import com.ssw.blog.pojo.Type;
import com.ssw.blog.service.BlogService;
import com.ssw.blog.utils.IdWorker;
import com.ssw.blog.utils.Page;
import com.ssw.blog.vo.BlogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private IdWorker idWorker;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Blog blog) {
        blog.setBlogId(idWorker.nextId() + "");
        blogMapper.save(blog);
        saveBlogTypes(blog);
    }

    @Override
    public Blog getById(String id) {
        Blog blog = blogMapper.getById(id);
        if (blog != null) {
            blog.setTypeIds(blogMapper.getTypeIdsByBlogId(id));
        }
        return blog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Blog blog) {
        blogMapper.update(blog);
        blogMapper.deleteBlogTypes(blog.getBlogId());
        saveBlogTypes(blog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BlogVo readById(String id) {
        Blog blog = blogMapper.getById(id);

        BlogVo blogVo = new BlogVo();
        BeanUtils.copyProperties(blog, blogVo);
        fillTypes(blogVo);
        return blogVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        blogMapper.deleteBlogTypes(id);
        blogMapper.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Page<BlogVo> getByPage(Page<BlogVo> page) {
        List<BlogVo> blogVoList = blogMapper.getByPage(page);
        blogVoList.forEach(this::fillTypes);
        page.setList(blogVoList);
        int totalCount = blogMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public List<BlogVo> getTimeLine() {
        return blogMapper.getTimeLine();
    }

    private void saveBlogTypes(Blog blog) {
        if (blog.getTypeIds() != null && !blog.getTypeIds().isEmpty()) {
            blogMapper.saveBlogTypes(blog.getBlogId(), blog.getTypeIds());
        }
    }

    private void fillTypes(BlogVo blogVo) {
        List<Type> types = blogMapper.getTypesByBlogId(blogVo.getBlogId());
        blogVo.setType(types);
        if (types != null && !types.isEmpty()) {
            StringBuilder typeName = new StringBuilder();
            for (Type type : types) {
                if (typeName.length() > 0) {
                    typeName.append(",");
                }
                typeName.append(type.getTypeName());
            }
            blogVo.setTypeName(typeName.toString());
        }
    }
}
