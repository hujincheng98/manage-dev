/*
 * @(#) ReqHisErrorLog  2017-08-03 15:51:35
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

import com.fasterxml.jackson.annotation.JsonFormat;
  
/**
 * 
 * <p>Title: 日志表</p>
 * <p>Description: TODO 日志表Entity层
 *
 * @author llt
 * @version 1.00.00 创建时间：2017-08-02 09:51:35
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 */ 
public class ReqHisErrorLog extends AbstractEntity implements Serializable 
{	
    private static final long serialVersionUID = 1L;
	
    /**
     * 主键
     */
    private String id; 
    /**
     * 渠道名称(渠道token)
     */
    private String chName;      
    /**
     * 渠道名称(非表字段)
     */
    private String chnnelsName;      
    /**
     * 医院编号
     */
    private String hosId;      
    /**
     * 医院名称(非标字段)
     */
    private String hosName;      
    /**
     * 机构编号
     */
    private String orgId;   
    /**
     * 机构编号(非表字段)
     */
    private String oId;      
    /**
     * 请求地址
     */
    private String reqUrl;      
    /**
     * 请求时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date reqDate;      
    /**
     * 交易名称
     */
    private String transactionCode;      
    /**
     * 交易码
     */
    private String operationType;      
    /**
     * 请求数据
     */
    private String requestData;      
    /**
     * 返回编号
     */
    private String respCode;      
    /**
     * 返回数据
     */
    private String respData;
    
    /**
     * 错误日志消息(非表字段)
     */
    private String errorMsg;
    /**
     * 请求时长
     */
    private String consuming;
    /**
     * 解决方案
     */
    private String resolve;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	public String getHosId() {
		return hosId;
	}
	public void setHosId(String hosId) {
		this.hosId = hosId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getReqUrl() {
		return reqUrl;
	}
	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}
	public Date getReqDate() {
		return reqDate;
	}
	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getRequestData() {
		return requestData;
	}
	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespData() {
		return respData;
	}
	public void setRespData(String respData) {
		this.respData = respData;
	}
	public String getHosName() {
		return hosName;
	}
	public void setHosName(String hosName) {
		this.hosName = hosName;
	}
	public String getoId() {
		return oId;
	}
	public void setoId(String oId) {
		this.oId = oId;
	}
	public String getChnnelsName() {
		return chnnelsName;
	}
	public void setChnnelsName(String chnnelsName) {
		this.chnnelsName = chnnelsName;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getConsuming() {
		return consuming;
	}
	public void setConsuming(String consuming) {
		this.consuming = consuming;
	}
	public String getResolve() {
		return resolve;
	}
	public void setResolve(String resolve) {
		this.resolve = resolve;
	}
	
}