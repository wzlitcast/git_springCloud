package com.it.cast.entity;

import lombok.Data;

/**
 * @description:
 * @author: WuZhiLong
 * @create: 2019-06-19 17:44
 **/
@Data
public class MsgVo {

    private String mobile;

    private String templateCode;

    private String signName;

    private String param;
}
