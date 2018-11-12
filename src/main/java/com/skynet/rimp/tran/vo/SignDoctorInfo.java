/*
 * @(#) SignDoctorInfo  2017-05-11 09:21:05
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
 * @ClassName:		SignDoctorInfo.java
 * @Description:	签约医生信息Entity层 
 * @Date:           2017-5-11 下午5:42:02 
 * 
 * @author:			Administrator
 * @version:		 
 * @since :			JDK 1.7
 */
public class SignDoctorInfo  implements Serializable 
{	
    private static final long serialVersionUID = 1L;
	
    /**
     * 主键
     */
    private String sdiId;      
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
     * 医院名称
     */
    private String hosName;      
    /**
     * 签约状态
     */
    private Integer states;      
    /**
     * 签约时长
     */
    private Integer signedMouth;      
    /**
     * 签约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date beginDate;      
    /**
     * 签约结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date endDate;      
    /**
     * 最后一次欠费中断时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date breakDate;      
    /**
     * 终止签约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date cancelDate;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createDate;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getSdiId() {
		return sdiId;
	}
	public void setSdiId(String sdiId) {
		this.sdiId = sdiId;
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
	public Integer getStates() {
		return states;
	}
	public void setStates(Integer states) {
		this.states = states;
	}
	public Integer getSignedMouth() {
		return signedMouth;
	}
	public void setSignedMouth(Integer signedMouth) {
		this.signedMouth = signedMouth;
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
	public Date getBreakDate() {
		return breakDate;
	}
	public void setBreakDate(Date breakDate) {
		this.breakDate = breakDate;
	}
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}      
    
  
}