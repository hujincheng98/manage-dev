package com.skynet.rimp.schmInfo.service;

import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.schmInfo.vo.SchmMainInfo;
import com.skynet.rimp.schmInfo.vo.SchmQueueInfo;
import com.skynet.rimp.schmInfo.vo.SchmQueueInfoKey;

import java.util.List;

public interface ISchmQueueInfoService extends BaseService<SchmQueueInfo> {

	int delete(SchmQueueInfoKey schmQueueInfoKey);
	
	int deleteBySchmId(String schmId);

	int deleteBySchmIds(String[] schmId);

	int update(SchmQueueInfo schmQueueInfo);

	List<SchmQueueInfo> updateQueue(SchmMainInfo schmMainInfo);

	List<SchmQueueInfo> insertQueue(SchmMainInfo schmMainInfo);
	
	List<SchmQueueInfo> generete(SchmMainInfo schmMainInfo);
	
    String getQueuePushMark(String hosid);
}
