package com.aspire.commons.analysis_excel.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description 监听类，可以自定义
 * @Date 2018-06-05
 * @Time 16:58
 */
@Slf4j
public class ExcelListener extends AnalysisEventListener {

    /**
     * 自定义用于暂时存储data,可以通过实例获取该值
     */
    private List<Object> datas = new ArrayList<>();


    /**
     * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
     */
    @Override
    public void invoke(Object object, AnalysisContext context) {
        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        datas.add(object);
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //解析结束销毁不用的资源
    }

    public List<Object> getDatas() {
        //解决模板重复使用导致会有很多行数据为空也被校验）
        int flag = 0;
        for(int i = (datas.size()-1);i>=0;i--){
            if (checkElementsIsNull(datas.get(i))) {
                flag = i;
                break;
            }
        }
        return datas.subList(0,flag+1);
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    private boolean checkElementsIsNull(Object obj){
        boolean b = false;
        if (null==obj) {
            return b;
        }
        String objStr = obj.toString();
        String[] split = objStr.substring(1, objStr.length() - 1).split(",");

        for (int i=0;i<split.length;i++){
            if (!split[i].trim().equals("null")){
                b = true;
                break;
            }else {
                b = false;
            }
        }
        return b;
    }
}