package com.scohong.controller;

import com.google.common.base.Joiner;
import com.scohong.constant.ImageManagment;
import com.scohong.dao.FrameDao;
import com.scohong.dao.ProgramDao;
import com.scohong.dao.ShopDao;
import com.scohong.entity.common.Response;
import com.scohong.entity.junengchi.BackendProgramAndShop;
import com.scohong.entity.junengchi.FrameData;
import com.scohong.entity.junengchi.Program;
import com.scohong.entity.junengchi.Shop;
import com.scohong.utils.CommonUtils;
import com.scohong.utils.FileUtil;
import com.scohong.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/9/23 11:14
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping("/frame")
public class FrameController {
    @Autowired
    FrameDao frameDao;
    @Autowired
    ShopDao shopDao;
    @Autowired
    ProgramDao programDao;

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
     * 更新取景数据
     * @return
     */
    @PostMapping("/update")
    public Response delAllData(@RequestBody FrameData frameData) {
        boolean isSuccess = frameDao.updateFrameData(frameData);
        if (isSuccess) {
            return ResponseUtil.ok().setMsg("更新成功").setResult(frameDao.getAllFrameData());
        } else {
            return ResponseUtil.error().setMsg("更新失败").setResult(frameDao.getAllFrameData());
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

    /**
     *  添加取景数据
     * react传参需优化，program的参数名需改为id和name，目前为key和value;
     * @return
     */
    @PostMapping("/add")
    public Response addFramedata(@RequestParam(value = "image1",required = false) MultipartFile[] image1,
                                 @RequestParam(value = "image2",required = false) MultipartFile[] image2,
                                 @RequestParam(value = "image3",required = false) MultipartFile[] image3,
                                 @RequestParam(value = "appearTime1", defaultValue = "",required = false)String appearTime1,
                                 @RequestParam(value = "appearTime2",required = false,defaultValue = "")String appearTime2,
                                 @RequestParam(value = "appearTime3",required = false,defaultValue = "")String appearTime3,
                                 @RequestParam(value = "startTime",required = false,defaultValue = "")String startTime,
                                 @RequestParam(value = "endTime",required = false,defaultValue = "")String endTime,
                                 @RequestParam("programName") String programName,
                                 @RequestParam("shopName") String shopName,
                                 @RequestParam(value = "introduction",required = false) String introduction,
                                 @RequestParam(value = "foods",required = false,defaultValue = "") String foods,
                                 @RequestParam(value = "episode",required = false,defaultValue = "0") String episode) {
        if (startTime.length() > 1) {
            startTime = CommonUtils.formatTime(startTime);
        }
        if (endTime.length() > 1) {
            endTime = CommonUtils.formatTime(endTime);
        }
        //拼接取景时间
        String appearTime = "";
        if (appearTime1.length() > 0) {
            appearTime = CommonUtils.formatTime(appearTime1);
        }
        if (appearTime2.length() > 0) {
            appearTime = appearTime.concat(",").concat(CommonUtils.formatTime(appearTime2));
            if (appearTime3.length() > 0) {
                appearTime = appearTime.concat(",").concat(CommonUtils.formatTime(appearTime3));
            }
        }
        BackendProgramAndShop framedata = new BackendProgramAndShop(programName,shopName,introduction,foods,episode,appearTime,startTime,endTime);
        //获取商家id和节目id
        Shop shop = shopDao.getShopByName(shopName);
        Program program = programDao.getProgramByName(programName);
        framedata.setShopId(shop.getShopId());
        framedata.setProgramId(program.getProgramId());
        //保存图片和更新图片地址
        //1.找到对应的文件夹
        String savePath = ImageManagment.realImagesPath.concat(framedata.getProgramName());
        //创建对应文件夹
        File file = new File(savePath);
        if (!file.isDirectory()) {
            log.info("创建新目录：" + file.getName());
            file.mkdir();
        }
        //取景图片数据
        List<String> imagesPathList = new ArrayList<>();
        List<String> thumbImagesPathList = new ArrayList<>();
        //2.保存图片
        //文件路径
        String saveLocalPath = ImageManagment.backendUpload.concat(framedata.getProgramName()).concat(File.separator);
        try {
            if (image1.length > 0) {
                FileUtil.saveFile(image1[0], savePath);
                imagesPathList.add(saveLocalPath.concat(image1[0].getOriginalFilename()));
                thumbImagesPathList.add(saveLocalPath.concat("t_").concat(image1[0].getOriginalFilename()));
            }
            if (image2.length > 0) {
                FileUtil.saveFile(image2[0], savePath);
                imagesPathList.add(saveLocalPath.concat(image2[0].getOriginalFilename()));
                thumbImagesPathList.add(saveLocalPath.concat("t_").concat(image2[0].getOriginalFilename()));
            }
            if (image3.length > 0) {
                FileUtil.saveFile(image3[0], savePath);
                imagesPathList.add(saveLocalPath.concat(image3[0].getOriginalFilename()));
                thumbImagesPathList.add(saveLocalPath.concat("t_").concat(image3[0].getOriginalFilename()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imageSql = Joiner.on(";").join(imagesPathList);
        String thumbImageSql = Joiner.on(";").join(imagesPathList);
        //3.更新图片路径
        framedata.setImages(imageSql);
        framedata.setThumbnails(thumbImageSql);
        //4.生成缩略图 todo
//        ImageUtil.getThumbFile(new File(savePath).listFiles());
        //数据持久化到数据库
        boolean isSuccess = frameDao.addFramedata(framedata);
        if (isSuccess) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.error().setMsg("添加失败，请重新尝试！");
        }
    }

}
