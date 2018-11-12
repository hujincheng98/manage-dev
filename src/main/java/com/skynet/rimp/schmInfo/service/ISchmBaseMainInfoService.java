package com.skynet.rimp.schmInfo.service;

import java.util.List;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.schmInfo.vo.SchmBaseMainInfoEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ISchmBaseMainInfoService extends BaseService<SchmBaseMainInfoEntity>
{

	void deleteByKeyArr(String[] schmId) throws Exception;

	boolean checkDocmShiftTime(SchmBaseMainInfoEntity basemain);
	
	SchmBaseMainInfoEntity save(SchmBaseMainInfoEntity vo) throws Exception;
	
	void delete(String schmId) throws Exception;
	
	void update(SchmBaseMainInfoEntity entity) throws Exception;
	
	public List<SchmBaseMainInfoEntity> findByConditionBydel(SearchParams params) throws Exception;

	//导出excel
	public void getExportExcel(HttpServletRequest request, HttpServletResponse response, SearchParams params) throws Exception;

}
