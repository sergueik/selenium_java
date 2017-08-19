package com.wuwei.service;

import com.wuwei.entity.Result;
import com.wuwei.entity.Student;

public interface BaseService {

    public Result addStudent(Student student);
    
    public Result findAllStudent();

    public Result updateStudent(Student student);
    
    public Result delStudentById(String id);
}
