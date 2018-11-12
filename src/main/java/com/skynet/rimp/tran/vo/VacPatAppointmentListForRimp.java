 
package com.skynet.rimp.tran.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * @ClassName:		VacPatAppointmentListForRimp.java
 * @Description:	 用于向云平台返回疫苗接种预约信息
 * @Date:           2017-5-22 下午1:31:53 
 * 
 * @author:			llt
 * @version:		 
 * @since :			JDK 1.7 
 */
public class VacPatAppointmentListForRimp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
     * 患者接种预约列表主键
     */
    private String vpaId;      
    /**
     * 患者ID
     */
    private String userId;      
    /**
     * 患者接种计划主键
     */
    private String vpsId;      
    /**
     * 预约日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date apDate;      
    /**
     * 预约医院
     */
    private String hosId;      
    /**
     * 预约医院名称
     */
    private String hosName;      
    /**
     * 预约状态:0-取消预约，1-已预约
     */
    private String states;      
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createDate;      
    /**
     *取消预约时间 
     */
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date cancleDate;
    /**
     * 疫苗全称
     */
    private String vacName;
    /**
     * 疫苗接种清单
     */
    //private List<VacNormStageList> VacNormStageList;
    /**
     * 接种时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date vacDate;
    /**
     * 患者出生年月
     */
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date userBirthday;
    /**
     * 接种阶段主键
     */
    private String vnsId;
    /**
     * 患者姓名
     */
    private String userName;
    /**
     * 患者身份证
     */
    private String idCard;
    /**
     * 患者电话
     */
    private String userTelephone;
    
	public VacPatAppointmentListForRimp() {
		super();
	}	
	public VacPatAppointmentListForRimp(String vpaId, String userId, String vpsId, Date apDate, String hosId, String hosName, String states,
			Date createDate, Date cancleDate, String vacName, Date vacDate, Date userBirthday, String vnsId, String userName, String idCard,
			String userTelephone) {
		super();
		this.vpaId = vpaId;
		this.userId = userId;
		this.vpsId = vpsId;
		this.apDate = apDate;
		this.hosId = hosId;
		this.hosName = hosName;
		this.states = states;
		this.createDate = createDate;
		this.cancleDate = cancleDate;
		this.vacName = vacName;
		this.vacDate = vacDate;
		this.userBirthday = userBirthday;
		this.vnsId = vnsId;
		this.userName = userName;
		this.idCard = idCard;
		this.userTelephone = userTelephone;
	}
	public String getVpaId() {
		return vpaId;
	}

	public void setVpaId(String vpaId) {
		this.vpaId = vpaId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVpsId() {
		return vpsId;
	}

	public void setVpsId(String vpsId) {
		this.vpsId = vpsId;
	}

	public Date getApDate() {
		return apDate;
	}

	public void setApDate(Date apDate) {
		this.apDate = apDate;
	}

	public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	public String getHosName() {
		return hosName;
	}

	public void setHosName(String hosName) {
		this.hosName = hosName;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCancleDate() {
		return cancleDate;
	}

	public void setCancleDate(Date cancleDate) {
		this.cancleDate = cancleDate;
	}

	public Date getVacDate() {
		return vacDate;
	}

	public void setVacDate(Date vacDate) {
		this.vacDate = vacDate;
	}

	public Date getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}

	public String getVnsId() {
		return vnsId;
	}

	public void setVnsId(String vnsId) {
		this.vnsId = vnsId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getUserTelephone() {
		return userTelephone;
	}

	public void setUserTelephone(String userTelephone) {
		this.userTelephone = userTelephone;
	}

	public String getVacName() {
		return vacName;
	}

	public void setVacName(String vacName) {
		this.vacName = vacName;
	}
	
}
