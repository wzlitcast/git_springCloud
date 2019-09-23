package com.aspire.commons.analysis_excel.templateVo;

import com.aspire.commons.csv.annotation.Csv;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2019-06-11
 */
@Data
public class CsvUser{

    @Csv(label = "账号",textStyle = true)
    private String userId;

    @Csv(label = "姓名")
    private String userName;

    @Csv(label = "邮箱")
    private String email;

    @Csv(label = "密码")
    private String password;
}
