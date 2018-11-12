/*
 * @(#) ApiRes  2017-08-07 15:34:15
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
package com.skynet.rimp.channelInfo.vo; 

import java.io.Serializable;
  
/**
 * 
 * <p>Title: 接口表</p>
 * <p>Description: TODO 日志表Entity层
 *
 * @author llt
 * @version 1.00.00 创建时间：2017-08-07 10:34:15
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 */ 
public class ApiRes implements Serializable 
{	
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String id;
    /**
     * API版本
     */
    private String apiVersion; 
    /**
     * 模块名称
     */
    private String model;    
    /**
     * API名称
     */
    private String apiName;
    /**
     * API连接
     */
    private String apiUrl;
    /**
     * 描述说明
     */
    private String discription; 
    /**
     * 是否启用(0:未启用,1:启用)
     */
    private Integer isEnabled;
    /**
     * 非表字段 是否被选中
     */
    private String checked = "false";
    
	public ApiRes() {
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApiVersion() {
		return apiVersion;
	}
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public Integer getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	} 
}