/*
 * @(#) PayClinicRecipeTrans  2017-06-28 11:17:26
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
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.dom4j.tree.AbstractEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skynet.platform.common.util.CacheCoreUtil;
import com.skynet.platform.core.entity.DictionaryEntity;
  
/**
 * 
 * <p>处方缴费交易信息表</p>
 * <p>Description: TODO his日志Entity层
 *
 * @author llt
 * @version 1.00.00 创建时间：2017-06-28 14:17:26
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 */ 
public class PayClinicRecipeTrans extends AbstractEntity implements Serializable 
{	
    private static final long serialVersionUID = 1L;
	
    /**
     * 主键
     */
    private String transId;      
    /**
     * 卡号
     */
    private String cardId;      
    /**
     * 处方号
     */
    private String clinicRecipeId;      
    /**
     * 患者姓名
     */
    private String patientName;      
    /**
     * 患者手机号
     */
    private String telephone;      
    /**
     * 患者身份证号码
     */
    private String identityCard;      
    /**
     * 支付方式
     */
    private String payType;      
    /**
     * 缴费金额
     */
    private BigDecimal money;      
    /**
     * 交易平台流水号（渠道流水号）
     */
    private String serialNumber;      
    /**
     * 支付类型
     */
    private String costType;      
    /**
     * 支付凭证
     */
    private String payVoucher;      
    /**
     * 支付时间
     */
    private String payDate;      
    /**
     * HIS操作员编号
     */
    private String operatorId;      
    /**
     * HIS操作员编号
     */
    private String hisSerialNumber;      
    /**
     * 执行科室
     */
    private String officeName;      
    /**
     * 科室位置
     */
    private String officeAddress;      
    /**
     * 窗口名称
     */
    private String windowName;      
    /**
     * 交易状态
     */
    private String transState;      
    /**
     * 渠道名称
     */
    private String chName;      
    /**
     * 渠道token
     */
    private String chToken;   
    /**
     * 医院id
     */
    private String hosId;      
    /**
     * 组织机构id
     */
    private String orgId;      
    /**
     * 备注
     */
    private String remarks;      
    /**
     * 预留字段1
     */
    private String ext1;      
    /**
     * 预留字段2
     */
    private String ext2;      
    /**
     * 预留字段3
     */
    private String ext3;  
    /**
     *创建时间 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")    
    private Date createDate;
       
	public PayClinicRecipeTrans() {
		super();
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getClinicRecipeId() {
		return clinicRecipeId;
	}
	public void setClinicRecipeId(String clinicRecipeId) {
		this.clinicRecipeId = clinicRecipeId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getPayType() {
		return payType;
	}
	//支付方式
	public String getPayTypeName() {
	    String payType = "";
		if ("1".equals(getPayType())) {
			payType = "微信支付";
		  } else if ("2".equals(getPayType())) {
			payType = "支付宝支付";
		  } else if ("3".equals(getPayType())) {
		  	payType = "银联支付";
		  } else {
			//payType = "未知";
			payType = getPayType().trim();
		  }
	      return payType;
	    }
	public void setPayType(String payType) {
		this.payType = payType == null ? null : payType.trim();
	}

	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	public String getPayVoucher() {
		return payVoucher;
	}
	public void setPayVoucher(String payVoucher) {
		this.payVoucher = payVoucher;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getHisSerialNumber() {
		return hisSerialNumber;
	}
	public void setHisSerialNumber(String hisSerialNumber) {
		this.hisSerialNumber = hisSerialNumber;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}
	public String getWindowName() {
		return windowName;
	}
	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}
	public String getTransState() {
		return transState;
	}
	//获取交易状态   
	public String getTransStateName() {
		if (StringUtils.isNotEmpty(getTransState())) {
			DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getTransState());
			if (dictionary != null) {
				return dictionary.getDictName();
			}
		}
		return null;
	}
	public void setTransState(String transState) {
		this.transState = transState;
	}
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	public String getChToken() {
		return chToken;
	}
	public void setChToken(String chToken) {
		this.chToken = chToken;
	}
	public String gethosId() {
		return hosId;
	}
	public void sethosId(String hosId) {
		this.hosId = hosId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
    
}