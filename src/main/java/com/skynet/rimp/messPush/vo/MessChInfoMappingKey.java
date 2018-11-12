package com.skynet.rimp.messPush.vo;

public class MessChInfoMappingKey {
	
    private String messCode;

    private String chId;

    public String getMessCode() {
        return messCode;
    }

    public void setMessCode(String messCode) {
        this.messCode = messCode == null ? null : messCode.trim();
    }

    public String getChId() {
        return chId;
    }

    public void setChId(String chId) {
        this.chId = chId == null ? null : chId.trim();
    }
}