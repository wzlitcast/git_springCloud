package com.it.cast.util;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aspire.commons.log.LogCommons;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.it.cast.enums.MsgType;

/**
 * Created on 17/6/7.
 * 短信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过
 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可)
 * 工程依赖了2个jar包(存放在工程的libs目录下)
 * 1:aliyun-java-sdk-core.jar
 * 2:aliyun-java-sdk-dysmsapi.jar
 *
 * 备注:Demo工程编码采用UTF-8
 * 国际短信发送请勿参照此DEMO
 */
@Component
public class SmsUtil {

    @Value("${accessKeyId}")
    private String accessKeyId;

    @Value("${accessKeySecret}")
    private String accessKeySecret;

    @Value("${reqionid}")
    private String reqionid;

    @Value("${doamin}")
    private String doamin;

    @Value("${templateCode}")
    private String templateCode;

    @Value("${templateCodeT}")
    private String templateCodeT;

    @Value("${version}")
    private String version;


    /**
     * 发送短信
     * */
    public CommonResponse sendSms(String mobile, String templateCode, String signName, String param){

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        DefaultProfile profile = DefaultProfile.getProfile(reqionid, accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(doamin);
        request.setVersion(version);
        request.setAction(MsgType.SEND_SMS.getValue());
        request.putQueryParameter("RegionId", reqionid);
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", param);
        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
            LogCommons.info(response.getData());
        } catch (ServerException e) {
           LogCommons.error("error1:{}",e);
        } catch (ClientException e1) {
            LogCommons.error("error2:{}",e1);
        }
        return response;
    }


    public CommonResponse querySendDetails(String mobile,String date ,String pageSize,String currentPage,String bizId){

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        DefaultProfile profile = DefaultProfile.getProfile(reqionid, accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(doamin);
        request.setVersion(version);
        request.setAction(MsgType.QUERY_SEND_DETAILS.getValue());
        request.putQueryParameter("PhoneNumber", mobile);
        request.putQueryParameter("SendDate", date);
        request.putQueryParameter("PageSize", pageSize);
        request.putQueryParameter("CurrentPage", currentPage);
        request.putQueryParameter("BizId",bizId);
        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
            LogCommons.info(response.getData());
        } catch (ServerException e) {
            LogCommons.error("error3:{}",e);
        } catch (ClientException e1) {
            LogCommons.error("error4:{}",e1);
        }
        return response;
    }

    public CommonResponse SendBatchSms(String mobile, String templateCode, String signName, String param){

        DefaultProfile profile = DefaultProfile.getProfile(reqionid, accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(doamin);
        request.setVersion(version);
        request.setAction(MsgType.SEND_BATCH_SMS.getValue());
        request.putQueryParameter("PhoneNumberJson", mobile);
        request.putQueryParameter("SignNameJson", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParamJson", param);
        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
            LogCommons.info(response.getData());
        } catch (ServerException e) {
            LogCommons.error("error3:{}",e);
        } catch (ClientException e1) {
            LogCommons.error("error4:{}",e1);
        }
        return response;
    }
}
