package com.scohong.controller;

import com.scohong.constant.ConfigManagment;
import com.scohong.dao.ShopDao;
import com.scohong.entity.common.Response;
import com.scohong.entity.junengchi.Shop;
import com.scohong.utils.FileUtil;
import com.scohong.utils.ResponseUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/9/20 15:05
 * @Description:
 */
@RestController
@RequestMapping("/shop")
@Slf4j
@Api(tags = "商家branch")
public class ShopController {
    @Autowired
    ShopDao shopDao;

    /**
     * 获取商家所有数据
     * @return
     */
    @GetMapping("/all")
    public Response getAllShops() {
        return ResponseUtil.ok().setResult(shopDao.getAllShops());
    }

    /**
     * 删除商家
     * @param shopId
     * @return
     */
    @GetMapping("/del")
    public Response shopDel(@RequestParam int shopId) {
        boolean isSuccess = shopDao.delShop(shopId);
        if (isSuccess) {
            return ResponseUtil.ok().setResult(shopDao.getAllShops());
        } else {
            return ResponseUtil.error().setMsg("删除失败");
        }
    }

    /**
     * 商家搜索
     * @param context
     * @return
     */
    @GetMapping("/search")
    public Response programSearch(@RequestParam String context) {
        if ("".equals(context)) {
            return ResponseUtil.ok().setResult(shopDao.getAllShops());
        } else {
            return ResponseUtil.ok().setResult(shopDao.searchShopByName(context));
        }
    }


    /**
     * 更新商家数据
     * @param shop
     * @return
     */
    @PostMapping("/update")
    public Response updateShop(@RequestBody Shop shop) {
        int isSuccess = shopDao.updateShop(shop);
        if (isSuccess == 1) {
            return ResponseUtil.ok().setResult(shopDao.getAllShops());
        } else {
            return ResponseUtil.error().setMsg("删除失败");
        }
    }

    /**
     * 更新商家宣传图
     * @return
     */
    @PostMapping("/updateCoverPic")
    public Response updateShopCoverPic(@RequestParam("image") MultipartFile[] coverPic,
                                       @RequestParam("shopName") String shopName) {
        //在文件夹中添加封面图
        String savePath = ConfigManagment.realShopPath.concat(shopName);
        //创建对应文件夹
        File file = new File(savePath);
        if (!file.isDirectory()) {
            file.mkdir();
        }
        //保存图片
        String coverPicPath = ConfigManagment.relativeShopPath.concat(shopName).concat("/").concat(coverPic[0].getOriginalFilename());
        try {
            FileUtil.saveFile(coverPic[0], savePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //更新数据库的图片数据
        shopDao.updateShopCoverPic(shopName, coverPicPath);
        return ResponseUtil.ok();
    }

    @PostMapping("/add")
    public Response addShop(@RequestParam("coverPic") MultipartFile[] coverPic,
                                   @RequestParam(value = "shopName") String shopName,
                                   @RequestParam(value = "city",defaultValue = "") String city,
                                   @RequestParam(value = "address",defaultValue = "") String address,
                                   @RequestParam(value = "introduction",defaultValue = "") String introduction) {
        Shop shop = new Shop(shopName,city,address,introduction);
        // 测试MultipartFile接口的各个方法
        List<Shop> shops = shopDao.getAllShops();
        for (Shop s:shops
        ) {
            if (s.getShopName().equals(shopName)) {
                return ResponseUtil.error().setMsg("该商家已存在，请重新输入");
            }
        }
        //在文件夹中添加封面图
        String savePath = ConfigManagment.realShopPath.concat(shopName);
        //创建对应文件夹
        File file = new File(savePath);
        if (!file.isDirectory()) {
            file.mkdir();
        }
        //保存图片
        String coverPicPath = ConfigManagment.relativeShopPath.concat(shopName).concat("/").concat(coverPic[0].getOriginalFilename());
        try {
            FileUtil.saveFile(coverPic[0], savePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //更新数据库的图片数据
        shop.setCoverPic(coverPicPath);
        log.info(shop.getShopName());
        boolean isSuccess = shopDao.addShop(shop);
        if (isSuccess) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.error().setMsg("添加失败，请重新尝试！");
        }
    }
}
