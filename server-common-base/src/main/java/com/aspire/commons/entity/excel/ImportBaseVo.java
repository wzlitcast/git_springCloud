package com.aspire.commons.entity.excel;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ImportBaseVo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    //用户id
    private String creator;
    //收件人邮箱
    private String email;
    //0 不是最后一次，1 最后一次
    private int flag = 0;
    //总数
    private int totalNum;
    //业务id
    private String msgId;
    //导入实体
    private List entityList = new ArrayList<>();
}
