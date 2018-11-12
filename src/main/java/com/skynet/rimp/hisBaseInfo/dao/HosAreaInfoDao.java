package com.skynet.rimp.hisBaseInfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.skynet.common.SearchParams;
import com.skynet.rimp.hisBaseInfo.vo.HosAreaInfo;
@Repository
public interface HosAreaInfoDao {
	public List<HosAreaInfo> findByCondition(SearchParams params);
	public List<HosAreaInfo	> findAll();
	public void insert(HosAreaInfo hosAreaInfo);
	public void updateByPk(HosAreaInfo hosAreaInfo);
	public List<HosAreaInfo	> findByAreaId(String areaId);
	public List<HosAreaInfo	> findByAreaName(String areaId);
	public int deleteByPk(String areaId);
	public HosAreaInfo getByAreaName(String areaName);
}
