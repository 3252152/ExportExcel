import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import util.ExportUtil;
import util.HSSFWorkbookUtil;
import util.JdbcMysql;
import util.JdbcOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入密码：");
        String password = sc.next();
        if(!password.equals("Aa123456!")){
            System.out.println("密码错误");
           System.exit(0);
        }


        System.out.println("请输入开始时间（xxxx-xx-xx）：");
        String startTiem = sc.next();
        System.out.println("请输入结束时间（xxxx-xx-xx）：");
        String endTiem = sc.next();

        System.out.println("1-----导出全量质检增量\t2-----导出专题分析提取数据：");
        int is = sc.nextInt();

        String fileName ="";
        String[] qltitleList = {"id", "count", "name","create_time","end_time","node_name"};
        String[] zttitleList = {"专题数量", "区域"};
        String [] titleList = {};
        String sql = "";
        if(is == 1){
             fileName = startTiem + "到" + endTiem + "全量质检增量.xls";
             sql = "select * from (select a.id,a.numbers,a.name,a.create_time,a.end_time,b.node_name from (select * from imb_zhijiang where  numbers is not null  order by numbers desc) a,ims.im_sm_organ  b where a.organ_id=b.id(+)) where create_time>=? and create_time<=? and node_name is not null order by node_name,create_time";
            titleList = qltitleList;
        }else if(is ==2){
            fileName = startTiem + "到" + endTiem + "专题分析数据.xls";
            sql = "select count(province) 专题数量，province 区域 from  imc_tb_zhuanti where province is not null and  create_timezs>=? and create_timezs<=?  group by province";
            titleList = zttitleList;
        }

        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Connection conn = JdbcOracle.getConn();

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, startTiem);
            pst.setString(2, endTiem);
            ResultSet rs = pst.executeQuery();
            if(is == 1){
                while (rs.next()) {
                    Map<String, Object> data = new LinkedHashMap<>();
                    data.put("id", rs.getInt("id"));
                    data.put("count", rs.getString("numbers"));
                    data.put("name", rs.getString("name"));
                    data.put("create_time", rs.getString("create_time"));
                    data.put("end_time", rs.getString("end_time"));
                    data.put("node_name", rs.getString("node_name"));
                    dataList.add(data);
                }
            }else if(is == 2){
                while (rs.next()) {
                    Map<String, Object> data = new LinkedHashMap<>();
                    data.put("专题数量", rs.getInt("专题数量"));
                    data.put("区域", rs.getString("区域"));
                    dataList.add(data);
                }
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
