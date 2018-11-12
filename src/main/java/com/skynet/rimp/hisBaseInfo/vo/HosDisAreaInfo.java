package com.skynet.rimp.hisBaseInfo.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class HosDisAreaInfo {
	
    private String disId;

    private String hosId;

    private String disOrgCode;

    private String disName;

    private String disSite;

    private String disPhone;

    private String disState;

    private String remarks;

    private String ext1;

    private String ext2;

    private String ext3;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private String createUser;

    private Date updateDate;

    private String updateUser;
    private String hosName;
    public String getHosName() {
		return hosInfo==null?null:hosInfo.getHosName();
	}

	private HosInfo hosInfo;
    public HosInfo getHosInfo() {
		return hosInfo;
	}

	public void setHosInfo(HosInfo hosInfo) {
		this.hosInfo = hosInfo;
	}

	public String getDisId() {
        return disId;
    }

    public void setDisId(String disId) {
        this.disId = disId == null ? null : disId.trim();
    }

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId == null ? null : hosId.trim();
    }

    public String getDisOrgCode() {
        return disOrgCode;
    }

    public void setDisOrgCode(String disOrgCode) {
        this.disOrgCode = disOrgCode == null ? null : disOrgCode.trim();
    }

    public String getDisName() {
        return disName;
    }

    public void setDisName(String disName) {
        this.disName = disName == null ? null : disName.trim();
    }

    public String getDisSite() {
        return disSite;
    }

    public void setDisSite(String disSite) {
        this.disSite = disSite == null ? null : disSite.trim();
    }

    public String getDisPhone() {
        return disPhone;
    }

    public void setDisPhone(String disPhone) {
        this.disPhone = disPhone == null ? null : disPhone.trim();
    }

    public String getDisState() {
        return disState;
    }

    public void setDisState(String disState) {
        this.disState = disState == null ? null : disState.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
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
        this.createUser = createUser == null ? null : createUser.trim();
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
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}