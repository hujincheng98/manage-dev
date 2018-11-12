package com.skynet.rimp.hisBaseInfo.service;


import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.hisBaseInfo.vo.HosDocmInfo;
/**
 * 
 * <p>Title: 医生管理service</p>
 * <p>Description: </p>
 *
 * @author Torlay
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 *
 */
public interface IHosDocmService extends BaseService<HosDocmInfo> {
	
	public int updateState(HosDocmInfo record) throws Exception;
	
	public int deleteByDocmId(String docmId) throws Exception;
	
	public void save(HosDocmInfo hosDocmInfo);
	
	public void update(HosDocmInfo hosDocmInfo);
	
	HosDocmInfo selectByPrimaryKey(String docmId);
	
	public int deleteByKeyArr(String[] docmIdArr) throws Exception;
}
