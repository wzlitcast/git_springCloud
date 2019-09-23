package com.it.cast.controller;

import com.it.cast.entity.Student;
import com.it.cast.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 控制层
 * @author: WuZhiLong
 * @create: 2019-06-13 17:19
 **/
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @PutMapping("/update")
    public Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }

    @GetMapping("/{id}")
    public Student findStudentById(@PathVariable("id") String id){
        return studentService.findStudentById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable("id") String id){
        studentService.deleteStudent(id);
    }

    @GetMapping("/list")
    public List<Student> findAllStudent(){
        return studentService.findAllStudent();
    }

}