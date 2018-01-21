package com.tulip.springbootelasticsearchdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@AllArgsConstructor
@Data
public class Person {
    private String id;
    private String name;
    private int age;
    private String sex;
    private Date birthday;
    private String introduce;

    public Person( String name, int age, String sex, Date birthday, String introduce) {
        super();
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
        this.introduce = introduce;
    }
}
