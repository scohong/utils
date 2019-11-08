package com.scohong.controller;

import com.scohong.constant.ConfigManagment;
import com.scohong.dao.FrameDao;
import com.scohong.dao.ProgramDao;
import com.scohong.dao.ShopDao;
import com.scohong.entity.common.Response;
import com.scohong.entity.junengchi.FrameData;
import com.scohong.entity.junengchi.Program;
import com.scohong.entity.junengchi.Shop;
import com.scohong.utils.FileUtil;
import com.scohong.utils.ResponseUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.misc.FieldUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/10/24 18:14
 * @Description:
 */
@RestController
@RequestMapping("/data")
@Slf4j
@Api(tags = "图片数据迁移")
public class DataController {
    @Autowired
    ProgramDao programDao;
    @Autowired
    ShopDao shopDao;
    @Autowired
    FrameDao frameDao;

    /**
     * 节目封面图
     *
     * @return
     */
    @GetMapping("/programPic")
    public Response removeProgramPic() {
        List<Program> programList = programDao.getAllProgram();
        String newProgramDir = ConfigManagment.PROGRAMEPICDIR;
        for (Program p : programList
        ) {
            String coverPic = p.getCoverPic();
            String thumbCoverPic = p.getVerticalCoverPic();
            if (coverPic != null && coverPic.length() > 0) {
                coverPic = coverPic.replaceAll("/images", "");
                //原始图片地址，picFile
                File picFile = new File(ConfigManagment.realImagesPath + coverPic);
                //图片存在，则复制迁移，待删除
                if (picFile.isFile()) {
                    //拼接新的目录
                    File newFile = new File(newProgramDir + coverPic);
                    //创建父级目录
                    FileUtil.mkParentDir(newFile);
                    //复制文件
                    FileUtil.copyFile(picFile.getAbsolutePath(), newFile.getAbsolutePath());
                } else {
                    log.info("文件不存在：" + picFile.getName());
                }
            }
            if (thumbCoverPic != null && thumbCoverPic.length() > 0) {
                thumbCoverPic = thumbCoverPic.replaceAll("/images", "");
                //原始图片地址，picFile
                File thumbPicFile = new File(ConfigManagment.realImagesPath + thumbCoverPic);
                //图片存在，则复制迁移，待删除
                if (thumbPicFile.isFile()) {
                    //拼接新的目录
                    File newThumbFile = new File(newProgramDir + thumbCoverPic);
                    //创建父级目录
                    FileUtil.mkParentDir(newThumbFile);
                    //复制文件
                    FileUtil.copyFile(thumbPicFile.getAbsolutePath(), newThumbFile.getAbsolutePath());
                } else {
                    log.info("文件不存在：" + thumbPicFile.getName());
                }
            }
        }
        return ResponseUtil.ok();
    }

    /**
     * 商家门面图
     *
     * @return
     */
    @GetMapping("/shopPic")
    public Response removeShopPic() {
        List<Shop> shopList = shopDao.getAllShops();
        String newShopDir = ConfigManagment.SHOP_PIC_DIR;
        File dir = new File(newShopDir);
        //创建目录
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        for (Shop p : shopList
        ) {
            String coverPic = p.getCoverPic();
            if (coverPic != null && coverPic.length() > 0) {
                coverPic = coverPic.replaceAll("/images", "");
                //原始图片地址，picFile
                File picFile = new File(ConfigManagment.realImagesPath + coverPic);
                //图片存在，则复制迁移，待删除
                if (picFile.isFile()) {
                    //拼接新的目录
                    File newFile = new File(newShopDir + coverPic);
                    //创建父级目录
                    FileUtil.mkParentDir(newFile);
                    //复制文件
                    FileUtil.copyFile(picFile.getAbsolutePath(), newFile.getAbsolutePath());
                } else {
                    log.info("文件不存在：" + picFile.getName());
                }
            }
        }
        return ResponseUtil.ok();
    }

