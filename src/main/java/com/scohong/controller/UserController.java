package com.scohong.controller;

import com.scohong.dao.UserDao;
import com.scohong.entity.common.Response;
import com.scohong.entity.junengchi.Pagination;
import com.scohong.entity.junengchi.UserSubmission;
import com.scohong.utils.Comparaor.StatusSorterComparator;
import com.scohong.utils.ResponseUtil;
import com.scohong.utils.filter.ProgramTypeFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/10/11 20:57
 * @Description:
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserDao userDao;


    /**
     *  获取用户上传数据,只访问数据库一次，排序和筛选通过lambda进行
     *
     * @return 用户上传数据
     */
    @GetMapping("/submission")
    public Response userUploadData(@RequestParam(required = false,defaultValue = "") String sorter,
                              @RequestParam(required = false,defaultValue = "") String status,
                              @RequestParam(required = false,defaultValue = "") String name,
                              @RequestParam(required = false,defaultValue = "") String types,
                              @RequestParam(required = false,defaultValue = "10") int pageSize,
                              @RequestParam(required = false,defaultValue = "1") int currentPage) {
        Pagination pagination = new Pagination(pageSize,currentPage);
        //分页情况
        List<UserSubmission> userSubmissions = userDao.getUserSubmission(name,status);
        log.info("搜索内容:"  + name);
        // 是否校验排序
        if (!"".equals(sorter)) {
            String [] params = sorter.split("_");
            Collections.sort(userSubmissions, new StatusSorterComparator(params[0],params[1]));
        }
        //类型判断
        if (!"".equals(types) ) {
            userSubmissions = ProgramTypeFilter.typeFilter(types, userSubmissions);
        }
        return ResponseUtil.ok().setResult(userSubmissions).setPagination(pagination);
    }

    /**
     * 审核用户上传数据
     *
     * @return 标准response
     */
    @PostMapping("/confirm")
    public Response confirm(@RequestParam("isCheck") int isCheck,
                            @RequestParam("id") int id) {
        Boolean isSuccess = userDao.userSubmissionConfirmById(id, isCheck);
        if (isSuccess) {
            return ResponseUtil.ok().setResult(userDao.getUserSubmission("",""));
        } else {
            return ResponseUtil.error();
        }
    }
}
