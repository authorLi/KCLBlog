package com.mycclee.blog.controller;

import com.google.gson.Gson;
import com.mycclee.blog.utils.ResponseUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author mycclee
 * @createTime 2019/7/17 11:21
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    @PostMapping
    public ResponseEntity<ResponseUtils> upload(@RequestParam("file") MultipartFile file){
        String accessKey = "ElUKm0uR8CUpx4CW-hUSdwXxsm2mg1QfyVMbvVtT";
        String secretKey = "JUKoFwn09XFdhNqNrQIWMSZA1vqWR-wGLqPubEAs";
        String bucket = "photoserver";
        if (file.isEmpty()){
            System.out.println(file.getOriginalFilename());
            System.out.println(file.getName());
            return ResponseEntity.ok(new ResponseUtils(false,"上传图片啊"));
        }

        Configuration configuration = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(configuration);

        Auth auth = Auth.create(accessKey,secretKey);
        String upToken = auth.uploadToken(bucket);
        String photoName = UUID.randomUUID().toString().replace("-","") + ".jpg";
        try{
            Response response = uploadManager.put(file.getBytes(), photoName,upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
            System.out.println(file);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            String retUrl = "purp508wq.bkt.clouddn.com/" + photoName;
            return ResponseEntity.ok(new ResponseUtils(true,"修改成功",retUrl));
        }catch (QiniuException ex){
            System.err.println(ex.response.toString());
            try{
                System.err.println(ex.response.bodyString());
            }catch (QiniuException e){
                return ResponseEntity.ok(new ResponseUtils(false,"上传图片啊"));
            }
        }catch (IOException e){
            return ResponseEntity.ok(new ResponseUtils(false,"上传图片啊"));
        }
        return null;
    }
}
