package com.example.service.impl;


import com.example.dto.SelectAddressByUidDTO;
import com.example.service.TAddressService;
import com.example.vo.ResultVO;
import com.example.dto.SelectAddressByUidDTO;
import com.example.vo.SelectAddressByUIDVO;
import org.springframework.stereotype.Service;
import com.example.mapper.TAddressMapper;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class TAddressServiceImpl implements TAddressService {

    @Resource
    private TAddressMapper tAddressMapper;

    @Override
    public ResultVO selectAddressByUid(Integer id) {
        List<SelectAddressByUidDTO> selectAddressByUidDTOS = tAddressMapper.selectAddressByUid(id);
        SelectAddressByUIDVO selectAddressByUIDVO = new SelectAddressByUIDVO();
        List<String> lists = new ArrayList<>();
        for(SelectAddressByUidDTO selectAddressByUidDTO : selectAddressByUidDTOS){
            if(selectAddressByUIDVO.getId() == null) {
                selectAddressByUIDVO.setId(selectAddressByUidDTO.getId());
            }
            if(selectAddressByUIDVO.getName() == null){
                selectAddressByUIDVO.setName(selectAddressByUidDTO.getName());
            }
            lists.add(selectAddressByUidDTO.getAddressName());
        }
        selectAddressByUIDVO.settAddressList(lists);
        return ResultVO.success("200","成功",selectAddressByUIDVO);
    }
}
