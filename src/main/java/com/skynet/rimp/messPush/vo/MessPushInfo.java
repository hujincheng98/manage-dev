package com.skynet.rimp.messPush.vo;

import java.util.Date;

public class MessPushInfo {
	
    private String messCode;

    private String messName;

    private String messState;

    private String ext1;

    private String ext2;

    private String ext3;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    public String getMessCode() {
        return messCode;
    }

    public void setMessCode(String messCode) {
        this.messCode = messCode == null ? null : messCode.trim();
    }

    public String getMessName() {
        return messName;
    }

    public void setMessName(String messName) {
        this.messName = messName == null ? null : messName.trim();
    }

    public String getMessState() {
		return messState;
	}

	public void setMessState(String messState) {
		this.messState = messState;
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