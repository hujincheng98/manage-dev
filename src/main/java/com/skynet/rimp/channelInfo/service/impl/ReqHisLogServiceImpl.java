package com.skynet.rimp.channelInfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skynet.rimp.channelInfo.dao.ReqHisLogDao;
import com.skynet.rimp.channelInfo.service.IReqHisLogService;
import com.skynet.rimp.channelInfo.vo.ReqHisLogInfo;
/**
 * 请求HIS日志
 * @author wangshen
 *
 */
@Service
public class ReqHisLogServiceImpl implements IReqHisLogService {

	@Autowired
	private ReqHisLogDao reqHisLogDao;
	
	@Override
	public int insert(ReqHisLogInfo logInfo) {
		// TODO Auto-generated method stub
		return reqHisLogDao.insertSelective(logInfo);
	}

}
