package com.ssw.blog.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.ssw.blog.service.UploadService;
import com.ssw.blog.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileInputStream;
import java.util.UUID;

@Service
public class UploadServiceImpl  implements UploadService {
  //注入常量如果出错的话那就多运行几次就自动解决了
    @Value("${qiniu.accessKey}")
    private String accesskey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket; //空间名称

    @Value("${qiniu.path}")
    private String path; //存储空间->空间概览中的测试域名
    @Override
    public String uploadImage(MultipartFile file) {
        //1.获取图片名称 abc.jpg
        String imageName = file.getOriginalFilename();
        //方式1:list集合 2.map 3.set 4.正则表达式\
//        imageName = imageName.toLowerCase();
//        if(!imageName.matches("^.+\\.(jpg|png|gif)$")) {
//
//            return null;
//        }
       try {
           //2.是否为恶意程序 主要判断图片属性width和heigh
//           BufferedImage bufferedImage =
//                   ImageIO.read(file.getInputStream());
//           int width = bufferedImage.getWidth();
//           int height = bufferedImage.getHeight();
//
//           if(width ==0 || height==0) {
//
//               return null;
//           }
           String originalFilename = file.getOriginalFilename();
           //获取文件后缀.jpg
           String suffix= "."+ StringUtils.substringAfterLast(originalFilename,".");
           FileInputStream inputStream = (FileInputStream) file.getInputStream();
           //构造一个带指定Zone（区域）对象的配置类（七牛云的依赖记得加verson才有这个Zone类）
           //llcweb是华南区域的
           Configuration cfg=new Configuration(Zone.zone0());
           //其他参数参考类注释
           UploadManager uploadManager=new UploadManager(cfg);
           Auth auth=Auth.create(accesskey,secretKey);
           String upToken=auth.uploadToken(bucket);
           //默认不指定key情况下，以文件内容的hash值作为文件名
           Response response=uploadManager.put(inputStream, UUID.randomUUID().toString()+suffix,upToken,null,null);
           //解析上传成功的结果
           DefaultPutRet putRet=new ObjectMapper().readValue(response.bodyString(),DefaultPutRet.class);
           System.out.println(putRet.key); //即参数key
           String return_path=path+"/"+putRet.key;
           return return_path;

       }catch (Exception e){
           return  null;
       }

    }
}
