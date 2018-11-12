package com.skynet.rimp.blackListInfo.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.skynet.common.SearchParams;
import com.skynet.rimp.blackListInfo.vo.PabaRuleInfo;

@Repository
public interface PabaRuleInfoDao {
	
	int insert(PabaRuleInfo record);

    int delete(String pabaId);

    int update(PabaRuleInfo record);
    
    List<PabaRuleInfo> findAll();
    
    List<PabaRuleInfo> findByCondition(SearchParams params);
    
    List<PabaRuleInfo> finddefault();
    
    List<PabaRuleInfo> findHosBychanne();
    
    List<PabaRuleInfo> findHosRule(String hosId);
    
    
}
