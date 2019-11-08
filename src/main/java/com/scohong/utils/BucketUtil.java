package com.scohong.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scohong.constant.ConfigManagment;
import com.scohong.entity.bucket.BucketResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @Author: scohong
 * @Date: 2019/11/4 21:40
 * @Description:
 */
public class BucketUtil {

    public static void main(String[] args) {
        uploadFile();
    }

    public static String authAlgo(String fileName) {
        String method = "POST";
        String bucket = ConfigManagment.BUCKET;
        String filename = fileName;
        String accessKey = ConfigManagment.ACCESS_KEY;
        String secretKey = ConfigManagment.ACCESS_SECRET;
        long expires = System.currentTimeMillis();
        String data = method + bucket + fileName + secretKey + expires;
        String encryption = DigestUtils.md5Hex(data);
        String auth = accessKey + ":" + encryption + ":" + expires;
        return auth;
    }

    public static void uploadFile() {
        //随机生成四位数验证码
        ObjectMapper objectMapper = new ObjectMapper();
        //kugou接口
        RestTemplate restTemplate = new RestTemplate();
        String auth = authAlgo("");
        String url = "http://bssulbig.kugou.com/upload";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization",auth);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("bucket", "schoolaudio");
        map.add("extendname", "mp4");
//        map.add("use_ext", 1);
        map.add("filename", "");
        map.add("authorization", auth);
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        //文件流
        //"F:\\数据分部\\压缩\\video-0.5\\舌尖上的中国3\\fce53161.mp4")
//        HttpEntity<FileSystemResource> httpEntity = new HttpEntity<>(new FileSystemResource("F:\\数据分部\\压缩\\video-0.5\\舌尖上的中国3\\fce53161.mp4"), headers);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url,httpEntity , String.class);
//        ResponseEntity<String> response = restTemplate.postForEntity( url,httpEntity, request , String.class );
        System.out.println(response.getBody());
//        BucketResponse bucketResponse = null;
//        try {
//            bucketResponse = objectMapper.readValue(response.getBody(), BucketResponse.class);
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//        }
//        if (bucketResponse.getStatus() == 1) {
//            System.out.println("上传成功");
//        } else {
//            System.out.println("上传失败");
//        }
    }
}
