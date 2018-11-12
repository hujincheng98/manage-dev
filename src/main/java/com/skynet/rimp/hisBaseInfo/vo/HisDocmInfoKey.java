package com.skynet.rimp.hisBaseInfo.vo;

public class HisDocmInfoKey {
    private String hosId;

    private String docmHisCode;

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId == null ? null : hosId.trim();
    }

    public String getDocmHisCode() {
        return docmHisCode;
    }

    public void setDocmHisCode(String docmHisCode) {
        this.docmHisCode = docmHisCode == null ? null : docmHisCode.trim();
    }
}