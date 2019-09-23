package com.aspire.commons.analysis_excel.templateVo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2019-05-10
 */
@Data
public class ExportUser extends BaseRowModel implements Serializable {

    @ExcelProperty(value = "用户ID" ,index = 0)
    private String userId;

    @ExcelProperty(value = "姓名" ,index = 1)
    private String userName;

    @ExcelProperty(value = "邮箱" ,index = 2)
    private String email;

    @ExcelProperty(value = "密码" ,index = 3)
    private String password;
	

}
