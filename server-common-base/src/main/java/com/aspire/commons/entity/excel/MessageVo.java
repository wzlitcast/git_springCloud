package com.aspire.commons.entity.excel;

import com.aspire.commons.ExcelTemlate;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class MessageVo<T> implements Serializable {
    //主題
    private String subject;
    //收件人邮箱
    private String email;
    //发送类型 1 导出 2 导入
    private String sendType;
    //成功条数
    private Integer successNum;
    //失败条数
    private Integer failNum;
    //导出模板实体
    private List<T> entityList;
    private ExcelTemlate excelTemlate;
    //错误信息集合
    private List<Map<String,String>> errorInfo;
}
