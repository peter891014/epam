package com.epam.excel;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by happy on 2019/06/05
 * In order to transform excel to sql/hsql,
 * otherwise, as same with sql/hsql to excel.
 */
public class ExcelSQLTransformCheckRule {

    /**
     * @param filePath
     * @param storePath
     * @return
     * @throws Exception
     */
    public static List<Map> readExcel(String filePath, String storePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        /**
         * Get the suffix of the file
         */
        String suffix = filePath.substring(filePath.lastIndexOf("."));

        /**
         * if suffix cannot equals .xls && .xlsx ,return null
         */
        if (!".xls".equals(suffix) && !".xlsx".equals(suffix)) {
            return null;
        }

        //返回值列
        List<Map> reaultList = new ArrayList<Map>();
        if (".xls".equals(suffix)) {
            reaultList = readExcel2007(filePath, storePath);
        } else if (".xlsx".equals(suffix)) {
            reaultList = readExcel2007(filePath, storePath);
        }
        System.out.println(reaultList);
        return reaultList;
    }

    /**
     * @param filePath
     * @param storePath
     * @return
     * @throws IOException
     */
    public static List<Map> readExcel2007(String filePath, String storePath) throws IOException {
        List<Map> valueList = new ArrayList<Map>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            XSSFWorkbook xwb = new XSSFWorkbook(fis);       // 构造 XSSFWorkbook 对象，strPath 传入文件路径

            int sheetNum = xwb.getNumberOfSheets();
            /**
             *  foreach to read sheet.
             */
            XSSFSheet sheet;
            XSSFRow row;
            String sql;

            for (int s = 0; s <= sheetNum - 1; s++) {
                sheet = xwb.getSheetAt(s);
                String table_name = sheet.getSheetName();
                int totalRow = sheet.getLastRowNum();

                String resutl = "";
                for (int i = 1; i <= totalRow; i++) {
                    row = sheet.getRow(i);
                    sql = "insert into  t_sensitive_words(type,vaule) values('";
                    String a = row.getCell(1) + "' ,'" + row.getCell(3) + "');\n";
                    sql = sql + a;
                    resutl = resutl + sql;
//                    } else {
//                        String a = row.getCell(1) + "," + row.getCell(2) + "";
//                        sql = sql + a;
//                    }
                }
                System.out.println(resutl);
              //  new SaveDataToFile().appendFile(storePath, resutl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fis.close();
        }
        return valueList;
    }

    /**
     * main
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String readurl = "C:\\Users\\ASUS\\Desktop\\minganciku\\敏感词库表统计.xlsx";  //读取excel文件的路径
        String storeurl = "d:\\";                                           //保存sql命令的路径
        readExcel(readurl, storeurl);
    }
}
