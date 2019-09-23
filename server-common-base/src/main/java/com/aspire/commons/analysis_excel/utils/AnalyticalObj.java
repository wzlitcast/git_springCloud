package com.aspire.commons.analysis_excel.utils;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.aspire.commons.CommonConstant;
import com.aspire.commons.analysis_excel.annotation.LineNumber;
import com.aspire.commons.analysis_excel.annotation.Mapping;
import com.aspire.commons.analysis_excel.constantCode.ConstantCode;
import com.aspire.commons.analysis_excel.listener.ExcelListener;
import com.aspire.commons.log.LogCommons;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AnalyticalObj<T> {

    private Class<Mapping> mapping = Mapping.class;
    private Class<LineNumber> lineNumber = LineNumber.class;


    Set<String> set = new HashSet<>();

    public List<T> verifyObj(InputStream in, Class<T> clss) {

        ExcelListener listener = new ExcelListener();

        try (BufferedInputStream inputStream = new BufferedInputStream(in)) {
            EasyExcelFactory.readBySax(inputStream, new Sheet(1, 0), listener);
        } catch (IOException e) {
            LogCommons.error("解析Ecxel表格数据失败，失败原因：{}",e);
        }

        List<Object> headStr = listener.getDatas().subList(0, 1);
        List<Object> rowList = listener.getDatas().subList(1, listener.getDatas().size());
        List<T> objectList = new ArrayList<>();
        Object object ;
        try {

            Field[] fields = clss.getDeclaredFields();

            List<String> field = new ArrayList<>();

            for (Field field1 : fields) {
                Mapping m = field1.getAnnotation(mapping);
                if (m != null) {
                    field.add(m.key());
                }
            }
            //判断模板字段
            List<String> headList = (List) headStr.get(0);
            List<String> heads = new ArrayList<>();
            headList.forEach(item->{
                if (StringUtils.isNotEmpty(item))
                    heads.add(item);
            });
            if(!heads.equals(field)){
                return new ArrayList<>();
            }


            for(int i=1;i<=rowList.size();i++){
                object = clss.newInstance();

                //错误信息集合
                StringBuilder errorMessages= new StringBuilder();
                //单行cell循环次数
                Integer times = 0;

                for (Field f : fields) {
                    // 获取当前属性的注解数组，并查看是否有数量
                    Mapping m = f.getAnnotation(mapping);
                    LineNumber ln = f.getAnnotation(lineNumber);

                    if (null != m) {
                        times++;
                        //是否加入错误信息到实体（默认为false）
                        boolean add = false;

                        if ((times)%(field.size())==0){
                            add = true;
                        }

                        //属性赋值操作
                        Map<String,Object> mappingOperation = this.mappingOperation(object, f, m, heads, rowList,i-1,add,errorMessages.toString());

                        //获取每一次的错误信息
                        if(mappingOperation.get(ConstantCode.ERRORMESSAGE)!=null){
                            errorMessages.append(mappingOperation.get(ConstantCode.ERRORMESSAGE).toString()+";");
                        }

                        // 获取注解是否删除null属性
                        // 如果这次映射失败，属性值为null
                        // 并且注解标识需要删除该字段为null的信息，则删除
                        if(m.delNull() && !(Boolean) mappingOperation.get("boolean")) {
                            return new ArrayList<>();
                        }
                    }
                    if(null != ln) {
                        // 行数映射操作
                        this.lineNumberOperation(object, f, i+1);
                    }
                }
                objectList.add((T) object);
            }
        } catch (ReflectiveOperationException e)
        {
            LogCommons.error("AnalyticalObj.verifyObj()解析Excel表格数据或者利用反射对象失败 error {}",e);
        }
        return objectList;
    }

    private Map<String,Object> mappingOperation(Object object, Field field, Mapping mapping, List<String> headStr, List<Object> analysisRow,int num,boolean add,String errorMessages) throws ReflectiveOperationException {
        // 获取字段名称
        String fieldName = field.getName();
        // 获取注解映射属性
        String title = mapping.key();
        // 获取注解正则属性
        String rex = mapping.rex();
        // 获取注解的截取长度
        int length = mapping.length();
        //获取注解去重属性
        boolean flag= mapping.distinct();
        //返回错误信息返回+添加信息
        Map<String, Object> addResult = new HashMap<>();
        //总结error信息只有在切换行的时候可以使用的到
        StringBuilder finalMessage = new StringBuilder();

        // 拼接方法名
        String methodName = new StringBuilder().append("set").append(upperCase(fieldName)).toString();

        String fieldType = field.getType().toString();

        for(int y=0; y<headStr.size(); y++){
            // 当前头标
            String thisHead = headStr.get(y).trim();
            if(title.equals(thisHead) && !StringUtils.isEmpty(thisHead)){
                List strList = (List) analysisRow.get(num);
                String string = "";
                try {
                    string = strList.get(y)==null?"":strList.get(y).toString().trim();
                } catch (IndexOutOfBoundsException e) {
                    LogCommons.debug("IndexOutOfBoundsException error{}",e) ;
                }

                //添加错误信息（必填错误）
                if (mapping.delNull() && StringUtils.isEmpty(string)){
                    if (add){
                        finalMessage.append("["+title+"]"+ CommonConstant.FILED_DATA_NULL_ERROR);
                    }else{
                        addResult.put(ConstantCode.ERRORMESSAGE, "["+title+"]"+ CommonConstant.FILED_DATA_NULL_ERROR);
                        addResult.put(ConstantCode.BOOLEAN,false);
                    }
                }

                //添加错误信息（科学计数法/时间格式 格式错误）
                if (!"".endsWith(string) && !"class java.util.Date".equals(fieldType)){
                    Pattern compile1 = Pattern.compile(ConstantCode.SCIENTIFIC_NOTATION_REX);
                    Pattern compile2 = Pattern.compile(ConstantCode.DATE_REX);
                    Matcher matcher1 = compile1.matcher(string);
                    Matcher matcher2 = compile2.matcher(string);
                    boolean matches1 = matcher1.matches();
                    boolean matches2 = matcher2.matches();
                    if (matches1||matches2 ) {
                        // 如果匹配失败设为null
                        if (add){
                            finalMessage.append("["+title+":"+string+"]"+ CommonConstant.FILED_TYPE_ERROR);
                        }else{
                            addResult.put(ConstantCode.ERRORMESSAGE,"["+title+":"+string+"]"+ CommonConstant.FILED_TYPE_ERROR);
                            addResult.put(ConstantCode.BOOLEAN,false);
                        }
                    }
                }

                //添加错误信息（长度错误）
                if (length!=-1 && !StringUtils.isEmpty(string) && string.length()>length){
                    if (add){
                        finalMessage.append("["+title+":"+string+"]"+ CommonConstant.FILED_LENGTH_ERROR+length+"个字！");
                    }else{
                        addResult.put(ConstantCode.ERRORMESSAGE,"["+title+":"+string+"]"+ CommonConstant.FILED_LENGTH_ERROR+length+"个字！");
                        addResult.put(ConstantCode.BOOLEAN,false);
                    }
                }

                //数据去重
                if(flag && !set.add(string)){
                    if (add){
                        finalMessage.append("["+title+":"+string+"]"+ CommonConstant.FILED_DATA_REPETITION_ERROR);
                    }else{
                        addResult.put(ConstantCode.ERRORMESSAGE,"["+title+":"+string+"]"+ CommonConstant.FILED_DATA_REPETITION_ERROR);
                        addResult.put(ConstantCode.BOOLEAN,false);
                    }
                 }


                //添加错误信息（格式错误）,判断正则是否为空，为空则不处理
                if(!StringUtils.isEmpty(rex) && !StringUtils.isEmpty(string)) {
                    boolean matches = string.matches(rex);
                    if(!matches) {
                        // 如果匹配失败设为null
                        if (add){
                            finalMessage.append("["+title+":"+string+"]"+ CommonConstant.FILED_TYPE_ERROR2);
                        }else{
                            addResult.put(ConstantCode.ERRORMESSAGE,"["+title+":"+string+"]"+ CommonConstant.FILED_TYPE_ERROR2);
                            addResult.put(ConstantCode.BOOLEAN,false);
                        }
                    }
                }
                if(add){
                    this.screening(object, "setErrorMessage", "class java.lang.String", errorMessages+finalMessage);
                }
                this.screening(object, methodName, fieldType, string);
                addResult.put(ConstantCode.BOOLEAN,true);
                return addResult;
            }
        }
        addResult.put(ConstantCode.BOOLEAN,false);
        return addResult;
    }



    private void lineNumberOperation(Object object, Field field,int lineNum) throws ReflectiveOperationException {
        // 获取字段名称
        String fieldName = field.getName();
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String setMethodName = "set" + firstLetter + fieldName.substring(1);
        String fieldType = field.getType().toString();
        this.screening(object, setMethodName, fieldType, String.valueOf(lineNum));
    }


    private void screening( Object object, String methodName, String fieldType, String fieldValue) throws ReflectiveOperationException {
        Double parseToDouble = null;
        switch(fieldType){
            case "class java.util.Date":
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                format.setLenient(false);
                Date parse = null;
                try{
                    parse = format.parse(fieldValue);
                }catch(ParseException e){
                    // 如果异常，比如字段为空或null，设置为null
                    parse = null;
                }
                getMethod(object,methodName,Date.class).invoke(object, parse);
                break;
            case "class java.lang.Integer":
                Integer parseInt = null;
                parseToDouble = parseToDouble(fieldValue);
                if(parseToDouble != null) {
                    double doubleVal = parseToDouble;
                    parseInt = (int)doubleVal;
                }
                try {
                    parseInt = Integer.parseInt(fieldValue);
                }catch(NumberFormatException e) {
                    LogCommons.debug("parseInt() error {}",e) ;}
                getMethod(object,methodName,Integer.class).invoke(object, parseInt);
                break;
            case "class java.lang.String":
                if(StringUtils.isEmpty(fieldType)) {
                    break;
                }
                getMethod(object,methodName,String.class).invoke(object, fieldValue.trim());
                break;
            case "class java.lang.Long":
                Long parseLong = null;

                parseToDouble = parseToDouble(fieldValue);
                if(parseToDouble != null) {
                    double doubleVal = parseToDouble;
                    parseLong = (long)doubleVal;
                }
                try{
                    parseLong = Long.parseLong(fieldValue);
                }catch(NumberFormatException e){
                    LogCommons.debug("parseLong() error {}",e) ;}
                getMethod(object,methodName,Long.class).invoke(object, parseLong);
                break;
            case "class java.lang.Double":
                Double parseDouble = null;
                try{
                    parseDouble = Double.parseDouble(fieldValue);
                }catch(NumberFormatException e){
                    LogCommons.debug("parseDouble() error {}",e) ;}
                getMethod(object,methodName,Double.class).invoke(object, parseDouble);
                break;
            case "class java.lang.Float":
                Float parseFloat = null;
                try{
                    parseFloat = Float.parseFloat(fieldValue);
                }catch(NumberFormatException e){
                    LogCommons.debug("parseFloat() error {}",e) ;}
                getMethod(object,methodName,Float.class).invoke(object, parseFloat);
                break;
            case "class java.lang.Short":
                Short parseShort = null;

                parseToDouble = parseToDouble(fieldValue);
                if(parseToDouble != null) {
                    double doubleVal = parseToDouble;
                    parseShort = (short)doubleVal;
                }
                try{
                    parseShort = Short.parseShort(fieldValue);
                }catch(NumberFormatException e){
                    LogCommons.debug("parseShort() error {}",e) ;}
                getMethod(object,methodName,Short.class).invoke(object, parseShort);
                break;
            case "class java.lang.Boolean":
                Boolean parseBoolean = Boolean.parseBoolean(fieldValue);
                getMethod(object,methodName,Boolean.class).invoke(object, parseBoolean);
                break;
                default:
                    break;
        }
    }

    /**
     * 转为Double类型
     * @return
     */
    public Double parseToDouble(String fieldValue) {
        Double parseDouble = null;
        try{
            if(fieldValue != null && fieldValue.contains(".")) {
                parseDouble = Double.parseDouble(fieldValue);
            }
        }catch(NumberFormatException e){
            LogCommons.debug("parseToDouble() error {}",e) ;}
        return parseDouble;
    }

    /**
     * 将字符串首字母大写
     * @param str
     * @return
     */
    public static String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    private Method getMethod(Object obj, String methodName, Class<?>... parameterTypes) throws ReflectiveOperationException {
        try {
            return obj.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            LogCommons.error("AOP切面field反射异常,obj：{},name:{}", obj, methodName, e);
            throw new ReflectiveOperationException("切面类获取get方法异常", e);
        }
    }

}
