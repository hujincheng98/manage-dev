package com.skynet.rimp.hisBaseInfo.vo;

public class HisDeptInfo extends HisDeptInfoKey {
    private String deptName;

    private String parentCode;

    private String parentName;

    private String hospitalAreaName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName == null ? null : parentName.trim();
    }

    public String getHospitalAreaName() {
        return hospitalAreaName;
    }

    public void setHospitalAreaName(String hospitalAreaName) {
        this.hospitalAreaName = hospitalAreaName == null ? null : hospitalAreaName.trim();
    }
}