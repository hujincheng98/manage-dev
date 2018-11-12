package com.skynet.rimp.hisBaseInfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.skynet.common.SearchParams;
import com.skynet.rimp.hisBaseInfo.vo.HosDisAreaInfo;
@Repository
public interface HosDisAreaInfoDao {
	
	int insert(HosDisAreaInfo hosDisAreaInfo);

	int delete(String disId);

	int update(HosDisAreaInfo hosDisAreaInfo);
	public List<HosDisAreaInfo> findByDisName(String disName);
    List<HosDisAreaInfo> findAll();
    
    List<HosDisAreaInfo> findByCondition(SearchParams params);
    List<HosDisAreaInfo> findByDisId(String disId);

	HosDisAreaInfo getByDisName(String disName);
}