package com.skynet.his.dto;

public class SchmQueueDTO {

	private String schmId; // 排班ID
	
	private Integer queueNum; // 排队号
	
	private String queueDate; // 排队时间
	
	private String queueState; // 排队状态

	public SchmQueueDTO(String schmId, Integer queueNum, String queueDate, String queueState) {
		super();
		this.schmId = schmId;
		this.queueNum = queueNum;
		this.queueDate = queueDate;
		this.queueState = queueState;
	}

	public String getSchmId() {
		return schmId;
	}

	public void setSchmId(String schmId) {
		this.schmId = schmId;
	}

	public Integer getQueueNum() {
		return queueNum;
	}

	public void setQueueNum(Integer queueNum) {
		this.queueNum = queueNum;
	}

	public String getQueueDate() {
		return queueDate;
	}

	public void setQueueDate(String queueDate) {
		this.queueDate = queueDate;
	}

	public String getQueueState() {
		return queueState;
	}

	public void setQueueState(String queueState) {
		this.queueState = queueState;
	}
}
