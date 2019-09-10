package com.scohong.controller;

import com.scohong.entity.pianchangDO.Response;
import com.scohong.entity.video.Program;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public List<Program> test() {
        List<Program> programs = new ArrayList<>();
        File[] dirs = new File("E:\\下载\\资源\\").listFiles();
        for (File file: dirs
             ) {
            List<Program> childProgram = new ArrayList<>();
            Program program = new Program(file.getName(), file.getName());
            File[] childFiles = new File("E:\\下载\\资源\\" + file.getName()).listFiles();
            for (File cFile: childFiles
                 ) {
                childProgram.add(new Program(cFile.getName(), cFile.getName()));
            }
            program.setChildren(childProgram);
            programs.add(program);

        }
        return programs;
    }

    //获取节目目录
    @GetMapping("/programs")
    public List<Program> getProgramFile() {
        List<Program> list = new ArrayList<>();

        list.add(new Program("a","b"));
        list.add(new Program("d","d"));
        list.add(new Program("s","s"));
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
