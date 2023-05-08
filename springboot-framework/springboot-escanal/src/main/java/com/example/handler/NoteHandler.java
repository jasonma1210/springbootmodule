package com.example.handler;

import com.example.dto.TNote;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

@CanalTable("t_note")
@Component
public class NoteHandler  implements EntryHandler<TNote> {
    /**
     * mysql中数据有新增时自动执行
     * @param tNote 新增的数据
     */
    @Override
    public void insert(TNote tNote) {
        //把新增数据hotel,添加到ES即可
    }

    /**
     * mysql中数据有修改时自动执行
     * @param before 修改前的数据
     * @param after 修改后的数据
     */
    @Override
    public void update(TNote before, TNote after) {
        //把修改数据,更新到ES即可
    }

    /**
     * ysql中数据有删除时自动执行
     * @param tNote 要删除的数据
     */
    @Override
    public void delete(TNote tNote) {
        //把要删除的数据hotel,从ES删除即可
    }
}
