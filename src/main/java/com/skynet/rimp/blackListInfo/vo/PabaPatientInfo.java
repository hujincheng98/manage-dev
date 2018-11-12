package com.skynet.rimp.blackListInfo.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skynet.platform.common.util.CacheCoreUtil;
import com.skynet.platform.core.entity.DictionaryEntity;
import com.skynet.util.StringUtil;

public class PabaPatientInfo {
	
	private String pabaPatientId;

	private String pabaPatientName;

	private String pabaPatientCard;

	private String pabaPatientTel;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", locale = "zh", timezone = "GMT+8")
	private Date pabaOffDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", locale = "zh", timezone = "GMT+8")
	private Date pabaOnDate;

	private String pabaOffRemark;
	
	private String hosId;

	private String orgId;

	private String pabaPatientState;

	private String ext1;

	private String ext2;

	private String ext3;

	private Date createDate;

	private String createUser;

	private Date updateDate;

	private String updateUser;
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
	private Date  pabaOffDateTask;

	public String getPabaPatientId() {
		return pabaPatientId;
	}

	public void setPabaPatientId(String pabaPatientId) {
		this.pabaPatientId = pabaPatientId == null ? null : pabaPatientId
				.trim();
	}

	public String getPabaPatientName() {
		return pabaPatientName;
	}

	public void setPabaPatientName(String pabaPatientName) {
		this.pabaPatientName = pabaPatientName == null ? null : pabaPatientName
				.trim();
	}

	public String getPabaPatientCard() {
		return pabaPatientCard;
	}

	public void setPabaPatientCard(String pabaPatientCard) {
		this.pabaPatientCard = pabaPatientCard == null ? null : pabaPatientCard
				.trim();
	}

	public String getPabaPatientTel() {
		return pabaPatientTel;
	}

	public void setPabaPatientTel(String pabaPatientTel) {
		this.pabaPatientTel = pabaPatientTel == null ? null : pabaPatientTel
				.trim();
	}

	public Date getPabaOffDate() {
		return pabaOffDate;
	}

	public void setPabaOffDate(Date pabaOffDate) {
		this.pabaOffDate = pabaOffDate;
	}

	public Date getPabaOnDate() {
		return pabaOnDate;
	}

	public void setPabaOnDate(Date pabaOnDate) {
		this.pabaOnDate = pabaOnDate;
	}

	public String getPabaOffRemark() {
		return pabaOffRemark;
	}

	public void setPabaOffRemark(String pabaOffRemark) {
		this.pabaOffRemark = pabaOffRemark == null ? null : pabaOffRemark
				.trim();
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
		this.orgId = orgId == null ? null : orgId.trim();
	}

	public String getPabaPatientState() {
		return pabaPatientState;
	}

	public void setPabaPatientState(String pabaPatientState) {
		this.pabaPatientState = pabaPatientState == null ? null
				: pabaPatientState.trim();
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1 == null ? null : ext1.trim();
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2 == null ? null : ext2.trim();
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3 == null ? null : ext3.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser == null ? null : createUser.trim();
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser == null ? null : updateUser.trim();
	}
	
	
	//补充字段
	
	/**
	 * 获取黑名单状态名称
	 * @return
	 */
	public String getPabaStateName(){
		if (StringUtil.isNotEmpty(getPabaPatientState())){
	        DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getPabaPatientState());
	        if (dictionary != null){
	            return dictionary.getDictName();
	        }
	    }
	    return null;
	}
	
	public Date getPabaOffDateTask() {
		return pabaOffDateTask;
	}

	public void setPabaOffDateTask(Date pabaOffDateTask) {
		this.pabaOffDateTask = pabaOffDateTask;
	}
	
}