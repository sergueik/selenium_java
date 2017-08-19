package com.wuwei.dao;

import com.wuwei.entity.Student;
import java.util.List;

public interface Dao {

    public int addStudent(Student student);

    public List<?> findAllStudent();

    public int updateStudent(Student student);

    public int delStudentById(long id);

}
