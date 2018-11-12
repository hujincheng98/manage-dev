package com.skynet.rimp.hisBaseInfo.vo;

public class HisDeptInfoKey {
    private String hosId;

    private String deptHisCode;

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId == null ? null : hosId.trim();
    }

    public String getDeptHisCode() {
        return deptHisCode;
    }

    public void setDeptHisCode(String deptHisCode) {
        this.deptHisCode = deptHisCode == null ? null : deptHisCode.trim();
    }
}