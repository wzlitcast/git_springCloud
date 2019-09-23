package com.aspire.commons.analysis_zip;


import com.aspire.commons.AjaxResult;
import com.aspire.commons.log.LogCommons;
import com.aspire.commons.response_json.CommonJsonNode;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 压缩文件处理
 */
public class ZipUtils {

    public static final String ZIP = "zip";
    public static final String DOT = "\\.";
    public static final String GBK = "GBK";
    public static final String MS = "ms";


    /**
     * 文件解压
     * @param multipartFile
     * @return
     */
    public AjaxResult<JsonNode> unZip(MultipartFile multipartFile) throws IOException {
        long startTime=System.currentTimeMillis();
        String originalFilename = multipartFile.getOriginalFilename();
        //判断压缩包文件类型是否正确
        if (!ZIP.equals(Arrays.asList(originalFilename.split(DOT)).get(1))){
            return new AjaxResult<JsonNode>().fail("-1","文件类型错误，请重新操作！");
        }
        //选择解压后路径(该项目根目录下)
        File f = new File(new ZipUtils().getClass().getResource("/").getPath());
        String merchantUnZipPath = f.toString();
        //MultipartFile转File
        String prefix=originalFilename.substring(originalFilename.lastIndexOf("."));
        final File file = File.createTempFile(UUID.randomUUID().toString(), prefix);
        multipartFile.transferTo(file);

        ZipFile zipFile = null;
        String unZipPath = null;
        try {
            //设置编码格式
            zipFile = new ZipFile(file, Charset.forName(GBK));
            Enumeration e = zipFile.entries();
            int i = 0;
            while (e.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) e.nextElement();
                String name = zipEntry.getName();
                if (zipEntry.isDirectory()) {
                    i++;
                    name = name.substring(0, name.length() - 1);
                    if (i==1){
                        unZipPath = name;
                    }
                    File file2 = new File(merchantUnZipPath + File.separator + name);
                    file2.mkdirs();
                } else {
                    File file2 = new File(merchantUnZipPath + File.separator + name);
                    file2.getParentFile().mkdirs();
                    try( InputStream in = zipFile.getInputStream(zipEntry); FileOutputStream out = new FileOutputStream(file2)){
                    int length = 0;
                    byte[] readByte = new byte[1024];
                        while ((length = in.read(readByte, 0, 1024)) != -1) {
                            out.write(readByte, 0, length);
                        }
                    } catch (Exception e2) {
                        LogCommons.error("解压文件失败!",e2);
                    }
                }
            }

        } catch (IOException e) {
            LogCommons.error("解压文件不存在!", e);
        }finally {
            zipFile.close();
        }

        long endTime=System.currentTimeMillis();
        LogCommons.info("解压耗时",()->endTime-startTime+MS);
        return new AjaxResult<JsonNode>().success(CommonJsonNode.results(merchantUnZipPath + File.separator+ unZipPath));
    }
}