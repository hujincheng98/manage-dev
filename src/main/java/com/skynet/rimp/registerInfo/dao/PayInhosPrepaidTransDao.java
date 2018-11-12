/** 
 * Project Name:rimp_V1.0 
 * File Name:PayInhosPrepaidTransDao.java 
 * Package Name:com.skynet.rimp.registerInfo.dao 
 * Date:2017-6-27下午4:13:39 
 * Copyright (c) 2017 by [天网软件股份有限公司] 
 * 
 */  
package com.skynet.rimp.registerInfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.skynet.common.SearchParams;
import com.skynet.rimp.registerInfo.vo.PayInhosPrepaidTrans;

/**
 * @ClassName:		PayInhosPrepaidTransDao.java
 * @Description:	TODO用一句话描述该文件做什么 
 * @Date:           2017-6-27 下午4:13:39 
 * 
 * @author:			llt
 * @version:		 
 * @since :			JDK 1.7 
 */
@Repository
public interface PayInhosPrepaidTransDao {
	
    List<PayInhosPrepaidTrans> findAll();
    
    List<PayInhosPrepaidTrans> findByCondition(SearchParams params);

}
