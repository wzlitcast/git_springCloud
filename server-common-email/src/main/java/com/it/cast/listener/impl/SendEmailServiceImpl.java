package com.it.cast.listener.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.aspire.commons.analysis_excel.constantCode.ConstantCode;
import com.aspire.commons.analysis_excel.handler.StyleExcelHandler;
import com.aspire.commons.analysis_excel.utils.ExcelUtil;
import com.aspire.commons.analysis_zip.ZipCompress;
import com.aspire.commons.entity.email.BuffTypeExcelVo;
import com.aspire.commons.response_json.CommonJsonNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.it.cast.listener.SendEmailService;
import com.it.cast.utils.Email;
import com.aspire.commons.AjaxResult;
import com.aspire.commons.CommonConstant;
import com.aspire.commons.entity.email.MailVo;
import com.aspire.commons.entity.excel.ExportBaseVo;
import com.aspire.commons.entity.excel.MessageVo;
import com.aspire.commons.log.LogCommons;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import java.io.*;
import java.util.*;

@Service
public class SendEmailServiceImpl implements SendEmailService {

    private ObjectMapper mapper = new ObjectMapper();

    private Email email = new Email();

    @Override
    public void excelMail(MessageVo messageVo) {

        try {
            LogCommons.info("HandleExcelAndEmail.processing:{}",messageVo.getSendType());
            if ("1".equals(messageVo.getSendType())){
                ExportBaseVo baseRowModelExportBaseVo = new ExportBaseVo();
                Class classZ = messageVo.getExcelTemlate().getClassZ();
                List<Object> objects = new ArrayList<>();
                messageVo.getEntityList().forEach(item->{
                    try {
                        objects.add(mapper.readValue(mapper.writeValueAsString(item),classZ));
                    } catch (IOException e) {
                       LogCommons.error("对象解析失败",e);
                    }
                });
                baseRowModelExportBaseVo.setEntityList(objects);
                String content = "";
                List<Map<String,String>> errorInfo = messageVo.getErrorInfo();
                if (null != errorInfo  && errorInfo.size()>0){
                    //有错误信息
                    for (Map item:
                            errorInfo) {
                        for (Object item2:
                                item.values()) {
                            content += item2+"  ";
                        }
                        content = content+"<br>";
                    }
                }else{
                    content = "结果已生成excel，请查收附件！";
                }
                //生成excel并返回地址
                String filePath = null;
                try {
                    filePath = ExcelUtil.writeExcelOfZip(baseRowModelExportBaseVo.getEntityList(),messageVo.getSubject(),messageVo.getSubject(),classZ);
                    //filePath = ExcelUtil.writeCsv(messageVo.getSubject(),objects);
                } catch (Exception e) {
                    LogCommons.error("processing WriteExcel.exportExcel error",e);
                    Map<String, String> excelError = new HashMap<>();
                    messageVo.getErrorInfo().add(excelError);
                    email.sendMail(messageVo.getEmail(),messageVo.getSubject(),"生成excel表格异常，请稍后重试！",null);
                }
                email.sendMail(messageVo.getEmail(),messageVo.getSubject(),content,filePath);
            }else{
                //导入失败信息遍历
                List<Map<String, String>> errorInfo = messageVo.getErrorInfo();
                List<Map<String, String>> error1 = new ArrayList<>();
                StringBuilder builder = new StringBuilder();
                errorInfo.forEach(item->{
                    if (!StringUtils.isEmpty(item.get(CommonConstant.LINE_NUM))){
                        error1.add(item);
                    }
                    if (!StringUtils.isEmpty(item.get(CommonConstant.ERROR))){
                        builder.append(item.get(CommonConstant.ERROR)+"<br>");
                    }
                });
                //排序
                error1.sort((o1,o2)->Integer.valueOf(o1.get(CommonConstant.LINE_NUM))-Integer.valueOf(o2.get(CommonConstant.LINE_NUM)));
                if(null==messageVo.getSuccessNum())
                    messageVo.setSuccessNum(0);
                if(null==messageVo.getFailNum())
                    messageVo.setFailNum(0);
                String error = "数据导入    成功条数："+messageVo.getSuccessNum()+"，失败条数："+messageVo.getFailNum()+"<br>";

                for (Map<String,String> item: error1) {
                    String m1 = "";
                    if (null!=item.get(CommonConstant.LINE_NUM))
                        m1+="第"+item.get(CommonConstant.LINE_NUM)+"行:&nbsp;&nbsp;";
                    if (null!=item.get(CommonConstant.INVALID))
                        m1+=item.get(CommonConstant.INVALID);
                    error+=m1+"<br>";
                }
                email.sendMail(messageVo.getEmail(),messageVo.getSubject(),error+builder,null);
            }
        } catch (Exception e) {
            LogCommons.error("SendEmailServiceImpl.excelMail error",e);
        }
    }

    @Override
    public AjaxResult<JsonNode> commonMail(MailVo mailVo) throws Exception {
        try {
            LogCommons.info("commonMail start:{}",mailVo);
            email.sendMail(mailVo.getReceiver(),mailVo.getSubject(),mailVo.getContent(),null);
        } catch (Exception e) {
            LogCommons.error("commonMail error:{}",e);
            return new AjaxResult<JsonNode>().fail("-1","邮件发送失败");
        }
        return new AjaxResult<JsonNode>().success();
    }

    @Override
    public AjaxResult<JsonNode> bufferTypeMail(byte[] bytes,String email,String fileName) throws Exception{
        Email email1 = new Email();
        try {
            email1.sendMail2(email,"测试邮件","测试邮件",bytes,fileName);
        } catch (Exception e) {
            LogCommons.error("error:{}",e);
            return new AjaxResult<JsonNode>().fail("-1","邮件发送失败");
        }
        return new AjaxResult<JsonNode>().success();
    }

    @Override
    public AjaxResult<JsonNode> commonMail2(String param) throws Exception {
        try {
            MailVo mailVo = new ObjectMapper().readValue(param, new TypeReference<MailVo>() {
            });
            LogCommons.info("commonMail start:{}",mailVo);
            email.sendMail(mailVo.getReceiver(),mailVo.getSubject(),mailVo.getContent(),null);
        } catch (Exception e) {
            LogCommons.error("commonMail error:{}",e);
            return new AjaxResult<JsonNode>().fail("-1","邮件发送失败");
        }
        return new AjaxResult<JsonNode>().success();
    }

}
