package com.skynet.rimp.hisBaseInfo.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.skynet.common.SearchParams;
import com.skynet.rimp.hisBaseInfo.vo.HosInfo;

@Repository
public interface HosInfoDao {
	
    int insert(HosInfo record);

    int delete(String hosId);
    
    int update(HosInfo record);
    
    List<HosInfo> findAll();
    
    List<HosInfo> findByHosName(String hosName);
    
    List<HosInfo> findByCondition(SearchParams params);
    
    HosInfo findByhosId(String hosId);

	HosInfo findByHosOrgId(String orgId);

}