package com.ssw.blog.service.impl;


import com.ssw.blog.mapper.LinkMapper;
import com.ssw.blog.pojo.Link;
import com.ssw.blog.service.AboutService;
import com.ssw.blog.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LinkServiceImpl implements LinkService {
    @Autowired
    private  LinkMapper linkMapper;
    //添加
    @Override
    public void save(Link link) {
     linkMapper.save(link);
    }

    @Override
    public void update(Link link) {
        linkMapper.update(link);
    }

    @Override
    public Link getById(Integer id) {

        return linkMapper.getById(id);
    }

    @Override
    public List<Link> getAll() {
        return linkMapper.getAll();
    }

    @Override
    public void deleteById(Integer id) {
        linkMapper.deleteById(id);
    }
}
