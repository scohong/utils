package com.scohong.controller;

import com.scohong.constant.ConfigManagment;
import com.scohong.dao.FrameDao;
import com.scohong.dao.ProgramDao;
import com.scohong.dao.ShopDao;
import com.scohong.entity.common.Response;
import com.scohong.entity.junengchi.FrameData;
import com.scohong.entity.junengchi.Program;
import com.scohong.entity.junengchi.Shop;
import com.scohong.utils.ResponseUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/10/23 17:26
 * @Description:
 */
@RestController
@Slf4j
@Api(tags = "图片大小检测")
public class ImageController {

    @Autowired
    ProgramDao programDao;
    @Autowired
    ShopDao shopDao;
    @Autowired
    FrameDao frameDao;

    /**
     * 筛选节目封面图
     *
     * @return
     */
    @GetMapping("/getProgramPicSize")
    public Response getProgramCoverPicInfo() {
        List<Program> programList = programDao.getAllProgram();
        for (Program p : programList
        ) {
            String coverPic = p.getCoverPic();
            String thumbCoverPic = p.getVerticalCoverPic();
            if (coverPic != null && coverPic.length() > 0) {
                coverPic = coverPic.replaceAll("/images", "");
                File picFile = new File(ConfigManagment.realImagesPath + coverPic);
                if (picFile.isFile()) {
                    long size = picFile.length() / 1024;
                    if (size > 300) {
                        log.info(p.getProgramName() + "pic:" + size);
                        try {
                            Thumbnails
                                    .of(picFile)
                                    .size(1280, 720)
                                    .outputQuality(0.8)
                                    .toFile(picFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    log.info("图片不匹配：" + picFile.getName());
                }
            }
            if (thumbCoverPic != null && thumbCoverPic.length() > 0) {
                thumbCoverPic = thumbCoverPic.replaceAll("/images", "");
                File vPicFile = new File(ConfigManagment.realImagesPath + thumbCoverPic);
                if (vPicFile.isFile()) {
                    long vSize = vPicFile.length() / 1024;
                    if (vSize > 200) {
                        log.info(p.getProgramName() + "vpic:" + vSize);
                        try {
                            Thumbnails
                                    .of(vPicFile)
                                    .size(1280, 720)
                                    .outputQuality(0.8)
                                    .toFile(vPicFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    log.info("图片不匹配："+vPicFile.getName());
                }
            }
        }
        return ResponseUtil.ok();
    }

    /**
     * 筛选商家宣传图
     *
     * @return
     */
    @GetMapping("/getShopPicSize")
    public Response getShopCoverPicInfo() {
        List<Shop> shopList = shopDao.getAllShops();
        int overSizeCount = 0;
        int notExistCount = 0;
        for (Shop s : shopList
        ) {
            String coverPic = s.getCoverPic();
            if (coverPic != null && coverPic.length() > 0) {
                coverPic = coverPic.replaceAll("/images", "");
                File picFile = new File(ConfigManagment.realImagesPath + coverPic);
                if (picFile.isFile()) {
                    long size = picFile.length() / 1024;
                    if (size > 200) {
                        overSizeCount++;
                        System.out.println(s.getShopName() + "picBefore:" + size);
                        try {
                            Thumbnails
                                    .of(picFile)
                                    .size(1280, 720)
                                    .outputQuality(0.8)
                                    .toFile(picFile);
                            System.out.println(s.getShopName() + "picAfter:" + picFile.length()/1024);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    notExistCount++;
                    log.info("商家不存在" + picFile.getAbsolutePath());
                }
            }
        }
        System.out.println("over:" + overSizeCount);
        System.out.println("noExist:" + notExistCount);
        return ResponseUtil.ok();
    }

    /**
     * 取景图大小判断
     */
    @GetMapping("/getFramePicSize")
    public Response getFramePicInfo() {
        List<FrameData> frameList = frameDao.getAllFrameData();
        int count = 0;
        int notExistCount = 0;
        int thumb = 0;
        boolean sUpdateSql = false;
        for (FrameData f : frameList
        ) {
            String coverPic = f.getThumbImages();
            List<String> list = new ArrayList<>();
            if (coverPic != null && coverPic.length() > 0) {
                String[] pics = coverPic.split(";");
                for (String s : pics) {
                    String sTmp = s;
                    s = s.replaceAll("/images", "");
                    File picFile = new File(ConfigManagment.realImagesPath + s);
                    if (picFile.isFile()) {
                        long size = picFile.length() / 1024;
                        if (size > 200) {
                            count++;
                            System.out.println(f.getId() + "pic:" + size);
                            //是否被压缩过
                            boolean isThumb = picFile.getName().indexOf("t_") != -1 ? true : false;
                            if (isThumb) {
                                thumb++;
//                                try {
//                                    Thumbnails
//                                        .of(picFile)
//                                        .size(1280,720)
//                                        .outputFormat("jpg")
//                                        .outputQuality(0.75)
//                                        .toFile(picFile);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
                            } else {
                                //压缩后更新文件名到数据库，并对文件重命名
                                sUpdateSql = true;
                                //sql
                                sTmp = sTmp.replaceAll(picFile.getName(), "t_" + picFile.getName());
                                list.add(sTmp);
                                s = s.replaceAll(picFile.getName(), "t_" + picFile.getName());
                                String newThumbName = picFile.getParent().concat(s);
//                                try {
//                                    Thumbnails
//                                        .of(picFile)
//                                        .size(1280,720)
//                                        .outputFormat("jpg")
//                                        .outputQuality(0.75)
//                                        .toFile(newThumbName);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
                            }
                        }
                    } else {
                        notExistCount++;
                        System.out.println("notExist:" + s);
                    }
                }
                if (sUpdateSql) {
                    //数据库更新字段
                    String sqlStrA = StringUtils.join(list.toArray(), ";");
                    log.info(sqlStrA);
                    frameDao.updateImageById(String.valueOf(f.getId()), sqlStrA);
                    sUpdateSql = false;
                }

            }
        }
        System.out.println("sum:" + count);
        System.out.println("noExist:" + notExistCount);
        System.out.println("thumb:" + thumb);
        return ResponseUtil.ok();
    }

}
