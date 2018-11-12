/*
 * @(#) ApiResAuthMapper  2017-08-07 15:34:25
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
package com.skynet.rimp.channelInfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.skynet.rimp.channelInfo.vo.ApiResAuth;

  
/**
 * 
 * <p>Title: 接口权限表表</p>
 * <p>Description: TODO 日志表Mapper层
 *
 * @author llt
 * @version 1.00.00 创建时间：2017-08-07 10:34:25
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容 
 * 
 */ 
@Repository
public interface ApiResAuthDao{
   
	List<ApiResAuth> findByConditionForAdm(ApiResAuth apiResAuth);
	
	List<ApiResAuth> findByConditionForUser(ApiResAuth apiResAuth);
	
	ApiResAuth findByPrimaryKey(String id);
	
	int insert(ApiResAuth apiResAuth);
	
	int update(ApiResAuth apiResAuth);
	
	List<ApiResAuth> findByApiUrlInParamsForAdm(List<String> apiUrl);
	
	List<ApiResAuth> findByApiUrlInParamsForUser(List<String> apiUrl);

	List<ApiResAuth> findAll();
	
	
	
}