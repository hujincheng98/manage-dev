package com.skynet.rimp.channelInfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.skynet.common.SearchParams;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;

@Repository
public interface OtherChannelsInfoDao {
	
	int insert(OtherChannelsInfo otherChannelsInfo);

	int delete(String chId);
	
	public int deleteByKeyArr(String[] chIdArr);

	int update(OtherChannelsInfo otherChannelsInfo);
    
    List<OtherChannelsInfo> findAll();
    
    List<OtherChannelsInfo> findByCondition(SearchParams params);
    
    OtherChannelsInfo findById(String chId);
    
    List<OtherChannelsInfo> findListByOrgId(@Param(value = "orgId")String orgId);

    OtherChannelsInfo findByHosId(String hosId);
    
}