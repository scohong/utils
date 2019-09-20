package com.scohong.dao;

import com.scohong.entity.junengchi.Program;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
}
