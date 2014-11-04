package com.vanstone.centralserver.weixin.sdk.persistence;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.vanstone.centralserver.weixin.sdk.persistence.object.WeixinInfoDO;
import com.vanstone.centralserver.weixin.sdk.persistence.object.WeixinInfoDOWithBLOBs;
import com.vanstone.dal.mybatis.MyBatisRepository;

@MyBatisRepository
public interface WeixinInfoDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(WeixinInfoDOWithBLOBs record);

    int insertSelective(WeixinInfoDOWithBLOBs record);

    WeixinInfoDOWithBLOBs selectByPrimaryKey(String id);
    
    WeixinInfoDOWithBLOBs selectBy_Appid_AppSecret(@Param("appid")String appid, @Param("appSecret")String appSecret);
    
    int updateByPrimaryKeySelective(WeixinInfoDOWithBLOBs record);
    
    int updateBaseWeixinInfo(WeixinInfoDOWithBLOBs record);
    
    int updateByPrimaryKeyWithBLOBs(WeixinInfoDOWithBLOBs record);

    int updateByPrimaryKey(WeixinInfoDO record);
    
    List<WeixinInfoDOWithBLOBs> selectByKey(@Param("key")String key, RowBounds rowBounds);
    
    int selectCountByKey(@Param("key")String key);
}