/*
 * @(#) ReqHisErrorLogMapper  2017-08-03 15:51:35
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
package com.skynet.rimp.registerInfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.skynet.common.SearchParams;
import com.skynet.rimp.registerInfo.vo.ReqHisErrorLog;

  
/**
 * 
 * <p>Title: 日志表</p>
 * <p>Description: TODO 日志表Mapper层
 *
 * @author llt
 * @version 1.00.00 创建时间：2017-08-02 09:51:35
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 */
@Repository
public interface ReqHisErrorLogDao{
	
	 List<ReqHisErrorLog> findAll();
    
	 List<ReqHisErrorLog> findByCondition(SearchParams params);
	 
	 ReqHisErrorLog findByPrimaryKey(String id);
}