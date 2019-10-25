package com.scohong.controller;

import com.scohong.constant.ConfigManagment;
import com.scohong.dao.FrameDao;
import com.scohong.dao.ProgramDao;
import com.scohong.dao.ShopDao;
import com.scohong.entity.common.Response;
import com.scohong.entity.junengchi.FrameData;
import com.scohong.entity.junengchi.Program;
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

    @GetMapping("/frame")
    public Response removeFramePic() {
        List<FrameData> frameDataList = frameDao.getAllFrameData();
        String newProgramDir = ConfigManagment.PROGRAMEPICDIR;
        for (FrameData f : frameDataList
        ) {
            String thumbImages = f.getThumbImages();
            String [] imageStr = thumbImages.split(";");
            for (String image:imageStr
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
                    //复制文件
//                    FileUtil.copyFile(picFile.getAbsolutePath(), newFile.getAbsolutePath());
                } else {
                    log.info("文件不存在：" + image);
                }
            }
        }
        return ResponseUtil.ok();
    }
}

