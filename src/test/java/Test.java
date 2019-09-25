import lombok.extern.slf4j.Slf4j;

/**
 * @Author: scohong
 * @Date: 2019/8/13 14:42
 * @Description:
 */
@Slf4j
public class Test {
    public static void main(String[] args)throws  Exception {
        String images = "/images/pianchang/红楼梦-黄山飞来石-cbbe.jpg;/images/pianchang/红楼梦-黄山飞来石-b484.jpg;/images/pianchang/红楼梦-黄山飞来石-7202.jpg;/images/pianchang/红楼梦-黄山飞来石-65af.jpg";
        String name = "红楼梦";
        images = images.replaceAll("/images/pianchang/","/images/pianchang/" + name +"/");
        log.info(images);
    }
}
