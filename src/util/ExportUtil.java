package util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExportUtil {

    public static boolean export(String fileName, HSSFWorkbook wb){
        // 当前文件所在位置
        String path = System.getProperty("user.dir");
        File file = new File(path,fileName);
        try {
            FileOutputStream os  = new FileOutputStream(file);
            wb.write(os);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }

}
