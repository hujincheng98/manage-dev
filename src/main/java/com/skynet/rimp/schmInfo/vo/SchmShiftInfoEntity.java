package com.skynet.rimp.schmInfo.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>
 * Title: 班次信息
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author wangshen
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
public class SchmShiftInfoEntity {

	private String shiftId;

	private String shiftName;

	@JsonFormat(pattern = "HH:mm", locale = "zh", timezone = "GMT+8")
	private Date shiftStartDate;

	@JsonFormat(pattern = "HH:mm", locale = "zh", timezone = "GMT+8")
	private Date shiftEndDate;

	private String hosId;

	private String orgId;

	private String remarks;
	@JsonIgnore
	private String ext1;

	@JsonIgnore
	private String ext2;

	@JsonIgnore
	private String ext3;

	@JsonIgnore
	private String createUser;

	@JsonIgnore
	private Date createDate;

	@JsonIgnore
	private String updateUser;

	@JsonIgnore
	private Date updateDate;

	private List<SchmTislInfoEntity> tisls = new ArrayList<SchmTislInfoEntity>();

	public SchmShiftInfoEntity() {
		super();
	}

	public String getShiftId() {
		return shiftId;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public Date getShiftStartDate() {
		return shiftStartDate;
	}

	public void setShiftStartDate(Date shiftStartDate) {
		this.shiftStartDate = shiftStartDate;
	}

	public Date getShiftEndDate() {
		return shiftEndDate;
	}

	public void setShiftEndDate(Date shiftEndDate) {
		this.shiftEndDate = shiftEndDate;
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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<SchmTislInfoEntity> getTisls() {
		return tisls;
	}

	public void setTisls(List<SchmTislInfoEntity> tisls) {
		this.tisls = tisls;
	}

}
