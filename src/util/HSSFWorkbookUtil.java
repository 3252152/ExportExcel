package util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.List;
import java.util.Map;

public class HSSFWorkbookUtil {

    public HSSFWorkbook HSSFWorkbook(String[] titleList, List<Map<String, Object>> DataList) {

        HSSFWorkbook wb = new HSSFWorkbook();

        // 创建一个sheet
        HSSFSheet sheet = wb.createSheet();

        // 创建一个表头
        HSSFRow row = sheet.createRow(0);

        //创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 创建一个居中格式
        style.setAlignment(HorizontalAlignment.CENTER);

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < titleList.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(titleList[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < DataList.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> map = DataList.get(i);
            int j = 0;
            for (Object obj:map.keySet()) {
                row.createCell(j).setCellValue( map.get(obj).toString());
                j++;
            }
        }

        return wb;
    }

}

