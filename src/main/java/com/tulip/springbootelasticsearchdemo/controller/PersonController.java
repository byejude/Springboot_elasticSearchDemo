package com.tulip.springbootelasticsearchdemo.controller;

import com.tulip.springbootelasticsearchdemo.domain.Person;
import com.tulip.springbootelasticsearchdemo.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
@Slf4j
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/save/person",method = RequestMethod.POST)
    public Object saveParam(@RequestParam(name = "name") String name,
                            @RequestParam(name = "sex")String sex,
                            @RequestParam(name="age") Integer age,
                            @RequestParam(name="introduce")String introduce,
                            @RequestParam(name="birthday")@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date birthday){

      log.info("name {}",name);
      log.info("sex {}",sex);
      log.info("age {}",age);
      log.info("introduce {}",introduce);
      log.info("birthday {}",birthday);

      Person person = new Person(name,age,sex,birthday,introduce);
      return personService.savePerson(person);
    }

    @RequestMapping(value = "/update/person/{id}",method = RequestMethod.POST)
    public Object updatePerson(@PathVariable("id") String id,
                               @RequestParam(name = "name") String name,
                               @RequestParam(name = "sex")String sex,
                               @RequestParam(name = "age")Integer age,
                               @RequestParam(name = "introduce")String introduce,
                               @RequestParam(name = "birthday")@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date birthday){

        Person person = new Person(name,age,sex,birthday,introduce);
        person.setId(id);
        return personService.savePerson(person);
    }

    @RequestMapping(value = "/del/person/{id}",method = RequestMethod.POST)
    public Object delPerson(@PathVariable("id") String id){
        return personService.delPerson(id);
    }

    @RequestMapping(value = "/person/{id}",method = RequestMethod.GET)
    public Object getPerson(@PathVariable("id") String id){
        return personService.findPerson(id);
    }

    @RequestMapping(value = "/query/person/_search",method = RequestMethod.POST)
    public Object queryPerson(@RequestParam(name = "name",required = false) String name,
                              @RequestParam(name="age",required = false,defaultValue = "0") Integer age,
                              @RequestParam(name = "introduce",required = false)String introduce){
        Person person = new Person(name,age,null,null,introduce);
        return personService.queryPerson(person);
    }




}
