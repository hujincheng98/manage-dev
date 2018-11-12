package com.skynet.his.dto;

import java.util.List;

/**
 * 推送排班数据格式
 * 
 * @author wangshen
 * 
 */
public class SchmDTO {
	
	//排班接口交易码
	public final static String T_CODE = "10002";
	
	private String transactionCode;// 交易码
	private String operationType;// 交易类型
	private List<SchmMainDTO> requestData;// 请求数据
	
	public SchmDTO(String operationType, List<SchmMainDTO> requestData) {
		super();
		this.transactionCode = T_CODE;
		this.operationType = operationType;
		this.requestData = requestData;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public List<SchmMainDTO> getRequestData() {
		return requestData;
	}

	public void setRequestData(List<SchmMainDTO> requestData) {
		this.requestData = requestData;
	}

}
