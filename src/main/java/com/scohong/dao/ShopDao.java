package com.scohong.dao;

import com.scohong.entity.junengchi.Shop;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopDao {

    @Select("select id shopId,`name` shopName,city,address,introduction,cover_pic,thumb_cover_pic " +
            "from shop_test ")
    List<Shop> getAllShops();

    @Delete("delete from shop_test where id =#{shopId}")
    boolean delShop(int shopId);
}
