package com.skynet.rimp.schmInfo.vo;

public class SchmQueueInfoKey {
	
    protected String schmId;

    protected Integer queueNum;

    public String getSchmId() {
        return schmId;
    }

    public void setSchmId(String schmId) {
        this.schmId = schmId == null ? null : schmId.trim();
    }

    public Integer getQueueNum() {
        return queueNum;
    }

    public void setQueueNum(Integer queueNum) {
        this.queueNum = queueNum;
    }
}