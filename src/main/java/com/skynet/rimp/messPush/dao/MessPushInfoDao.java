package com.skynet.rimp.messPush.dao;

import java.util.List;
import com.skynet.common.SearchParams;
import com.skynet.rimp.messPush.vo.MessPushInfo;

public interface MessPushInfoDao {
	
	int insert(MessPushInfo record);

	int delete(String messCode);

    int update(MessPushInfo record);
    
    List<MessPushInfo> findAll();
    
    List<MessPushInfo> findByCondition(SearchParams params);

}