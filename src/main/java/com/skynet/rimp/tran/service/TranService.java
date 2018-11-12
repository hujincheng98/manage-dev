/** 
 * Project Name:rimpServ 
 * File Name:TranService.java 
 * Package Name:com.skynet.service 
 * Date:2017-4-16下午1:59:12 
 * Copyright (c) 2017 by [天网软件股份有限公司] 
 * 
 */  
package com.skynet.rimp.tran.service;

import org.apache.poi.ss.formula.functions.T;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.BaseService;

/**
 * @ClassName:		TranService.java
 * @Description:	TODO用一句话描述该文件做什么 
 * @Date:           2017-4-16 下午1:59:12 
 * 
 * @author:			Administrator
 * @version:		 
 * @since :			JDK 1.7 
 */
public interface TranService extends BaseService<T>{ 


	public Pagination<T> tran(SearchParams params);

}
