package com.skynet.rimp.registerInfo.dao;

import java.util.List;

import com.skynet.common.SearchParams;
import com.skynet.rimp.registerInfo.vo.PayMedicalCardTransInfo;

public interface PayMedicalCardTransInfoDao {
	
	int insert(PayMedicalCardTransInfo record);

	int delete(Integer transId);

    int update(PayMedicalCardTransInfo record);
    
    List<PayMedicalCardTransInfo> findAll();
    
    List<PayMedicalCardTransInfo> findByCondition(SearchParams params);

}