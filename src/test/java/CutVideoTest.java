import java.io.IOException;

/**
 * @Author: scohong
 * @Date: 2019/11/8 15:33
 * @Description:
 */
public class CutVideoTest {
    public static void main(String[] args) {
        try {
            /**py脚本
             * 'ffmpeg -i "{video_path}" -ss {start_time} -c copy -to {end_time} -codec:a aac "{out_path}"'
             * */
            /** 剪视频*/
            String filePath = "D:\\剧能吃-纪录片\\都在酒里\\第一集.mp4";
            String videoFile = "F:\\数据分部\\gc\\jiu1.mp4";
            String[] cutVideo = new String[] { "D:\\Anaconda\\python.exe", "F:/workspace/python/cutVideo.py",
                    "D:\\剧能吃-纪录片\\都在酒里\\第一集.mp4", "175","231", "F:\\数据分部\\gc\\jiu1.mp4"};
            Process proc = Runtime.getRuntime().exec(cutVideo);
            proc.waitFor();
            //用输入输出流来截取结果
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
