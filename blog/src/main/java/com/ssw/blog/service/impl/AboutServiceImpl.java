package com.ssw.blog.service.impl;


import com.ssw.blog.enums.ResultEnum;
import com.ssw.blog.enums.StateEnums;
import com.ssw.blog.exception.BlogException;
import com.ssw.blog.mapper.AboutMapper;
import com.ssw.blog.pojo.About;
import com.ssw.blog.service.AboutService;
import com.ssw.blog.service.AdminService;
import com.ssw.blog.utils.Page;
import com.ssw.blog.vo.BlogVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Slf4j
public class AboutServiceImpl implements AboutService {
    @Autowired
    private AboutMapper aboutMapper;

    /**
     * 添加
     * @param about
     */
    @Override
    public void save(About about) {
      aboutMapper.save(about);
    }

    /**
     * 根据id更新
     * @param about
     */
    @Override
    public void update(About about) {
        aboutMapper.updateById(about);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public About getById(Integer id) {

        return aboutMapper.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public About read() {
        About about=aboutMapper.getAbout();
        if(about==null){
            return null;
        }
        //更新阅读数
        about.setAboutRead(about.getAboutRead()+1);
        aboutMapper.updateById(about);
        return about;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
       aboutMapper.deleteById(id);
    }

    /**
     * 根据id启用
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enable(Integer id) {
        //查询是否已经存在启用
        About about = aboutMapper.getAbout();
        if(about!=null){
            throw new BlogException(ResultEnum.ERROR.getCode(),"当前以存在已启用的关于我的");
        }
        about = aboutMapper.getById(id);
        about.setEnable(StateEnums.ENABLED.getCode());
        aboutMapper.updateEnable(about);
    }

    /**
     * 根据id弃用
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disable(Integer id) {
        //查询是否已经存在启用
        About about = aboutMapper.getAbout();
        about.setEnable(StateEnums.NOT_ENABLE.getCode());
        aboutMapper.updateEnable(about);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    public Page<About> getByPage(Page<About> page) {
        //查询数据
        List<About> aboutList= aboutMapper.getByPage(page);
        page.setList(aboutList);
        //查询总数
        int totalCount=aboutMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

}
