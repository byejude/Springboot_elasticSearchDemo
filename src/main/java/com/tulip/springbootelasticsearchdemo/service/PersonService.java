package com.tulip.springbootelasticsearchdemo.service;

import com.tulip.springbootelasticsearchdemo.domain.Person;
import com.tulip.springbootelasticsearchdemo.domain.Result;

import java.util.Map;

public interface PersonService {

    Result<Object> savePerson(Person person);

    Result<Object> updatePerson(Person person);

    Result<Object> delPerson(String id);

    Result<Object> findPerson(String id);

    Result<Object> queryPerson(Person person);
}
