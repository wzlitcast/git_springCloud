package com.aspire.commons.analysis_excel.templateVo;


import com.alibaba.excel.metadata.BaseRowModel;
import com.aspire.commons.analysis_excel.annotation.LineNumber;
import com.aspire.commons.analysis_excel.annotation.Mapping;
import lombok.Data;

@Data
public class ImportGoods extends BaseRowModel {

    @LineNumber
    private Integer lineNum;

    @Mapping(key="SKU编号（必填）",delNull = true,rex="^[0-9]{3,}$",distinct = true)
    private String skuId;

    @Mapping(key="固定售价（单位：元）",rex="^([0-9]{0,5})(\\.[0-9]{1,2})?$")
    private Double fixedPrice;

    private String errorMessage;

}
