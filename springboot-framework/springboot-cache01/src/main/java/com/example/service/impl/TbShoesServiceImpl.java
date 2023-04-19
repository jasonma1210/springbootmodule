package com.example.service.impl;

import cn.hutool.bloomfilter.BitMapBloomFilter;
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

    /**
     * 获得缓存中的结果
     *   1）如果缓存中有结果直接拿
     *   2）如果缓存中没有结果
     *      2-1）先从数据库中读取
     *      2-2）再写会到缓存中    目的：其他用户就不会再次进入数据库中频繁读取
     * @param id
     * @return
     */
    @Override
    public TbShoes getTbShoesById(Integer id) {

           boolean flag =  bloomFilter.mightContain(String.valueOf(id));
        TbShoes tbShoes = null;
        if(flag == true) {
               //1.先判断缓存中有没有结果
               tbShoes = tbShoesManage.getCacheTbShoes(cachePrefix + id);
               //当前结果是缓存中没有，所以需要从数据库中去读取
               if (tbShoes == null) {
                   TbShoes tbShoes1 = tbShoesMapper.queryTbShoes(id);
                   if (tbShoes1 != null) {
                       Map<String, Object> map = new HashMap<>();
                       map.put(cachePrefix + id, tbShoes1);
                       tbShoesManage.mSet(map);
                       tbShoes = tbShoes1;
                       map = null;
                   }
               }
           }

            return tbShoes;
    }


}
