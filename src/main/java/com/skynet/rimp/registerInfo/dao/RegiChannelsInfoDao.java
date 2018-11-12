package com.skynet.rimp.registerInfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.skynet.common.SearchParams;
import com.skynet.rimp.registerInfo.vo.RegiChannelsInfo;

@Repository
public interface RegiChannelsInfoDao {
	
	int insert(RegiChannelsInfo regiChannelsInfo);

	int delete(String regiId);

    int update(RegiChannelsInfo regiChannelsInfo);
    
    List<RegiChannelsInfo> findAll();
    
    List<RegiChannelsInfo> findByCondition(SearchParams params);
}