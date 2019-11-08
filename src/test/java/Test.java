import com.google.common.hash.Hashing;
import com.scohong.constant.ConfigManagment;
import com.scohong.utils.FileUtil;
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
    public static void main(String[] args) {
//        FileUtil.checkImageDimenSize("F:\\数据分部\\压缩\\压缩结果-540p\\石梅湾艾美度假酒店\\酒店5.jpg");
        thumbImage();

    }
    public static void thumbImage() {
        //        FileUtil.checkImageFileSize("F:\\数据分部\\原图\\shop-1104\\shops\\");
        File[] file = new File("F:\\数据分部\\压缩\\待压缩\\").listFiles();
        for (File f : file) {
            for (File ff : f.listFiles()) {
                if (ff.getAbsolutePath().indexOf("jpg") != -1) {
                    FileUtil.checkImageDimenSize(ff.getAbsolutePath());
                }
                String newFileName = ff.getAbsolutePath().replaceAll("待压缩", "压缩结果-540p");
                FileUtil.mkParentDir(new File(newFileName));
                System.out.println(newFileName);
                try {
                    for (int i = 1; i <= 10; i++) {
                        String newName = newFileName.replaceAll(".png", String.valueOf(i));
                        newName = newName.replaceAll(".jpg", i + ".jpg");
                        System.out.println(newName);
                        Thumbnails
                                .of(ff)
                                .scale(i*0.1)
                                .outputQuality(0.8)
                                .outputFormat("jpg")
                                .toFile(newName);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
