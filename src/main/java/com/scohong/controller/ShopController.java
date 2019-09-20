package com.scohong.controller;

import com.scohong.dao.ShopDao;
import com.scohong.entity.common.Response;
import com.scohong.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: scohong
 * @Date: 2019/9/20 15:05
 * @Description:
 */
@RestController
@RequestMapping("/shop")
@Slf4j
public class ShopController {
    @Autowired
    ShopDao shopDao;

    @GetMapping("/all")
    public Response getAllShops() {

        return ResponseUtil.ok().setResult(shopDao.getAllShops());
    }

    @GetMapping("/del")
    public Response shopDel(@RequestParam int shopId) {
        boolean isSuccess = shopDao.delShop(shopId);
        if (isSuccess) {
            return ResponseUtil.ok().setResult(shopDao.getAllShops());
        } else {
            return ResponseUtil.error().setMsg("删除失败");
        }
    }
}
