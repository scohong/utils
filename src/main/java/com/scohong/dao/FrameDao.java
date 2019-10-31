package com.scohong.dao;

import com.scohong.entity.junengchi.BackendProgramAndShop;
import com.scohong.entity.junengchi.FrameData;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 取景数据
 */
@Mapper
public interface FrameDao {

    /**
     * 获取取景数据
     * @return
     */
    @Select("select s.name shopName,p.`name` programName,ps.* " +
            "from program_shop ps,shop s,program p " +
            "where ps.shop_id = s.id and ps.program_id = p.id order by ps.id")
    List<FrameData> getAllFrameData();

    /**
     * 根据id删除数据
     * @param id
     * @return
     */
    @Delete("DELETE from program_shop where id = #{id}")
    boolean delFrameData(int id);

    /**
     * 更新取景数据
     * @param
     * @return
     */
    @Update("update program_shop set foods = #{foods} ,episode = #{episode},appear_time = #{appearTime}, " +
            "introduction = #{introduction}  where id = #{id}")
    boolean updateFrameData(FrameData frameData);

    /**
     * 根据节目和商家获取取景记录
     * @param shopId
     * @param programId
     * @param episode
     * @return
     */
    @Select("select id from program_shop where shop_id = #{shopId} and program_id = #{programId} " +
            "and episode = #{episode}")
    Integer recordExit(int shopId, int programId, int episode);

    /**
     * 更新取景数据的视频记录
     * @param id
     * @param video
     * @return
     */
    @Insert("update program_shop set video = #{video} where id = #{id}")
    boolean updateVideo(int id, String video);

    /**
     * 模糊搜索取景数据
     * @param programName
     * @param shopName
     * @return
     */
    @Select("select s.name shopName,p.`name` programName,ps.* from program_shop ps,program p,shop s where ps.shop_id = s.id and ps.program_id = p.id " +
            "and p.name like CONCAT('%',#{programName},'%') and s.name like CONCAT('%',#{shopName},'%')")
    List<FrameData> searchFrame(String programName, String shopName);

    /**
     * @Description: 添加取景数据
     * @param data
     * @return
     */
    @Insert("INSERT INTO program_shop(program_id,shop_id,foods,images,thumb_images,introduction,episode,appear_time, " +
            "start_time,end_time) " +
            "VALUES (#{programId},#{shopId},#{foods},#{images},#{thumbnails} ,#{introduction},#{episode} ,#{appearTime}, " +
            "#{startTime} ,#{endTime}) ")
    @Options(useGeneratedKeys=true)
    boolean addFramedata(BackendProgramAndShop data);

    /**
     * 根据id获取取景数据
     */
    @Select("SELECT thumb_images from program_shop where id = #{id}")
    String getImageById(String id);

    /**
     * 更新取景图片
     */
    @Update("update program_shop set thumb_images = #{images} where id = #{id}")
    int updateImageById(String id,String images);

    /**
     * 更新取景图片，MD5
     */
    @Update("update program_shop set thumb_images_md5 = #{images} where id = #{id}")
    int updateImageMd5ById(String id,String images);

    /**
     * 更新gif数据
     */
    @Update("update program_shop set gif = #{gifPath},gif_start_time = #{gifStartTime},gif_end_time = #{gifEndTime} where id = #{id}")
    int updateGifById(int id,String gifPath,int gifStartTime,int gifEndTime);

    /**
     * 更新video数据
     */
    @Update("update program_shop set video = #{videoPath},video_start_time = #{videoStartTime},video_end_time = #{videoEndTime} where id = #{id}")
    int updateVideoById(int id,String videoPath,int videoStartTime,int videoEndTime);
}
