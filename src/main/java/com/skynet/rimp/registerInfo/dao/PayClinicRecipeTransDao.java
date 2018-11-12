/*
 * @(#) PayClinicRecipeTransMapper  2017-06-28 11:17:26
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

import org.springframework.stereotype.Repository;

import com.skynet.common.SearchParams;
import com.skynet.rimp.registerInfo.vo.PayClinicRecipeTrans;

  
/**
 * 
 * <p>Title: his日志</p>
 * <p>Description: TODO his日志Mapper层
 *
 * @author llt
 * @version 1.00.00 创建时间：2017-06-28 14:45:26
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 */ 
@Repository
public interface PayClinicRecipeTransDao{
	
	 List<PayClinicRecipeTrans> findAll();
	    
	 List<PayClinicRecipeTrans> findByCondition(SearchParams params);
   
}