package com.wuwei.test;

import com.wuwei.entity.Student;
import com.wuwei.util.JDBCUtils;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnectionTest {

    public static void main(String[] args) {
        queryTest();
    }

    public static void queryTest() {
        try {
            Connection conn = JDBCUtils.getConnection();
            String sql = "select * from student where id = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, 1);
            ResultSet rs = pre.executeQuery();
            List<?> list = JDBCUtils.TranverseToList(rs, Student.class);
            for (int i = 0; i < list.size(); i++) {
                Student student = (Student) list.get(i);
                System.out.println(student);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DatabaseConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JDBCUtils.close();
        }
    }
}
