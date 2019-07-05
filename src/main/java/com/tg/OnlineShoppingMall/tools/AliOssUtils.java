package com.tg.OnlineShoppingMall.tools;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;


import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;


/**
 * # OSS 配置
 * oss.endpoint=http://oss-cn-shanghai.aliyuncs.com
 * oss.accessKeyId=LTAICHWL4ouRcNfi
 * oss.accessKeySecret=wGZVwRzhNR59uAJYCieZHPKI3VVtiA
 * oss.bucketName=get-blog
 * oss.expireTime=3600
 */
public class AliOssUtils {
    //oss配置
    private static String endpoint="http://oss-cn-shanghai.aliyuncs.com";

    private static String accessKeyId="LTAICHWL4ouRcNfi";

    private static String accessKeySecret="wGZVwRzhNR59uAJYCieZHPKI3VVtiA";

    private static String bucketName="get-blog";

    private OSSClient ossClient;
    public AliOssUtils(){
       this.ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }
    public  String upload(MultipartFile file,String path){
        String url="";
        //文件不为空
        if(null == file){
            url="";
        }else{
            //不为空时创建
            try{
                //上传到OSS
                String objectName =path;
                ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(file.getBytes()));
                url = "https://"+bucketName+".oss-cn-shanghai.aliyuncs.com/"+objectName;
            }catch (OSSException oe){
                System.out.println(oe.getMessage());
            }catch (ClientException ce){
                System.out.println(ce.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                //关闭
                ossClient.shutdown();
            }
        }
        return url;
    }
    
}