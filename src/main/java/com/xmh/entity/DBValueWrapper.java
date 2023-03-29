package com.xmh.entity;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * .
 *
 * @author 谢明辉
 * @create 2022/11/17 15:50
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class DBValueWrapper<T> extends DBValue {

    private T obj;

    public DBValueWrapper(T obj) {
        this.obj = obj;
    }

    @Override
    public void setValue(PreparedStatement ps, int paramIndex) throws SQLException {
        ps.setString(paramIndex, JSON.toJSONString(obj));
    }


}
