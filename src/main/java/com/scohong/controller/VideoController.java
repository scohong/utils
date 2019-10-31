package com.scohong.controller;

import com.scohong.constant.ConfigManagment;
import com.scohong.dao.FrameDao;
import com.scohong.entity.common.Response;
import com.scohong.entity.video.ProgramSelector;
import com.scohong.entity.video.Video;
import com.scohong.utils.CommonUtils;
import com.scohong.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: scohong
 * @Date: 2019/9/6 15:38
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping("/localDir")
public class VideoController {
    @Autowired
    FrameDao frameDao;
    //获取节目目录
    @GetMapping("/record")
    public List<ProgramSelector> localRecordList() {
        List<ProgramSelector> programSelectors = new ArrayList<>();
        File[] dirs = new File(ConfigManagment.RECORDLOCALDIR).listFiles();
        for (File file: dirs
             ) {
            List<ProgramSelector> childProgramSelector = new ArrayList<>();
            ProgramSelector programSelector = new ProgramSelector(file.getName(), file.getName());
            File[] childFiles = new File(ConfigManagment.RECORDLOCALDIR + file.getName()).listFiles();
            for (File cFile: childFiles
                 ) {
                childProgramSelector.add(new ProgramSelector(cFile.getName(), cFile.getName()));
            }
            programSelector.setChildren(childProgramSelector);
            programSelectors.add(programSelector);
        }
        return programSelectors;
    }

    //获取剧集列表
    //获取处理后的视频
    //播放处理后的视频


