package com.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import com.domain.Person;
import com.utils.DataSourceUtils;

public class DaoImpl implements Dao {
    /*
     * 找到被抽选的人的所有信息
     */
    @Override
    public Person findPerson(String id) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

        String sql = "select * from student_info where id= ?";

        return qr.query(sql, new BeanHandler<Person>(Person.class), id);
    }

	/*
     * 把所有人的学号取出来，放在ArrayList里
	 */

    @Override
    public List<Object> findPersonId() throws Exception {
        List<Object> idArrayList;
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

        String sql = "select * from student_info";

        idArrayList = qr.query(sql, new ColumnListHandler("id"));

        return idArrayList;
    }

    /*
     * 修改圈数
     */
    @Override
    public void alterCount(String id, int count) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

        String sql = "update student_info set count=count+ ? where id= ?";
        String sql1 = "update student_info set times=times+ 1 where id= ?";

        qr.update(sql, count, id);
        qr.update(sql1, id);
    }

    @Override
    public List<Person> getBeanList() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

        String sql = "select * from student_info";

        List<Person> beanList = qr.query(sql, new BeanListHandler<Person>(Person.class));

        return beanList;
    }

    @Override
    public List<Map<String, Object>> getRanObj() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select id,weight from student_info";

        List<Map<String, Object>> p = qr.query(sql, new MapListHandler());
        return p;
    }

}
