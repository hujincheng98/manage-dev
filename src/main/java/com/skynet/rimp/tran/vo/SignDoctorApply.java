/*
 * @(#) SignDoctorApply  2017-05-11 09:19:14
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
 * @ClassName:		SignDoctorApply.java
 * @Description:	家庭医生签约申请Entity层 
 * @Date:           2017-5-11 下午5:41:48 
 * @author:			Administrator
 * @version:		 
 * @since :			JDK 1.7
 */
public class SignDoctorApply implements Serializable 
{	
    private static final long serialVersionUID = 1L;
	
    /**
     * 主键
     */
    private String sadId;      
    /**
     * 患者ID
     */
    private String userId;      
    /**
     * 申请医生ID
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
     * 申请状态
     */
    private Integer states;      
    /**
     * 签约时长
     */
    private Integer signedMouth;      
    /**
     * 签约时间
     */
    private Date signedDate;      
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date aduitDate;      
    /**
     * 取消申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date cancleDate;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createDate;
    /**
     * 签约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date signedTime;
    /**
     * 患者姓名
     */
    private String userName;
    /**
     * 患者证件号
     */
    private String idCard;
    /**
     * 患者电话
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
	public Date getSignedTime() {
		return signedTime;
	}
	public void setSignedTime(Date signedTime) {
		this.signedTime = signedTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getSadId() {
		return sadId;
	}
	public void setSadId(String sadId) {
		this.sadId = sadId;
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
	public Date getSignedDate() {
		return signedDate;
	}
	public void setSignedDate(Date signedDate) {
		this.signedDate = signedDate;
	}
	public Date getAduitDate() {
		return aduitDate;
	}
	public void setAduitDate(Date aduitDate) {
		this.aduitDate = aduitDate;
	}
	public Date getCancleDate() {
		return cancleDate;
	}
	public void setCancleDate(Date cancleDate) {
		this.cancleDate = cancleDate;
	}      
    
    
}