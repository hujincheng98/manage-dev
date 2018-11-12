package com.skynet.rimp.tran.vo;

import java.io.Serializable;

/**
 * 
 * @ClassName:		HosFeedback.java
 * @Description:	TODO 反馈渠道信息实体层
 * @Date:           2017-04-31 16:30:04
 * @author:		
 * @since :			JDK1.7
 */
public class OtherChannelsInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	 /**
     * TOKEN
     */
	private String id;
	 /**
     *CH_NAME 
     */
	private String text;
	/**
	 * ORG_ID
	 */
	private String orgId;
	
	public OtherChannelsInfo() {
		super();
	}

	public OtherChannelsInfo(String id, String text, String orgId) {
		super();
		this.id = id;
		this.text = text;
		this.orgId = orgId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
