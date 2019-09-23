package com.aspire.commons;



import com.aspire.commons.analysis_excel.templateVo.CsvUser;
import com.aspire.commons.analysis_excel.templateVo.ExportUser;


public enum ExcelTemlate {

    EXPORT_USER(ExportUser.class),
    CSV_USER(CsvUser.class);


    private Class classZ;

    public Class getClassZ() {
        return classZ;
    }

    private ExcelTemlate(Class classZ) {
        this.classZ = classZ;
    }
}
