package com.skynet.rimp.channelInfo.dao;

import org.springframework.stereotype.Repository;

import com.skynet.rimp.channelInfo.vo.ReqHisLogInfo;

@Repository
public interface ReqHisLogDao {

    int insertSelective(ReqHisLogInfo record);

}