package com.skynet.rimp.schmInfo.service;

import java.util.List;
import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.schmInfo.vo.SchmReleLimit;



public interface ISchmReleLimitService extends BaseService<SchmReleLimit>
{
	 public List<SchmReleLimit> findAll() throws Exception;
	 
	 public SchmReleLimit update(SchmReleLimit record);
	 
	 SchmReleLimit getSchmReleLimitByHosId(String hosId);
}