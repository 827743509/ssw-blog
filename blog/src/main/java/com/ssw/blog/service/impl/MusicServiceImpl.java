package com.ssw.blog.service.impl;


import com.ssw.blog.enums.StateEnums;
import com.ssw.blog.mapper.MusicMapper;
import com.ssw.blog.pojo.Music;
import com.ssw.blog.service.AboutService;
import com.ssw.blog.service.MusicService;
import com.ssw.blog.utils.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MusicServiceImpl implements MusicService {
    @Autowired
    private MusicMapper musicMapper;

    /**
     * 添加
     * @param music
     */
    @Override
    public void save(Music music) {
       musicMapper.save(music);
    }

    /**
     * 修改
     * @param music
     */
    @Override
    public void update(Music music) {
            musicMapper.update(music);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Music getById(Integer id) {
        return musicMapper.getById(id);
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
           musicMapper.deleteById(id);
    }

    /**
     * 启用
     * @param id
     */
    @Override
    public void enableById(Integer id) {

          Music music=  musicMapper.getById(id);
          music.setEnable(StateEnums.ENABLED.getCode());
          musicMapper.update(music);
    }

    /**
     * 禁用
     * @param id
     */
    @Override
    public void disableById(Integer id) {
        Music music=  musicMapper.getById(id);
        music.setEnable(StateEnums.NOT_ENABLE.getCode());
        musicMapper.update(music);
    }

    /**
     * 分页
     * @param page
     * @return
     */
    @Override
    public Page<Music> getByPage(Page<Music> page) {
        // 查询数据
        List<Music> musicList = musicMapper.getByPage(page);
        page.setList(musicList);
        // 查询总数
        int totalCount = musicMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Music> getList() {
        return musicMapper.getList();
    }
}
