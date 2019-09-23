package com.it.cast.dao;

import com.it.cast.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @description: dao
 * @author: WuZhiLong
 * @create: 2019-06-13 17:22
 **/
@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
}