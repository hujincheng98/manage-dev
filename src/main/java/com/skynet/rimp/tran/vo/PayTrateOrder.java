package com.skynet.rimp.tran.vo;

/*
 * @(#) PayTrateOrder  2017-06-06 15:44:48
 *
 * Copyright 2003 by TiuWeb Corporation.
 * 51 zhangba six Road, xian, PRC 710065 // Replace with xian’s address
 * 
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * TiuWeb Corporation.  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with TiuWeb.
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * <p>
 * Title: 在线支付订单总表
 * </p>
 * <p>
 * Description: TODO 在线支付订单总表Entity层
 *
 * @author 陈学朋
 * @version 1.00.00 创建时间：2017-06-06 15:44:48
 * 
 *          <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 */
public class PayTrateOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键UUID
	 */
	private String ptoID;
	/**
	 * 渠道token
	 */
	private String chToken;
	/**
	 * 医院ID
	 */
	private String hosID;
	/**
	 * 医院名称(非该表字段)
	 */
	private String hosName;
	/**
	 * 组织机构ID
	 */
	private String orgID;
	/**
	 * 交易订单号
	 */
	private String tradeID;
	/**
	 * HIS交易流水号
	 */
	private String hisTradeID;
	/**
	 * 商户号
	 */
	private String mchID;
	/**
	 * 设备号
	 */
	private String deviceInfo;
	/**
	 * 业务类型：也即商品描述，字典项business_type，1-预约取号；2.当日挂号；3-诊间支付；4-门诊预交金充值；5-住院预交金充值
	 */
	private Integer businessType;
	/**
	 * 支付类型：1-扫码支付，2-刷卡支付，3-公众号小程序支付，4-app支付，5-h5支付
	 */
	private Integer payType;
	/**
	 * 支付方式：1-微信支付；2-支付宝支付；3-银联支付
	 */
	private Integer tradeType;
	/**
	 * 充值金额
	 */
	private BigDecimal tradeFee;
	/**
	 * 授权码：扫码支付微富通返回的授权码，刷卡支付授权码， 设备读取用户展示的条码或者二维码信息
	 */
	private String authCode;
	/**
	 * 授权码链接：扫描支付时，威富通返回
	 */
	private String authImgURL;
	/**
	 * 渠道名称
	 */
	private String chName;
	/**
	 * 渠道名称(非表字段)
	 */
	private String chNa;
	/**
	 * 订单生成时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date tradeTime;
	/**
	 * 订单超时时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date tradeExpireTime;
	/**
	 * 门店编号
	 */
	private String shopID;
	/**
	 * 设备编号：当为刷卡支付时his传入
	 */
	private String deviceID;
	/**
	 * 商品标记
	 */
	private String goodsTag;
	/**
	 * 操作员id：his传入的操作员ID
	 */
	private String operatorID;
	/** 
	 * 交易状态：0-初始状态，1-交易成功，2-支付成功，-1-支付失败，-2-云平台撤销发起，-3-云平台撤销成功，-4-his撤销发起，-5-his撤销成功,-9-"云平台关闭订单成功,-10-云平台关闭订单失败
	 */
	private Integer tradeState;
	/**
	 * 附加信息：his提供的备注
	 */
	private String attach;
	/**
	 * 附加信息：his提供的备注
	 */
	private String attach2;
	/**
	 * 附加信息：his提供的备注
	 */
	private String attach3;
	/**
	 * 支付返回信息：威富通返回
	 */
	private String resultCode;
	/**
	 * 支付返回错误代码：威富通返回
	 */
	private String errCode;
	/**
	 * 支付返回错误描述：威富通返回
	 */
	private String errMsg;
	/**
	 * 支付结果：威富通返回，0—成功；其它—失败
	 */
	private Integer payResult;
	/**
	 * 支付结果信息：威富通返回
	 */
	private String payInfo;
	/**
	 * 随机字符串:威富通返回
	 */
	private String nonceStr;
	/**
	 * 交易撤销时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date tradeCancleTime;
	/**
	 * 通知次数：云平台给his推送通知的次数，满5次不再发送
	 */
	private Integer noticeTime;

	public String getPtoID() {
		return ptoID;
	}

	public void setPtoID(String ptoID) {
		this.ptoID = ptoID;
	}

	public String getChToken() {
		return chToken;
	}

	public void setChToken(String chToken) {
		this.chToken = chToken;
	}

	public String getHosID() {
		return hosID;
	}

	public void setHosID(String hosID) {
		this.hosID = hosID;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getTradeID() {
		return tradeID;
	}

	public void setTradeID(String tradeID) {
		this.tradeID = tradeID;
	}

	public String getHisTradeID() {
		return hisTradeID;
	}

	public void setHisTradeID(String hisTradeID) {
		this.hisTradeID = hisTradeID;
	}

	public String getMchID() {
		return mchID;
	}

	public void setMchID(String mchID) {
		this.mchID = mchID;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public BigDecimal getTradeFee() {
		return tradeFee;
	}

	public void setTradeFee(BigDecimal tradeFee) {
		this.tradeFee = tradeFee;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getAuthImgURL() {
		return authImgURL;
	}

	public void setAuthImgURL(String authImgURL) {
		this.authImgURL = authImgURL;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}
	
	public String getChNa() {
		return chNa;
	}

	public void setChNa(String chNa) {
		this.chNa = chNa;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public Date getTradeExpireTime() {
		return tradeExpireTime;
	}

	public void setTradeExpireTime(Date tradeExpireTime) {
		this.tradeExpireTime = tradeExpireTime;
	}

	public String getShopID() {
		return shopID;
	}

	public void setShopID(String shopID) {
		this.shopID = shopID;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getGoodsTag() {
		return goodsTag;
	}

	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}

	public String getOperatorID() {
		return operatorID;
	}

	public void setOperatorID(String operatorID) {
		this.operatorID = operatorID;
	}

	public Integer getTradeState() {
		return tradeState;
	}

	public void setTradeState(Integer tradeState) {
		this.tradeState = tradeState;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getAttach2() {
		return attach2;
	}

	public void setAttach2(String attach2) {
		this.attach2 = attach2;
	}

	public String getAttach3() {
		return attach3;
	}

	public void setAttach3(String attach3) {
		this.attach3 = attach3;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Integer getPayResult() {
		return payResult;
	}

	public void setPayResult(Integer payResult) {
		this.payResult = payResult;
	}

	public String getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public Date getTradeCancleTime() {
		return tradeCancleTime;
	}

	public void setTradeCancleTime(Date tradeCancleTime) {
		this.tradeCancleTime = tradeCancleTime;
	}

	public Integer getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(Integer noticeTime) {
		this.noticeTime = noticeTime;
	}

	public String getHosName() {
		return hosName;
	}

	public void setHosName(String hosName) {
		this.hosName = hosName;
	}
}