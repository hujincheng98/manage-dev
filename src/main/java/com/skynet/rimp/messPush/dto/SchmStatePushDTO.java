package com.skynet.rimp.messPush.dto;

import java.io.Serializable;

/**
 * <p>Title: 停诊消息推送DTO</p>
 */
public class SchmStatePushDTO implements Serializable {

	private static final long serialVersionUID = -8072580565596141687L;

	private String schmId;

	public String getSchmId() {
		return schmId;
	}

	public void setSchmId(String schmId) {
		this.schmId = schmId;
	}
}
