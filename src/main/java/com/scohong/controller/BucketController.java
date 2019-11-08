package com.scohong.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scohong.entity.bucket.BucketResponse;
import com.scohong.entity.common.Response;
import com.scohong.utils.BucketUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.jws.Oneway;
import java.io.IOException;

/**
 * @Author: scohong
 * @Date: 2019/11/4 21:27
 * @Description:
 */
public class BucketController {



    public void uploadFile() {

    }

}
