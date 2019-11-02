import com.scohong.utils.RedisUtil;
import org.springframework.boot.autoconfigure.cache.CacheProperties;

/**
 * @Author: scohong
 * @Date: 2019/11/2 14:35
 * @Description:
 */
public class RedisTest {
    public static void main(String[] args) {
        System.out.println(RedisUtil.getRedisUtil().set("abc","ab"));
        System.out.println();

    }
}
