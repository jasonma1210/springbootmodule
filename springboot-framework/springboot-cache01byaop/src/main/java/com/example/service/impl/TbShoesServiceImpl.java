package com.example.service.impl;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import com.example.aop.Customer;
import com.example.dto.TbShoes;
import com.example.manage.TbShoesManage;
import com.example.mapper.TbShoesMapper;
import com.example.service.TbShoesService;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TbShoesServiceImpl implements TbShoesService {

@Resource
private TbShoesMapper tbShoesMapper;

@Resource
private TbShoesManage tbShoesManage;

private static String cachePrefix = "shopping:shoes:id:";

    //添加布隆过滤器，存放存在的id
    private BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 10000, 0.01);
    @Override
    public void insertAll() {

            //1.清理所有的缓存内容
        tbShoesManage.flushCurrentDB();
        //2.查询当前数据库中得物的数据内容，并进行处理
        List<TbShoes> tbShoes = tbShoesMapper.selectAll();


//        System.out.println(bloomFilter.mightContain("guangzhou"));
//        System.out.println(bloomFilter.mightContain("shenzhen"));


        //3.写入到缓存中
        Map<String,Object> maps = new HashMap<>();
        for(TbShoes tbShoe : tbShoes){
            bloomFilter.put(tbShoe.getId());
            maps.put(cachePrefix+tbShoe.getId(),tbShoe);
        }
        tbShoesManage.mSet(maps);

    }


    @Override
    @Customer(prefix = "shopping:shoes:id:")
    public TbShoes getTbShoesById(Integer id) {
        TbShoes   tbShoes = tbShoesMapper.queryTbShoes(id);
            return tbShoes;
    }


}
