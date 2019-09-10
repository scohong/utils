package com.scohong.dao;

import com.scohong.entity.pianchangDO.Movie;
import com.scohong.entity.pianchangDO.Plot;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: scohong
 * @Date: 2019/8/26 11:49
 * @Description:
 */
@Mapper
public interface PianchangDao {
    @Insert("insert into movie(id,cname,ename,cover_path,`year`,country_id,country_cname,country_ename,`type`,overview,series) " +
            "VALUES (#{id} ,#{cname},#{ename},#{coverPath},#{year},#{countryId},#{countryCname},#{countryEname} ,#{type} ,#{overview} ,#{series} )")
    int insertMovie(Movie movie);

    @Insert("insert into movie_place_relation(movie_name,place_id,place_cname,place_ename,lat,lng,ass_type,assed_id,assed_cname,assed_ename," +
            "up_level_area_id,up_level_area_cname,up_level_area_ename,scene_id,scene_name,episode,`position`,cover_path) " +
            "VALUES (#{movieName},#{plot.placeId},#{plot.placeCname},#{plot.placeEname},#{plot.lat},#{plot.lng},#{plot.assType}, " +
            "#{plot.assedId},#{plot.assedCname},#{plot.assedEname},#{plot.upLevelAreaId},#{plot.upLevelAreaCname}, " +
            "#{plot.upLevelAreaEname},#{plot.sceneId},#{plot.sceneName},#{plot.episode},#{plot.position},#{plot.coverPath})")
    int insertPlot( Plot plot, String movieName);

}
