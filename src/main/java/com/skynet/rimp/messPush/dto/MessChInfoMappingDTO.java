package com.skynet.rimp.messPush.dto;

public class MessChInfoMappingDTO {
	
	private String chId;
    private String hosId;
    private String messCode;
    private String orgId;
    
    public MessChInfoMappingDTO() {

    }

	public MessChInfoMappingDTO(String chId, String hosId, String messCode, String orgId) {
		this.chId = chId;
		this.hosId = hosId;
		this.messCode = messCode;
		this.orgId = orgId;
	}

	public String getChId() {
		return chId;
	}

	public void setChId(String chId) {
		this.chId = chId;
	}

	public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	public String getMessCode() {
		return messCode;
	}

	public void setMessCode(String messCode) {
		this.messCode = messCode;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}