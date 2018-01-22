package com.tulip.springbootelasticsearchdemo.service.Impl;

import com.tulip.springbootelasticsearchdemo.dao.PersonDao;
import com.tulip.springbootelasticsearchdemo.domain.Person;
import com.tulip.springbootelasticsearchdemo.domain.Result;
import com.tulip.springbootelasticsearchdemo.service.PersonService;
import com.tulip.springbootelasticsearchdemo.util.ResultUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PersonServiceImpl implements PersonService{

    private Logger log = Logger.getLogger(getClass());

    @Autowired
    private PersonDao personDao;

    @Override
    public Result<Object> savePerson(Person person) {
        String id = null;

        try {
            id = personDao.save(person);
            log.info("save person is "+person);
            log.info("the id is "+id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("save error",e);
            return ResultUtil.error();
        }
        return ResultUtil.success(id);
    }

    @Override
    public Result<Object> updatePerson(Person person) {
        String id = null;

        try {
            id = personDao.update(person);
            log.info("update person is "+person);
            log.info("the id is "+id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("update error",e);
            return ResultUtil.error();
        }
        return ResultUtil.success(id);
    }

    @Override
    public Result<Object> delPerson(String id) {

        try {
            id = personDao.delete(id);

            log.info("the id is "+id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("delete error",e);
            return ResultUtil.error();
        }
        return ResultUtil.success(id);
    }

    @Override
    public Result<Object> findPerson(String id) {
        Object obj = null;
        try {
            obj = personDao.find(id);

            log.info("the id is "+id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("find error",e);
            return ResultUtil.error();
        }
        return ResultUtil.success(obj);
    }

    @Override
    public Result<Object> queryPerson(Person person) {
        Object obj = null;
        try {
            obj = personDao.query(person);

            log.info("the query person is "+obj);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("find error",e);
            return ResultUtil.error();
        }
        return ResultUtil.success(obj);
    }
}
