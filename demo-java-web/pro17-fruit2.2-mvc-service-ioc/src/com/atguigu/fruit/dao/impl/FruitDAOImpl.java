package com.atguigu.fruit.dao.impl;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.basedao.BaseDAO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/20 19:00
 * @description TODO
 */
public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {
    @Override
    public List<Fruit> getFruitList() {
        return super.executeQuery("select * from t_fruit limit 12");
    }

    /**
     * 12: 每页固定显示12条数据
     *
     * @param pageNo
     * @return
     */
    @Override
    public List<Fruit> getFruitListByPageNo(String keyword, Integer pageNo) {
        String sql = "select * from t_fruit where fname like ? or remark like ? limit ?, 12";
        return super.executeQuery(sql, "%" + keyword + "%", "%" + keyword + "%", (pageNo - 1) * 12);
    }

    @Override
    public void updateFruit(Fruit fruit) {
        String sql = "update t_fruit set fname = ?, price = ?, fcount = ?, remark = ? where fid = ?";
        super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark(), fruit.getFid());
    }

    @Override
    public Fruit getFruitByFid(Integer fid) {
        return super.load("select * from t_fruit where fid = ?", fid);
    }

    @Override
    public void delFruit(Integer fid) {
        super.executeUpdate("delete from t_fruit where fid = ?", fid);
    }

    @Override
    public void addFruit(Fruit fruit) {
        String sql = "insert into t_fruit values(0,?,?,?,?)";
        super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark());
    }

    @Override
    public int getFruitCount(String keyword) {
        String sql = "select count(*) from t_fruit where fname like ? or remark like ?";
        return ((Long) super.executeComplexQuery(sql, "%" + keyword + "%", "%" + keyword + "%")[0]).intValue();
    }
}