    /**
     *  手动剪辑视频
     * @return
     */
    @PostMapping("/cutVideo")
    @Transactional
    public Response cutVideo(@RequestBody Video video) {
        log.info(video.toString());
        String localVideoDir = ConfigManagment.VIDEOCUTDIR;
        String localGifDir = ConfigManagment.GIFCUTDIR;
        String videoOutPath = localVideoDir+video.getProgram()[0]+File.separator;
        String gifOutPath = localGifDir+video.getProgram()[0]+File.separator;
        //没有目录就创建
        File videoDir = new File(videoOutPath);
        File gifDir = new File(gifOutPath);
        if (!videoDir.isDirectory()) {
            //多级路径没有，用mkdirs，只有一级路径用mkdir
            videoDir.mkdirs();
        }
        if (!gifDir.isDirectory()) {
            gifDir.mkdirs();
        }

        String program = video.getProgram()[0];
        String file = video.getProgram()[1];
        String filePath = ConfigManagment.RECORDLOCALDIR + program+File.separator+file;
        //根据
        String videoName = DigestUtils.md5Hex(video.getVideoStartTime()+ program+file).substring(0,8);
        String videoFile = videoOutPath + videoName + ".mp4";
        String gifName = DigestUtils.md5Hex(video.getGifStartTime()+ program + file).substring(0,8);
        String gifFile = gifOutPath + gifName + ".gif";
        //转换时间
        int videoStartTime = CommonUtils.getSecond(video.getVideoStartTime());
        int videoEndTime = CommonUtils.getSecond(video.getVideoEndTime());
        if (video.getGifStartTime() != null && video.getGifEndTime() != null) {
            int gifStartTime = CommonUtils.getSecond(video.getGifStartTime());
            int gifEndTime = CommonUtils.getSecond(video.getGifEndTime());
            if (gifEndTime <= gifStartTime) {
                return ResponseUtil.error().setMsg("结束时间大于起始时间，请重新选择！");
            }
            /** 剪gif*/
            String[] cutGif = new String[] { "D:\\Anaconda\\python.exe", "F:/workspace/python/cutGif.py",
                    filePath,String.valueOf(gifStartTime), String.valueOf(gifEndTime - gifStartTime), gifFile};
            Process procGif = null;
            try {
                procGif = Runtime.getRuntime().exec(cutGif);
                procGif.waitFor();
                String gifSqlPath = ConfigManagment.GIFSQLDIR + video.getProgram()[0] + "/" + gifName + ".gif";
                int i = frameDao.updateGifById(video.getFrameId(), gifSqlPath,gifStartTime,gifEndTime);
                if (i != 1) {
                    return ResponseUtil.error().setMsg("数据更新失败，请联系小洪");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // TODO Auto-generated method stub
        if (video.getVideoStartTime() != null && video.getVideoEndTime() != null) {
            if (videoStartTime > videoEndTime) {
                return ResponseUtil.error().setMsg("结束时间大于起始时间，请重新选择！");
            }
            try {
                /**py脚本
                 * 'ffmpeg -i "{video_path}" -ss {start_time} -c copy -to {end_time} -codec:a aac "{out_path}"'
                 * */
                /** 剪视频*/
                String[] cutVideo = new String[] { "D:\\Anaconda\\python.exe", "F:/workspace/python/cutVideo.py",
                        filePath, String.valueOf(videoStartTime), String.valueOf(videoEndTime - videoStartTime), videoFile};
                Process proc = Runtime.getRuntime().exec(cutVideo);
                proc.waitFor();
                //用输入输出流来截取结果
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(videoFile);
            log.info(gifFile);

            /**更新sql的gif和video数据*/
            String videoSqlPath = ConfigManagment.VIDEOSQLDIR + video.getProgram()[0] +"/"+ videoName + ".mp4";
            int i = frameDao.updateVideoById(video.getFrameId(), videoSqlPath,videoStartTime,videoEndTime);
            if (i == 1) {
                return ResponseUtil.ok().setMsg("剪辑成功");
            } else {
                return ResponseUtil.error().setMsg("数据更新失败，请联系小洪");
            }
        }
        return ResponseUtil.ok();
    }

    /**
     *  填坑，根据日志文件剪辑
     * @return
     */
    @Test
    @GetMapping("/test")
    public void cutVideoByFile() {
        File fileT = new File("C:\\Users\\scohong\\Desktop\\剧能吃\\测试\\没有视频.txt");
        BufferedReader reader = null;
        //用于解析json
        RestTemplate restTemplate = new RestTemplate();
        String program = "";
        String episode = "";
        try {
            reader = new BufferedReader(new FileReader(fileT));
            String tempString = null;

            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
//                System.out.println(tempString.substring(36));
                String videoJson = tempString.substring(36);
                String [] strs = videoJson.split(",");
                String  frameId = strs[0].substring(14);
                program = strs[1].substring(10);
                episode = strs[2].substring(1,strs[2].length() - 1);
                String  vStart = strs[3].substring(16,strs[3].length() - 1);
                String  vEnd = strs[4].substring(14,strs[4].length() - 1);
                Video video = new Video();
                video.setFrameId(Integer.parseInt(frameId));
                System.out.println(frameId);
                video.setVideoStartTime(vStart);
                video.setVideoEndTime(vEnd);
                line++;

                String localVideoDir = ConfigManagment.VIDEOCUTDIR;
                String localGifDir = ConfigManagment.GIFCUTDIR;
                String videoOutPath = localVideoDir+program+File.separator;
                String gifOutPath = localGifDir+episode+File.separator;
                //没有目录就创建
                File videoDir = new File(videoOutPath);
                File gifDir = new File(gifOutPath);
                if (!videoDir.isDirectory()) {
                    //多级路径没有，用mkdirs，只有一级路径用mkdir
                    videoDir.mkdirs();
                }
                if (!gifDir.isDirectory()) {
                    gifDir.mkdirs();
                }
                String file = episode;
                String filePath = ConfigManagment.RECORDLOCALDIR + program+File.separator+file;
                //根据
                String videoName = DigestUtils.md5Hex(video.getVideoStartTime()+ program+file).substring(0,8);
                String videoFile = videoOutPath + videoName + ".mp4";
                String gifName = DigestUtils.md5Hex(video.getGifStartTime()+ program + file).substring(0,8);
                String gifFile = gifOutPath + gifName + ".gif";
                //转换时间
                int videoStartTime = CommonUtils.getSecond(video.getVideoStartTime());
                int videoEndTime = CommonUtils.getSecond(video.getVideoEndTime());
                // TODO Auto-generated method stub
                if (video.getVideoStartTime() != null && video.getVideoEndTime() != null) {
                    if (videoStartTime > videoEndTime) {
                        System.out.println("结束时间大于起始时间，请重新选择！");
                    }
                    try {
                        /**py脚本
                         * 'ffmpeg -i "{video_path}" -ss {start_time} -c copy -to {end_time} -codec:a aac "{out_path}"'
                         * */
                        /** 剪视频*/
                        String[] cutVideo = new String[] { "D:\\Anaconda\\python.exe", "F:/workspace/python/cutVideo.py",
                                filePath, String.valueOf(videoStartTime), String.valueOf(videoEndTime - videoStartTime), videoFile};
                        Process proc = Runtime.getRuntime().exec(cutVideo);
                        proc.waitFor();
                        //用输入输出流来截取结果
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info(videoFile);
                    log.info(gifFile);

                    /**更新sql的gif和video数据*/
                    String videoSqlPath = ConfigManagment.VIDEOSQLDIR + program +"/"+ videoName + ".mp4";
                    int i = frameDao.updateVideoById(video.getFrameId(), videoSqlPath,0,0);
                    if (i == 1) {
                        System.out.println("剪辑成功");
                    } else {
                        System.out.println("数据更新失败，请联系小洪");
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

}
