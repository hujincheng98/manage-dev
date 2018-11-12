package com.skynet.rimp.registerInfo.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skynet.platform.common.util.CacheCoreUtil;
import com.skynet.platform.core.entity.DictionaryEntity;

public class PayMedicalCardTransInfo {

    private Integer transId;

    private String transMedicardId;

    private String transMedicardName;

    private String transMedicardIdencard;

    private String transMedicardPhone;

    private String transType;

    private BigDecimal transAmount;

    private BigDecimal medicardBalance;

    private String chName;

    private String chToken;

    private String hosId;

    private String orgId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date transTime;

    private String chTransNum;

    private String hisTransNum;

    private String transState;

    private String remark;

    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public String getTransMedicardId() {
        return transMedicardId;
    }

    public void setTransMedicardId(String transMedicardId) {
        this.transMedicardId = transMedicardId == null ? null : transMedicardId.trim();
    }

    public String getTransMedicardName() {
        return transMedicardName;
    }

    public void setTransMedicardName(String transMedicardName) {
        this.transMedicardName = transMedicardName == null ? null : transMedicardName.trim();
    }

    public String getTransMedicardIdencard() {
        return transMedicardIdencard;
    }

    public void setTransMedicardIdencard(String transMedicardIdencard) {
        this.transMedicardIdencard = transMedicardIdencard == null ? null : transMedicardIdencard.trim();
    }

    public String getTransMedicardPhone() {
        return transMedicardPhone;
    }

    public void setTransMedicardPhone(String transMedicardPhone) {
        this.transMedicardPhone = transMedicardPhone == null ? null : transMedicardPhone.trim();
    }

    public String getTransType() {
        return transType;
    }

    public String getTransTypeName() {
        String transType = "";
        if ("1".equals(getTransType())) {
            transType = "微信支付";
        } else if ("2".equals(getTransType())) {
            transType = "支付宝支付";
        } else if ("3".equals(getTransType())) {
            transType = "银联支付";
        } else if ("4".equals(getTransType())) {
            transType = "现金";
        } else {
            transType = "未知";
        }
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    public BigDecimal getMedicardBalance() {
        return medicardBalance;
    }

    public void setMedicardBalance(BigDecimal medicardBalance) {
        this.medicardBalance = medicardBalance;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName == null ? null : chName.trim();
    }

    public String getChToken() {
        return chToken;
    }

    public void setChToken(String chToken) {
        this.chToken = chToken == null ? null : chToken.trim();
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

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getChTransNum() {
        return chTransNum;
    }

    public void setChTransNum(String chTransNum) {
        this.chTransNum = chTransNum == null ? null : chTransNum.trim();
    }

    public String getHisTransNum() {
        return hisTransNum;
    }

    public void setHisTransNum(String hisTransNum) {
        this.hisTransNum = hisTransNum == null ? null : hisTransNum.trim();
    }

    public String getTransState() {
        return transState;
    }

    public String getTransStateName() {
        if (StringUtils.isNotEmpty(getTransState())) {
            DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getTransState());
            if (dictionary != null) {
                return dictionary.getDictName();
            }
        }
        return null;
    }

    public void setTransState(String transState) {
        this.transState = transState == null ? null : transState.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}