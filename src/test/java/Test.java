import com.scohong.Main;
import com.scohong.dao.PianchangDao;
import com.scohong.entity.common.StringUtils;
import com.scohong.entity.pianchangDO.Movie;
import com.scohong.entity.pianchangDO.PlaceDetail;
import com.scohong.entity.video.Program;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/8/13 14:42
 * @Description:
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        //新版09-09
        File[] files = new File("E:\\片场\\newMovies\\").listFiles();
        for (File file : files) {
            try {
                Movie movie = Main.pianchang2JsonToEntity(file);
//                System.out.println(movie.getCname()+ " " + movie.getType());
                List<PlaceDetail> placeDetails = movie.getPlaceDetails();
                log.info(String.valueOf(placeDetails.size()));
                //添加新电影
//                pianchangDao.insertMovie(movie);
                //添加新场景
//                String movieName = movie2.getData().getMovie().getCname();
//                List<Plot> plots = movie2.getData().getMovie().getPlots();
//                for (Plot p:plots) {
//                    pianchangDao.insertPlot(p,movieName);
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
