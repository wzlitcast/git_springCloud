package com.it.cast.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

/**
 * @description: 实体类
 * @author: WuZhiLong
 * @create: 2019-06-13 17:18
 **/
@Data
public class Student {

    @Id
    private String id;

    @NotNull
    private String studentId;

    private Integer age;

    private String name;

    private String gender;

}