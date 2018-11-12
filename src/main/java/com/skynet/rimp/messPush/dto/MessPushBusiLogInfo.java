package com.skynet.rimp.messPush.dto;



public class MessPushBusiLogInfo {
	private String id;
	private String pushUrl;//推送地址
	private String chToken;//渠道token
	private String pushDate;//推送日期
	private String transactionCode;//消息交易码
	private String transactionName;//消息名称
	private String pushData;//推送数据
	private String pushRespCode;//返回状态
	private String pushRespData;//返回数据信息
	private String hosId;//医院id
	private String chId;//医院渠道编号
	private String orgId;//医院组织结构编码

	public String getId() {
		return id;
	}

	public String getPushUrl() {
		return pushUrl;
	}

	public String getChToken() {
		return chToken;
	}

	public String getPushDate() {
		return pushDate;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public String getPushData() {
		return pushData;
	}

	public String getPushRespCode() {
		return pushRespCode;
	}

	public String getPushRespData() {
		return pushRespData;
	}

	public String getHosId() {
		return hosId;
	}

	public String getChId() {
		return chId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}

	public void setChToken(String chToken) {
		this.chToken = chToken;
	}

	public void setPushDate(String pushDate) {
		this.pushDate = pushDate;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public void setPushData(String pushData) {
		this.pushData = pushData;
	}

	public void setPushRespCode(String pushRespCode) {
		this.pushRespCode = pushRespCode;
	}

	public void setPushRespData(String pushRespData) {
		this.pushRespData = pushRespData;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	public void setChId(String chId) {
		this.chId = chId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}