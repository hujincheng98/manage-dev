package com.skynet.rimp.schmInfo.vo;

/**
 *
 * <p>Title: 自动排版实体</p>
 * <p>Description: 自动排版页面信息，绑定VO</p>
 *
 * @author Torlay
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 *
 *
 */
public class SchmAutoSchmInfoVo {


	private String alldept=""; //全部科室标志
	private String alldocm=""; //所有医生标志
	private String deptid=""; //科室id
	private String docmid=""; //医生id
	private String nextWeek=""; //下一周标志
	private String userdefine=""; //自定义标志
	private String startDate=""; //起始日期
	private String endDate=""; //截止日期
	private String schmState; //基础排班状态
	private String allshift = "";//所有班次标志
	private String shiftId = "";//班次id
	private String allWeek = "";//所有星期
	private String schmWeek = "";//星期

	/**
	 * @return the schmState
	 */
	public String getSchmState() {
		return schmState;
	}
	/**
	 * @param schmState the schmState to set
	 */
	public void setSchmState(String schmState) {
		this.schmState = schmState;
	}

	public String getAlldept() {
		return alldept;
	}
	public void setAlldept(String alldept) {
		this.alldept = alldept;
	}
	public String getAlldocm() {
		return alldocm;
	}
	public void setAlldocm(String alldocm) {
		this.alldocm = alldocm;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDocmid() {
		return docmid;
	}
	public void setDocmid(String docmid) {
		this.docmid = docmid;
	}
	public String getNextWeek() {
		return nextWeek;
	}
	public void setNextWeek(String nextWeek) {
		this.nextWeek = nextWeek;
	}
	public String getUserdefine() {
		return userdefine;
	}
	public void setUserdefine(String userdefine) {
		this.userdefine = userdefine;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAllshift() {
		return allshift;
	}

	public void setAllshift(String allshift) {

		this.allshift = allshift;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	public String getShiftId() {

		return shiftId;
	}

	public String getAllWeek() {
		return allWeek;
	}

	public void setAllWeek(String allWeek) {
		this.allWeek = allWeek;
	}

	public String getSchmWeek() {
		return schmWeek;
	}

	public void setSchmWeek(String schmWeek) {
		this.schmWeek = schmWeek;
	}
}