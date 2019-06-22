import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import util.ExportUtil;
import util.HSSFWorkbookUtil;
import util.JdbcMysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入开始时间（xxxx-xx-xx）：");
        String startTiem = sc.next();
        System.out.println("请输入结束时间（xxxx-xx-xx）：");
        String endTiem = sc.next();

        String fileName = startTiem + "到" + endTiem + "文件.xls";
        String[] titleList = {"id", "名字", "时间"};
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();


        String sql = "select * from book where time >=? and time <=?";
        Connection conn = JdbcMysql.getConn();

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, startTiem);
            pst.setString(2, endTiem);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Map<String, Object> data = new LinkedHashMap<>();
                data.put("id", rs.getInt("id"));
                data.put("name", rs.getString("name"));
                data.put("time", rs.getString("time"));
                dataList.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

        // 生成一个excel
        if (dataList.size() > 0) {
            HSSFWorkbookUtil hssfWorkbookUtil = new HSSFWorkbookUtil();
            HSSFWorkbook wb = hssfWorkbookUtil.HSSFWorkbook(titleList, dataList);

            // 导出
            ExportUtil.export(fileName, wb);
        } else {
            System.out.println("无数据");
        }

    }
}
