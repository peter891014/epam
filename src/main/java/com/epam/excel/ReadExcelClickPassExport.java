package com.epam.excel;

import com.epam.entity.IcsCheckRule;
import com.epam.entity.IcsCheckRuleDetail;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Rows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

//import org.mapstruct.ap.shaded.freemarker.core.ReturnInstruction;

/**
 * @author hewangtong
 */
public class ReadExcelClickPassExport {

    // 构造方法
    public ReadExcelClickPassExport() {
    }

    public static String createExcel1(InputStream is, boolean isExcel2003, int sheet) throws Exception {
        try {
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(is);
            } else {// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
            return readExcelCreateTable(wb, sheet);// 读取Excel里面客户的信息
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 读EXCEL文件，获取信息集合
     *
     * @param
     * @return
     */
    public static String getExcelInfo1(Integer sheet, String filePath) throws FileNotFoundException {
        FileInputStream fis = null;
        fis = new FileInputStream(filePath);
        try {
            String map = createExcel1(fis, false, sheet);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取Excel里面客户的信息
     *
     * @param wb
     * @return
     */
    //`id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
    //`create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
    private static String readExcelCreateTable(Workbook wb, int sheetNum) throws Exception {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(sheetNum);
        // 得到Excel的行数
        int totalRows = sheet.getPhysicalNumberOfRows();
        //获取表名备注
        Row row1 = sheet.getRow(0);
        Cell commentCell = row1.getCell(1);
        String comment = commentCell.getStringCellValue();
        //获取表名
        Row row2 = sheet.getRow(1);
        Cell tableNameCell = row2.getCell(1);
        String tableName = tableNameCell.getStringCellValue().trim();


        tableName = tableName.toLowerCase();
        // 得到Excel的列数(前提是有行数)
        StringBuilder sql = new StringBuilder("CREATE TABLE `").append(tableName).append("` ( \n `id` varchar(36) NOT NULL,\n");

        for (int r = 1; r < totalRows; r++) {
            String name, code, type;
            Row row = sheet.getRow(r);
            name = row.getCell(3).getStringCellValue().trim();
            code = row.getCell(4).getStringCellValue().trim();
            type = row.getCell(5).getStringCellValue().trim();
            code = convertCamelToUnderscore(code);
            type = findCodeByName(type);
            if(!code.toLowerCase().equals("id")){
                sql.append(" `").append(code).append("` ").append(type).append(" COMMENT '").append(name).append("' ,\n");
            }
            if (r == totalRows - 1) {
                sql.append(" PRIMARY KEY (`id`) \n ").append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='").append(comment)
                        .append("';");
            }
        }
        return sql.toString();
    }

    public static String findCodeByName(String nameToFind) {
        // 遍历 FieldTypeTranslate 枚举值
        for (FiledTypeTranslate fieldType : FiledTypeTranslate.values()) {
            if (fieldType.getName().equals(nameToFind)) {
                // 找到匹配的 name，返回对应的 code
                return fieldType.getCode();
            }
        }
        // 如果没找到匹配的 name，返回一个默认值或者抛出异常，具体根据需求而定
        return null;
    }

    public static String convertCamelToUnderscore(String camelCase) {
        StringBuilder result = new StringBuilder();

        for (char c : camelCase.toCharArray()) {
            if (Character.isUpperCase(c)) {
                // 如果是大写字母，添加下划线和小写字母
                result.append("_").append(Character.toLowerCase(c));
            } else {
                // 如果是小写字母，直接添加
                result.append(c);
            }
        }

        return result.toString();
    }



    public static void main(String[] args) throws FileNotFoundException {

        String filePath = "C:\\Users\\ASUS\\Desktop\\新能源换电监管POC表结构1.xlsx";
        for(int i=1;i<16;i++){

            String sql=getExcelInfo1( i,filePath);
            System.out.println(sql);
        }
//        String sql=getExcelInfo1( 1,filePath);
//        System.out.println(sql);
    }
}
