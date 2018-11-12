package com.skynet.rimp.channelInfo.dto;

/**
 * 
 * <p>Title: 科室信息查询服务vo</p>
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
public class HosDeptInfoDTO {
	
    private String deptId;

    private String deptName;

    private String deptPinyCode;

    private String deptAreaId;

    private String deptParentId;

    private String deptTelep;

    private String deptPosi;

    private String deptIntro;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getDeptPinyCode() {
        return deptPinyCode;
    }

    public void setDeptPinyCode(String deptPinyCode) {
        this.deptPinyCode = deptPinyCode == null ? null : deptPinyCode.trim();
    }

    public String getDeptAreaId() {
        return deptAreaId;
    }

    public void setDeptAreaId(String deptAreaId) {
        this.deptAreaId = deptAreaId == null ? null : deptAreaId.trim();
    }

    public String getDeptParentId() {
        return deptParentId;
    }

    public void setDeptParentId(String deptParentId) {
        this.deptParentId = deptParentId == null ? null : deptParentId.trim();
    }

    public String getDeptTelep() {
        return deptTelep;
    }

    public void setDeptTelep(String deptTelep) {
        this.deptTelep = deptTelep == null ? null : deptTelep.trim();
    }

    public String getDeptPosi() {
        return deptPosi;
    }

    public void setDeptPosi(String deptPosi) {
        this.deptPosi = deptPosi == null ? null : deptPosi.trim();
    }

    public String getDeptIntro() {
        return deptIntro;
    }

    public void setDeptIntro(String deptIntro) {
        this.deptIntro = deptIntro == null ? null : deptIntro.trim();
    }
}