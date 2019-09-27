import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @Author: scohong
 * @Date: 2019/8/13 14:42
 * @Description:
 */
@Slf4j
public class Test {
    /**
     * 删除文件 todo  递归遍历所有文件
     * @param args
     * @throws Exception
     */
    public static void main(String[] args)throws  Exception {
        File[] file = new File("D:\\剧能吃-备份数据\\pianchang\\").listFiles();
        for (File f:file
             ) {
            for (File f2:f.listFiles()
                 ) {
                log.info(f2.getName());
                if (f2.isDirectory()) {
                    continue;
                }
                //获取文件大小(kb)
                long size = f2.length() /1024;
                log.info(f2.getName() + "大小：" + size/1024 +"k");
                if (size > 50) {
                    f2.delete();
                }
            }
        }
    }
}
