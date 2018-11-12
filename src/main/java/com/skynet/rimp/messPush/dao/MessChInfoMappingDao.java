package com.skynet.rimp.messPush.dao;

import java.util.List;
import com.skynet.common.SearchParams;
import com.skynet.rimp.messPush.dto.MessChInfoMappingDTO;
import com.skynet.rimp.messPush.vo.MessChInfoMapping;
import com.skynet.rimp.messPush.vo.MessChInfoMappingKey;

public interface MessChInfoMappingDao {
	
	int insert(MessChInfoMapping record);

	int delete(MessChInfoMappingKey key);
	
	int deleteByChId(String chId);
	
	public int deleteByKeyArr(String[] chIdArr);

    int update(MessChInfoMapping record);
    
    MessChInfoMapping getMessChInfo(MessChInfoMappingKey key);
    
    List<MessChInfoMapping> findMessChInfoByChId(String chId);
    
    List<MessChInfoMappingDTO> findMessChInfoRedisByChId(String chId);
    
    List<MessChInfoMapping> findAll();
    
    List<MessChInfoMapping> findByCondition(SearchParams params);

}