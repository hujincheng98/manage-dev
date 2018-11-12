package com.skynet.rimp.blackListInfo.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.skynet.common.SearchParams;
import com.skynet.platform.common.dao.BaseDao;
import com.skynet.rimp.blackListInfo.vo.PabaPatientInfo;

@Repository
public interface PabaPatientInfoDao extends BaseDao<PabaPatientInfo, String>{
	
	public List<PabaPatientInfo> findAll();
	
	List<PabaPatientInfo> findByCondition(SearchParams params);
	
	 int updateTaskPaba(PabaPatientInfo record);
}