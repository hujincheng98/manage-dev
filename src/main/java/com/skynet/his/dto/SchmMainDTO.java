package com.skynet.his.dto;

import java.io.Serializable;
import java.util.List;
/**
 * <p>Title: 排班信息主表</p>
 * <p>Description: </p>
 *
 * @author wangshen
 * @version 1.00.00
 * @date 2015-7-16 下午6:53:44 
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
public class SchmMainDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String schmId;// 排班编号
	private String schmDate;// 排班日期
	private String docmId;// 医生ID
	private String docmHisCode;//医生his编码
	private String domcName;// 医生名称
	private String deptHisCode;//排班科室HIS编码
	private String schmDeptId;// 排班科室
	private String schmDeptName;// 排班科室名称
	private String schmWeek;// 星期
	private String shiftId;// 班次ID
	private String shiftName;// 班次名称
	private String schmOnWork;// 上班时间
	private String schmOffWork;// 下班时间
	private String schmOnSum;// 线上预约总限数
	private String schmDownSum;// 线下预约总限数
	private String schmOnRegiSum;// 线上挂号总限数
	private String schmDownRegiSum;// 线下挂号总限数
	private String schmRegiOnNum;// 线上已挂号数
	private String schmRegiDownNUm;// 线下已挂号数
	private String schmUpreNum;// 线上已预约数
	private String schmDownreNum;// 线下已预约数
	private String docmPosition;//医生职称
	
	private List<SchmDetailDTO> detailLsit;//排班明细
	
	private List<SchmQueueDTO> queueList; // 排号明细
	private String RegisterId;//挂号类别ID
	private String RegisterHisCode;//挂号类别HIS编码
	private String RegisterClass;//挂号类别名称

	public SchmMainDTO(String schmId, String schmDate, String docmId,
			String domcName,String docmPosition, String schmDeptId,
			String schmDeptName, String schmWeek, String shiftId,
			String shiftName, String schmOnWork, String schmOffWork,
			String schmOnSum, String schmDownSum, String schmOnRegiSum,
			String schmDownRegiSum, String schmRegiOnNum,
			String schmRegiDownNUm, String schmUpreNum, String schmDownreNum,
			String deptHisCode, String docmHisCode, List<SchmDetailDTO> detailLsit, List<SchmQueueDTO> queueList,
			String RegisterId,String RegisterHisCode,String RegisterClass) {
		super();
		this.schmId = schmId;
		this.schmDate = schmDate;
		this.docmId = docmId;
		this.domcName = domcName;
		this.docmPosition = docmPosition;
		this.schmDeptId = schmDeptId;
		this.schmDeptName = schmDeptName;
		this.schmWeek = schmWeek;
		this.shiftId = shiftId;
		this.shiftName = shiftName;
		this.schmOnWork = schmOnWork;
		this.schmOffWork = schmOffWork;
		this.schmOnSum = schmOnSum==null?"0":schmOnSum;
		this.schmDownSum = schmDownSum==null?"0":schmDownSum;
		this.schmOnRegiSum = schmOnRegiSum==null?"0":schmOnRegiSum;
		this.schmDownRegiSum = schmDownRegiSum==null?"0":schmDownRegiSum;
		this.schmRegiOnNum = schmRegiOnNum==null?"0":schmRegiOnNum;
		this.schmRegiDownNUm = schmRegiDownNUm==null?"0":schmRegiDownNUm;
		this.schmUpreNum = schmUpreNum==null?"0":schmUpreNum;
		this.schmDownreNum = schmDownreNum==null?"0":schmDownreNum;
		this.deptHisCode = deptHisCode;
		this.docmHisCode = docmHisCode;
		this.detailLsit = detailLsit;
		this.queueList = queueList;
		this.RegisterId = RegisterId;
		this.RegisterHisCode = RegisterHisCode;
		this.RegisterClass = RegisterClass;
	}
	
	public String getSchmId() {
		return schmId;
	}

	public void setSchmId(String schmId) {
		this.schmId = schmId;
	}

	public String getSchmDate() {
		return schmDate;
	}

	public void setSchmDate(String schmDate) {
		this.schmDate = schmDate;
	}

	public String getDocmId() {
		return docmId;
	}

	public void setDocmId(String docmId) {
		this.docmId = docmId;
	}

	public String getDomcName() {
		return domcName;
	}

	public void setDomcName(String domcName) {
		this.domcName = domcName;
	}

	public String getSchmDeptId() {
		return schmDeptId;
	}

	public void setSchmDeptId(String schmDeptId) {
		this.schmDeptId = schmDeptId;
	}

	public String getSchmDeptName() {
		return schmDeptName;
	}

	public void setSchmDeptName(String schmDeptName) {
		this.schmDeptName = schmDeptName;
	}

	public String getSchmWeek() {
		return schmWeek;
	}

	public void setSchmWeek(String schmWeek) {
		this.schmWeek = schmWeek;
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

	public String getSchmOnWork() {
		return schmOnWork;
	}

	public void setSchmOnWork(String schmOnWork) {
		this.schmOnWork = schmOnWork;
	}

	public String getSchmOffWork() {
		return schmOffWork;
	}

	public void setSchmOffWork(String schmOffWork) {
		this.schmOffWork = schmOffWork;
	}

	public String getSchmOnSum() {
		return schmOnSum;
	}

	public void setSchmOnSum(String schmOnSum) {
		this.schmOnSum = schmOnSum;
	}

	public String getSchmDownSum() {
		return schmDownSum;
	}

	public void setSchmDownSum(String schmDownSum) {
		this.schmDownSum = schmDownSum;
	}

	public String getSchmOnRegiSum() {
		return schmOnRegiSum;
	}

	public void setSchmOnRegiSum(String schmOnRegiSum) {
		this.schmOnRegiSum = schmOnRegiSum;
	}

	public String getSchmDownRegiSum() {
		return schmDownRegiSum;
	}

	public void setSchmDownRegiSum(String schmDownRegiSum) {
		this.schmDownRegiSum = schmDownRegiSum;
	}

	public String getSchmRegiOnNum() {
		return schmRegiOnNum;
	}

	public void setSchmRegiOnNum(String schmRegiOnNum) {
		this.schmRegiOnNum = schmRegiOnNum;
	}

	public String getSchmRegiDownNUm() {
		return schmRegiDownNUm;
	}

	public void setSchmRegiDownNUm(String schmRegiDownNUm) {
		this.schmRegiDownNUm = schmRegiDownNUm;
	}

	public String getSchmUpreNum() {
		return schmUpreNum;
	}

	public void setSchmUpreNum(String schmUpreNum) {
		this.schmUpreNum = schmUpreNum;
	}

	public String getSchmDownreNum() {
		return schmDownreNum;
	}

	public void setSchmDownreNum(String schmDownreNum) {
		this.schmDownreNum = schmDownreNum;
	}

	public String getDocmPosition() {
		return docmPosition;
	}

	public void setDocmPosition(String docmPosition) {
		this.docmPosition = docmPosition;
	}
	
	public String getDeptHisCode() {
		return deptHisCode;
	}
	
	public void setDeptHisCode(String deptHisCode) {
		this.deptHisCode = deptHisCode;
	}
	
	public String getDocmHisCode() {
		return docmHisCode;
	}

	public void setDocmHisCode(String docmHisCode) {
		this.docmHisCode = docmHisCode;
	}
	
	public List<SchmDetailDTO> getDetailLsit() {
		return detailLsit;
	}

	public void setDetailLsit(List<SchmDetailDTO> detailLsit) {
		this.detailLsit = detailLsit;
	}

	public List<SchmQueueDTO> getQueueList() {
		return queueList;
	}

	public void setQueueList(List<SchmQueueDTO> queueList) {
		this.queueList = queueList;
	}

    public String getRegisterId() {
        return RegisterId;
    }

    public void setRegisterId(String registerId) {
        RegisterId = registerId;
    }

    public String getRegisterHisCode() {
        return RegisterHisCode;
    }

    public void setRegisterHisCode(String registerHisCode) {
        RegisterHisCode = registerHisCode;
    }

    public String getRegisterClass() {
        return RegisterClass;
    }

    public void setRegisterClass(String registerClass) {
        RegisterClass = registerClass;
    }
}
