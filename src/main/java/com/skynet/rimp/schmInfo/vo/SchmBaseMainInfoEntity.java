package com.skynet.rimp.schmInfo.vo;

import java.util.Date;
import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.skynet.platform.common.util.CacheCoreUtil;
import com.skynet.platform.core.entity.DictionaryEntity;

public class SchmBaseMainInfoEntity {
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
	private Date schmDate;

	private String docmId;

	private String deptId;

	private String schmDeptId;

	private String schmWeek;

	private String shiftId;
	
	private String schmState;
	
	private String schmStateName;

	private Integer schmRegiSum;

	private Integer schmOnSum;

	private Integer schmDownSum;

	private Integer schmOnRegiSum;

	private Integer schmDownRegiSum;

	private String orgId;

	private String remarks;

	private String ext1;//用作挂号类别ID

	private String ext2;

	private String ext3;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date createDate;

	private String createUser;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date updateDate;

	private String updateUser;
	private String docmName;
	private String docmTitle;
	private String docmTitleName;
	private String deptName;
	private String schmDeptName;
	private String shiftName;
	private String deptHosId;
    private Integer diagnosisInte; //就诊间隔

	private String regcategory;//挂号类别
	private String regCategoryId;//挂号类别ID
	private String servCoding;//挂号类别HIS编码
	
	

	public SchmBaseMainInfoEntity() {
		super();
	}

	private String schmId;

	public String getSchmId() {
		return schmId;
	}

	public void setSchmId(String schmId) {
		this.schmId = schmId;
	}

	public Date getSchmDate() {
		return schmDate;
	}

	public void setSchmDate(Date schmDate) {
		this.schmDate = schmDate;
	}

	public String getDocmId() {
		return docmId;
	}

	public void setDocmId(String docmId) {
		this.docmId = docmId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getSchmDeptId() {
		return schmDeptId;
	}

	public void setSchmDeptId(String schmDeptId) {
		this.schmDeptId = schmDeptId;
	}

	public String getSchmWeek() {
		return schmWeek;
	}

	public void setSchmWeek(String schmWeek) {
		this.schmWeek = schmWeek;
	}

	public String getShiftId() {
		return shiftId;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	public Integer getSchmRegiSum() {
		return schmRegiSum;
	}

	public void setSchmRegiSum(Integer schmRegiSum) {
		this.schmRegiSum = schmRegiSum;
	}

	public Integer getSchmOnSum() {
		return schmOnSum;
	}

	public void setSchmOnSum(Integer schmOnSum) {
		this.schmOnSum = schmOnSum;
	}

	public Integer getSchmDownSum() {
		return schmDownSum;
	}

	public void setSchmDownSum(Integer schmDownSum) {
		this.schmDownSum = schmDownSum;
	}

	public Integer getSchmOnRegiSum() {
		return schmOnRegiSum;
	}

	public void setSchmOnRegiSum(Integer schmOnRegiSum) {
		this.schmOnRegiSum = schmOnRegiSum;
	}

	public Integer getSchmDownRegiSum() {
		return schmDownRegiSum;
	}

	public void setSchmDownRegiSum(Integer schmDownRegiSum) {
		this.schmDownRegiSum = schmDownRegiSum;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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


	public String getDocmName() {
		return docmName;
	}

	public void setDocmName(String docmName) {
		this.docmName = docmName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getSchmDeptName() {
		return schmDeptName;
	}

	public void setSchmDeptName(String schmDeptName) {
		this.schmDeptName = schmDeptName;
	}

	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public String getDocmTitle() {
		return docmTitle;
	}
	
	public void setDocmTitleName(String docmTitleName) {
		this.docmTitleName = docmTitleName;
	}

	public String getDocmTitleName() {
		if (StringUtils.isNotEmpty(getDocmTitle()))
        {
            DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getDocmTitle());
            if (dictionary != null)
            {
                return dictionary.getDictName();
            }
        }
        return null;
	}

	public void setDocmTitle(String docmTitle) {
		this.docmTitle = docmTitle;
	}
	
	public String getDeptHosId() {
		return deptHosId;
	}

	public void setDeptHosId(String deptHosId) {
		this.deptHosId = deptHosId;
	}

	public String getSchmState() {
		return schmState;
	}
	
	public String getSchmStateName() {
		if (StringUtils.isNotEmpty(getSchmState())) {
			DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getSchmState());
			if (dictionary != null) {
				return dictionary.getDictName();
			}
		}
		return null;
	}

	public void setSchmState(String schmState) {
		this.schmState = schmState;
	}
	
	public Integer getDiagnosisInte() {
        return diagnosisInte;
    }

    public void setDiagnosisInte(Integer diagnosisInte) {
        this.diagnosisInte = diagnosisInte;
    }

	public String getRegcategory() {
		return regcategory;
	}

	public void setRegcategory(String regcategory) {
		this.regcategory = regcategory;
	}

	public String getRegCategoryId() {
		return regCategoryId;
	}

	public void setRegCategoryId(String regCategoryId) {
		this.regCategoryId = regCategoryId;
	}

	public String getServCoding() {
		return servCoding;
	}

	public void setServCoding(String servCoding) {
		this.servCoding = servCoding;
	}
}
