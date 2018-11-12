package com.skynet.rimp.hisBaseInfo.dao;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.dao.BaseDao;
import com.skynet.rimp.hisBaseInfo.vo.HosDeptInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HosDeptInfoDao extends BaseDao<HosDeptInfo, String>{

	public List<HosDeptInfo> findAll();

	List<HosDeptInfo> findByParentId(@Param(value = "parentId") String parentId);

	int findCountByParentId(@Param(value = "parentId") String parentId);
	
	List<HosDeptInfo> findByCondition(SearchParams params);

	List<HosDeptInfo> findExistByCondition(SearchParams params);
	
	public List<HosDeptInfo> listChildren(String parentId);
	
	public int getChildrenCount(String deptId);
	
	public int findByAreaId(String areaId);

	public Integer selectMaxReleDayNum(String hosId);

	public int findByDisId(String disId);
	
	HosDeptInfo selectByHisCode(String hisCode);
	
	public int deleteByKeyArr(String[] deptIdArr);
	
}