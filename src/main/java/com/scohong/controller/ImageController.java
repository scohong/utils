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
import org.apache.commons.codec.digest.DigestUtils;
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
        String vPicSql = null;
        String picSql = null;
        for (Program p : programList
        ) {
            String coverPic = p.getCoverPic();
            String thumbCoverPic = p.getVerticalCoverPic();
            if (coverPic != null && coverPic.length() > 0) {
                coverPic = coverPic.replaceAll("/images", "");
                File picFile = new File(ConfigManagment.realImagesPath + coverPic);
                if (picFile.isFile()) {
                    long size = picFile.length() / 1024;
                    String fileMd5 = DigestUtils.md5Hex(picFile.getName());
                    String newFileDirStr = ConfigManagment.ONLYTHUMBIMAGEPATH
                            .concat(p.getProgramName())
                            .concat("/");
                    File newFileDir = new File(newFileDirStr);
                    if (!newFileDir.isDirectory()) {
                        newFileDir.mkdirs();
                    }
                    String newFileName = newFileDirStr.concat(fileMd5).concat(".jpg");
                    System.out.println(newFileName);
                     picSql = "/images/" + p.getProgramName() + "/" + fileMd5 + ".jpg";
//                    list.add("/images/" + f.getProgramName() + "/" + fileMd5 + ".jpg");
//                    if (size > 300) {
//                        log.info(p.getProgramName() + "pic:" + size);
                    FileUtil.copyFile(picFile.getAbsolutePath(),newFileName);

//                    try {
//                            Thumbnails
//                                    .of(picFile)
//                                    .scale(1)
//                                    .outputQuality(1)
//                                    .toFile(newFileName);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                } else {
                    log.info("图片不匹配：" + picFile.getName());
                }
            }
            if (thumbCoverPic != null && thumbCoverPic.length() > 0) {
                thumbCoverPic = thumbCoverPic.replaceAll("/images", "");
                File vPicFile = new File(ConfigManagment.realImagesPath + thumbCoverPic);
                if (vPicFile.isFile()) {
                    long vSize = vPicFile.length() / 1024;
                    String vFileMd5 = DigestUtils.md5Hex(vPicFile.getName());
                    String newVFileDirStr = ConfigManagment.ONLYTHUMBIMAGEPATH
                            .concat(p.getProgramName())
                            .concat("/");
                    File newFileDir = new File(newVFileDirStr);
                    if (!newFileDir.isDirectory()) {
                        newFileDir.mkdirs();
                    }
                    String newVFileName = newVFileDirStr.concat(vFileMd5).concat(".jpg");
                    vPicSql = "/images/" + p.getProgramName() + "/" + vFileMd5 + ".jpg";
                    System.out.println(newVFileName);
                    FileUtil.copyFile(vPicFile.getAbsolutePath(),newVFileName);
//                    if (vSize > 200) {
//                        log.info(p.getProgramName() + "vpic:" + vSize);
//                        try {
//                            Thumbnails
//                                    .of(vPicFile)
//                                    .scale(1)
//                                    .outputQuality(1)
//                                    .toFile(newVFileName);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                } else {
                    log.info("图片不匹配："+vPicFile.getName());
                }
            }
            //更新数据库
            programDao.updateProgramPicMd5(p.getProgramId(),picSql,vPicSql);
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
        String shopSql = "";
        for (Shop s : shopList
        ) {
            String coverPic = s.getCoverPic();
            if (coverPic != null && coverPic.length() > 0) {
                coverPic = coverPic.replaceAll("/images", "");
                File picFile = new File(ConfigManagment.realImagesPath + coverPic);
                if (picFile.isFile()) {
                    long size = picFile.length() / 1024;
                    String fileMd5 = DigestUtils.md5Hex(picFile.getName());
                    String newFileDirStr = ConfigManagment.ONLYTHUMBIMAGEPATH
                            .concat(s.getShopName())
                            .concat("/");
                    File newFileDir = new File(newFileDirStr);
                    if (!newFileDir.isDirectory()) {
                        newFileDir.mkdirs();
                    }
                    String newFileName = newFileDirStr.concat(fileMd5).concat(".jpg");
                    System.out.println(newFileName);
                    shopSql = "/shops/" + s.getShopName() + "/" + fileMd5 + ".jpg";
                    FileUtil.copyFile(picFile.getAbsolutePath(),newFileName);
//                    if (size > 200) {
//                        overSizeCount++;
//                        System.out.println(s.getShopName() + "picBefore:" + size);
//                        try {
//                            Thumbnails
//                                    .of(picFile)
//                                    .size(1280, 720)
//                                    .outputQuality(0.8)
//                                    .toFile(picFile);
//                            System.out.println(s.getShopName() + "picAfter:" + picFile.length()/1024);
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                } else {
                    notExistCount++;
                    log.info("商家不存在" + picFile.getAbsolutePath());
                }
                //更新数据库
                shopDao.updateShopCoverPic(s.getShopName(),shopSql);
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
            //检测缩略图
//            String coverPic = f.getThumbImages();
            //检测原图,缩略后保存到新文件夹，同时更新数据库
            String coverPic = f.getImages();
            if (coverPic == null || coverPic.isEmpty()) {
                continue;
            }
            List<String> list = new ArrayList<>();
            if (coverPic != null && coverPic.length() > 0) {
                String[] pics = coverPic.split(";");
                for (String s : pics) {
                    String sTmp = s;
                    s = s.replaceAll("/images", "");
                    File picFile = new File(ConfigManagment.ONLYREALIMAGEPATH + s);
                    if (picFile.isFile()) {
                        long size = picFile.length() / 1024;
                        String fileMd5 = DigestUtils.md5Hex(picFile.getName());
                        String newFileDirStr = ConfigManagment.ONLYTHUMBIMAGEPATH
                                .concat(f.getProgramName())
                                .concat("/");
                        File newFileDir = new File(newFileDirStr);
                        if (!newFileDir.isDirectory()) {
                            newFileDir.mkdirs();
                        }
                        String newFileName = newFileDirStr.concat(fileMd5).concat(".jpg");
                        list.add("/images/" + f.getProgramName() + "/" + fileMd5 + ".jpg");
//                        if (size > 200) {
                            count++;
//                                try {
//                                    Thumbnails
//                                        .of(picFile)
//                                            .scale(1)
//                                        .outputFormat("jpg")
//                                        .outputQuality(1)
//                                        .toFile(newFileName);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                        }
                    } else {
                        notExistCount++;
                        System.out.println("notExist:" + s);
                    }
                }
//                if (sUpdateSql) {
                    //数据库更新字段
//                    String sqlStrA = StringUtils.join(list.toArray(), ";");
//                    log.info(sqlStrA);
//                    frameDao.updateImageById(String.valueOf(f.getId()), sqlStrA);
//                    frameDao.updateImageMd5ById(String.valueOf(f.getId()), sqlStrA);
//                    sUpdateSql = false;
//                }
            }
        }
        System.out.println("sum:" + count);
        System.out.println("noExist:" + notExistCount);
        System.out.println("thumb:" + thumb);
        return ResponseUtil.ok();
    }
}
