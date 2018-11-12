package com.skynet.rimp.hisBaseInfo.vo;

import java.util.Date;

/**
 * 
 * @author 胡洋
 *
 */
public class HosAreaInfo {
	// id
	private String areaId;
	@Override
	public String toString() {
		return "HosAreaInfo [areaId=" + areaId + ", areaName=" + areaName
				+ ", areaState=" + areaState + ", areaAddr=" + areaAddr
				+ ", hosId=" + hosId + ", state=" + state + ", remarks="
				+ remarks + ", ext1=" + ext1 + ", ext2=" + ext2 + ", ext3="
				+ ext3 + ", createDate=" + createDate + ", createUser="
				+ createUser + ", updateDate=" + updateDate + ", updateUser="
				+ updateUser + "]";
	}

	// 名称
	private String areaName;
	// 名称
	private String areaState;
	// 地址
	private String areaAddr;
	// 所属医院id
	private String hosId;
	// 院区状态
	private String state;
	// 备注
	private String remarks;
	// 院区业务编码
	private String hosAreaHisCode;
	// 院区照片
	private String hosAreaPhoto;
	
	private String ext1;
	private String ext2;
	private String ext3;

	private Date createDate;
	private String createUser;
	private String orgId;
	private Date updateDate;
	private String updateUser;
	private HosInfo hosInfo;
	public String getHosName(){
		return hosInfo==null?null:hosInfo.getHosName();
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public HosInfo getHosInfo() {
		return hosInfo;
	}

	public void setHosInfo(HosInfo hosInfo) {
		this.hosInfo = hosInfo;
	}

	public String getAreaState() {
		return areaState;
	}

	public void setAreaState(String areaState) {
		this.areaState = areaState;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaAddr() {
		return areaAddr;
	}

	public void setAreaAddr(String areaAddr) {
		this.areaAddr = areaAddr;
	}

	public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getHosAreaHisCode() {
		return hosAreaHisCode;
	}
	public void setHosAreaHisCode(String hosAreaHisCode) {
		this.hosAreaHisCode = hosAreaHisCode;
	}
	public String getHosAreaPhoto() {
		return hosAreaPhoto;
	}
	public void setHosAreaPhoto(String hosAreaPhoto) {
		this.hosAreaPhoto = hosAreaPhoto;
	}

}
