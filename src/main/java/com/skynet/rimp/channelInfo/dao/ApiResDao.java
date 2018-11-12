/*
 * @(#) ApiResMapper  2017-08-07 15:34:15
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

import com.skynet.rimp.channelInfo.vo.ApiRes;

/**
 * 
 * <p>Title: 接口表</p>
 * <p>Description: TODO 日志表Mapper层
 *
 * @author llt
 * @version 1.00.00 创建时间：2017-08-07 09:34:15
 * <pre>
 * 修改记录: 
 * 版本号    修改人        修改日期       修改内容
 * 
 */ 
@Repository
public interface ApiResDao {
	
	 List<ApiRes> findAll();
	 
	 ApiRes findByPrimaryKey(String id);
   
}