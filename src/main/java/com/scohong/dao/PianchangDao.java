package com.scohong.dao;

import com.scohong.entity.junengchi.Program;
import com.scohong.entity.pianchangDO.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    @Insert("insert into place_detail(id,cname,ename,oname,alias,coverPath,coverSource,mapPath,lat,lng,areaId,caddress,eaddress,addressTips," +
            "dispark,disparkRemark,assType,assedId,satellitePath,satelliteDesc,phone,rmmDesc,tips,description,createTime," +
            "staticMapUrl,milliSecond,areaCname,areaEname,level3Id,level3Cname,level3Ename,level2Id,level2Cname,level2Ename," +
            "level1Id,level1Cname,level1Ename,level0Id,level0Cname,level0Ename,assedCname,assedEname,asses,movies,staticMapUrlAss" +
            ") " +
            "VALUES (#{id},#{cname},#{ename},#{oname},#{alias},#{coverPath},#{coverSource},#{mapPath},#{lat},#{lng},#{areaId}, " +
            " #{caddress},#{eaddress},#{addressTips},#{dispark},#{disparkRemark},#{assType},#{assedId},#{satellitePath}," +
            " #{satelliteDesc},#{phone},#{rmmDesc},#{tips},#{description},#{createTime},#{staticMapUrl},#{milliSecond},#{areaCname}," +
            " #{areaEname},#{level3Id},#{level3Cname},#{level3Ename},#{level2Id},#{level2Cname},#{level2Ename},#{level1Id}," +
            " #{level1Cname},#{level1Ename},#{level0Id},#{level0Cname},#{level0Ename},#{assedCname},#{assedEname},#{asses}," +
            " #{movies},#{staticMapUrlAss})")
    int insertPlotDetail( PlaceDetail placeDetail);



    //所有节目id
    @Select("select distinct id from place_detail")
    List<Integer> getPlaceId();

    //片场所有场景
    @Select("select distinct * from place_detail")
    List<PlaceDetail> getPlaceDetail();

    //片场所有场景
    @Select("select distinct * from movie")
    List<Movie> getPianchangMovie();

    //片场所有记录
    @Select("select distinct * from program_shop")
    List<PianchangRelateRecord> getRecordDetail();

    //添加节目-商家关联信息
    @Insert("insert into program_shop(program_id,shop_id,introduction,episode,appear_time,images,thumb_images) " +
            "VALUES (#{programId} ,#{placeId},#{description},#{episode},#{appearTime},#{images},#{thumbImages} )")
    int addRecord(PianchangRelateRecord record);

    //更新片场取景图片
    @Update("update program_shop set images = #{imageUrl} where introduction = #{intruduction}")
    int updateRecord(String introduction,String imageUrl);


    /**
     * @Description: 片场数据导入
     * @return
     */
    @Insert("INSERT INTO program(`name`,cover_pic) VALUES (#{cname}, #{coverPath})")
    @Options(useGeneratedKeys=true)
    int addPianchangMovie(Movie movie);

    /**
     * @Description: 片场取景地导入
     * @return
     */
    @Insert("INSERT INTO shop(id,`name`,city,address) VALUES (#{id} ,#{cname},#{areaCname},#{caddress} )")
    @Options(useGeneratedKeys=true)
    int addPianchangPlace(PlaceDetail placeDetail);

    //从片场导出节目-商家关联信息
    @Insert("insert into p_program_shop(program_id,shop_id,episode,appear_time,images,thumb_images,introduction) " +
            "VALUES (#{programId} ,#{placeId},#{episode},#{appearTime},#{images},#{thumbImages},#{description} )")
    int addRecordDetail(PianchangRelateRecord record);

    //测试用
    @Select("select id programId,vertical_cover_pic from program where id > 28")
    List<Program> getTmp();

    //测试更新
    @Update("update  program  set vertical_cover_pic = #{coverPic}  where id = #{id}")
    int updateTmp(String coverPic,int id);
}
