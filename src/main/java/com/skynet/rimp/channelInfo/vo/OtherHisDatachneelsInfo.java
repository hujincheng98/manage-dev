package com.skynet.rimp.channelInfo.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OtherHisDatachneelsInfo {
    private Integer id;

    private String chHisName;

    private String chHisUrl;

    private String chHisToken;

    private Integer schmSynchNumday;

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date schmSynchDate;

    private String hosId;
    
    private String hosName;

    private String orgId;

    private String chState;

    private String remarks;

    private String ext1;

    private String ext2;

    private String ext3;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChHisName() {
        return chHisName;
    }

    public void setChHisName(String chHisName) {
        this.chHisName = chHisName == null ? null : chHisName.trim();
    }

    public String getChHisUrl() {
        return chHisUrl;
    }

    public void setChHisUrl(String chHisUrl) {
        this.chHisUrl = chHisUrl == null ? null : chHisUrl.trim();
    }

    public String getChHisToken() {
        return chHisToken;
    }

    public void setChHisToken(String chHisToken) {
        this.chHisToken = chHisToken == null ? null : chHisToken.trim();
    }

    public Integer getSchmSynchNumday() {
        return schmSynchNumday;
    }

    public void setSchmSynchNumday(Integer schmSynchNumday) {
        this.schmSynchNumday = schmSynchNumday;
    }

    public Date getSchmSynchDate() {
        return schmSynchDate;
    }

    public void setSchmSynchDate(Date schmSynchDate) {
        this.schmSynchDate = schmSynchDate;
    }

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId == null ? null : hosId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getChState() {
        return chState;
    }

    public void setChState(String chState) {
        this.chState = chState == null ? null : chState.trim();
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

	public String getHosName() {
		return hosName;
	}
}