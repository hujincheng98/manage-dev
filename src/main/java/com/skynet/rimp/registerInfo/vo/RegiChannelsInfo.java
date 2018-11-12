package com.skynet.rimp.registerInfo.vo;

import java.util.Date;
import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.skynet.platform.common.util.CacheCoreUtil;
import com.skynet.platform.core.entity.DictionaryEntity;

public class RegiChannelsInfo {
	
    private String regiId;

    private String bespeakid; // 预约号

    private String bespeakofficeid; // 预约科室名称
    
    private String bespeakdoctorid; // 预约医生名称
    
    private String bespeakchannelscode; // 预约渠道授权码

    private String bespeakchannels; // 预约渠道名称

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date bespeakdate; // 就诊日期

    private String worktype; // 班次名称

    @JsonFormat(pattern = "HH:mm", locale = "zh", timezone = "GMT+8")
    private Date starttime; // 预约起始时间

    @JsonFormat(pattern = "HH:mm", locale = "zh", timezone = "GMT+8")
    private Date endtime; // 预约结束时间

    private String schmId; // 排班编号
    
    private Integer patientType; // 患者类型
    
    private String guarName; // 监护人姓名

    private String patientname; // 患者姓名
    
    private String patientSex; //患者性别 0:表示女，1：表示男

    private String identitycard; // 患者身份证号

    private String telephone; // 患者电话

    private String hosId; // 所属医院
    
    private String hosName; 

    private String registate; // 预约状态

    private String ext1;

    private String ext2;

    private String ext3;

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date regicreatedate;

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date regicandate;

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date regigodate;
    
    private String dataType; // 排班类型
    
    private String queueNum; // 排号数
    
    private String queueDate; // 排号时间

    public String getRegiId() {
        return regiId;
    }

    public void setRegiId(String regiId) {
        this.regiId = regiId == null ? null : regiId.trim();
    }

    public String getBespeakid() {
        return bespeakid;
    }

    public void setBespeakid(String bespeakid) {
        this.bespeakid = bespeakid == null ? null : bespeakid.trim();
    }

    public String getBespeakofficeid() {
        return bespeakofficeid;
    }

    public void setBespeakofficeid(String bespeakofficeid) {
        this.bespeakofficeid = bespeakofficeid == null ? null : bespeakofficeid.trim();
    }

    public String getBespeakdoctorid() {
        return bespeakdoctorid;
    }

    public void setBespeakdoctorid(String bespeakdoctorid) {
        this.bespeakdoctorid = bespeakdoctorid == null ? null : bespeakdoctorid.trim();
    }

    public String getBespeakchannelscode() {
        return bespeakchannelscode;
    }

    public void setBespeakchannelscode(String bespeakchannelscode) {
        this.bespeakchannelscode = bespeakchannelscode == null ? null : bespeakchannelscode.trim();
    }

    public String getBespeakchannels() {
        return bespeakchannels;
    }

    public void setBespeakchannels(String bespeakchannels) {
        this.bespeakchannels = bespeakchannels == null ? null : bespeakchannels.trim();
    }

    public Date getBespeakdate() {
        return bespeakdate;
    }

    public void setBespeakdate(Date bespeakdate) {
        this.bespeakdate = bespeakdate;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype == null ? null : worktype.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getSchmId() {
        return schmId;
    }

    public void setSchmId(String schmId) {
        this.schmId = schmId == null ? null : schmId.trim();
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname == null ? null : patientname.trim();
    }

    public String getIdentitycard() {
        return identitycard;
    }

    public void setIdentitycard(String identitycard) {
        this.identitycard = identitycard == null ? null : identitycard.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
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

	public String getRegistate() {
        return registate;
    }
    
    public String getRegistateName() {
		if (StringUtils.isNotEmpty(getRegistate())) {
			DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getRegistate());
			if (dictionary != null) {
				return dictionary.getDictName();
			}
		}
		return null;
	}

    public void setRegistate(String registate) {
        this.registate = registate == null ? null : registate.trim();
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

    public Date getRegicreatedate() {
        return regicreatedate;
    }

    public void setRegicreatedate(Date regicreatedate) {
        this.regicreatedate = regicreatedate;
    }

    public Date getRegicandate() {
        return regicandate;
    }

    public void setRegicandate(Date regicandate) {
        this.regicandate = regicandate;
    }

    public Date getRegigodate() {
        return regigodate;
    }

    public void setRegigodate(Date regigodate) {
        this.regigodate = regigodate;
    }

	public String getDataType() {
		return "1".equals(dataType) ? "时段信息" : "时间点信息";
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getQueueNum() {
		return queueNum;
	}

	public void setQueueNum(String queueNum) {
		this.queueNum = queueNum;
	}

	public String getQueueDate() {
		return queueDate;
	}

	public void setQueueDate(String queueDate) {
		this.queueDate = queueDate;
	}

	public Integer getPatientType() {
		return patientType;
	}

	public void setPatientType(Integer patientType) {
		this.patientType = patientType;
	}

	public String getGuarName() {
		return guarName;
	}

	public void setGuarName(String guarName) {
		this.guarName = guarName;
	}

	public String getPatientSex() {
		return patientSex;
	}

	public void setPatientSex(String patientSex) {
		this.patientSex = patientSex;
	}

}