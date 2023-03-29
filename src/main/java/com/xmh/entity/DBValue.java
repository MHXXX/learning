package com.xmh.entity;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.SqlValue;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * .
 *
 * @author 谢明辉
 * @create 2022/11/17 15:44
 */
@Slf4j
public class DBValue implements SqlValue {


    @Override
    public void setValue(PreparedStatement ps, int paramIndex) throws SQLException {
        ps.setString(paramIndex, JSON.toJSONString(this));
    }

    @Override
    public void cleanup() {

    }
}
