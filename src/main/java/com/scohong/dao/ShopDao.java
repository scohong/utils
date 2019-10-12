package com.scohong.dao;

import com.scohong.entity.junengchi.Shop;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShopDao {

    @Select("select id shopId,`name` shopName,city,address,introduction,cover_pic,thumb_cover_pic " +
            "from shop ")
    List<Shop> getAllShops();

    @Insert("INSERT into shop(`name`,introduction,city,address,cover_pic) values (#{shopName},#{introduction} ,#{city}, " +
            "#{address} ,#{coverPic} )")
    boolean addShop(Shop shop);

    @Delete("delete from shop where id =#{shopId}")
    boolean delShop(int shopId);

    @Select("select id  from shop where `name` = #{shopName}")
    Integer getShopIdByName(String name);

    @Select("select id shopId,`name` shopName,city,address,introduction,cover_pic,thumb_cover_pic " +
            "from shop where `name` = #{shopName}")
    Shop getShopByName(String name);

    @Update("update shop set `name` = #{shopName},city = #{city},introduction = #{introduction}," +
            "address = #{address},cover_pic = #{coverPic} where id = #{shopId} ")
    Integer updateShop(Shop shop);

    @Select("select id shopId,`name` shopName,city,address,introduction,cover_pic,thumb_cover_pic " +
            "from shop where `name` like CONCAT('%',#{name},'%')")
    List<Shop> searchShopByName(String name);
}
