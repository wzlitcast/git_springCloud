package com.aspire.commons.analysis_excel.utils;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.aspire.commons.analysis_excel.constantCode.ConstantCode;
import com.aspire.commons.analysis_excel.excelException.ExcelException;
import com.aspire.commons.analysis_excel.factory.ExcelWriterFactroy;
import com.aspire.commons.analysis_excel.handler.StyleExcelHandler;
import com.aspire.commons.analysis_excel.listener.ExcelListener;
import com.aspire.commons.analysis_zip.ZipCompress;
import com.aspire.commons.csv.bs.CsvWriteBs;
import com.aspire.commons.log.LogCommons;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * ExcelUtil
 * read write
 */
public class ExcelUtil {

    public ExcelUtil() {
    }


    //==============================================
    //             readExcel to List<T>
    //==============================================


    /**
     * 读取 Excel(多个 sheet)
     * 将多sheet合并成一个list数据集，通过自定义ExcelReader继承AnalysisEventListener
     * 重写invoke doAfterAllAnalysed方法
     * getExtendsBeanList 主要是做Bean的属性拷贝 ，可以通过ExcelReader中添加的数据集直接获取
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @return Excel 数据 list
     */
    public static <T extends BaseRowModel> List<T> readExcel(MultipartFile excel,Class<T>  rowModel) throws ExcelException {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excel, excelListener);
        if (reader == null) {
            return new ArrayList<>();
        }
        for (Sheet sheet : reader.getSheets()) {
            sheet.setClazz(rowModel);
            reader.read(sheet);
        }
        return getExtendsBeanList(excelListener.getDatas(),rowModel);
    }

    /**
     * 读取 sheet 的 Excel（注解校验：lineNum+errorMessage）
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @return Excel 数据 list
     */
    public static <T extends BaseRowModel> List<T> readExcelAddVerify(MultipartFile excel,Class<T>  rowModel) throws Exception {
        AnalyticalObj<T> analyticalObj = new AnalyticalObj<>();
        List<T> verifyObjs = analyticalObj.verifyObj(excel.getInputStream(), rowModel);
        return verifyObjs;
    }

    /**
     * 读取某个 sheet 的 Excel
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @param sheetNo  sheet 的序号 从1开始
     * @return Excel 数据 list
     */
    public static <T extends BaseRowModel> List<T> readExcel(MultipartFile excel, Class<T>  rowModel, int sheetNo)  throws ExcelException{
        return readExcel(excel, rowModel, sheetNo, 1);
    }

    /**
     * 读取某个 sheet 的 Excel
     * @param excel       文件
     * @param rowModel    实体类映射，继承 BaseRowModel 类
     * @param sheetNo     sheet 的序号 从1开始
     * @param headLineNum 表头行数，默认为1
     * @return Excel 数据 list
     */
    public static <T extends BaseRowModel> List<T> readExcel(MultipartFile excel, Class<T>  rowModel, int sheetNo,
                                                             int headLineNum) throws ExcelException {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excel, excelListener);
        if (reader == null) {
            return new ArrayList<>();
        }
        reader.read(new Sheet(sheetNo, headLineNum, rowModel));
        return getExtendsBeanList(excelListener.getDatas(),rowModel);
    }


    /**
     * 使用BeanCopy转换列表
     */
    public static <T extends BaseRowModel> List<T> getExtendsBeanList(List<?> list,Class<T> typeClazz){
        return MyBeanCopy.convert(list,typeClazz);
    }


    //==============================================
    //             readExcel to List<Object>
    //==============================================


    /**
     * 读取 Excel(多个 sheet)
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @return Excel 数据 list
     */
    public static List<Object> readExcel(MultipartFile excel, BaseRowModel rowModel) {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excel, excelListener);
        if (reader == null) {
            return new ArrayList<>();
        }
        for (Sheet sheet : reader.getSheets()) {
            if (rowModel != null) {
                sheet.setClazz(rowModel.getClass());
            }
            reader.read(sheet);
        }
        return excelListener.getDatas();
    }

    /**
     * 读取某个 sheet 的 Excel
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @param sheetNo  sheet 的序号 从1开始
     * @return Excel 数据 list
     */
    public static List<Object> readExcel(MultipartFile excel, BaseRowModel rowModel, int sheetNo) {
        return readExcel(excel, rowModel, sheetNo, 1);
    }

    /**
     * 读取某个 sheet 的 Excel
     * @param excel       文件
     * @param rowModel    实体类映射，继承 BaseRowModel 类
     * @param sheetNo     sheet 的序号 从1开始
     * @param headLineNum 表头行数，默认为1
     * @return Excel 数据 list
     */
    public static List<Object> readExcel(MultipartFile excel, BaseRowModel rowModel, int sheetNo,
                                         int headLineNum) {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excel, excelListener);
        if (reader == null) {
            return new ArrayList<>();
        }
        reader.read(new Sheet(sheetNo, headLineNum, rowModel.getClass()));
        return excelListener.getDatas();
    }

    //==============================================
    //            writeExcel Browser or Zip
    //==============================================

    /**
     * 导出 Excel ：一个 sheet，带表头
     * @param response  HttpServletResponse
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param object    映射实体类，Excel 模型
     */
    public static void writeExcel(HttpServletResponse response, List<? extends BaseRowModel> list,
                                  String fileName, String sheetName, BaseRowModel object) {
        ExcelWriter writer = new ExcelWriter(getOutputStream(fileName, response), ExcelTypeEnum.XLSX);
        Sheet sheet = new Sheet(1, 0, object.getClass());
        sheet.setSheetName(sheetName);
        writer.write(list, sheet);
        writer.finish();
    }

    /**
     * 导出 Excel ：多个 sheet，带表头
     * 服务层自定义多Sheet(浏览器形式)
     * @param response  HttpServletResponse
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param object    映射实体类，Excel 模型
     */
    public static ExcelWriterFactroy writeExcelWithSheets(HttpServletResponse response, List<? extends BaseRowModel> list,
                                                          String fileName, String sheetName, BaseRowModel object) {
        ExcelWriterFactroy writer = new ExcelWriterFactroy(getOutputStream(fileName, response), ExcelTypeEnum.XLSX);
        Sheet sheet = new Sheet(1, 0, object.getClass());
        sheet.setSheetName(sheetName);
        writer.write(list, sheet);
        return writer;
    }


    /**
     * 导出 Excel ：多个 sheet，带表头
     * 服务层自定义多Sheet，并返回生成路径（zip）
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param object    映射实体类，Excel 模型
     */
    public static ExcelWriterFactroy writeExcelWithSheets(List<? extends BaseRowModel> list,
                                                          String fileName, String sheetName, BaseRowModel object,String filePath) throws FileNotFoundException {
        String format = new SimpleDateFormat(ConstantCode.DATE_FORMATT).format(new Date());
        String path =System.getProperty(ConstantCode.USER_DIR)+System.getProperty(ConstantCode.FILE_SEPARATOR)+autoFilrName();
        File file = new File(path);
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file)) ;
        ZipEntry entry = new ZipEntry(fileName + "(" + format + ")" + ConstantCode.FILE_TYPE);
        try {
            zipOutputStream.putNextEntry(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelWriterFactroy writer = null;
        writer = new ExcelWriterFactroy(zipOutputStream, ExcelTypeEnum.XLSX);
        Sheet sheet = new Sheet(1, 0, object.getClass());
        sheet.setSheetName(sheetName);
        writer.write(list, sheet);
        return writer;
    }


    /**
     * 导出 Excel ：多个 sheet，带表头 (zip)
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param intervalNumber    映射实体类，Excel 模型
     */
    public static String writeExcelWithSheetsOfZip(List<? extends BaseRowModel> list,
                                                          String fileName, String sheetName, Class calssZ,int  intervalNumber) {
        String format = new SimpleDateFormat(ConstantCode.DATE_FORMATT).format(new Date());
        String path =System.getProperty(ConstantCode.USER_DIR)+System.getProperty(ConstantCode.FILE_SEPARATOR)+autoFilrName();
        File file = new File(path);
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file))) {
            ZipEntry entry = new ZipEntry(fileName + "(" + format + ")" + ConstantCode.FILE_TYPE);
            zipOutputStream.putNextEntry(entry);
            ExcelWriter writer = new ExcelWriter(zipOutputStream, ExcelTypeEnum.XLSX);
            int sheets = 0;
            if (list.size()<=intervalNumber){
                sheets = 1;
            }
            if (list.size()>intervalNumber){
                sheets = list.size()/intervalNumber+1;
            }
            for (int i=1;i<=sheets;i++){
                Sheet sheet = new Sheet(i, 0,calssZ);
                sheet.setSheetName(sheetName+(i));
                List<? extends BaseRowModel> baseRowModels = null;
                if (i==1){
                    if (sheets>1)
                        baseRowModels = list.subList(i-1, intervalNumber);
                    if (sheets==1)
                        baseRowModels = list.subList(i-1, list.size());
                }else{
                    if (i<sheets)
                        baseRowModels = list.subList(( i-1)*intervalNumber, i*intervalNumber);
                    if (i==sheets)
                        baseRowModels = list.subList((i-1)*intervalNumber, list.size());
                }
                writer.write(baseRowModels, sheet);
            }
            writer.finish();
        } catch (Exception e) {
            LogCommons.error("writeExcelWithSheetsOfZip error",e);
        }
        return path;
    }


    /**
     * 导出 Excel ：多个 sheet，带表头
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param filePath  文件路径
     * @param sheetName 导入文件的 sheet 名
     * @param classZ    映射实体类泛型
     */
    public static void writeExcelWithSheets(List<? extends BaseRowModel> list, String filePath, String sheetName, Class classZ,int  intervalNumber) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath))){
            ExcelWriter writer = new ExcelWriter(fileOutputStream, ExcelTypeEnum.XLSX);
            int sheets = 0;
            if (list.size()<=intervalNumber){
                sheets = 1;
            }
            if (list.size()>intervalNumber){
                sheets = list.size()/intervalNumber+1;
            }
            for (int i=1;i<=sheets;i++){
                Sheet sheet = new Sheet(i, 0, classZ);
                sheet.setSheetName(sheetName+(i));
                List<? extends BaseRowModel> baseRowModels = null;
                if (i==1){
                    if (sheets>1)
                        baseRowModels = list.subList(i-1, intervalNumber);
                    if (sheets==1)
                        baseRowModels = list.subList(i-1, list.size());
                }else{
                    if (i<sheets)
                        baseRowModels = list.subList(( i-1)*intervalNumber, i*intervalNumber);
                    if (i==sheets)
                        baseRowModels = list.subList((i-1)*intervalNumber, list.size());
                }
                writer.write(baseRowModels, sheet);
            }
            writer.finish();
        } catch (IOException e) {
            LogCommons.error("writeExcelWithSheets errpr{}",e);
        }
    }


    /**
     * 导出 Excel ：1个 sheet，带表头
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param filePath  文件路径
     * @param sheetName 导入文件的 sheet 名
     * @param classZ    映射实体类泛型
     */
    public static void writeExcelWithSheet(List<? extends BaseRowModel> list, String filePath, String sheetName, Class classZ,int sheetNo) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath))){
            ExcelWriter writer = new ExcelWriter(fileOutputStream, ExcelTypeEnum.XLSX);
            Sheet sheet = new Sheet(sheetNo, 0, classZ);
            sheet.setSheetName(sheetName);
            writer.write(list, sheet);
            writer.finish();
        } catch (IOException e) {
            LogCommons.error("writeExcelWithSheets error{}",e);
        }
    }


    /**
     * 导出 Excel ：1个 sheet，带表头
     * @param response  HttpServletResponse
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param object    映射实体类，Excel 模型
     */
    public static ExcelWriterFactroy writeExcelWithSheetsOfBrowser(HttpServletResponse response, List<? extends BaseRowModel> list,
                                                                   String fileName, String sheetName, BaseRowModel object) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
        ExcelWriterFactroy writer = new ExcelWriterFactroy(getOutputStream(fileName, response), ExcelTypeEnum.XLSX);
        Sheet sheet = new Sheet(1, 0, object.getClass());
        sheet.setSheetName(sheetName);
        writer.write(list, sheet);
        return writer;
    }

    /**
     * 导出文件时为Writer生成OutputStream
     */
    public static OutputStream getOutputStream(String fileName, HttpServletResponse response) {
        //创建本地文件
        String filePath = fileName + ConstantCode.FILE_TYPE;
        File dbfFile = new File(filePath);
        try {
            if (!dbfFile.exists() || dbfFile.isDirectory()) {
                dbfFile.createNewFile();
            }
            fileName = new String(filePath.getBytes(), "ISO-8859-1");
            response.addHeader("Content-Disposition", "filename=" + fileName);
            return response.getOutputStream();
        } catch (IOException e) {
            throw new ExcelException("创建文件失败！");
        }
    }


    /**
     * 导出 Excel ：1个 sheet，带表头（zip）
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param classz    泛型
     * @return
     */
    public static String writeExcelOfZip(List<? extends BaseRowModel> list, String fileName, String sheetName, Class classz) {
        String format = new SimpleDateFormat(ConstantCode.DATE_FORMATT).format(new Date());
        String path =System.getProperty(ConstantCode.USER_DIR)+System.getProperty(ConstantCode.FILE_SEPARATOR)+autoFilrName();
        File file = new File(path);
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file))) {
            ZipEntry entry = new ZipEntry(fileName + "(" + format + ")" + ConstantCode.FILE_TYPE);
            zipOutputStream.putNextEntry(entry);
            ExcelWriter writer = new ExcelWriter(zipOutputStream, ExcelTypeEnum.XLSX);
            Sheet sheet = new Sheet(1, 0, classz);
            sheet.setSheetName(sheetName);
            writer.write(list, sheet);
            writer.finish();
        } catch (Exception e) {
            LogCommons.error("writeExcelOfZip error",e);
        }
        return path;
    }


    /**
     * Export Excel: a sheet with header (not in effect)
     * Custom WriterStyleHandler allows you to customize row and column data for flexible activation
     * @param list      Data list, each element is a BaseRowModel
     * @param fileName  Exported file name
     * @param sheetName Import the sheet name of the file
     */
    public static <T extends BaseRowModel> String writeExcelAddStyle(List<T> list, String fileName, String sheetName, BaseRowModel object)  throws ExcelException{
        String format = new SimpleDateFormat(ConstantCode.DATE_FORMATT).format(new Date());
        String path =System.getProperty(ConstantCode.USER_DIR)+System.getProperty(ConstantCode.FILE_SEPARATOR)+autoFilrName();
        File file = new File(path);
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file))) {
            ZipEntry entry = new ZipEntry(fileName + "(" + format + ")" + ConstantCode.FILE_TYPE);
            zipOutputStream.putNextEntry(entry);
            ExcelWriter writer = EasyExcelFactory.getWriterWithTempAndHandler(null, zipOutputStream, ExcelTypeEnum.XLSX, true,new StyleExcelHandler());
            Sheet sheet = new Sheet(1, 0, object.getClass());
            sheet.setSheetName(sheetName);
            writer.write(list, sheet);
            writer.finish();
        } catch (Exception e) {
            LogCommons.error("writeExcelAddStyle error",e);
        }
        return path;
    }

    /**
     * 生成文件名
     * @return String
     */
    public static String autoFilrName(){
        Date date = new Date();
        String autoFilrName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
        return autoFilrName+".zip";
    }



    /**
     * 返回 ExcelReader
     * @param excel  需要解析的 Excel 文件
     * @param excelListener new ExcelListener()
     */
    private static ExcelReader getReader(MultipartFile excel,
                                         ExcelListener excelListener) {
        String filename = excel.getOriginalFilename();
        if (filename == null || (!filename.toLowerCase().endsWith(".xls") && !filename.toLowerCase().endsWith(".xlsx"))) {
            LogCommons.error("文件格式错误！",filename);
        }
        InputStream inputStream;
        try {
            inputStream = new BufferedInputStream(excel.getInputStream());
            return new ExcelReader(inputStream, null, excelListener, false);
        } catch (IOException e) {
            LogCommons.error("getReader error",e);
        }
        return null;
    }


    /**
     * @param fileName 文件名称
     * @param objects 数据集合
     * @return Boolean
     * 描述：输出的csv文件没有大表头，没有格式样式，不能分sheet，速度快，但是不是文本格式
     * append("\t" + "12345678910" + "\t");  文本格式设置方法
     */
    public static String writeCsv(String fileName, List objects){
        String csvPath =System.getProperty(ConstantCode.USER_DIR)+System.getProperty(ConstantCode.FILE_SEPARATOR)+fileName+".csv";
        String zipPath =System.getProperty(ConstantCode.USER_DIR)+System.getProperty(ConstantCode.FILE_SEPARATOR)+autoFilrName();
        try {
            CsvWriteBs.newInstance(csvPath).write(objects);
        } catch (Exception e) {
            LogCommons.error("输出csv文件失败，错误原因：{}",e);
            return "";
        }
        //压缩文件
        try {
            new ZipCompress(zipPath,csvPath).zip();
        } catch (Exception e) {
            LogCommons.error("压缩文件失败，失败原因：{}",e);
            return "";
        }finally {
            //csv文件删除，zip文件邮件发送后删除
            if (deleteDir(FileUtils.getFile(csvPath))){
                LogCommons.error("csv文件删除成功!");
            }else{
                LogCommons.error("csv文件删除失败！文件路径：{}",csvPath);
            }
        }
        return zipPath;
    }


    /**
     * @param dir 文件目录
     * @return boolean
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
