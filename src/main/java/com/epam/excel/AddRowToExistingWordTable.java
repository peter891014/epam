package com.epam.excel;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;

public class AddRowToExistingWordTable {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\ASUS\\Desktop\\抽查记录单.docx"); // 替换为你的现有Word文件的路径
            XWPFDocument document = new XWPFDocument(fis);

            // 选择要添加行的表格（假设这里是第一个表格）
            XWPFTable table = document.getTables().get(0);

            // 创建一个新行
            XWPFTableRow newRow = table.createRow();

            // 在新行中创建单元格并添加数据
            newRow.getCell(0).setText("施工单位");
            newRow.getCell(1).setText("中国化学工程第十四建设有限公司");
            newRow.getCell(2).setText("项目经理");
            newRow.getCell(3).setText("黄才福");
            for (XWPFTableCell cell : newRow.getTableCells()) {
                XWPFParagraph paragraph = cell.getParagraphs().get(0);
                XWPFRun run = paragraph.createRun();
                run.setText(cell.getText());
                run.setTextPosition(10); // 设置文本位置，正数向下移动，负数向上移动
                paragraph.setAlignment(ParagraphAlignment.CENTER);
            }


            XWPFTable table2 = document.getTables().get(1);

            // 创建一个新行
            XWPFTableRow newRow2 = table2.createRow();

            // 在新行中创建单元格并添加数据
            newRow2.getCell(0).setText("3");
            newRow2.getCell(1).setText("现场防火");
            newRow2.getCell(2).setText("灭火器材失效");
            newRow2.getCell(3).setText("《建筑施工安全 检查标准》\n" +
                    "JGJ59-2011  第 3.2.3 条");
            newRow2.getCell(4).setText("其他");

            for (XWPFTableCell cell : newRow2.getTableCells()) {
                XWPFParagraph paragraph = cell.getParagraphs().get(0);
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun run = paragraph.createRun();
                run.setText(cell.getText());
                run.setTextPosition(10); // 设置文本位置，正数向下移动，负数向上移动
                // 设置垂直居中
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            }

            // 保存修改后的Word文档
            FileOutputStream fos = new FileOutputStream("C:\\Users\\ASUS\\Desktop\\抽查记录单1.docx"); // 替换为你要保存的新Word文件的路径
            document.write(fos);
            fos.close();

            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void translate2PDF(String urlIn,String urlOut) {
        File inputWord = new File("C:\\Users\\ASUS\\Desktop\\抽查记录单.docx");
        File outputFile = new File("C:\\Users\\ASUS\\Desktop\\抽查记录单1.pdf");
        try {
            InputStream docxInputStream = new FileInputStream(inputWord);
            OutputStream outputStream = new FileOutputStream(outputFile);
            IConverter converter = LocalConverter.builder().build();
            converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
            outputStream.close();
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
