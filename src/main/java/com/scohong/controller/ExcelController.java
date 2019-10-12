package com.scohong.controller;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.scohong.dao.FrameDao;
import com.scohong.dao.ProgramDao;
import com.scohong.dao.ShopDao;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: scohong
 * @Date: 2019/9/24 11:18
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping("/frame")
public class ExcelController {
    @Autowired
    ProgramDao programDao;
    @Autowired
    ShopDao shopDao;
    @Autowired
    FrameDao frameDao;

    /**
     * @param filePath
     * @return
     * @throws IOException
     */
    @PostMapping("/videoPathToSql")
    @ApiOperation(value = "注入视频字段")
    public boolean addFile(@RequestParam String filePath) throws IOException {
        List<String> programNames = programDao.getProgramName();
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        for (Sheet sheet:workbook
             ) {
            String program = sheet.getSheetName();
            if (!programNames.contains(program)) {
                continue;
            }
            log.info("开始处理文件：" + sheet.getSheetName());
            //获取节目id跟集数和商家名称进行定位
            String programName = sheet.getSheetName();
            int count = 0;
            //行数从0开始计算
            int rowNum = sheet.getLastRowNum();
            //todo 读取每一行数据，数据格式暂时写死了，没有数据需要默认填充
            Integer programId = programDao.getProgramId(programName);
            if (programId == null) {
                continue;
            }
            for (int i = 1; i <= rowNum; i++) {
                log.info("行数："+i);
                Row curRow = sheet.getRow(i);
                curRow.getCell(0).setCellType(CellType.STRING);
                //||curRow.getCell(0).getStringCellValue().length() == 0
                if (curRow.getCell(0) == null ) {
                    continue;
                }
                int episode = 0;
                try {
                    episode = Integer.parseInt(curRow.getCell(0).getStringCellValue());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    continue;
                }
                String shopName = curRow.getCell(1).getStringCellValue();
                curRow.getCell(9).setCellType(CellType.STRING);
                String startTime = curRow.getCell(9).getStringCellValue();
                //获取商家id
                Integer shopId = null;
                try {
                    shopId = shopDao.getShopIdByName(shopName);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
                if (shopId == null || shopId == 0) {
                    continue;
                }
                //判断数据库是否有记录，没有则跳过

                Integer recordId = null;
                try {
                    recordId = frameDao.recordExit(shopId, programId, episode);
                } catch (Exception e) {
                    log.info("" + shopName+episode);
                    e.printStackTrace();
                    continue;
                }
                //没有记录则跳过
                if ( recordId == null) {
                    continue;
                }
                //开始拼接视频字符串
                String video = "/video/"+programName+"/" + programName+"-"+episode+"-"+startTime+".mp4";
                //插入数据库
                Boolean isSuccess = frameDao.updateVideo(recordId, video);
                if (isSuccess) {
                    count++;
                }
            }
            log.info("成功插入：" + count + "条数据");
            count = 0;
        }

        return true;
    }
}
