package com.skynet.rimp.schmInfo.vo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skynet.platform.common.util.CacheCoreUtil;
import com.skynet.platform.core.entity.DictionaryEntity;

/**
 * 
 * <p>Title: 排班主表信息实体</p>
 * <p>Description: </p>
 *
 * @author Torlay
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
*
 */
public class SchmMainInfo {
    private String schmId;

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date schmDate;

    private String docmId;

    private String deptId;//所属科室

    private String schmDeptId;//排班科室

    private String schmWeek;

    private String shiftId;

    @JsonFormat(pattern = "HH:mm", locale = "zh", timezone = "GMT+8")
    private Date schmOnWork;

    @JsonFormat(pattern = "HH:mm", locale = "zh", timezone = "GMT+8")
    private Date schmOffWork;

    private Integer schmRegiSum;

    private Integer schmOnSum;

    private Integer schmDownSum;

    private Integer schmOnRegiSum;

    private Integer schmDownRegiSum;

    private Integer schmRegiOnNum;

    private Integer schmRegiDownNum;

    private Integer schmUpreNum;

    private Integer schmDownreNum;

    private Integer schmUpgoNum;

    private Integer schmDowngoNum;
    
	private String hosId;

    private String orgId;

    private String schmState;

    private String remarks;

    private String ext1;//作为挂号类别ID使用

    private String ext2;

    private String ext3;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;
    
	private String deptName;
	
    private String schmDeptName;
    
    private String docmName;
    
	private String schmStateName;
	
    private String shiftName;
    
	private String docmTitle;
	
	private String docmTitleName;

	private String regcategory;//挂号类别
    private String regCategoryId;//挂号类别ID
    private String servCoding;//挂号类别HIS编码
	
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
	private Date startDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
	private Date endDate;
	
	
    private Integer diagnosisInte; //就诊间隔
    
    

    public String getSchmId() {
        return schmId;
    }

    public void setSchmId(String schmId) {
        this.schmId = schmId == null ? null : schmId.trim();
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
        this.docmId = docmId == null ? null : docmId.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getSchmDeptId() {
        return schmDeptId;
    }

    public void setSchmDeptId(String schmDeptId) {
        this.schmDeptId = schmDeptId == null ? null : schmDeptId.trim();
    }

    public String getSchmWeek() {
        return schmWeek;
    }

    public void setSchmWeek(String schmWeek) {
        this.schmWeek = schmWeek == null ? null : schmWeek.trim();
    }

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId == null ? null : shiftId.trim();
    }

    public Date getSchmOnWork() {
        return schmOnWork;
    }

    public void setSchmOnWork(Date schmOnWork) {
        this.schmOnWork = schmOnWork;
    }

    public Date getSchmOffWork() {
        return schmOffWork;
    }

    public void setSchmOffWork(Date schmOffWork) {
        this.schmOffWork = schmOffWork;
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

    public Integer getSchmRegiOnNum() {
        return schmRegiOnNum;
    }

    public void setSchmRegiOnNum(Integer schmRegiOnNum) {
        this.schmRegiOnNum = schmRegiOnNum;
    }

    public Integer getSchmRegiDownNum() {
        return schmRegiDownNum;
    }

    public void setSchmRegiDownNum(Integer schmRegiDownNum) {
        this.schmRegiDownNum = schmRegiDownNum;
    }

    public Integer getSchmUpreNum() {
        return schmUpreNum;
    }

    public void setSchmUpreNum(Integer schmUpreNum) {
        this.schmUpreNum = schmUpreNum;
    }

    public Integer getSchmDownreNum() {
        return schmDownreNum;
    }

    public void setSchmDownreNum(Integer schmDownreNum) {
        this.schmDownreNum = schmDownreNum;
    }

    public Integer getSchmUpgoNum() {
        return schmUpgoNum;
    }

    public void setSchmUpgoNum(Integer schmUpgoNum) {
        this.schmUpgoNum = schmUpgoNum;
    }

    public Integer getSchmDowngoNum() {
        return schmDowngoNum;
    }

    public void setSchmDowngoNum(Integer schmDowngoNum) {
        this.schmDowngoNum = schmDowngoNum;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getSchmState() {
        return schmState;
    }

    public void setSchmState(String schmState) {
        this.schmState = schmState == null ? null : schmState.trim();
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

	public String getDocmName() {
		return docmName;
	}

	public void setDocmName(String docmName) {
		this.docmName = docmName;
	}
	 public String getSchmStateName() {
		 if (StringUtils.isNotEmpty(getSchmState()))
	        {
	            DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getSchmState());
	            if (dictionary != null)
	            {
	                return dictionary.getDictName();
	            }
	        }
	        return null;
		 
	}

	public void setSchmStateName(String schmStateName) {
		this.schmStateName = schmStateName;
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

	public void setDocmTitle(String docmTitle) {
		this.docmTitle = docmTitle;
	}
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public void setDocmTitleName(String docmTitleName) {
		this.docmTitleName = docmTitleName;
	}
	
	 public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}
	
	public Integer getDiagnosisInte() {
        return diagnosisInte;
    }

    public void setDiagnosisInte(Integer diagnosisInte) {
        this.diagnosisInte = diagnosisInte;
    }

    public void setRegcategory(String regcategory) {
        this.regcategory = regcategory;
    }

    public String getRegcategory() {

        return regcategory;
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