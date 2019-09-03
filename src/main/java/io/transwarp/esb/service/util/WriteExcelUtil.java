package io.transwarp.esb.service.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.SQLTransactionRollbackException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriteExcelUtil {

    public static void download(HttpServletResponse response){
//        HSSFWorkbook mworkbook = writeExcel();
//        OutputStream outputStream;
//        try {
//            outputStream = response.getOutputStream();
//            response.setHeader("Content-disposition", "attachment;filename="+new String("result".getBytes(),"iso-8859-1")+".xls");
//            response.setContentType("application/vnd.ms-excel");
//            mworkbook.write(outputStream);
//            outputStream.close();
//        }catch (IOException e){
//            e.printStackTrace();
//        }

    }


    public static void writeExcel(HttpServletRequest request,HttpServletResponse response){

        List<List<String>> personAllList = getTest();
//        Workbook workbook = new XSSFWorkbook(new FileInputStream(Constants.UPLOAD_PATH + "reportModel.xlsx"));
        OutputStream out = null;
        try {
//            XSSFWorkbook mWorkbook = new XSSFWorkbook();   //创建excel
//            XSSFSheet mSheet = ((XSSFWorkbook) mWorkbook).createSheet("Score");//创建sheet
//            XSSFRow headRow = mSheet.createRow(0);
            //写入excel
           // String fileName = "";
            HSSFWorkbook mWorkbook = new HSSFWorkbook();   //创建excel
            HSSFCellStyle hssfCellStyle = mWorkbook.createCellStyle();
        //    hssfCellStyle.setDataFormat();
            HSSFSheet mSheet = mWorkbook.createSheet("Score");  //创建sheet

//             创建Excel标题行，第一行
            HSSFRow headRow = mSheet.createRow(0);
            headRow.createCell(0).setCellValue("姓名");
            headRow.createCell(1).setCellValue("政治素质");
            headRow.createCell(2).setCellValue("廉洁从业");
            headRow.createCell(3).setCellValue("团队合作");
            headRow.createCell(4).setCellValue("业务水平");
            headRow.createCell(5).setCellValue("创新能力");
            headRow.createCell(6).setCellValue("组织协调");
            headRow.createCell(7).setCellValue("工作实绩");
            headRow.createCell(8).setCellValue("责任意识");
            headRow.createCell(9).setCellValue("人才培养");
            headRow.createCell(10).setCellValue("履职成效");
            headRow.createCell(11).setCellValue("总分");


            // 往Excel表中写入i行数据
            for (int i=0;i<personAllList.size();i++) {
                List<String> each = personAllList.get(i);
                createCell(each.get(0),each.get(1),each.get(2),each.get(3),each.get(4),each.get(5),each.get(6),each.get(7),each.get(8),each.get(9),each.get(10),each.get(11) ,mSheet);
            }
        File xlsFile = new File("./result.xls");
//        try {
//            mWorkbook.write(xlsFile);// 或者以流的形式写入文件 mWorkbook.write(new FileOutputStream(xlsFile));
//            mWorkbook.close();
//        }catch (IOException e){
//            e.printStackTrace();
//        }

      //   String filename = "result.xls";
//            try {
//                filename = URLEncoder.encode( filename,"GBK");
//            }catch (UnsupportedEncodingException e){
//                e.printStackTrace();
//            }
            String fileName = "result";
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(),"iso-8859-1") + ".xls");
            response.setContentType("application/x-msdownload");


//            String filename = "attachment; filename=result.xls";
////            response.setHeader("Content-disposition", new String(filename.getBytes("gbk"), "utf-8"));
//            response.setHeader("Content-disposition", filename);
//
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//            response.setHeader("Pragma", "no-cache");
//            response.setHeader("Cache-Control", "no-cache");
//            response.setDateHeader("Expires", 0);

        //    response.setHeader("Content-disposition", "attachment;filename="+filename);
         //   response.setHeader("Cache-Control","no-cache");
            OutputStream output = response.getOutputStream();
//            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
//            bufferedOutPut.flush();
            mWorkbook.write(output);
//            mWorkbook.write(bufferedOutputStream);// 或者以流的形式写入文件 mWorkbook.write(new FileOutputStream(xlsFile));
//            mWorkbook.write(new FileOutputStream(xlsFile));
//            mWorkbook.write(out);
            mWorkbook.close();
            output.close();
//            out.close();

        }catch (IOException e){
            e.printStackTrace();
        }






    }



    // 创建Excel的一行数据
    private static void createCell(String name, String s1, String s2,String s3,String s4,String s5,String s6,String s7,String s8,String s9,String s10, String s11,HSSFSheet sheet) {
        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
        dataRow.createCell(0).setCellValue(name);
        dataRow.createCell(1).setCellValue(s1);
        dataRow.createCell(2).setCellValue(s2);
        dataRow.createCell(3).setCellValue(s3);
        dataRow.createCell(4).setCellValue(s4);
        dataRow.createCell(5).setCellValue(s5);
        dataRow.createCell(6).setCellValue(s6);
        dataRow.createCell(7).setCellValue(s7);
        dataRow.createCell(8).setCellValue(s8);
        dataRow.createCell(9).setCellValue(s9);
        dataRow.createCell(10).setCellValue(s10);
        dataRow.createCell(11).setCellValue(s11);
    }


    private static List<List<String>> getTest(){

        List<String> list = new ArrayList<>();
        List<List<String>> personList = new ArrayList<>();
        list.add("lzh");
        list.add("7");
        list.add("3");
        list.add("2");
        list.add("1");
        list.add("0");
        list.add("9");
        list.add("3");
        list.add("7");
        list.add("4");
        list.add("5");
        list.add("01");
        personList.add(list);
        return personList;
    }

}

