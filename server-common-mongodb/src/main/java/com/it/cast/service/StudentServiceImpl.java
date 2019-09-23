package com.it.cast.service;

import com.it.cast.dao.StudentRepository;
import com.it.cast.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description: 实现类
 * @author: WuZhiLong
 * @create: 2019-06-13 17:21
 **/
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    /**
     * 添加学生信息
     * @param student
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    /**
     * 根据 id 删除学生信息
     * @param id
     */
    @Override
    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }

    /**
     * 更新学生信息
     * @param student
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Student updateStudent(Student student) {
        Student oldStudent = this.findStudentById(student.getId());
        if (oldStudent != null){
            oldStudent.setStudentId(student.getStudentId());
            oldStudent.setAge(student.getAge());
            oldStudent.setName(student.getName());
            oldStudent.setGender(student.getGender());
            return studentRepository.save(oldStudent);
        } else {
            return null;
        }
    }

    /**
     * 根据 id 查询学生信息
     * @param id
     * @return
     */
    @Override
    public Student findStudentById(String id) {
        return studentRepository.findById(id).get();
    }

    /**
     * 查询学生信息列表
     * @return
     */
    @Override
    public List<Student> findAllStudent() {
        return studentRepository.findAll();
    }
}