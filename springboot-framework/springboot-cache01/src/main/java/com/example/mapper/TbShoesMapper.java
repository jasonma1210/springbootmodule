package com.example.mapper;

import com.example.dto.TbShoes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TbShoesMapper {
            List<TbShoes> selectAll();
    public TbShoes queryTbShoes(@Param("id") Integer id);
}