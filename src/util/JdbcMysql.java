package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcMysql {

    public static Connection getConn() {
        String url = "jdbc:mysql://127.0.0.1:3306/aysw?serverTimezone=GMT%2B8";
        String user = "root";
        String password = "123456";
        try {
            Connection conn; //定义一个MYSQL链接对象
            Class.forName("com.mysql.cj.jdbc.Driver"); //MYSQL驱动
            conn = DriverManager.getConnection(url, user, password); //链接本地MYSQL
            return conn;
        } catch (Exception e) {
            System.out.print("MYSQL ERROR:" + e.getMessage());
        }
        return null;
    }

}
