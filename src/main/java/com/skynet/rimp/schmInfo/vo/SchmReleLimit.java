package com.skynet.rimp.schmInfo.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SchmReleLimit
{
    private String releId;
    
    private Integer releDayNum;
    
    @JsonFormat(pattern="HH:mm", locale="zh", timezone="GMT+8")
    private Date releDate;
    
    @JsonFormat(pattern="HH:mm", locale="zh", timezone="GMT+8")
    private Date ruleDate;
    
    private String ruleFlag;
    
    @JsonIgnore
    private String hosId;
    
    @JsonIgnore
    private String orgId;
    
    @JsonIgnore
    private String remarks;
    
    @JsonIgnore
    private String ext1;
    
    @JsonIgnore
    private String ext2;
    
    @JsonIgnore
    private String ext3;
    
    @JsonIgnore
    private Date createDate;
    
    @JsonIgnore
    private String createUser;
    
    @JsonIgnore
    private Date updateDate;
    
    @JsonIgnore
    private String updateUser;
    
    public String getReleId()
    {
        return releId;
    }
    
    public Date getRuleDate() {
		return ruleDate;
	}

	public void setRuleDate(Date ruleDate) {
		this.ruleDate = ruleDate;
	}

	public String getRuleFlag() {
		return ruleFlag;
	}

	public void setRuleFlag(String ruleFlag) {
		this.ruleFlag = ruleFlag;
	}

	public void setReleId(String releId)
    {
        this.releId = releId == null ? null : releId.trim();
    }
    
    public Integer getReleDayNum()
    {
        return releDayNum;
    }
    
    public void setReleDayNum(Integer releDayNum)
    {
        this.releDayNum = releDayNum;
    }
    
    public Date getReleDate()
    {
        return releDate;
    }
    
    public void setReleDate(Date releDate)
    {
        this.releDate = releDate;
    }
    
    public String getHosId()
    {
        return hosId;
    }
    
    public void setHosId(String hosId)
    {
        this.hosId = hosId == null ? null : hosId.trim();
    }
    
    public String getOrgId()
    {
        return orgId;
    }
    
    public void setOrgId(String orgId)
    {
        this.orgId = orgId == null ? null : orgId.trim();
    }
    
    public String getRemarks()
    {
        return remarks;
    }
    
    public void setRemarks(String remarks)
    {
        this.remarks = remarks == null ? null : remarks.trim();
    }
    
    public String getExt1()
    {
        return ext1;
    }
    
    public void setExt1(String ext1)
    {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }
    
    public String getExt2()
    {
        return ext2;
    }
    
    public void setExt2(String ext2)
    {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }
    
    public String getExt3()
    {
        return ext3;
    }
    
    public void setExt3(String ext3)
    {
        this.ext3 = ext3 == null ? null : ext3.trim();
    }
    
    public Date getCreateDate()
    {
        return createDate;
    }
    
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }
    
    public String getCreateUser()
    {
        return createUser;
    }
    
    public void setCreateUser(String createUser)
    {
        this.createUser = createUser == null ? null : createUser.trim();
    }
    
    public Date getUpdateDate()
    {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }
    
    public String getUpdateUser()
    {
        return updateUser;
    }
    
    public void setUpdateUser(String updateUser)
    {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}