package com.ssw.blog.service;


import com.ssw.blog.pojo.Music;
import com.ssw.blog.utils.Page;

import java.util.List;

public interface MusicService {

    void save(Music music);

    void update(Music music);

    Music getById(Integer id);

    void deleteById(Integer id);

    void enableById(Integer id);

    void disableById(Integer id);

    Page<Music> getByPage(Page<Music> page);

    List<Music> getList();
}
