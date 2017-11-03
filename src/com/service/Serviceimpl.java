package com.service;

import com.dao.Dao;
import com.dao.DaoImpl;
import com.domain.Person;
import com.utils.RandomUtils;

import java.util.*;

public class Serviceimpl implements Service {

    @Override
    public Person search(int quan) throws Exception {
        Dao dao = new DaoImpl();
        ArrayList<String> idList = new ArrayList<String>();
        List<Map<String, Object>> allList = dao.getRanObj();

        for (Map<String, Object> temp : allList) {
            String id = temp.get("id").toString();
            int weight = Integer.parseInt(temp.get("weight").toString());
            for (int i = 1; i <= weight; i++) {
                idList.add(id);
            }
        }

        RandomUtils ru = new RandomUtils();
        String result = ru.RandomId(idList);

        Person t = dao.findPerson(result);

        if (t == null) {
            return null;
        }
        dao.alterCount(t.getId(), quan);

        return t;
    }

    @Override
    public List<Object> getAllId() throws Exception {
        Dao dao = new DaoImpl();
        List<Object> idArr = dao.findPersonId();
        return idArr;
    }

    @Override
    public List<Person> getAllBean() throws Exception {
        Dao dao = new DaoImpl();
        List<Person> allBean = dao.getBeanList();
        return allBean;
    }


}
