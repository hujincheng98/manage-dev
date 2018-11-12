package com.skynet.rimp.hisBaseInfo.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skynet.platform.common.util.CacheCoreUtil;
import com.skynet.platform.core.entity.DictionaryEntity;
import com.skynet.util.StringUtil;

public class HosDeptInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 320545967975651749L;

	private String deptId;

	private String deptName;

	private String deptPinyCode;

	private String deptHisCode;

	private String parentDeptId;

	private String deptType;

	private String deptTelep;

	private String deptAttr;

	private String deptDiseArea;

	private String deptHosArea;

	private String deptSort;

	private String deptPosi;

	private String deptIntro;

	private String hosId;

	private String orgId;

	private String deptState;

	private String remarks;

	private String ext1;

	private String ext2;

	private String ext3;

	private Date createDate;

	private String createUser;

	private Date updateDate;

	private String updateUser;
	
	private String deptHosAreaName;
	
	private Integer releDayNum;//放号天数
	
	@JsonFormat(pattern="HH:mm", locale="zh", timezone="GMT+8")
    private Date releDate; // 放号时间
	
	public Integer getReleDayNum() {
		return releDayNum;
	}

	public void setReleDayNum(Integer releDayNum) {
		this.releDayNum = releDayNum;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptPinyCode() {
		return deptPinyCode;
	}

	public void setDeptPinyCode(String deptPinyCode) {
		this.deptPinyCode = deptPinyCode;
	}

	public String getDeptHisCode() {
		return deptHisCode;
	}

	public void setDeptHisCode(String deptHisCode) {
		this.deptHisCode = deptHisCode;
	}

	public String getParentDeptId() {
		return parentDeptId;
	}

	public void setParentDeptId(String parentDeptId) {
		this.parentDeptId = parentDeptId;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getDeptTelep() {
		return deptTelep;
	}

	public void setDeptTelep(String deptTelep) {
		this.deptTelep = deptTelep;
	}

	public String getDeptAttr() {
		return deptAttr;
	}

	public void setDeptAttr(String deptAttr) {
		this.deptAttr = deptAttr;
	}

	public String getDeptDiseArea() {
		return deptDiseArea;
	}

	public void setDeptDiseArea(String deptDiseArea) {
		this.deptDiseArea = deptDiseArea;
	}

	public String getDeptHosArea() {
		return deptHosArea;
	}

	public void setDeptHosArea(String deptHosArea) {
		this.deptHosArea = deptHosArea;
	}

	public String getDeptSort() {
		return deptSort;
	}

	public void setDeptSort(String deptSort) {
		this.deptSort = deptSort;
	}

	public String getDeptPosi() {
		return deptPosi;
	}

	public void setDeptPosi(String deptPosi) {
		this.deptPosi = deptPosi;
	}

	public String getDeptIntro() {
		return deptIntro;
	}

	public void setDeptIntro(String deptIntro) {
		this.deptIntro = deptIntro;
	}

	public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDeptState() {
		return deptState;
	}

	public void setDeptState(String deptState) {
		this.deptState = deptState;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
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
		this.createUser = createUser;
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
		this.updateUser = updateUser;
	}

	public String getDeptHosAreaName() {
		return deptHosAreaName;
	}

	public void setDeptHosAreaName(String deptHosAreaName) {
		this.deptHosAreaName = deptHosAreaName;
	}

	// //////扩展字段
	private boolean isParent;
	private List<HosDeptInfo> children;
	private HosInfo hosInfo;
	private HosAreaInfo hosArea;
	private HosDisAreaInfo hosDisArea;
	
	public HosAreaInfo getHosArea() {
		return hosArea;
	}

	public void setHosArea(HosAreaInfo hosArea) {
		this.hosArea = hosArea;
	}

	public HosDisAreaInfo getHosDisArea() {
		return hosDisArea;
	}

	public void setHosDisArea(HosDisAreaInfo hosDisArea) {
		this.hosDisArea = hosDisArea;
	}

	public HosInfo getHosInfo() {
		return hosInfo;
	}
	public void setHosInfo(HosInfo hosInfo) {
		this.hosInfo = hosInfo;
	}
	
	public String getHosName(){
		if(getHosInfo()==null){
			return "";
		}
		return getHosInfo().getHosName();
	}
	
	public String getHosAreaName(){
		if(getHosArea()==null){
			return "";
		}
		return getHosArea().getAreaName();
	}
	
	public String getHosDisAreaName(){
		if(getHosDisArea()==null){
			return "";
		}
		return getHosDisArea().getDisName();
	}
	
	/**
	 * 获取科别名称
	 * @return
	 */
	public String getDeptTyepName() {
		if (StringUtil.isNotEmpty(getDeptType())){
	        DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getDeptType());
	        if (dictionary != null){
	            return dictionary.getDictName();
	        }
	    }
	    return null;
	}
	/**
	 * 获取状态名称
	 * @return
	 */
	public String getDeptStaName() {
		if (StringUtil.isNotEmpty(getDeptState())){
	        DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getDeptState());
	        if (dictionary != null){
	            return dictionary.getDictName();
	        }
	    }
	    return null;
	}

	public boolean isIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	public List<HosDeptInfo> getChildren() {
		return children;
	}

	public void setChildren(List<HosDeptInfo> children) {
		this.children = children;
	}

	public Date getReleDate() {
		return releDate;
	}

	public void setReleDate(Date releDate) {
		this.releDate = releDate;
	}

}