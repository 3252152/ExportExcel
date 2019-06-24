package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcOracle {

        public static Connection getConn(){
            String driver = "oracle.jdbc.driver.OracleDriver";    //驱动标识符
            String url = "jdbc:oracle:thin:@10.121.22.98:1521:wxhd"; //链接字符串
            String user = "qtsm_zbtj";         //数据库的用户名
            String password = "Incana_WXhdimdb_0627";     //数据库的密码
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            boolean flag = false;
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url,user, password);
            }catch (Exception e){
                e.printStackTrace();
            }
            return con;
        }

    }
