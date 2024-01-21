package com.cryptography.service;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Value("${inputfilepath}")
    String filepath;
    @Value("${xmlpath}")
    String xmlpath;

    public String readFile(){
//        String filepath = "E:/Sattarzadeh/Documents/Mebank/dvp/sayad/CHQREFNO.xlsx";

        List<String> sayadInquiryList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(filepath);
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    String data = cell.getStringCellValue();
                    sayadInquiryList.add(data);
                }
            }
            workbook.close();
            fileInputStream.close();

            createSayadInqiryXml(sayadInquiryList);

            return "Success!";
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return "Failed";

        }
    }
    public void createSayadInqiryXml(List<String> inquiryList) throws ParserConfigurationException {
        int maxFileEnteries = 40000;
        int numOfFiles = (int) Math.ceil((double)inquiryList.size()/maxFileEnteries);

//        List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
        List<List<String>> subSets = Lists.partition(inquiryList, maxFileEnteries);

        for (int i = 0; i<subSets.size(); i++){

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("SayadInquirySerial");
            doc.appendChild(rootElement);

            Element childBankElement = doc.createElement("BankCode");
            childBankElement.setTextContent("78");
            rootElement.appendChild(childBankElement);

            Element childInquiryList = doc.createElement("InquirySerialsActive");
            for (int j = 0; j< subSets.get(i).size(); j++){
                Element inquirySerial = doc.createElement("InquirySerial");
                inquirySerial.setTextContent(subSets.get(i).get(j));
                childInquiryList.appendChild(inquirySerial);
            }
            rootElement.appendChild(childInquiryList);

            LocalDate dateObj = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = dateObj.format(formatter);

            String outputFileName = "E:\\Sattarzadeh\\Documents\\Mebank\\dvp\\sayad\\ow-cheches\\inquiry-fixed\\SayadInquirySerial-"+date+"-"+i+".xml";
            try(FileOutputStream output =
                        new FileOutputStream(outputFileName)){
//            try(FileOutputStream output =
//                        new FileOutputStream(xmlpath)){
                writeXml(doc, output);
            }
            catch (IOException | TransformerException e) {
                e.printStackTrace();
            }
        }
    }
    private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }
    private Boolean symmetricEncryptFile() {

        return true;
    }
    //TO-DO
    private Boolean AsymmetricEncryptFile(){
        return true;
    }

}
