package com.skynet.rimp.schmInfo.dao;

import com.skynet.common.SearchParams;
import com.skynet.rimp.schmInfo.vo.SchmQueueInfo;
import com.skynet.rimp.schmInfo.vo.SchmQueueInfoKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchmQueueInfoDao {
    int deleteByPrimaryKey(SchmQueueInfoKey key);
    
    int deleteBySchmId(String schmId);

    int deleteBySchmIds(String[] schmIds);

    int insert(SchmQueueInfo record);
    
    int insertBatch(List<SchmQueueInfo> queueList);

    int insertSelective(SchmQueueInfo record);

    SchmQueueInfo selectByPrimaryKey(SchmQueueInfoKey key);

    int updateByPrimaryKeySelective(SchmQueueInfo record);

    int updateByPrimaryKey(SchmQueueInfo record);
    
    List<SchmQueueInfo> findAll();
    List<SchmQueueInfo> findByCondition(SearchParams params);
}