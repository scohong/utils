package com.scohong.dao;

import com.scohong.entity.junengchi.UserSubmission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserDao {
    /**
     * 获取后台所有数据
     *
     * @return 所有后台数据
     */
    @Select("<script> select * from  user_submission where 1 = 1 " +
            "<if test = \" status != '' \"> and status = #{status} </if> " +
            "<if test =\" name != '' \"> and (program_name like CONCAT('%',#{name},'%') " +
            "or shop_name like CONCAT('%',#{name},'%') )</if> " +
            "</script>")
    List<UserSubmission> getUserSubmission(String name, String status);

    /**
     * 审核用户上传数据
     *
     * @return 所有后台数据
     */
    @Update("update  user_submission set status =#{isCheck} where id = #{id}")
    boolean userSubmissionConfirmById(int id, int isCheck);
}
