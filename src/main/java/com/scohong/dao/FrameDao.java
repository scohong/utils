package com.scohong.dao;

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
            "from program_shop_util ps,shop s,program p " +
            "where ps.shop_id = s.id and ps.program_id = p.id order by ps.id")
    List<FrameData> getAllFrameData();

    /**
     * 根据id删除数据
     * @param id
     * @return
     */
    @Delete("DELETE from program_shop_util where id = #{id}")
    boolean delFrameData(int id);

    /**
     * 更新取景数据
     * @param
     * @return
     */
    @Update("update program_shop_util set foods = #{foods} ,episode = #{episode},appear_time = #{appearTime}, " +
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
}
