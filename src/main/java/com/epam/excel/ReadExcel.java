package com.epam.excel;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import scala.Int;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

//import org.mapstruct.ap.shaded.freemarker.core.ReturnInstruction;

/**
 * @author hewangtong
 */
public class ReadExcel {
    // 总行数
    private int totalRows = 0;
    // 总条数
    private int totalCells = 0;
    // 错误信息接收器
    private String errorMsg;

    // 构造方法
    public ReadExcel() {
    }

    // 获取总行数
    public int getTotalRows() {
        return totalRows;
    }

    // 获取总列数
    public int getTotalCells() {
        return totalCells;
    }

    // 获取错误信息
    public String getErrorInfo() {
        return errorMsg;
    }

    /**
     * 读EXCEL文件，获取信息集合
     *
     * @param fielName
     * @return
     */
    public List<Map<String, Object>> getExcelInfo(MultipartFile mFile) {
        String fileName = mFile.getOriginalFilename();// 获取文件名
        //        List<Map<String, Object>> userList = new LinkedList<Map<String, Object>>();
        try {
            if (!validateExcel(fileName)) {// 验证文件名是否合格
                return null;
            }
            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
            if (isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            return createExcel(mFile.getInputStream(), isExcel2003);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据excel里面的内容读取客户信息
     *
     * @param is          输入流
     * @param isExcel2003 excel是2003还是2007版本
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> createExcel(InputStream is, boolean isExcel2003) throws Exception {
        try {
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(is);
            } else {// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
            return readExcelValue(wb);// 读取Excel里面客户的信息
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createExcel1(InputStream is, boolean isExcel2003) throws Exception {
        try {
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(is);
            } else {// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
            return readExcelCreateTable(wb);// 读取Excel里面客户的信息
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 读EXCEL文件，获取信息集合
     *
     * @param fielName
     * @return
     */
    public static String getExcelInfo1(String filePath) throws FileNotFoundException {
        FileInputStream fis = null;
        fis = new FileInputStream(filePath);
        String str = null;
        try {
            str = createExcel1(fis, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isValidDate(String s) {
        try {
            if(!s.endsWith("00:00:00")){
                return false;
            }
            SimpleDateFormat dateFormat = null;
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            dateFormat.parse(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidDateTime(String s) {
        try {
            SimpleDateFormat dateFormat = null;
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setLenient(false);
            dateFormat.parse(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isNumber(String n) {
        // 判断字符串是否为数字：正负整数、正负小数
        Pattern p = Pattern.compile("^-?(\\d+|\\d+)$");
        return p.matcher(n).find();
    }

    private boolean isDate(String n) {
        // 简单判断字符串是否为日期：年的位数小于等于四位就是了，月01到12，日01到31。暂不考虑特殊情况
        Pattern p1 = Pattern.compile("^\\d{1,4}-(0[1-9]|1[0-2])-([0-2][09]|3[0-1])$");
        return p1.matcher(n).find();
    }


    /**
     * 读取Excel里面客户的信息
     *
     * @param wb
     * @return
     */
    //`id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
    //`create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
    private static String readExcelCreateTable(Workbook wb) throws Exception {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        int totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
        StringBuilder sql = new StringBuilder("CREATE TABLE `").append(sheet.getSheetName()).append("`(");
        int totalCells = 0;
        if (totalRows > 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        Row firstRow = sheet.getRow(0);
        Row secondRow = sheet.getRow(1);
         List<String> fieldTypeList = new ArrayList<>( firstRow.getRowNum());
        for (int i = 0; i < totalCells; i++) {
            Cell cell1 = firstRow.getCell(i);
            Cell cell2 = secondRow.getCell(i);
            String filedName = String.valueOf(cell1.getStringCellValue());
            String field;
            String value = "";
            try {
                if(cell2 == null || cell2.getCellType() == (Cell.CELL_TYPE_BLANK)){
                    value = "";
                }
                if (cell2.getCellType() == Cell.CELL_TYPE_STRING) {
                    value = cell2.getStringCellValue();
                } else if (cell2.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    if(HSSFDateUtil.isCellDateFormatted(cell2)){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = HSSFDateUtil.getJavaDate(cell2.getNumericCellValue());
                         value = sdf.format(date);
                    }else{
                        Double a = cell2.getNumericCellValue();
                        value = String.valueOf(a);
                    }
                }
            } catch (Exception e) {
                System.out.println("e:" + e.getMessage());
            }
            if (isNumber(value)) {
                if (Long.valueOf(value) <= 127 && Long.valueOf(value) > -128) {
                    field = "tinyint(3) ";
                } else if (Long.valueOf(value) <= 32767 && Long.valueOf(value) > -32768) {
                    field = "smallint(6) ";
                } else if (Long.valueOf(value) <= 2147483647 && Long.valueOf(value) > -2147483648) {
                    field = "int(11) ";
                } else {
                    field = "bigint(20)";
                }
                fieldTypeList.add("int");
                sql.append(" `").append(filedName).append("` ").append(field);
            }
            else if (isValidDate(value)) {
                field = "date";
                fieldTypeList.add(field);
                sql.append(" `").append(filedName).append("` ").append(field);
            }
            else if (isValidDateTime(value)) {
                field = "datetime";
                fieldTypeList.add(field);
                sql.append(" `").append(filedName).append("` ").append(field);
            } else {
                int l = 32 > (value.length()) ? 32 : value.length() * 2;
                field = "varchar(" + l + ") ";
                fieldTypeList.add("varchar");
                sql.append(" `").append(filedName).append("` ")
                        .append(field).append(" CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci ");
            }
            if (i == 0) {
                sql.append(" NOT NULL , \n");
            } else if (i >= 0 && i < totalCells - 1) {
                sql.append(" NULL, \n");
            } else if (i == totalCells - 1) {
                sql.append(" NULL \n );");
            }
        }
        System.out.println(sql);
      //  INSERT INTO `ces_order_goods` VALUES ('1335894537799213058',
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            StringBuilder insert = new StringBuilder("INSERT INTO `").append(sheet.getSheetName()).append("` VALUES ( ");
            Row row = sheet.getRow(i);
            for (int j = 0; j < totalCells; j++) {
                Cell cell2 = row.getCell(j);
                String value = "";
                if (cell2 == null || cell2.getCellType() == (Cell.CELL_TYPE_BLANK)) {
                    if(fieldTypeList.get(j).equals("int")||fieldTypeList.get(j).equals("date")||
                            fieldTypeList.get(j).equals("datetime")){
                        if (j == totalCells - 1) {
                            insert.append(" null, ) ;\n");
                        } else {
                            insert.append("  null,");
                            System.out.println(insert);
                        }
                    }else{
                        if (j == totalCells - 1) {
                            insert.append(" '' ) ;\n");
                        } else {
                            insert.append(" '' ,");
                        }
                    }

                } else if (cell2.getCellType() == Cell.CELL_TYPE_STRING) {
                    value = cell2.getStringCellValue();
                    if(value.contains("'")){
                        value.replace("'","");
                    }
                    if (j == totalCells - 1) {
                        insert.append(" '").append(value).append("') ;\n");
                    } else {
                        insert.append(" '").append(value).append("' ,");
                    }

                } else if (cell2.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    if(HSSFDateUtil.isCellDateFormatted(cell2)){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = HSSFDateUtil.getJavaDate(cell2.getNumericCellValue());
                        value = sdf.format(date);
                        if (j == totalCells - 1) {
                            insert.append("'").append(value).append("' );\n");
                        } else {
                            insert.append("'").append(value).append("' ,");
                        }
                    }else{
                        Double a = cell2.getNumericCellValue();
                        value = String.valueOf(a);
                        if (j == totalCells - 1) {
                            insert.append(value).append(");\n");
                        } else {
                            insert.append(value).append(",");
                        }
                    }


                }
            }
            System.out.println(insert);
            File file = new File("d:/"+sheet.getSheetName()+".sql");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(new String(insert));
            bufferedWriter.close();
        }
        return new String(sql);
    }

    private List<Map<String, Object>> readExcelValue(Workbook wb) throws Exception {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
        StringBuilder sql = new StringBuilder("CREATE TABLE '").append(sheet.getSheetName()).append("'(");
        if (totalRows > 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
        // 循环Excel行数
        for (int r = 2; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            // 循环Excel的列
            Map<String, Object> map = new HashMap<String, Object>();
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    if (c == 0) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String name = String.valueOf(cell.getStringCellValue());
                        if (name.isEmpty()) {
                            break;
                        }
                        map.put("name", name);
                        // 如果是纯数字,比如你写的是25,cell.getNumericCellValue()获得是25.0,通过截取字符串去掉.0获得25
//                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                            String name = String.valueOf(cell.getNumericCellValue());
//                            //map.put("name", name.substring(0, name.length() - 2 > 0 ? name.length() - 2 : 1));// 名称
//                            map.put("name",name);
//                        } else {
//                            map.put("name", cell.getStringCellValue());// 名称
////
////                            };
//                        }
                    } else if (c == 1) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        map.put("phone", cell.getStringCellValue());// 性别
//                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                            String sex = String.valueOf(cell.getNumericCellValue());
//                            map.put("phone", sex.substring(0, sex.length() - 2 > 0 ? sex.length() - 2 : 1));// 性别
//                        } else {
//                            map.put("phone", cell.getStringCellValue());// 性别
//                        }
//                    } else if (c == 2) {
//                        cell.setCellType(Cell.CELL_TYPE_STRING);
//                        map.put("entprzId", cell.getStringCellValue());
////                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
////                            String age = String.valueOf(cell.getNumericCellValue());
////                            map.put("entprzId", age.substring(0, age.length() - 2 > 0 ? age.length() - 2 : 1));// 年龄
////                        } else {
////                            map.put("entprzId", cell.getStringCellValue());// 年龄
////                        }
//                     } else if (c == 2) {
//                        cell.setCellType(Cell.CELL_TYPE_STRING);
//                        map.put("hrCoId", cell.getStringCellValue());
//                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                            String hrCoId = String.valueOf(cell.getNumericCellValue());
//                            map.put("hrCoId",hrCoId);// 年龄
//                           // map.put("hrCoId", age.substring(0, age.length() - 2 > 0 ? age.length() - 2 : 1));// 年龄
//                        } else {
//                            map.put("hrCoId", cell.getStringCellValue());// 年龄
//                        }
                    } else if (c == 2) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        map.put("orderType", cell.getStringCellValue());
//                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                            String oderType = String.valueOf(cell.getNumericCellValue());
//                            map.put("oderType",oderType);// 年龄
//                            //map.put("oderType", age.substring(0, age.length() - 2 > 0 ? age.length() - 2 : 1));// 年龄
//                        } else {
//                            map.put("oderType", cell.getStringCellValue());// 年龄
//                        }
                    } else if (c == 3) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        Integer manhours = parseInt(cell.getStringCellValue());
                        map.put("manhours", manhours);
//                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                            String manhours = String.valueOf(cell.getNumericCellValue());
//                            map.put("manhours", manhours.substring(0, manhours.length() - 2 > 0 ? manhours.length() - 2 : 1));// 年龄
//                        } else {
//                            map.put("manhours", cell.getStringCellValue());// 年龄
//                        }
                    } else if (c == 4) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        float price = Float.parseFloat(cell.getStringCellValue());
                        map.put("price", price);
//                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                            String manhours = String.valueOf(cell.getNumericCellValue());
//                            map.put("manhours", manhours.substring(0, manhours.length() - 2 > 0 ? manhours.length() - 2 : 1));// 年龄
//                        } else {
//                            map.put("manhours", cell.getStringCellValue());// 年龄
//                        }
                    }

                }
            }
            // 添加到list
            userList.add(map);
        }
        return userList;
    }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            errorMsg = "文件名不是excel格式";
            return false;
        }
        return true;
    }

    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    // @描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    public static void main(String[] args) throws FileNotFoundException {

//        String value ="你好'啊";
//        if(value.contains("'")){
//            value.replace("啊","");
//        }
//        System.out.println(value+"你好\'啊");
        String filePath = "F:\\4.响水县房普数据\\4.响水县房普数据\\risk_census_water_pipe_prop.xlsx";
        getExcelInfo1(filePath);
    }
}
