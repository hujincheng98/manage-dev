package com.skynet.rimp.schmInfo.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>
 * Title: 时段信息
 * </p>
 * <p>
 * Description: 时段信息
 * </p>
 * 
 * @author wangshen
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
public class SchmTislInfoEntity {
	
	private String tislId;

	private String shiftId;

	@JsonFormat(pattern = "HH:mm", locale = "zh", timezone = "GMT+8")
	private Date tislStartDate;

	@JsonFormat(pattern = "HH:mm", locale = "zh", timezone = "GMT+8")
	private Date tislEndDate;

	private Integer tislOnline;

	private Integer tislOffline;

	private String orgId;

	private String remarks;
	@JsonIgnore
	private String ext1;

	@JsonIgnore
	private String ext2;

	@JsonIgnore
	private String ext3;

	@JsonIgnore
	private Date createDate;

	@JsonIgnore
	private String createUser;

	@JsonIgnore
	private Date updateDate;

	@JsonIgnore
	private String updateUser;

	public SchmTislInfoEntity() {
		super();
	}

	public String getTislId() {
		return tislId;
	}

	public void setTislId(String tislId) {
		this.tislId = tislId == null ? null : tislId.trim();
	}

	public String getShiftId() {
		return shiftId;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId == null ? null : shiftId.trim();
	}

	public Date getTislStartDate() {
		return tislStartDate;
	}

	public void setTislStartDate(Date tislStartDate) {
		this.tislStartDate = tislStartDate;
	}

	public Date getTislEndDate() {
		return tislEndDate;
	}

	public void setTislEndDate(Date tislEndDate) {
		this.tislEndDate = tislEndDate;
	}

	public Integer getTislOnline() {
		return tislOnline;
	}

	public void setTislOnline(Integer tislOnline) {
		this.tislOnline = tislOnline;
	}

	public Integer getTislOffline() {
		return tislOffline;
	}

	public void setTislOffline(Integer tislOffline) {
		this.tislOffline = tislOffline;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId == null ? null : orgId.trim();
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
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
}