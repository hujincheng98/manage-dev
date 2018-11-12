package com.skynet.rimp.channelInfo.dto;

import java.io.Serializable; 
import java.util.List;

import com.skynet.rimp.channelInfo.vo.ApiRes;

/**
 * 
 * <p>Title: 接口权限服务(页面)vo</p>
 * <p>Description: TODO 日志表Impl层
 *
 * @author llt
 * @version 1.00.00 创建时间：2017-08-07 10:34:25
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 */ 
public class ApiResDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 接口id
	 */
	private String resId;
	/**
	 * 渠道id
	 */
    private String chId;
    /**
     * 渠道信息表中对应该渠道的hosId
     */
    private String chaHosId;
    /**
   	 * 渠道key
   	 */
    private String token;
    /**
     * 模块名称
     */
    private String model;
    /**
     * 是否被选中
     */
    private String checked = "false";
    /**
     * 子类接口
     */
    private List<ApiRes> children;
	public String getChId() {
		return chId;
	}
	public void setChId(String chId) {
		this.chId = chId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public List<ApiRes> getChildren() {
		return children;
	}
	public void setChildren(List<ApiRes> children) {
		this.children = children;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getChaHosId() {
		return chaHosId;
	}
	public void setChaHosId(String chaHosId) {
		this.chaHosId = chaHosId;
	}
}
