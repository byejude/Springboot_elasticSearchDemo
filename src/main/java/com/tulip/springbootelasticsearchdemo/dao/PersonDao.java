package com.tulip.springbootelasticsearchdemo.dao;

import com.tulip.springbootelasticsearchdemo.domain.Person;

public interface PersonDao {
    String save(Person person);

    String update(Person person);

    String delete(String id);

    Object find(String id);

    Object query(Person person);
}
