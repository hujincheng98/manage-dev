package com.skynet.rimp.blackListInfo.service;

import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.blackListInfo.vo.PabaPatientInfo;
/**
 * <p>Title: 黑名单</p>
 * <p>Description: </p>
 *
 * @author wangshen
 * @version 1.00.00
 * @date 2015-7-11 下午3:03:43 
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
public interface IPabaPatientInfoService extends BaseService<PabaPatientInfo>{
	
	int deleteByPrimaryKey(String pabaPatientId);

	int insert(PabaPatientInfo record);

	int insertSelective(PabaPatientInfo record);

	PabaPatientInfo selectByPrimaryKey(String pabaPatientId);

	int updateByPrimaryKeySelective(PabaPatientInfo record);

	int updateByPrimaryKey(PabaPatientInfo record);
	
	public void updateTaskPaba() throws Exception;;
}
