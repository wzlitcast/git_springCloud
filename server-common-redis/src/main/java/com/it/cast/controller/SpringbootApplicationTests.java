package com.it.cast.controller;

import com.alibaba.fastjson.JSONObject;
import com.it.cast.entiry.Person;
import com.it.cast.listener.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

    private JSONObject json = new JSONObject();

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void test1(){
        Person person1 = new Person("person1", "male");
        Person person2 = new Person("person2", "female");
        Person person3 = new Person("person3", "male");
        List<Person> list = new ArrayList<>();
        list.add(person1);
        list.add(person2);
        list.add(person3);
        redisService.addList("personList1",list);
    }

    @Test
    public void test2(){
        System.out.println(json.toJSONString(redisService.getList("personList1")));
    }

    @Test
    public void test3(){
        System.out.println(json.toJSONString(redisService.getAllKeys()));
    }

}
