package com.example.mapper;

import com.example.dto.SelectAddressByUidDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TAddressMapper {
        List<SelectAddressByUidDTO> selectAddressByUid(@Param("id") Integer id);
}