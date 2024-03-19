package com.epam.excel;

import com.epam.entity.IcsCheckRule;
import com.epam.entity.IcsCheckRuleDetail;
import com.epam.utils.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
public class ReadExcelChecklistRule {
    // 总行数
    private int totalRows = 0;
    // 总条数
    private int totalCells = 0;
    // 错误信息接收器
    private String errorMsg;

    // 构造方法
    public ReadExcelChecklistRule() {
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
     * @param
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

    public static Map<String,List<?>> createExcel1(InputStream is, boolean isExcel2003,int sheet) throws Exception {
        try {
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(is);
            } else {// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
            return readExcelCreateTable(wb,sheet);// 读取Excel里面客户的信息
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
    public static Map<String,List<?>> getExcelInfo1(Integer sheet,String filePath) throws FileNotFoundException {
        FileInputStream fis = null;
        fis = new FileInputStream(filePath);
        try {
            Map<String,List<?>> map = createExcel1(fis, false,sheet);
            return  map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isValidDate(String s) {
        try {
            if (!s.endsWith("00:00:00")) {
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
    private static Map<String,List<?>> readExcelCreateTable(Workbook wb,int sheetNum) throws Exception {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(sheetNum);
        // 得到Excel的行数
        int totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
//        StringBuilder sql = new StringBuilder("CREATE TABLE `").append(sheet.getSheetName()).append("`(");
        int totalCells = 0;
        if (totalRows > 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }


        List<IcsCheckRule> checkRuleList = new ArrayList<>();
        List<IcsCheckRuleDetail> checkRuleDetailList = new ArrayList<>();
//        Builder insert = new StringBuilder("INSERT INTO `").append(sheet.getSheetName()).append("` VALUES ( ");
        Row row1 = sheet.getRow(1);
        String checkType0 = row1.getCell(1).getStringCellValue();
        String checkProject0 = row1.getCell(2).getStringCellValue();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows()-1; i++) {
            IcsCheckRule checkRule = new IcsCheckRule();
            checkRule.setApplicableObject(sheetNum==0?"B06A01":sheetNum==1?"B06A02":sheetNum==2?"B06A03":"");
            checkRule.setId(sheetNum==1?3000+i:sheetNum==2?4000+i:i);
//            StringBuilder insert = new StringBuilder("INSERT INTO `").append(sheet.getSheetName()).append("` VALUES ( ");
            Row row = sheet.getRow(i);

            for (int j = 1; j < totalCells; j++) {
                Cell cell = row.getCell(j);
                String value;
                if (cell.getCellType() == Cell.CELL_TYPE_STRING||cell.getCellType()== Cell.CELL_TYPE_BLANK) {
                    value = cell.getStringCellValue();
                    if (j == 1) {
                        if (StringUtils.isNotEmpty(value) && !checkType0.equals(value)) {
                            checkType0 = value;
                        }
                        checkRule.setCheckType(checkType0);
                    }
                    if (j == 2) {
                        if (StringUtils.isNotEmpty(value)  && !checkProject0.equals(value)) {
                            checkProject0 = value;
                        }
                        checkRule.setCheckProject(checkProject0);
                    }
                    if (j == 3) {
                        //未制定（伤亡控制/安全达标/文明施工等）管理目标
                        if (value.indexOf("（") > 0) {
                            String[] arr1 = value.split("（");
                            String[] arr2 = arr1[1].split("）");
                            if (arr2[0].indexOf("/") > 0) {
                                String arr3[] = arr2[0].split("/");
                                for (int k = 0; k < arr3.length - 1; k++) {
                                    IcsCheckRuleDetail detail = new IcsCheckRuleDetail();
                                    detail.setCheckRuleId(checkRule.getId());
                                    detail.setName(arr3[k]);
                                    checkRuleDetailList.add(detail);
                                }
                                String value1 = arr2.length > 1 ? arr1[0] + "_" + arr2[1] : arr1[0];
                                checkRule.setExistProblem(value1);
                            }
                            else {
                                checkRule.setExistProblem(value);
                            }
                        }else{
                            checkRule.setExistProblem(value);
                        }

                    }
                    if (j == 4) {
                        checkRule.setAccording(value);
                    }
                    if (j == 5) {
                        checkRule.setCategory(value);
                    }
                }
            }
            checkRule.setCreateBy("admin");
            checkRule.setCreateTime(new Date());
            checkRuleList.add(checkRule);
        }
        Map<String,List<?>>  map = new HashMap<>();
        map.put("checkRule",checkRuleList);
        map.put("detail",checkRuleDetailList);
        return map;
    }

    private List<Map<String, Object>> readExcelValue(Workbook wb) throws Exception {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
//        StringBuilder sql = new StringBuilder("CREATE TABLE '").append(sheet.getSheetName()).append("'(");
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
        String filePath = "C:\\Users\\ASUS\\Desktop\\监督检查用语.xlsx";
        getExcelInfo1(0,filePath);
    }
}
