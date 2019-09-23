package com.aspire.commons.entity.email;


import com.aspire.commons.ExcelTemlate;
import lombok.Data;

import java.util.List;

@Data
public class BuffTypeExcelVo {

    /**
     * 插入数据
     */
    private List entityList;

    /**
     * 路径后缀名（UUID.zip）
     */
    private String suffixName;

    /**
     * 泛型实例
     */
    private ExcelTemlate excelTemlate;

    /**
     * 邮件
     */
    private String email;

    /**
     * sheetName
     */
    private String sheetName;

    /**
     * sheetNo
     */
    private Integer sheetNo;


    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 判断是否为最后一次生成sheet  0不是，1是
     */
    private String flag;

}
