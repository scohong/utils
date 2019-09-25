package com.scohong.dao;

import com.scohong.entity.junengchi.FrameData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 取景数据
 */
@Mapper
public interface FrameDao {

    @Select("select s.name shopName,p.`name` programName,ps.* " +
            "from program_shop_util ps,shop s,program p " +
            "where ps.shop_id = s.id and ps.program_id = p.id order by ps.id")
    List<FrameData> getAllFrameData();

    @Delete("DELETE from program_shop_util where id = #{id}")
    boolean delFrameData(int id);

    @Select("select id from program_shop where shop_id = #{shopId} and program_id = #{programId} " +
            "and episode = #{episode}")
    Integer recordExit(int shopId, int programId, int episode);

    @Insert("update program_shop set video = #{video} where id = #{id}")
    boolean updateVideo(int id, String video);

    @Select("select s.name shopName,p.`name` programName,ps.* from program_shop ps,program p,shop s where ps.shop_id = s.id and ps.program_id = p.id " +
            "and p.name like CONCAT('%',#{programName},'%') and s.name like CONCAT('%',#{shopName},'%')")
    List<FrameData> searchFrame(String programName, String shopName);
}
