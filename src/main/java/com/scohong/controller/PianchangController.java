package com.scohong.controller;

import com.google.common.base.Joiner;
import com.scohong.dao.PianchangDao;
import com.scohong.entity.junengchi.Program;
import com.scohong.entity.pianchangDO.*;
import com.scohong.images.DownloadImages;
import com.scohong.utils.PianchangUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @Author: scohong
 * @Date: 2019/9/10 09:28
 * @Description:
 */
@RestController
@Slf4j
public class PianchangController {
    @Autowired
    PianchangDao pianchangDao;

    @GetMapping("/data")
    public String getData() throws Exception {
        //新版09-09
        //添加场景详情placeDetail
        File[] files = new File("E:\\片场\\newMovies\\").listFiles();
        List<Integer> oldPlaceIds = pianchangDao.getPlaceId();
        List<Integer> newPlaceIds = pianchangDao.getPlaceId();
        for (File file : files){
            try {
                Movie movie = PianchangUtil.pianchang2JsonToEntity(file);
                int programId = movie.getId();
                String programName = movie.getCname();
                log.info(""+movie.getId());
                List<PlaceDetail> placeDetails = movie.getPlaceDetails();
                //获取palceDetail数据
                if (placeDetails.size() > 0) {
                    log.info("地点："+placeDetails.get(0).getCname());
                    //添加取景地址详情
                    for (PlaceDetail placeDetail:placeDetails
                         ) {
                        //空值判断
                        if (placeDetail == null) {
                            continue;
                        }
                        //商家基本信息
                        int placeId = placeDetail.getId();
                        log.info("placeId" + placeId);
                        String placeName = placeDetail.getCname();
                        String address = placeDetail.getCaddress();
                        log.info(placeDetail.getCname());
//                        if (oldPlaceIds.indexOf(placeId) == -1) {
//                            //原有地点比对
//                            //新增地点比对
//                            if (newPlaceIds.indexOf(placeId) == -1) {
//                                newPlaceIds.add(placeId);
//                                pianchangDao.insertPlotDetail(placeDetail);
//                            }
//                        }
                        //获取取景节目列表
                        List<Scene> scenes = placeDetail.getScenes();
                        for (Scene s:scenes
                             ) {
                            //非空判断
                            if (s == null) {
                                continue;
                            }
                            //判断是否同一节目
                            int movieId = s.getMovieId();
                            if (movieId != programId) {
                                continue;
                            }
                            log.info("节目: " + s.getMovieCname());
                            List<Detail> detailList = s.getDetails();
                            //不同取景地点
                            for (Detail detail : detailList) {
                                //非空判断
                                if (detail == null) {
                                    continue;
                                }
                                //添加取景记录
                                //获取取景图片，并拼接成字符串
                                String images;
                                List<Still> stillList = detail.getStills();
                                List<String> localPicPath = new ArrayList<>();
                                //非空判断
                                for (Still image: stillList
                                     ) {
                                    if (image == null) {
                                        continue;
                                    } else {
                                        //下载图片
                                        String scenePic = image.getPicPath();
                                        String picTmp = programName+"-"+placeDetail.getCname()+"-"+UUID.randomUUID().toString().substring(0,4)+".jpg";
                                        DownloadImages.download(scenePic,picTmp,"E:\\剧能吃\\data\\pianchang\\"+programName);
                                        localPicPath.add("/images/pianchang/" +programName + "/"+ picTmp);
                                    }
                                }
                                //获取取景时间
                                String appearTime = PianchangUtil.secondTimeFormat(detail.getPosition());
                                log.info("取景时间：" + appearTime);
                                //list拼接成字符串，添加分隔符
                                images = StringUtils.join(localPicPath.toArray(), ";");
                                //数据库添加取景记录，需要取景id，电影id，图片，描述等信息
                                PianchangRelateRecord record = new PianchangRelateRecord();
                                record.setProgramId(programId);
                                record.setEpisode(detail.getEpisode());
                                record.setPlaceId(placeId);
                                record.setImages(images);
                                record.setThumbImages(images);
                                record.setDescription(detail.getDescription());
                                record.setProgramName(programName);
                                record.setPlaceName(placeName);
                                record.setAppearTime(appearTime);
                                //添加取景数据
                                pianchangDao.addRecord(record);
                                //更新取景图片
                            }

                        }
                    }

                    //添加节目-取景地的映射关系
                    //获取scene数据
//                    log.info("节目："+scene.getMovieCname());
//                    log.info("场景介绍："+scene.getSceneOverride());
//                    //detail
//                    Detail detail = scene.getDetails().get(0);
//                    log.info("details:" + detail.getDescription() );
//                    //场景描述，多图
//                    Still still = detail.getStills().get(0);
//                    log.info("still:" + still.getPicPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "hohoho";
    }

    /**
     * 导出数据
     * @return
     */
    @GetMapping("/toJunengchi")
    public String exportData() {
        int pNums = 1;
        int sNums = 1;
        int rNums = 1;
        //获取片场节目id，用于重复判断
        List<Movie> programs = pianchangDao.getPianchangMovie();
        //获取片场场景id
        List<PlaceDetail> places = pianchangDao.getPlaceDetail();
        //获取片场场景id
        List<PianchangRelateRecord> records = pianchangDao.getRecordDetail();
        //节目id跟场景id不重复再插入数据
        //movie: 28
        //shop:551

//        for (Movie movie: programs
//             ) {
//            int id = movie.getId();
//            if (id > 28) {
//                log.info("新增节目：" + pNums);
//                pNums++;
//                pianchangDao.addPianchangMovie(movie);
//            }
//        }
        // new 2859
        HashMap<Integer,String> map = new HashMap();
        for (PlaceDetail placeDetail : places) {
            int pId = placeDetail.getId();
            if (pId > 551 && !map.containsKey(pId)) {
                map.put(pId, "");
                log.info("新增地址：" + sNums);
                sNums++;
                pianchangDao.addPianchangPlace(placeDetail);
            }
        }

//        for (PianchangRelateRecord record : records) {
//            int programId = record.getProgramId();
//            int shopId = record.getPlaceId();
//
//            if (shopId > 551 && programId > 28) {
//                log.info("新增记录：" + rNums);
//                rNums++;
//                pianchangDao.addRecordDetail(record);
//            }
//        }



        return "hohoho";
    }

    /**
     * 更新片场的取景数据
     * @return
     */
    @GetMapping("/testa")
    public String changUrl() {
        int count = 0;
        List<Program> tmps = pianchangDao.getTmp();
        for (Program t:tmps
             ) {
            String name = t.getProgramName();
            String images = t.getCoverPic();
            String thumbImages = t.getVerticalCoverPic();
            String[] thumbSplit = thumbImages.split("/");
            //转成list,在拼接成string
            thumbSplit[3] += "/thumb";
            thumbSplit[4] = "t_" + thumbSplit[4];
            String res = Joiner.on('/').join(thumbSplit);
            //插入数据
            log.info(""+res);
            int success = pianchangDao.updateTmp(res,t.getProgramId());
            if (success == 1) {
                count++;
            }
        }
        return "ok";
    }
}
