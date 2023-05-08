package com.atguigu.fruit.service;

import com.atguigu.fruit.pojo.Fruit;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/24 17:36
 * @description TODO
 */
public interface FruitService {
    // 获取指定页面的库存列表信息
    List<Fruit> getFruitList(String keyword, Integer pageNo);

    // 添加库存记录
    void addFruit(Fruit fruit);

    // 根据ID查询指定库存记录
    Fruit getFruitByFid(Integer fid);

    // 删除库存信息
    void delFruit(Integer fid);

    // 获取总页数
    Integer getPageCount(String keyword);

    // 修改
    void updateFruit(Fruit fruit);
}
