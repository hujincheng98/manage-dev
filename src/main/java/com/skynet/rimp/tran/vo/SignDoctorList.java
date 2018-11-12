/*
 * @(#) SignDoctorList  2017-05-11 09:21:27
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
package com.skynet.rimp.tran.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
  
/**
 *  
 * @ClassName:		SignDoctorList.java
 * @Description:	签约医生列表Entity层
 * @Date:           2017-5-11 下午5:42:14 
 * 
 * @author:			Administrator
 * @version:		 
 * @since :			JDK 1.7
 */
public class SignDoctorList  implements Serializable 
{	
    private static final long serialVersionUID = 1L;
	
    /**
     * 主键
     */
    private String sdlId;      
    /**
     * 签约ID
     */
    private String sdiId;      
    /**
     * 签约开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date beginDate;      
    /**
     * 签约结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date endDate;      
    /**
     * 签约持续时长
     */
    private Integer signedDay;      
    /**
     * 结束原由
     */
    private Integer endCause;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createDate;
    /**
     * 患者ID
     */
    private String userId;      
    /**
     * 医生ID
     */
    private String docId;      
    /**
     * 医生姓名
     */
    private String docName;      
    /**
     * 医院ID 
     */
    private String hosId;      
    /**
     * 医院ID 
     */
    private String hosName; 
    
    /**
     * 患者姓名
     */
    private String userName;
    /**
     * 证件号
     */
    private String idCard;
    /**
     * 电话
     */
    private String telePhone;
    
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getTelePhone() {
		return telePhone;
	}
	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getHosId() {
		return hosId;
	}
	public void setHosId(String hosId) {
		this.hosId = hosId;
	}
	public String getHosName() {
		return hosName;
	}
	public void setHosName(String hosName) {
		this.hosName = hosName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getSdlId() {
		return sdlId;
	}
	public void setSdlId(String sdlId) {
		this.sdlId = sdlId;
	}
	public String getSdiId() {
		return sdiId;
	}
	public void setSdiId(String sdiId) {
		this.sdiId = sdiId;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getSignedDay() {
		return signedDay;
	}
	public void setSignedDay(Integer signedDay) {
		this.signedDay = signedDay;
	}
	public Integer getEndCause() {
		return endCause;
	}
	public void setEndCause(Integer endCause) {
		this.endCause = endCause;
	}      
    
    
}