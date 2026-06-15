package com.ssw.blog.mapper;


import com.ssw.blog.pojo.Music;
import com.ssw.blog.utils.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 接口访问日志表Mapper
 * </p>
 *
 * @author 稽哥
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 *
 */
@Component
public interface MusicMapper {

    void save(Music music);

    void update(Music music);

    Music getById(Integer id);

    void deleteById(Integer id);

    List<Music> getByPage(Page<Music> page);

    int getCountByPage(Page<Music> page);

    List<Music> getList();
}
