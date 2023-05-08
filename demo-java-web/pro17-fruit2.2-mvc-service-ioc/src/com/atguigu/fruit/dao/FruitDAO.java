package com.atguigu.fruit.dao;

import com.atguigu.fruit.pojo.Fruit;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/20 18:59
 * @description TODO
 */
public interface FruitDAO {
    /**
     * 获取所有的库存列表信息
     *
     * @return
     */
    List<Fruit> getFruitList();

    /**
     * 获取指定页码上的所有库存信息，每页显示12条
     */
    List<Fruit> getFruitListByPageNo(String keyword, Integer pageNo);

    /**
     * 根据fid获取特定的水果库存信息
     *
     * @param fid
     * @return
     */
    Fruit getFruitByFid(Integer fid);

    /**
     * 修改
     *
     * @param fruit
     */
    void updateFruit(Fruit fruit);

    /**
     * 根据fid删除指定的库存记录
     */
    void delFruit(Integer fid);

    /**
     * 添加记录
     */
    void addFruit(Fruit fruit);

    /**
     * 查询库存总记录条数
     */
    int getFruitCount(String keyword);


}
