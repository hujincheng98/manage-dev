package com.skynet.rimp.hisBaseInfo.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.skynet.common.SearchParams;
import com.skynet.rimp.hisBaseInfo.vo.HisDocmInfo;
import com.skynet.rimp.hisBaseInfo.vo.HisDocmInfoKey;

@Repository
public interface HisDocmInfoDao {
	
    int deleteByPrimaryKey(HisDocmInfoKey key);

    int insert(HisDocmInfo record);

    int insertSelective(HisDocmInfo record);

    HisDocmInfo selectByPrimaryKey(HisDocmInfoKey key);

    int updateByPrimaryKeySelective(HisDocmInfo record);

    int updateByPrimaryKey(HisDocmInfo record);
    
    List<HisDocmInfo> findAll();
    
    List<HisDocmInfo> findByCondition(SearchParams params);
}