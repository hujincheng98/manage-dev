/*
 * @(#) ReqHisErrorCode  2017-08-30 17:55:11
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
package com.skynet.rimp.registerInfo.vo;

import java.io.Serializable;
import java.util.Date;

import org.dom4j.tree.AbstractEntity;
  
/**
 * 
 * <p>Title: 错误日志解决方案详情表</p>
 * <p>Description: TODO 权限基础表Entity层
 *
 * @author llt
 * @version 1.00.00 创建时间：2017-08-31 09:55:11
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 */ 
public class ReqHisErrorCode extends AbstractEntity implements Serializable 
{	
    private static final long serialVersionUID = 1L;
	
    /**
     * 
     */
    private String code;      
    /**
     * 
     */
    private String errorMsg;      
    /**
     * 交易名称
     */
    private String transactionCode;      
    /**
     * 解决方案
     */
    private String resolve;
    
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	public String getResolve() {
		return resolve;
	}
	public void setResolve(String resolve) {
		this.resolve = resolve;
	}      
     
}