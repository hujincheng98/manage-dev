package com.skynet.rimp.messPush.service;

import java.util.List;
import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.messPush.dto.MessChInfoMappingDTO;
import com.skynet.rimp.messPush.vo.MessChInfoMapping;
import com.skynet.rimp.messPush.vo.MessChInfoMappingKey;

public interface IMessChInfoMappingService extends BaseService<MessChInfoMapping> {

	int insert (MessChInfoMapping record);
	
	int delete (MessChInfoMappingKey key);
	
	int deleteByChId(String chId);
	
	public int deleteByKeyArr(String[] chIdArr) throws Exception;
	
	int update (MessChInfoMapping record);
	
	List<MessChInfoMapping> findMessChInfoByChId(String chId);
	
	List<MessChInfoMappingDTO> findMessChInfoRedisByChId(String chId);
	
	MessChInfoMapping getMessChInfo(MessChInfoMappingKey key);
}
