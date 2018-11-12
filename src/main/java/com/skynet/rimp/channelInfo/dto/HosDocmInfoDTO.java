package com.skynet.rimp.channelInfo.dto;

/**
 * 
 * <p>Title: 医生信息查询服务vo</p>
 * <p>Description: </p>
 *
 * @author lxr
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 *
 */

public class HosDocmInfoDTO {

    private String docmId;

    private String docmName;

    private String docmPinyCode;
    
    private String deptId;

    private String docmBirthDate;

    private String docmTelep;

    private String docmPosition;

    private String docmIntro;

    private String docmSpec;

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

    public String getDocmBirthDate() {
        return docmBirthDate;
    }

    public void setDocmBirthDate(String docmBirthDate) {
        this.docmBirthDate = docmBirthDate == null ? null : docmBirthDate.trim();
    }

    public String getDocmTelep() {
        return docmTelep;
    }

    public void setDocmTelep(String docmTelep) {
        this.docmTelep = docmTelep == null ? null : docmTelep.trim();
    }

    public String getDocmPosition() {
        return docmPosition;
    }

    public void setDocmPosition(String docmPosition) {
        this.docmPosition = docmPosition == null ? null : docmPosition.trim();
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

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
}