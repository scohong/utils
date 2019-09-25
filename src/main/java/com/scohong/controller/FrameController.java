package com.scohong.controller;

import com.scohong.dao.FrameDao;
import com.scohong.entity.common.Response;
import com.scohong.utils.ResponseUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: scohong
 * @Date: 2019/9/23 11:14
 * @Description:
 */
@RestController
@RequestMapping("/frame")
public class FrameController {
    @Autowired
    FrameDao frameDao;

    /**
     * 获取取景数据
     * @return
     */
    @GetMapping("/all")
    public Response getAllData() {
        return ResponseUtil.ok().setResult(frameDao.getAllFrameData());
    }

    /**
     * 删除取景数据
     * @return
     */
    @GetMapping("/del")
    public Response delAllData(@RequestParam int id) {
        boolean isSuccess = frameDao.delFrameData(id);
        if (isSuccess) {
            return ResponseUtil.ok().setMsg("删除成功").setResult(frameDao.getAllFrameData());
        } else {
            return ResponseUtil.error().setMsg("删除失败").setResult(frameDao.getAllFrameData());
        }
    }

    /**
     * 搜索取景数据
     * @param programName
     * @param shopName
     * @return
     */
    @GetMapping("/search")
    public Response backendSearch(@RequestParam(value = "programSearch",required = false,defaultValue = "") String programName,
                                  @RequestParam(value = "shopSearch",required = false,defaultValue = "") String shopName) {

        return ResponseUtil.ok().setResult(frameDao.searchFrame(programName,shopName));
    }
}
