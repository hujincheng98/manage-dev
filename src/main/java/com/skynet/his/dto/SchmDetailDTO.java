package com.skynet.his.dto;

/**
 * <p>
 * Title: 排班时段信息
 * </p>
 * <p>
 * Description: 
 * </p>
 * 
 * @author wangshen
 * @version 1.00.00
 * @date 2015-7-17 上午9:15:47
 * 
 *       <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
public class SchmDetailDTO {

	private String schmDetailId; // ID
	private String schmOnNum; // 线上预约限数
	private String schmDownNum;// 线下预约限数
	private String schmId;// 排班主表ID
	private String shiftId;// 班次ID
	private String tislStartDate;// 时段开始时间
	private String tislEndDate;// 时段终止时间
	private String detailUpreNum;// 线上已预约数
	private String detailDownUpreNum;// 线下已预约数
	
	
	public SchmDetailDTO(String schmDetailId, String schmOnNum,
			String schmDownNum, String schmId, String shiftId,
			String tislStartDate, String tislEndDate, String detailUpreNum,
			String detailDownUpreNum) {
		super();
		this.schmDetailId = schmDetailId;
		this.schmOnNum = schmOnNum==null?"0":schmOnNum;
		this.schmDownNum = schmDownNum==null?"0":schmDownNum;
		this.schmId = schmId;
		this.shiftId = shiftId;
		this.tislStartDate = tislStartDate;
		this.tislEndDate = tislEndDate;
		this.detailUpreNum = detailUpreNum==null?"0":detailUpreNum;
		this.detailDownUpreNum = detailDownUpreNum==null?"0":detailDownUpreNum;
	}

	public String getSchmDetailId() {
		return schmDetailId;
	}

	public void setSchmDetailId(String schmDetailId) {
		this.schmDetailId = schmDetailId;
	}

	public String getSchmOnNum() {
		return schmOnNum;
	}

	public void setSchmOnNum(String schmOnNum) {
		this.schmOnNum = schmOnNum;
	}

	public String getSchmDownNum() {
		return schmDownNum;
	}

	public void setSchmDownNum(String schmDownNum) {
		this.schmDownNum = schmDownNum;
	}

	public String getSchmId() {
		return schmId;
	}

	public void setSchmId(String schmId) {
		this.schmId = schmId;
	}

	public String getShiftId() {
		return shiftId;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	public String getTislStartDate() {
		return tislStartDate;
	}

	public void setTislStartDate(String tislStartDate) {
		this.tislStartDate = tislStartDate;
	}

	public String getTislEndDate() {
		return tislEndDate;
	}

	public void setTislEndDate(String tislEndDate) {
		this.tislEndDate = tislEndDate;
	}

	public String getDetailUpreNum() {
		return detailUpreNum;
	}

	public void setDetailUpreNum(String detailUpreNum) {
		this.detailUpreNum = detailUpreNum;
	}

	public String getDetailDownUpreNum() {
		return detailDownUpreNum;
	}

	public void setDetailDownUpreNum(String detailDownUpreNum) {
		this.detailDownUpreNum = detailDownUpreNum;
	}

}
