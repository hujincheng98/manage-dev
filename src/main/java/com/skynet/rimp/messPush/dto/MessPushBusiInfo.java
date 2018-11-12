package com.skynet.rimp.messPush.dto;



public class MessPushBusiInfo {
	
	private String pushUrl;//推送地址
	private String chToken;//渠道token
	private String transactionCode;//消息交易码
	private String pushData;//推送数据
	private String hosId;//医院id
	private String chId;//医院渠道编号
	private String orgId;//医院组织结构编码
	

	/**
	 * @return the pushUrl
	 */
	public String getPushUrl() {
		return pushUrl;
	}
	/**
	 * @param pushUrl the pushUrl to set
	 */
	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}
	/**
	 * @return the chToken
	 */
	public String getChToken() {
		return chToken;
	}
	/**
	 * @param chToken the chToken to set
	 */
	public void setChToken(String chToken) {
		this.chToken = chToken;
	}
	/**
	 * @return the transactionCode
	 */
	public String getTransactionCode() {
		return transactionCode;
	}
	/**
	 * @param transactionCode the transactionCode to set
	 */
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	/**
	 * @return the pushData
	 */
	public String getPushData() {
		return pushData;
	}
	/**
	 * @param pushData the pushData to set
	 */
	public void setPushData(String pushData) {
		this.pushData = pushData;
	}
	/**
	 * @return the hosId
	 */
	public String getHosId() {
		return hosId;
	}
	/**
	 * @param hosId the hosId to set
	 */
	public void setHosId(String hosId) {
		this.hosId = hosId;
	}
	/**
	 * @return the chId
	 */
	public String getChId() {
		return chId;
	}
	/**
	 * @param chId the chId to set
	 */
	public void setChId(String chId) {
		this.chId = chId;
	}
	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}