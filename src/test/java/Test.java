import com.google.common.hash.Hashing;
import com.scohong.constant.ConfigManagment;
import com.scohong.utils.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
    public static void main(String[] args){
//        thumbFile(new File("D:\\剧能吃-压缩图片\\"));
//        String dir = "D:\\剧能吃-压缩图片\\";
//            int num = diguiFile(new File(dir),0);
//        System.out.println("del:" + num);
//        String outDir = ConfigManagment.VIDEOCUTDIR;
//        String videoOutPath = outDir+"abc"+"\\video\\";
//        log.info(videoOutPath);
//        String gifOutPath = outDir+"abc"+"/gif/";
//        //没有目录就创建
//        File videoDir = new File(videoOutPath);
//        File gifDir = new File(gifOutPath);
//        if (!videoDir.isDirectory()) {
//            log.info("video创建目录");
//            videoDir.mkdirs();
//        }

        //压缩文件
        File file = new File("F:\\数据分部\\原图\\programs-0.8-0.8\\");
        int small = 0;
        int middle = 0;
        int big = 0;
        for (File f:file.listFiles()
             ) {
            if (!f.isDirectory()) {
                continue;
            }
            for (File ff : f.listFiles()) {
                long size = ff.length() / 1024;
                if (size < 100) {
                    small++;
                } else if (size < 200) {
                    middle++;
                } else {
                    big++;
                }
//                String newFileName = ff.getAbsolutePath().replaceAll("programs", "programs-0.8-0.8");
//                String dir = new File(newFileName).getParent();
//                if (!new File(dir).isDirectory()) {
//                    new File(dir).mkdirs();
//                }
//                System.out.println(newFileName);
//                try {
//                    Thumbnails
//                            .of(ff)
//                            .scale(1)
//                            .outputQuality(0.8)
//                            .toFile(newFileName);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

        }
        System.out.println(small);
        System.out.println(middle);
        System.out.println(big);


    }

    public static void thumbFile(File file) {
        if (file.isDirectory()) {
            for (File f:file.listFiles()
                 ) {
                thumbFile(f);
            }
        }
        if (file.getName().indexOf("jpg") != -1) {
            try {
                Thumbnails.of(file)
                        .size(750,566)
                        .toFile(file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println(file.getAbsolutePath());
                e.printStackTrace();
            }
        }
    }

    public static int diguiFile(File f,int count) throws Exception{
        if (f.isDirectory()) {
            for (File ff : f.listFiles()
            ) {
                diguiFile(ff, count);
            }
        } else if(!ImageUtil.getImageType(f).equals("other")){
            long size = f.length() /1024;
            if (size > 200) {
                count++;
                String newFileName = f.getName().replaceAll("png", "jpg");
                log.info(f.getParent()+newFileName );
                Thumbnails
                        .of(f)
                        .size(1280,720)
                        .outputFormat("jpg")
                        .outputQuality(0.75)
                        .toFile(f.getParent().concat(File.separator).concat("/t_").concat(newFileName));
            }
        }
//        if ( f.getName().indexOf("t_") != -1) {
//            f.delete();
//            count++;
//        }
                        //获取文件大小(kb)

        return count;
    }
}
