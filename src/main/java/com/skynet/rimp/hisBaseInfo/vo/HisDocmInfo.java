package com.skynet.rimp.hisBaseInfo.vo;

public class HisDocmInfo extends HisDocmInfoKey {
    private String docmName;

    private String docmSex;

    private String docmNation;

    private String docmDiploma;

    private String docmDuty;

    private String docmRole;

    private String docmDeptCode;

    private String docmDeptName;

    public String getDocmName() {
        return docmName;
    }

    public void setDocmName(String docmName) {
        this.docmName = docmName == null ? null : docmName.trim();
    }

    public String getDocmSex() {
        return docmSex;
    }

    public void setDocmSex(String docmSex) {
        this.docmSex = docmSex == null ? null : docmSex.trim();
    }

    public String getDocmNation() {
        return docmNation;
    }

    public void setDocmNation(String docmNation) {
        this.docmNation = docmNation == null ? null : docmNation.trim();
    }

    public String getDocmDiploma() {
        return docmDiploma;
    }

    public void setDocmDiploma(String docmDiploma) {
        this.docmDiploma = docmDiploma == null ? null : docmDiploma.trim();
    }

    public String getDocmDuty() {
        return docmDuty;
    }

    public void setDocmDuty(String docmDuty) {
        this.docmDuty = docmDuty == null ? null : docmDuty.trim();
    }

    public String getDocmRole() {
        return docmRole;
    }

    public void setDocmRole(String docmRole) {
        this.docmRole = docmRole == null ? null : docmRole.trim();
    }

    public String getDocmDeptCode() {
        return docmDeptCode;
    }

    public void setDocmDeptCode(String docmDeptCode) {
        this.docmDeptCode = docmDeptCode == null ? null : docmDeptCode.trim();
    }

    public String getDocmDeptName() {
        return docmDeptName;
    }

    public void setDocmDeptName(String docmDeptName) {
        this.docmDeptName = docmDeptName == null ? null : docmDeptName.trim();
    }
}