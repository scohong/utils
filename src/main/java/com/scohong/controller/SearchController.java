package com.scohong.controller;

import com.scohong.dao.CommonDao;
import com.scohong.dao.FrameDao;
import com.scohong.entity.common.Response;
import com.scohong.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: scohong
 * @Date: 2019/9/25 15:27
 * @Description:
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    CommonDao commonDao;

}
