package com.skynet.rimp.messPush.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: 推送返回消息</p>
 */
public class MessResponseDataDTO implements Serializable {
	
	private static final long serialVersionUID = -7196354280516435336L;

	private String transactionCode;
	
	private String token;
	
	private String hosId;
	
	private List<Object> requestData = new ArrayList<Object>();

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	public List<Object> getRequestData() {
		return requestData;
	}

	public void setRequestData(List<Object> requestData) {
		this.requestData = requestData;
	}

}
