package com.skynet.rimp.hisBaseInfo.vo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skynet.platform.common.util.CacheCoreUtil;
import com.skynet.platform.core.entity.DictionaryEntity;

public class HosInfo {
	
    private String hosId;

    private String hosName;

    private String hosPinyCode;

    private String hosLevel;

    private String hosOrgCode;

    private String hosWebAddr;

    private String hosAddr;

    private String hosTelep;

    private String hosEmail;
    
    private String areaProCode;
    
    private String areaCityCode;
    
    private String areaCountyCode;

    private String hosPropOne;

    private String hosPropTwo;

    private String hosPropTree;

    private Date hosRegiDate;

    private String orgId;

    private String hosState;

    private String remarks;

    private String ext1;

    private String ext2;

    private String ext3;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createDate;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date updateDate;

    private String updateUser;

    private String hosIntro;

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId == null ? null : hosId.trim();
    }

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName == null ? null : hosName.trim();
    }

    public String getHosPinyCode() {
        return hosPinyCode;
    }

    public void setHosPinyCode(String hosPinyCode) {
        this.hosPinyCode = hosPinyCode == null ? null : hosPinyCode.trim();
    }

    public String getHosLevel() {
        return hosLevel;
    }
    
    public String getHosLevelName() {
    	 if (StringUtils.isNotEmpty(getHosLevel()))
         {
             DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getHosLevel());
             if (dictionary != null)
             {
                 return dictionary.getDictName();
             }
         }
         return null;
    }

    public void setHosLevel(String hosLevel) {
        this.hosLevel = hosLevel == null ? null : hosLevel.trim();
    }

    public String getHosOrgCode() {
        return hosOrgCode;
    }

    public void setHosOrgCode(String hosOrgCode) {
        this.hosOrgCode = hosOrgCode == null ? null : hosOrgCode.trim();
    }

    public String getHosWebAddr() {
        return hosWebAddr;
    }

    public void setHosWebAddr(String hosWebAddr) {
        this.hosWebAddr = hosWebAddr == null ? null : hosWebAddr.trim();
    }

    public String getHosAddr() {
        return hosAddr;
    }

    public void setHosAddr(String hosAddr) {
        this.hosAddr = hosAddr == null ? null : hosAddr.trim();
    }

    public String getHosTelep() {
        return hosTelep;
    }

    public void setHosTelep(String hosTelep) {
        this.hosTelep = hosTelep == null ? null : hosTelep.trim();
    }

    public String getHosEmail() {
        return hosEmail;
    }

    public void setHosEmail(String hosEmail) {
        this.hosEmail = hosEmail == null ? null : hosEmail.trim();
    }

    public String getAreaProCode() {
		return areaProCode;
	}

	public void setAreaProCode(String areaProCode) {
		this.areaProCode = areaProCode;
	}

	public String getAreaCityCode() {
		return areaCityCode;
	}

	public void setAreaCityCode(String areaCityCode) {
		this.areaCityCode = areaCityCode;
	}

	public String getAreaCountyCode() {
		return areaCountyCode;
	}

	public void setAreaCountyCode(String areaCountyCode) {
		this.areaCountyCode = areaCountyCode;
	}

	public String getHosPropOne() {
        return hosPropOne;
    }

    public void setHosPropOne(String hosPropOne) {
        this.hosPropOne = hosPropOne == null ? null : hosPropOne.trim();
    }

    public String getHosPropTwo() {
        return hosPropTwo;
    }

    public void setHosPropTwo(String hosPropTwo) {
        this.hosPropTwo = hosPropTwo == null ? null : hosPropTwo.trim();
    }

    public String getHosPropTree() {
        return hosPropTree;
    }

    public void setHosPropTree(String hosPropTree) {
        this.hosPropTree = hosPropTree == null ? null : hosPropTree.trim();
    }

    public Date getHosRegiDate() {
        return hosRegiDate;
    }

    public void setHosRegiDate(Date hosRegiDate) {
        this.hosRegiDate = hosRegiDate;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getHosState() {
        return hosState;
    }

    public void setHosState(String hosState) {
        this.hosState = hosState == null ? null : hosState.trim();
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

    public String getHosIntro() {
        return hosIntro;
    }

    public void setHosIntro(String hosIntro) {
        this.hosIntro = hosIntro == null ? null : hosIntro.trim();
    }
}