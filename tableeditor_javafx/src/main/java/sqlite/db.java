package sqlite;

import pswkeeper.Constants;

import java.sql.*;

public class db {

    private static db dbInstance;
    private static Connection con ;

    private db() {
    }

    public static db getInstance(){
        if(dbInstance == null){
            dbInstance = new db();
        }
        return dbInstance;
    }

    // Подключение БД
    public static Connection getConnection() {
        if (con == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                con = DriverManager.getConnection("jdbc:sqlite:" + Constants.PATH_TO_DB);
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return con;
    }


}
