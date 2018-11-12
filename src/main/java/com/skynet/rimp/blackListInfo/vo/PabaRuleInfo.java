package com.skynet.rimp.blackListInfo.vo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skynet.platform.common.util.CacheCoreUtil;
import com.skynet.platform.core.entity.DictionaryEntity;

/**
 * 
 * @author huyang
 *
 */
public class PabaRuleInfo {
	
	private String pabaId;

    private String pabaRuleName;

    private String pabaRuleType;

    private String pabaRuleNum;

    private String hosId;
    
    private String hosName;

    private String orgId;

    private String ext1;

    private String ext2;

    private String ext3;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", locale = "zh", timezone = "GMT+8")
    private Date createDate;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", locale = "zh", timezone = "GMT+8")
    private Date updateDate;

    private String updateUser;

    public String getPabaId() {
        return pabaId;
    }

    public void setPabaId(String pabaId) {
        this.pabaId = pabaId == null ? null : pabaId.trim();
    }

    public String getPabaRuleName() {
    	if (StringUtils.isNotEmpty(getPabaRuleType()))
        {
            DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getPabaRuleType());
            if (dictionary != null)
            {
                return dictionary.getDictName();
            }
        }
        return null;
    }

    public void setPabaRuleName(String pabaRuleName) {
        this.pabaRuleName = pabaRuleName == null ? null : pabaRuleName.trim();
    }

    public String getPabaRuleType() {
        return pabaRuleType;
    }

    public void setPabaRuleType(String pabaRuleType) {
        this.pabaRuleType = pabaRuleType == null ? null : pabaRuleType.trim();
    }

    public String getPabaRuleNum() {
        return pabaRuleNum;
    }

    public void setPabaRuleNum(String pabaRuleNum) {
        this.pabaRuleNum = pabaRuleNum == null ? null : pabaRuleNum.trim();
    }

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
		this.hosName = hosName;
	}

	public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
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
