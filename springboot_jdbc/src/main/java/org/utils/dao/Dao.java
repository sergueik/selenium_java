package org.utils.dao;

import java.util.List;

import org.utils.entity.Student;

public interface Dao {

    public int addStudent(Student student);
    public List<?> findAllStudent();
    public int updateStudent(Student student);
    public int delStudentById(long id);
}
