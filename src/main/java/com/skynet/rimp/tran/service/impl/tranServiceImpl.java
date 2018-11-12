/** 
 * Project Name:rimpServ 
 * File Name:tranServiceImpl.java 
 * Package Name:com.skynet.service.impl 
 * Date:2017-4-16下午1:16:44 
 * Copyright (c) 2017 by [天网软件股份有限公司] 
 * 
 */
package com.skynet.rimp.tran.service.impl;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.tran.service.TranService;


/**
 * @ClassName: tranServiceImpl.java
 * @Description: TODO用一句话描述该文件做什么
 * @Date: 2017-4-16 下午1:16:44
 * 
 * @author: Administrator
 * @version:
 * @since : JDK 1.7
 */
@Service("tranService")
public class tranServiceImpl implements TranService {

	public String tranShe(String requestJson) {
		return requestJson;
		
}

	@Override
	public Pagination<T> findPageByCondition(SearchParams params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findByCondition(SearchParams params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pagination<T> tran(SearchParams params) {
		// TODO Auto-generated method stub
		return null;
	}
	}
