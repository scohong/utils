package com.scohong.controller;

import com.scohong.dao.ProgramDao;
import com.scohong.entity.common.Response;
import com.scohong.entity.junengchi.Program;
import com.scohong.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/update")
    public Response updateProgram(@RequestBody Program program) {
        int isSuccess = programDao.updateProgram(program);
        log.info(isSuccess+"");
        return ResponseUtil.ok().setResult(programDao.getAllProgram());
    }

}
