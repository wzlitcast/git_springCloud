package com.it.cast.utils;

import com.aspire.commons.analysis_zip.ZipCompress;
import com.aspire.commons.log.LogCommons;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.*;
import java.util.Properties;
import java.util.UUID;

public class Email {

    private Message message;


    private static final String port = "25";
    private static final String password = "wzl54506~";
    private static final String sender = "wuzhilong@aspirecn.com";
    private static final String host = "smtp.mail.139.com";
    private static final String protocol = "smtp";
    private static final String auth ="true";


    /**
     * @throws Exception
     */
    public void sendMail(String email, String subject,String content,String filePath) throws Exception {
        LogCommons.info("Email.sendMail()->发送邮件："+email+","+subject+","+content+","+filePath);
        try {
            Properties prop = new Properties();
            prop.setProperty("mail.host", host);
            prop.setProperty("mail.transport.protocol", protocol);
            prop.setProperty("mail.smtp.auth", auth);
            //使用JavaMail发送邮件的5个步骤
            //1、创建session
            Session session = Session.getInstance(prop);
            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            LogCommons.info("邮件开始发送************"+email);
            session.setDebug(false);
            //2、通过session得到transport对象
            Transport ts = session.getTransport();
            //3、连上邮件服务器
            ts.connect(host, sender, password);
            //4、创建邮件
            message = dataProcessing(session,email,subject,content,filePath);
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        }catch (Exception e){
            LogCommons.error("创建邮件容器失败,失败原因：{}",e);
        }finally {
            if (null!=filePath &&  !"".equals(filePath)) {
                boolean b = deleteDir(new File(filePath));
                if (b) {
                    LogCommons.info("文件删除成功");
                } else {
                    LogCommons.info("文件删除失败,文件路径：" + filePath);
                }
            }
        }
    }

    public void sendMail2(String email, String subject,String content,byte[] bytes,String fileName) throws Exception {
        LogCommons.info("Email.sendMail()->发送邮件："+email+","+subject+","+content+","+bytes);
        try {
            Properties prop = new Properties();
            prop.setProperty("mail.host", host);
            prop.setProperty("mail.transport.protocol", protocol);
            prop.setProperty("mail.smtp.auth", auth);
            //使用JavaMail发送邮件的5个步骤
            //1、创建session
            Session session = Session.getInstance(prop);
            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            LogCommons.info("邮件开始发送************"+email);
            session.setDebug(false);
            //2、通过session得到transport对象
            Transport ts = session.getTransport();
            //3、连上邮件服务器
            ts.connect(host, sender, password);
            //4、创建邮件
            message = fileExportMail(session,email,subject,content,bytes,fileName);
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        }catch (Exception e){
            LogCommons.error("创建邮件容器失败,失败原因：{}",e);
        }
    }


    /**
     * excel数据导出邮件发送
     * @param session
     * @return
     * @throws Exception
     */
    public MimeMessage dataProcessing(Session session,String email, String subject,String content,String filePath) throws Exception{
        MimeMessage message = new MimeMessage(session);

        //设置邮件的基本信息
        //发件人
        message.setFrom(new InternetAddress(sender));
        //收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));

        //    To: 增加收件人（可选）
        //message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress("dd@receive.com"));
        //    Cc: 抄送（可选）
        //message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress("ee@receive.com"));
        //    Bcc: 密送（可选）
        //message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress("ff@receive.com"));


        //邮件标题
        message.setSubject(subject);

        MimeBodyPart text = new MimeBodyPart();
        text.setContent(content, "text/html;charset=UTF-8");

        if (null==filePath ||  "".equals(filePath)){
            //创建容器描述数据关系
            MimeMultipart mp = new MimeMultipart();
            mp.addBodyPart(text);
            mp.setSubType("mixed");

            message.setContent(mp);
            message.saveChanges();

            //返回生成的邮件
            return message;
        }
        //创建邮件附件
        MimeBodyPart attach = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource(filePath));
        attach.setDataHandler(dh);
        attach.setFileName(dh.getName());

        //创建容器描述数据关系
        MimeMultipart mp = new MimeMultipart();
        mp.addBodyPart(text);
        mp.addBodyPart(attach);
        mp.setSubType("mixed");

        message.setContent(mp);
        message.saveChanges();

        //返回生成的邮件
        return message;
    }

    /**
     * excel数据导出邮件发送
     * @param session
     * @return
     * @throws Exception
     */
    public MimeMessage fileExportMail(Session session, String email, String subject, String content,byte[] bytes,String fileName) throws Exception{
        String PROJECT_PATH = System.getProperty("user.dir")+System.getProperty("file.separator");
        MimeMessage message = new MimeMessage(session);

        //发件人
        message.setFrom(new InternetAddress(sender));
        //收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        //邮件标题
        message.setSubject(subject);

        MimeBodyPart text = new MimeBodyPart();
        text.setContent(content, "text/html;charset=UTF-8");

        //创建邮件附件
        MimeBodyPart attach = new MimeBodyPart();

        //byte写入到文件中(目前只找到这种方法)
        String excelPath = fileName;
        String zipPath = UUID.randomUUID().toString()+".zip";
        try (FileOutputStream outputStream  =new FileOutputStream(new File(excelPath))){
            outputStream.write(bytes);
        } catch (IOException e) {
            LogCommons.error("error:{}",e);
        }

        //压缩成zip形式
        ZipCompress zipCompress = new ZipCompress(zipPath, excelPath);
        zipCompress.zip();

        //转InputStream代替原来用路径获取文件的方式
        DataHandler dh = new DataHandler(new FileDataSource(zipPath));
        attach.setDataHandler(dh);
        attach.setFileName(dh.getName());

        //创建容器描述数据关系
        MimeMultipart mp = new MimeMultipart();
        mp.addBodyPart(text);
        mp.addBodyPart(attach);
        mp.setSubType("mixed");

        message.setContent(mp);
        message.saveChanges();

        //返回生成的邮件
        return message;
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

}
