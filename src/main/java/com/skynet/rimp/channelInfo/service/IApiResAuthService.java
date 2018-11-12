/*
 * @(#) IApiResAuthService  2017-08-07 15:34:25
 *
 * Copyright 2003 by TiuWeb Corporation.
 * 51 zhangba six Road, xian, PRC 710065 // Replace with xian’s address
 * 
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * TiuWeb Corporation.  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with TiuWeb.
 */
package com.skynet.rimp.channelInfo.service;

import java.util.List;
import java.util.Map;

import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.channelInfo.dto.ApiResDTO;
import com.skynet.rimp.channelInfo.vo.ApiResAuth;
 
/**
 * 
 * <p>Title: 接口权限表</p>
 * <p>Description: TODO 日志表Service层
 *
 * @author llt
 * @version 1.00.00 创建时间：2017-08-07 10:34:25
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 */ 
public interface IApiResAuthService extends BaseService<ApiResAuth> 
{
	
	public void saveApiResAuth (ApiResDTO apiResDTO) throws Exception;
	
    public List<ApiResAuth> findByConditionForAdm(ApiResAuth apiResAuth);
	
	public List<ApiResAuth> findByConditionForUser(ApiResAuth apiResAuth);
	
	public Map<String, String> findApiResAuthAll();
	
	
}
