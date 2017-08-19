package com.wuwei.service;

import com.wuwei.dao.Dao;
import com.wuwei.entity.Result;
import com.wuwei.entity.Student;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ServiceImpl implements BaseService {

    @Resource(name = "JdbcDao")
    private Dao dao;
    private static final Logger logger = Logger.getLogger(ServiceImpl.class.getName());

    @Override
    public Result addStudent(Student student) {
        Result result = new Result();
        try {
            int res = dao.addStudent(student);
            result.setStatus(res);
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
        return result;
    }

    @Override
    public Result findAllStudent() {
        Result result = new Result();
        try {
            List<?> students = dao.findAllStudent();
            result.setStatus(1);
            result.setData(students);
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
        return result;
    }

    @Override
    public Result updateStudent(Student student) {
        Result result = new Result();
        try {
            int res = dao.updateStudent(student);
            result.setStatus(res);
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
        return result;
    }

    @Override
    public Result delStudentById(String id) {
        Result result = new Result();
        try {
            int res = dao.delStudentById(Long.parseLong(id));
            result.setStatus(res);
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
        return result;
    }
}
