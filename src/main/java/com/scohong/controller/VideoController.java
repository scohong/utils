package com.scohong.controller;

import com.scohong.entity.video.ProgramSelector;
import com.scohong.entity.video.Video;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/9/6 15:38
 * @Description:
 */
@RestController
@Slf4j
public class VideoController {


    //获取节目目录
    @GetMapping("/test")
    public List<ProgramSelector> test() {
        List<ProgramSelector> programSelectors = new ArrayList<>();
        File[] dirs = new File("E:\\下载\\资源\\").listFiles();
        for (File file: dirs
             ) {
            List<ProgramSelector> childProgramSelector = new ArrayList<>();
            ProgramSelector programSelector = new ProgramSelector(file.getName(), file.getName());
            File[] childFiles = new File("E:\\下载\\资源\\" + file.getName()).listFiles();
            for (File cFile: childFiles
                 ) {
                childProgramSelector.add(new ProgramSelector(cFile.getName(), cFile.getName()));
            }
            programSelector.setChildren(childProgramSelector);
            programSelectors.add(programSelector);

        }
        return programSelectors;
    }

    //获取节目目录
    @PostMapping("/testaaa")
    public List<ProgramSelector> testa(@RequestBody Video prefix) {
        List<ProgramSelector> programSelectors = new ArrayList<>();
        log.info(prefix.getWebsite());
        return programSelectors;
    }

    //获取节目目录
    @GetMapping("/programs")
    public List<ProgramSelector> getProgramFile() {
        List<ProgramSelector> list = new ArrayList<>();
        list.add(new ProgramSelector("a","b"));
        list.add(new ProgramSelector("d","d"));
        list.add(new ProgramSelector("s","s"));
        return list;
    }
    //获取剧集列表
    //获取处理后的视频
    //播放处理后的视频


    /**
     *  手动剪辑视频
     * @param program
     * @param episode
     * @param startTime
     * @param endTime
     * @return
     */
    @PostMapping("/cutVideo")
    public String cutVideo(@RequestParam String program,
                           @RequestParam String episode,
                           @RequestParam String startTime,
                           @RequestParam String endTime) {
        String path = "E:\\test\\" + program + File.separator + episode;
        // TODO Auto-generated method stub
        try {
//            Runtime.getRuntime().exec("source activate ");// 执行py文件
//            Runtime.getRuntime().exec("python F:\\workspace\\python\\test.py");// 执行py文件
            String[] args = new String[] { "D:\\Anaconda\\python.exe", "F:/workspace/python/test.py", path,startTime,endTime };
            Process proc = Runtime.getRuntime().exec(args);
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            log.info("res:"+line);
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "处理完成";
    }
}
