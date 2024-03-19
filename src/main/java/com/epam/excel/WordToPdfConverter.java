package com.epam.excel;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;

public class WordToPdfConverter {

//    public static void convertToPdf(String wordFilePath, String pdfFilePath) throws IOException {
//        // Load Word document
//        FileInputStream wordInputStream = new FileInputStream(wordFilePath);
//        XWPFDocument wordDocument = new XWPFDocument(wordInputStream);
//
//        // Create PDF document
//        PDDocument pdfDocument = new PDDocument();
//        PDPage pdfPage = new PDPage(PDRectangle.A4);
//        pdfDocument.addPage(pdfPage);
//
//        PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdfPage);
//
//        List<XWPFPictureData> pictures = wordDocument.getAllPictures();
//
//        for (XWPFPictureData picture : pictures) {
//            if (picture.getPackagePart().getContentType().equals("image/jpeg")) {
//                byte[] bytes = picture.getData();
//                PDImageXObject image = PDImageXObject.createFromByteArray(pdfDocument, bytes, "image");
//                float scale = 0.5f;
//                contentStream.drawImage(image, 50, 50, image.getWidth() * scale, image.getHeight() * scale);
//            }
//        }
//
//        contentStream.close();
//
//        pdfDocument.save(pdfFilePath);
//        pdfDocument.close();
//    }
    public static void main(String[] args) {
        File inputWord = new File("C:\\Users\\ASUS\\Desktop\\抽查记录单.docx");
        File outputFile = new File("C:\\Users\\ASUS\\Desktop\\抽查记录单1.pdf");
        try  {
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
//    public static void main(String[] args) {
//        try {
//            String wordFilePath = "path/to/your/input.docx";
//            String pdfFilePath = "path/to/your/output.pdf";
//            convertToPdf(wordFilePath, pdfFilePath);
//            System.out.println("Conversion completed successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
