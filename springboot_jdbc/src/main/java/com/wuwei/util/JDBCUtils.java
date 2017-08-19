package com.wuwei.util;

import java.sql.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JDBC工具类
 *
 * @author Wu Wei
 * @date 2017-8-9 18:33:13
 */
public class JDBCUtils {
    
    private static final Logger logger = Logger.getLogger(JDBCUtils.class.getName());
    public static Connection connection = null;
    public static PreparedStatement preparedStatement = null;
    public static ResultSet resultSet = null;

    private JDBCUtils() {
    }

    /**
     * 获取JDBC连接
     *
     * @return
     */
    public static Connection getConnection() {
        String url = "jdbc:sqlserver://localhost:1434;databaseName=student";
        String username = "sa";
        String password = "123456";
        if (connection != null) {
            return connection;
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    /**
     * 关闭资源
     */
    public static void close() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
            if (preparedStatement != null) {
                preparedStatement.close();
                preparedStatement = null;
            }
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

    /**
     * 将结果集转换成实体对象集合
     *
     * @param rs
     * @param clazz
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @return
     */
    public static List<?> TranverseToList(ResultSet rs, Class<?> clazz) throws SQLException, InstantiationException, IllegalAccessException {
        //结果集中列的名称和类型的信息
        ResultSetMetaData rsm = rs.getMetaData();
        int colNumber = rsm.getColumnCount();
        List<Object> list = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        //遍历每条记录
        while (rs.next()) {
            //实例化对象
            Object obj = clazz.newInstance();
            //取出每一个字段进行赋值
            for (int i = 1; i <= colNumber; i++) {
                Object value = rs.getObject(i);
                //匹配实体类中对应的属性
                for (Field f : fields) {
                    if (f.getName().equals(rsm.getColumnName(i))) {
                        boolean flag = f.isAccessible();
                        f.setAccessible(true);
                        f.set(obj, value);
                        f.setAccessible(flag);
                        break;
                    }
                }
            }
            list.add(obj);
        }
        return list;
    }
}
