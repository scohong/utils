package com.scohong.controller;

import com.scohong.constant.ImageManagment;
import com.scohong.dao.ProgramDao;
import com.scohong.entity.common.Response;
import com.scohong.entity.junengchi.Program;
import com.scohong.utils.FileUtil;
import com.scohong.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/9/18 20:32
 * @Description:
 */
@RestController
@RequestMapping("/program")
@Slf4j
public class ProgarmController {

    @Autowired
    ProgramDao programDao;

    @GetMapping("/all")
    public Response getPrograms() {
        return ResponseUtil.ok().setResult(programDao.getAllProgram());
    }

    @GetMapping("/test")
    public Response getProgramsTest() {
        return ResponseUtil.ok().setResult(programDao.getAllProgram());
    }

    @GetMapping("/del")
    public Response delProgramsTest(@RequestParam int programId) {
        log.info("删除id:" + programId);
        programDao.delProgram(programId);
        return ResponseUtil.ok().setResult(programDao.getAllProgram());
    }

    @GetMapping("/search")
    public Response programSearch(@RequestParam String context) {
        if ("".equals(context)) {
            return ResponseUtil.ok().setResult(programDao.getAllProgram());
        } else {
            return ResponseUtil.ok().setResult(programDao.searchProgramByName(context));
        }
    }

    @PostMapping("/update")
    public Response updateProgram(@RequestBody Program program) {
        int isSuccess = programDao.updateProgram(program);
        log.info(isSuccess+"");
        return ResponseUtil.ok().setResult(programDao.getAllProgram());
    }

    /**
     *  添加节目
     *
     * @return
     */
    @PostMapping("/add")
    public Response addProgramTest(@RequestParam("coverPic") MultipartFile[] files,
                                   @RequestParam("verticalCoverPic") MultipartFile[] verticalCoverPic,
                                   @RequestParam(value = "name") String name,
                                   @RequestParam(value = "alias",defaultValue = "") String alias,
                                   @RequestParam(value = "types",defaultValue = "") String types,
                                   @RequestParam(value = "actor",defaultValue = "") String actor,
                                   @RequestParam(value = "introduction",defaultValue = "") String introduction) {
        // 测试MultipartFile接口的各个方法
        Program program = new Program(name, alias, types, introduction, actor);
        List<Program> programs = programDao.getAllProgram();
        for (Program p:programs
        ) {
            if (p.getProgramName().equals(name)) {
                return ResponseUtil.error().setMsg("该节目已存在，请重新输入");
            }
        }
        //在文件夹中添加封面图
        String savePath = ImageManagment.realImagesPath.concat(name);
        //创建对应文件夹
        File file = new File(savePath);
        if (!file.isDirectory()) {
            file.mkdir();
        }
        //保存图片
        String coverPicPath = ImageManagment.backendUpload.concat(name).concat("/").concat(files[0].getOriginalFilename());
        String verticalCoverPicPath = ImageManagment.backendUpload.concat(name).concat("/").concat(verticalCoverPic[0].getOriginalFilename());
        try {
            FileUtil.saveFile(files[0], savePath);
            FileUtil.saveFile(verticalCoverPic[0], savePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //更新数据库的图片数据
        program.setCoverPic(coverPicPath);
        program.setVerticalCoverPic(verticalCoverPicPath);
        boolean isSuccess = programDao.addProgramDetail(program);
        if (isSuccess) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.error().setMsg("添加失败，请重新尝试！");
        }
    }

}
