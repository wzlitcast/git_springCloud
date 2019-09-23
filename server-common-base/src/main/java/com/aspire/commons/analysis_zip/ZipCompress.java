package com.aspire.commons.analysis_zip;

import com.aspire.commons.log.LogCommons;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCompress {

    /**目的地Zip文件*/
    private String zipFileName;
    /**源文件（带压缩的文件或文件夹)*/
    private String sourceFileName;

    public ZipCompress(String zipFileName,String sourceFileName)
    {
        this.zipFileName=zipFileName;
        this.sourceFileName=sourceFileName;
    }

    public void zip() throws Exception {
        LogCommons.info("压缩中...");
        //创建zip输出流
        ZipOutputStream out = new ZipOutputStream( new FileOutputStream(zipFileName));
        //创建缓冲输出流
        BufferedOutputStream bos = new BufferedOutputStream(out);
        File sourceFile = new File(sourceFileName);
        //调用函数
        compress(out,bos,sourceFile,sourceFile.getName());
        bos.close();
        out.close();
        LogCommons.info("压缩完成");
    }

    public void compress(ZipOutputStream out,BufferedOutputStream bos,File sourceFile,String base) throws Exception
    {
        //如果路径为目录（文件夹）
        if(sourceFile.isDirectory()) {
            //取出文件夹中的文件或子文件夹
            File[] flist = sourceFile.listFiles();
            //如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            if(flist.length==0) {
                LogCommons.info(base+"/");
                out.putNextEntry(  new ZipEntry(base+"/") );
            }
            //如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
            else{
                for(int i=0;i<flist.length;i++) {
                    compress(out,bos,flist[i],base+"/"+flist[i].getName());
                }
            }
        }
        //如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
        else {
            out.putNextEntry( new ZipEntry(base) );
            FileInputStream fos = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fos);

            int tag;
            LogCommons.info(base);
            //将源文件写入到zip文件中
            while((tag=bis.read())!=-1)
            {
                bos.write(tag);
            }
            bis.close();
            fos.close();

        }
    }
}
