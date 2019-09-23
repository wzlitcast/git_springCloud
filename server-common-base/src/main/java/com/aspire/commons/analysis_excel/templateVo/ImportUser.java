package com.aspire.commons.analysis_excel.templateVo;

import com.alibaba.excel.metadata.BaseRowModel;
import com.aspire.commons.analysis_excel.annotation.LineNumber;
import com.aspire.commons.analysis_excel.annotation.Mapping;
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
public class ImportUser extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @LineNumber
    private Integer LineNum;

    @Mapping(key = "员工编号",delNull = true)
    private String userId;

    @Mapping(key = "姓名",delNull = true)
    private String userName;

    @Mapping(key = "邮箱",delNull = true)
    private String email;

    @Mapping(key = "密码",delNull = true)
    private String password;

    private String errorMessage;

}
