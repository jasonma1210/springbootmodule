package com.example.manage;


import com.example.dto.TbShoes;

import java.util.Map;

/**
 * 操纵redis的实现
 */
public interface TbShoesManage {

    public void mSet(Map<String,Object> map);
    public void flushCurrentDB();

    public TbShoes getCacheTbShoes(String key);
}
