package com.scohong.dao;

import com.scohong.entity.junengchi.FrameData;
import com.scohong.entity.junengchi.Program;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/9/18 20:32
 * @Description:
 */

@Mapper
public interface ProgramDao {

    @Select("select id programId,`name` programName,alias programAlias,types,vertical_cover_pic,cover_pic,introduction,shop_nums,actor " +
            " from program order by id")
    List<Program>  getAllProgram();

    @Delete("DELETE from program where id = #{programId}")
    int  delProgram(int programId);

    @Update("UPDATE  program set `name` = #{programName},alias = #{programAlias} ," +
            " types = #{types} ,introduction=#{introduction} ,actor = #{actor} where id = #{programId}")
    int  updateProgram(Program program);

    @Select("SELECT  distinct `name` from program ")
    List<String>  getProgramName();

    /**
     * @Description: 添加节目详情
     * @param program {@link Program}
     * @return
     */
    @Insert("INSERT INTO program(`name`,alias,types,vertical_cover_pic,cover_pic,thumb_cover_pic,introduction,hot,is_hot," +
            "shop_nums,actor) VALUES (#{programName},#{programAlias} ,#{types} ,#{verticalCoverPic} ,#{coverPic} ,#{thumbCoverPic} ," +
            "#{introduction} ,#{hot} ,#{isHot} ,#{shopNums} ,#{actor} )")
    @Options(useGeneratedKeys=true)
    boolean addProgramDetail(Program program);

    /**
     * @Description: 更新节目详情
     * @param program {@link Program}
     * @return
     */
    @Update("update program set `name`=#{programName},alias=#{progarmAlias},types=#{types},vertical_cover_pic=#{verticalCoverPic}," +
            "cover_pic=#{coverPic},thumb_cover_pic=#{thumbCoverPic},introduction=#{introduction},hot=#{hot} ,is_hot=#{isHot}," +
            "shop_nums=#{shopNums},actor=#{actor} ")
    @Options(useGeneratedKeys=true)
    boolean updateProgramDetail(Program program);

    @Select("select id from program where name =#{name} ")
    Integer getProgramId(String name);

    /**
     * @Description: 根据节目名称获取详情
     * @return
     */
    @Select("SELECT *,id programId,`name` programName FROM program where `name` = #{programName}")
    Program getProgramByName(String programName);

    /**
     * @Description: 模糊搜索节目
     * @return
     */
    @Select("SELECT *,id programId,`name` programName FROM program where `name`  like CONCAT('%',#{programName},'%')")
    List<Program> searchProgramByName(String programName);

    /**
     * 更新节目宣传图
     * @param coverPic
     * @param programName
     * @return
     */
    @Update("Update program set cover_pic = #{coverPic} where `name` = #{programName}")
    boolean updateCoverPic(String coverPic, String programName);

    /**
     * 更新节目首页封面图
     * @param verticalCoverPic
     * @param programName
     * @return
     */
    @Update("Update program set vertical_cover_pic = #{verticalCoverPic} where `name` = #{programName}")
    boolean updateVerticalCoverPic(String verticalCoverPic, String programName);


}
