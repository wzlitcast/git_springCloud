package com.aspire.commons.analysis_excel.templateVo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class GroupBuyingOrderExport extends BaseRowModel {

    private String orderId;
    private String orderTime;
    private String goodsTogether;

    @ExcelProperty(value = {"订单号","下单时间","商品合计","","商品信息"},index = 0)
    private String goodsInfo;

    @ExcelProperty(value = {"","","","","单价"},index = 1)
    private String price;

    @ExcelProperty(value = {"数量"},index = 2)
    private String number;

    @ExcelProperty(value = {"小计"},index = 3)
    private String subtotal;

}
