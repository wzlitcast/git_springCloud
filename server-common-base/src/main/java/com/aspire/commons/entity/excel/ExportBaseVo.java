package com.aspire.commons.entity.excel;

import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.util.List;

@Data
public class ExportBaseVo<T extends BaseRowModel> {

    private String fileName;

    private String sheetName;

    private Class t;

    private List<? extends BaseRowModel> entityList;

}
