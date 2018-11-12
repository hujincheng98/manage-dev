package com.skynet.rimp.channelInfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.skynet.common.SearchParams;
import com.skynet.rimp.channelInfo.vo.OtherHisDatachneelsInfo;

@Repository
public interface OtherHisDatachneelsInfoDao {
	
    int deleteByPrimaryKey(Integer id);

    int insert(OtherHisDatachneelsInfo record);

    int insertSelective(OtherHisDatachneelsInfo record);

    OtherHisDatachneelsInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OtherHisDatachneelsInfo record);

    int updateByPrimaryKey(OtherHisDatachneelsInfo record);
    
    List<OtherHisDatachneelsInfo> findAll();
    
    List<OtherHisDatachneelsInfo> findByCondition(SearchParams params);
}