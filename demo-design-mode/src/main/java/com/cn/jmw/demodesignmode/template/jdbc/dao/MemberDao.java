package com.cn.jmw.demodesignmode.template.jdbc.dao;


import com.cn.jmw.demodesignmode.template.jdbc.JdbcTemplate;
import com.cn.jmw.demodesignmode.template.jdbc.Member;
import com.cn.jmw.demodesignmode.template.jdbc.RowMapper;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

@Slf4j
public class MemberDao extends JdbcTemplate {
    public MemberDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<?> selectAll(){
        String sql = "select * from t_member";
        return super.executeQuery(sql, new RowMapper<Member>() {
            public Member mapRow(ResultSet rs, int rowNum) throws Exception {
                Member member = new Member();
                //字段过多，原型模式
                member.setUsername(rs.getString("username"));
                member.setPassword(rs.getString("password"));
                member.setAge(rs.getInt("age"));
                member.setAddr(rs.getString("addr"));
                return member;
            }
        },null);
    }
}
