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
            " from program_test order by id")
    List<Program>  getAllProgram();

    @Delete("DELETE from program_test where id = #{programId}")
    int  delProgram(int programId);

    @Update("UPDATE  program_test set `name` = #{programName},alias = #{programAlias} ," +
            " types = #{types} ,introduction=#{introduction} ,actor = #{actor} where id = #{programId}")
    int  updateProgram(Program program);

    /**
     * @Description: 添加节目详情
     * @param program {@link Program}
     * @return
     */
    @Insert("INSERT INTO program_test(`name`,alias,types,vertical_cover_pic,cover_pic,thumb_cover_pic,introduction,hot,is_hot," +
            "shop_nums,actor) VALUES (#{programName},#{programAlias} ,#{types} ,#{verticalCoverPic} ,#{coverPic} ,#{thumbCoverPic} ," +
            "#{introduction} ,#{hot} ,#{isHot} ,#{shopNums} ,#{actor} )")
    @Options(useGeneratedKeys=true)
    boolean addProgramDetail(Program program);

    /**
     * @Description: 更新节目详情
     * @param program {@link Program}
     * @return
     */
    @Update("update program_test set `name`=#{programName},alias=#{progarmAlias},types=#{types},vertical_cover_pic=#{verticalCoverPic}," +
            "cover_pic=#{coverPic},thumb_cover_pic=#{thumbCoverPic},introduction=#{introduction},hot=#{hot} ,is_hot=#{isHot}," +
            "shop_nums=#{shopNums},actor=#{actor} ")
    @Options(useGeneratedKeys=true)
    boolean updateProgramDetail(Program program);

    @Select("select id from program where name =#{name} ")
    Integer getProgramId(String name);


}