    /**
     * 取景图-原图
     *
     * @return
     */
    @GetMapping("/frame")
    public Response removeFramePic() {
        List<FrameData> frameDataList = frameDao.getAllFrameData();
        String newProgramDir = ConfigManagment.PROGRAMEPICDIR;
        int newCount = 0;
        for (FrameData f : frameDataList
        ) {
            //迁移原图
            String thumbImages = f.getImages();
            if (thumbImages == null || thumbImages.isEmpty()) {
                continue;
            }
            String[] imageStr = thumbImages.split(";");
            for (String image : imageStr
            ) {
                image = image.replaceAll("/images", "");
                //原始图片地址，picFile
                File picFile = new File(ConfigManagment.realImagesPath + image);
                //图片存在，则复制迁移，待删除
                if (picFile.isFile()) {
                    //拼接新的目录
                    File newFile = new File(newProgramDir + image);
                    //创建父级目录
                    FileUtil.mkParentDir(newFile);
                    if (!newFile.isFile()) {
                        log.info(newFile.getName());
                        newCount++;
                        FileUtil.copyFile(picFile.getAbsolutePath(), newFile.getAbsolutePath());
                    }
                    //复制文件
                } else {
                    log.info("文件不存在：" + image);
                }
            }
        }
        System.out.println(newCount);
        return ResponseUtil.ok();
    }

    /**
     * 取景图-原图
     *
     * @return
     */
    @GetMapping("/thumbFrame")
    public Response removeThumbFramePic() {
        List<FrameData> frameDataList = frameDao.getAllFrameData();
        String newProgramDir = ConfigManagment.PROGRAMETHUMBPICDIR;
        int newCount = 0;
        for (FrameData f : frameDataList
        ) {
            //迁移原图
            String thumbImages = f.getThumbImages();
            if (thumbImages == null || thumbImages.isEmpty()) {
                continue;
            }
            String[] imageStr = thumbImages.split(";");
            for (String image : imageStr
            ) {
                image = image.replaceAll("/images", "");
                //原始图片地址，picFile
                File picFile = new File(ConfigManagment.realImagesPath + image);
                //图片存在，则复制迁移，待删除
                if (picFile.isFile()) {
                    //拼接新的目录
                    File newFile = new File(newProgramDir + image);
                    //创建父级目录
                    FileUtil.mkParentDir(newFile);
                    if (!newFile.isFile()) {
                        log.info(newFile.getName());
                        newCount++;
                        FileUtil.copyFile(picFile.getAbsolutePath(), newFile.getAbsolutePath());
                    }
                    //复制文件
                } else {
                    log.info("文件不存在：" + image);
                }
            }
        }
        System.out.println(newCount);
        return ResponseUtil.ok();
    }

    /**
     * 视频
     *
     * @return
     */
    @GetMapping("/video")
    public Response removeVideo() {
        List<FrameData> frameDataList = frameDao.getAllFrameData();
        String newVideoDir = ConfigManagment.VIDEODIR;
        int newCount = 0;
        for (FrameData f : frameDataList
        ) {
            String video = f.getVideo();
            if (video == null || video.isEmpty()) {
                continue;
            }
            video = video.replaceAll("/video", "");
            //原始图片地址，picFile
            File videoFile = new File(ConfigManagment.VIDEOLOCALPATH + video);
            //图片存在，则复制迁移，待删除
            if (videoFile.isFile()) {
                //拼接新的目录
                File newFile = new File(newVideoDir + video);
                //创建父级目录
                FileUtil.mkParentDir(newFile);
                if (!newFile.isFile()) {
                    newCount++;
                    log.info(videoFile.getAbsolutePath());
                    FileUtil.copyFile(videoFile.getAbsolutePath(), newFile.getAbsolutePath());
                }
                //复制文件
            } else {
                log.info("文件不存在：" + video);
            }

        }
        System.out.println(newCount);
        return ResponseUtil.ok();
    }
}

