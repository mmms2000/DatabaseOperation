package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // set up a database connection
    private static final String URL = "jdbc:mysql://localhost:3306/test_schema?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "moses325";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // MySQL 드라이버를 JVM에 등록 (JDBC 4.0 이상은 생략 가능)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB 연결
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println(" Database connected successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println(" MySQL Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(" Connection failed!");
            e.printStackTrace();
        }
        return connection;
    }
}
