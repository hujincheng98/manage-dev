package com.skynet.rimp.channelInfo.vo;

import java.util.Date;
import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.skynet.platform.common.util.CacheCoreUtil;
import com.skynet.platform.core.entity.DictionaryEntity;

public class OtherChannelsInfo {
	
    private String chId;

    private String chName;

    private String chUrl;
    
    private String hosId;
    
    private String hosName;
    
    private String chType;

    private String isReservation;

    private String isRegistration;

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date validDate;

    private String chGrade;

    private String token;
    
	private Integer chRegiNum;

    private String chState;

    private String orgId;
    
    private Integer regiPushMark; // 是否推送预约
    
    private Integer schmPushMark; // 是否推送排班
    
    private String remarks;

    private String ext1;

    private String ext2;

    private String ext3;
    
    private String appId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createDate;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date updateDate;

    private String updateUser;

    public String getChId() {
        return chId;
    }

    public void setChId(String chId) {
        this.chId = chId == null ? null : chId.trim();
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName == null ? null : chName.trim();
    }

    public String getChUrl() {
        return chUrl;
    }

    public void setChUrl(String chUrl) {
        this.chUrl = chUrl == null ? null : chUrl.trim();
    }

    public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	public String getChType() {
		return chType;
	}
	
	public String getChTypeName() {
		if (StringUtils.isNotEmpty(getChType())) {
			DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getChType());
			if (dictionary != null) {
				return dictionary.getDictName();
			}
		}
		return null;
	}

	public void setChType(String chType) {
		this.chType = chType;
	}

	public String getIsReservation() {
        return isReservation;
    }
    
    public String getIsReservationName() {
   	 if (StringUtils.isNotEmpty(getIsReservation()))
        {
            DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getIsReservation());
            if (dictionary != null)
            {
                return dictionary.getDictName();
            }
        }
        return null;
   }

    public void setIsReservation(String isReservation) {
        this.isReservation = isReservation == null ? null : isReservation.trim();
    }

    public String getIsRegistration() {
        return isRegistration;
    }
    
	public String getIsRegistrationName() {
		if (StringUtils.isNotEmpty(getIsRegistration())) {
			DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getIsRegistration());
			if (dictionary != null) {
				return dictionary.getDictName();
			}
		}
		return null;
	}

    public void setIsRegistration(String isRegistration) {
        this.isRegistration = isRegistration == null ? null : isRegistration.trim();
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate == null ? null : validDate;
    }

    public String getChGrade() {
        return chGrade;
    }
    
    public String getChGradeName() {
		if (StringUtils.isNotEmpty(getChGrade())) {
			DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getChGrade());
			if (dictionary != null) {
				return dictionary.getDictName();
			}
		}
		return null;
	}

    public void setChGrade(String chGrade) {
        this.chGrade = chGrade == null ? null : chGrade.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getChState() {
        return chState;
    }
    
    public String getChStateName() {
		if (StringUtils.isNotEmpty(getChState())) {
			DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getChState());
			if (dictionary != null) {
				return dictionary.getDictName();
			}
		}
		return null;
	}

    public void setChState(String chState) {
        this.chState = chState == null ? null : chState.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
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
    
    public String getExt1Name() {
    	return "".equals(ext1) || ext1 == null ? "否" : "是";
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

	public void setHosName(String hosName) {
		this.hosName = hosName;
	}
	public Integer getChRegiNum() {
		return chRegiNum;
	}

	public void setChRegiNum(Integer chRegiNum) {
		this.chRegiNum = chRegiNum;
	}

	public Integer getRegiPushMark() {
		return regiPushMark;
	}

	public void setRegiPushMark(Integer regiPushMark) {
		this.regiPushMark = regiPushMark;
	}

	public Integer getSchmPushMark() {
		return schmPushMark;
	}

	public void setSchmPushMark(Integer schmPushMark) {
		this.schmPushMark = schmPushMark;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}