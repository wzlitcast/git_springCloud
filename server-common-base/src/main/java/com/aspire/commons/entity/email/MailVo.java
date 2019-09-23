package com.aspire.commons.entity.email;

import lombok.Data;

@Data
public class MailVo {
    //收件人
    private String receiver;
    //主题
    private String subject;
    //内容
    private String content;
}
