package com.vanstone.centralserver.configuration.sdk.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.vanstone.centralserver.configuration.sdk.persistence.object.SysConfInfoDO;
import com.vanstone.dal.mybatis.MyBatisRepository;

@MyBatisRepository
public interface SysConfInfoDOMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(SysConfInfoDO record);

	int insertSelective(SysConfInfoDO record);

	SysConfInfoDO selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysConfInfoDO record);

	int updateByPrimaryKeyWithBLOBs(SysConfInfoDO record);

	int updateByPrimaryKey(SysConfInfoDO record);
	
	SysConfInfoDO selectByDataId_GroupId(@Param("groupId")String groupId,@Param("dataId")String dataId);
	
	SysConfInfoDO selectByDataId_GroupId_NotSelf(@Param("groupId")String groupId, @Param("dataId")String dataId, @Param("id")Integer id);
	
	int deleteByGroupId_DataId(@Param("groupId")String groupId,@Param("dataId")String dataId);
	
	List<String> selectGroupByGroupId();
	
	List<SysConfInfoDO> selectByGroupId(String groupId);
	
	List<SysConfInfoDO> selectByKey(@Param("key")String key, RowBounds rowBounds);
	
	int selectTotalByKey(@Param("key")String key);
	
	List<Map<String, String>> selectAllGroupIdDataIds();
	
	List<SysConfInfoDO> selectAll();
	
}