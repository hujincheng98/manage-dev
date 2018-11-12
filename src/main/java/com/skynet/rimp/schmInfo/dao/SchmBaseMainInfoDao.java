package com.skynet.rimp.schmInfo.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.skynet.common.SearchParams;
import com.skynet.rimp.schmInfo.vo.SchmAutoSchmInfoVo;
import com.skynet.rimp.schmInfo.vo.SchmBaseMainInfoEntity;

@Repository
public interface SchmBaseMainInfoDao
{

	public List<SchmBaseMainInfoEntity> findAll();
	
	public void deleteByPrimaryKey(String schmId);

	public void deleteByKeyArr(String[] schmIds);
	
	int findByShiftId(String shiftId);
    
	public int insert(SchmBaseMainInfoEntity record);
    
    public int update(SchmBaseMainInfoEntity record);

	public List<SchmBaseMainInfoEntity> findByCondition(SearchParams arg0);
	
	public List<SchmBaseMainInfoEntity> findByConditionByAutoSchm(SchmAutoSchmInfoVo recordvo);

	public int findCountBydocmAndWeek(Map<String, Object> params);
	
	public List<SchmBaseMainInfoEntity> findAllAuto();
	
    public List<SchmBaseMainInfoEntity> findByConditionBydel(SearchParams params);
	
	
}