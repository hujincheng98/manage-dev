package com.skynet.rimp.channelInfo.dao;

import com.skynet.common.SearchParams;
import com.skynet.rimp.channelInfo.vo.OtherHisChannelsInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtherHisChannelsInfoDao {
    int deleteByPrimaryKey(String chHisId);

    int insert(OtherHisChannelsInfo record);

    int insertSelective(OtherHisChannelsInfo record);

    OtherHisChannelsInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OtherHisChannelsInfo record);

    int updateByPrimaryKey(OtherHisChannelsInfo record);
    List<OtherHisChannelsInfo> findAll();

    List<OtherHisChannelsInfo> findByCondition(SearchParams params);
}