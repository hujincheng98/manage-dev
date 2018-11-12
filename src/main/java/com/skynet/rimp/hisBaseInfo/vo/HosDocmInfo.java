package com.skynet.rimp.hisBaseInfo.vo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skynet.platform.common.util.CacheCoreUtil;
import com.skynet.platform.core.entity.DictionaryEntity;

/**
 * 
 * <p>Title: 医生信息对应实体</p>
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
public class HosDocmInfo {
	
    private String docmId;

    private String docmName;

    private String docmPinyCode;

    private String docmHisCode;

    private String docmCredType;

    private String docmCredNum;

    private String docmSex;

    private String docmEduc;

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date docmBirthDate;

    private String docmNation;

    private String docmWageNum;

    private String docmPosition;

    private String docmTitle;

    private String docmWorkNature;

    private String docmRoom;

    private Integer docmSort;

    private String docmPhoto;
    
    private String hosId;

    private String orgId;

    private String deptId;
    
    private Integer diagnosisInte;

    private String remarks;

    private String docmState;

    private String ext1;

    private String ext2;

    private String ext3;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;
    
	private String deptName;
	
	private String docmStateName;
	
	private String docmIntro;

	private String docmSpec;
	
	private String docmCredTypeName;
	private String docmEducName;
	private String docmNationName;
	private String docmTitleName;
	
	private String docmWorkNatureName;
	private String deptHosId;
	
	private String docmSexName;

	public String getDocmId() {
        return docmId;
    }

    public void setDocmId(String docmId) {
        this.docmId = docmId == null ? null : docmId.trim();
    }

    public String getDocmName() {
        return docmName;
    }

    public void setDocmName(String docmName) {
        this.docmName = docmName == null ? null : docmName.trim();
    }

    public String getDocmPinyCode() {
        return docmPinyCode;
    }

    public void setDocmPinyCode(String docmPinyCode) {
        this.docmPinyCode = docmPinyCode == null ? null : docmPinyCode.trim();
    }

    public String getDocmHisCode() {
        return docmHisCode;
    }

    public void setDocmHisCode(String docmHisCode) {
        this.docmHisCode = docmHisCode == null ? null : docmHisCode.trim();
    }

    public String getDocmCredType() {
        return docmCredType;
    }

    public void setDocmCredType(String docmCredType) {
        this.docmCredType = docmCredType == null ? null : docmCredType.trim();
    }

    public String getDocmCredNum() {
        return docmCredNum;
    }

    public void setDocmCredNum(String docmCredNum) {
        this.docmCredNum = docmCredNum == null ? null : docmCredNum.trim();
    }

    public String getDocmSex() {
        return docmSex;
    }

    public void setDocmSex(String docmSex) {
        this.docmSex = docmSex == null ? null : docmSex.trim();
    }

    public String getDocmEduc() {
        return docmEduc;
    }

    public void setDocmEduc(String docmEduc) {
        this.docmEduc = docmEduc == null ? null : docmEduc.trim();
    }

    public Date getDocmBirthDate() {
        return docmBirthDate;
    }

    public void setDocmBirthDate(Date docmBirthDate) {
        this.docmBirthDate = docmBirthDate;
    }

    public String getDocmNation() {
        return docmNation;
    }

    public void setDocmNation(String docmNation) {
        this.docmNation = docmNation == null ? null : docmNation.trim();
    }

    public String getDocmWageNum() {
        return docmWageNum;
    }

    public void setDocmWageNum(String docmWageNum) {
        this.docmWageNum = docmWageNum == null ? null : docmWageNum.trim();
    }

    public String getDocmPosition() {
        return docmPosition;
    }

    public void setDocmPosition(String docmPosition) {
        this.docmPosition = docmPosition == null ? null : docmPosition.trim();
    }

    public String getDocmTitle() {
        return docmTitle;
    }

    public void setDocmTitle(String docmTitle) {
        this.docmTitle = docmTitle == null ? null : docmTitle.trim();
    }

    public String getDocmWorkNature() {
        return docmWorkNature;
    }

    public void setDocmWorkNature(String docmWorkNature) {
        this.docmWorkNature = docmWorkNature == null ? null : docmWorkNature.trim();
    }

    public String getDocmRoom() {
        return docmRoom;
    }

    public void setDocmRoom(String docmRoom) {
        this.docmRoom = docmRoom == null ? null : docmRoom.trim();
    }

    public Integer getDocmSort() {
        return docmSort;
    }

    public void setDocmSort(Integer docmSort) {
        this.docmSort = docmSort;
    }

    public String getDocmPhoto() {
        return docmPhoto;
    }

    public void setDocmPhoto(String docmPhoto) {
        this.docmPhoto = docmPhoto == null ? null : docmPhoto.trim();
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

    public Integer getDiagnosisInte() {
        return diagnosisInte;
    }

    public void setDiagnosisInte(Integer diagnosisInte) {
        this.diagnosisInte = diagnosisInte;
    }
    
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getDocmState() {
        return docmState;
    }

    public void setDocmState(String docmState) {
        this.docmState = docmState == null ? null : docmState.trim();
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
	
	 public String getDocmStateName() {
		  if (StringUtils.isNotEmpty(getDocmState()))
	        {
	            DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getDocmState());
	            if (dictionary != null)
	            {
	                return dictionary.getDictName();
	            }
	        }
	        return null;
		 
	}

	public void setDocmStateName(String docmStateName) {
		this.docmStateName = docmStateName;
	}
	
    public String getDocmIntro() {
        return docmIntro;
    }

    public void setDocmIntro(String docmIntro) {
        this.docmIntro = docmIntro == null ? null : docmIntro.trim();
    }

    public String getDocmSpec() {
        return docmSpec;
    }

    public void setDocmSpec(String docmSpec) {
        this.docmSpec = docmSpec == null ? null : docmSpec.trim();
    }
    
    //参数转换
    public String getDocmCredTypeName() {
    	 if (StringUtils.isNotEmpty(getDocmCredType()))
	        {
	            DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getDocmCredType());
	            if (dictionary != null)
	            {
	                return dictionary.getDictName();
	            }
	        }
	        return null;
    	
    	
	}

	public void setDocmCredTypeName(String docmCredTypeName) {
		this.docmCredTypeName = docmCredTypeName;
	}

	public String getDocmEducName() {
		
		if (StringUtils.isNotEmpty(getDocmEduc()))
        {
            DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getDocmEduc());
            if (dictionary != null)
            {
                return dictionary.getDictName();
            }
        }
        return null;
	}

	public void setDocmEducName(String docmEducName) {
		this.docmEducName = docmEducName;
	}

	public String getDocmNationName() {
		if (StringUtils.isNotEmpty(getDocmNation()))
        {
            DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getDocmNation());
            if (dictionary != null)
            {
                return dictionary.getDictName();
            }
        }
        return null;
	}

	public void setDocmNationName(String docmNationName) {
		this.docmNationName = docmNationName;
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
	
	public String getDocmWorkNatureName() {
		
		if (StringUtils.isNotEmpty(getDocmWorkNature()))
        {
            DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getDocmWorkNature());
            if (dictionary != null)
            {
                return dictionary.getDictName();
            }
        }
        return null;
	}

	public void setDocmWorkNatureName(String docmWorkNatureName) {
		this.docmWorkNatureName = docmWorkNatureName;
	}
	
	public String getDeptHosId() {
		return deptHosId;
	}

	public void setDeptHosId(String deptHosId) {
		this.deptHosId = deptHosId;
	}
	
	public String getDocmSexName() {

		if (StringUtils.isNotEmpty(getDocmSex()))
        {
            DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getDocmSex());
            if (dictionary != null)
            {
                return dictionary.getDictName();
            }
        }
        return null;
	}

	public void setDocmSexName(String docmSexName) {
		this.docmSexName = docmSexName;
	}
	
	
}