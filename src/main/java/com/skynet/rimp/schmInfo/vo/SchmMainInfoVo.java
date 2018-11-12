package com.skynet.rimp.schmInfo.vo;

import java.util.List;

/**
 * 
 * <p>Title: 医生排班接收页面传递vo</p>
 * <p>Description: </p>
 *
 * @author Torlay
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
*
 */
public class SchmMainInfoVo
{

	private String schmId;
	
    private String schmDate;
    
    private String schmWeek;
    
    private String docmId;
    
    private String docmTitle;
    
    private String deptName;
    
    private String schmDeptName;
    
    private String shiftId;
    
    private String shiftStartDate;
    
    private String shiftEndDate;
    
    private String schmOnRegiSum;
    
    private String schmDownRegiSum;
    
    private String schmOnSum;
    
    private String schmDownSum;
    
    private String docmIdtocontext;
    
    private String deptIdtocontext;
    
    private String schmDeptIDtocontext;
    
	private String hosId;
    
    private List<SchmDetailInfo> details;

    private String regcategoryId;//挂号类别ID
    private String regcategory;//挂号类别
    private String servcoding;//挂号类别HIS编码
    
    public SchmMainInfoVo()
    {
        super();
    }
    
    public String getSchmId() {
		return schmId;
	}

	public void setSchmId(String schmId) {
		this.schmId = schmId;
	}

    public String getSchmDate()
    {
        return schmDate;
    }
    
    public void setSchmDate(String schmDate)
    {
        this.schmDate = schmDate;
    }
    
    public String getSchmWeek()
    {
        return schmWeek;
    }
    
    public void setSchmWeek(String schmWeek)
    {
        this.schmWeek = schmWeek;
    }
    
    public String getDocmId()
    {
        return docmId;
    }
    
    public void setDocmId(String docmId)
    {
        this.docmId = docmId;
    }
    
    public String getDocmTitle()
    {
        return docmTitle;
    }
    
    public void setDocmTitle(String docmTitle)
    {
        this.docmTitle = docmTitle;
    }
    
    public String getDeptName()
    {
        return deptName;
    }
    
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }
    
    public String getSchmDeptName()
    {
        return schmDeptName;
    }
    
    public void setSchmDeptName(String schmDeptName)
    {
        this.schmDeptName = schmDeptName;
    }
    
    public String getShiftId()
    {
        return shiftId;
    }
    
    public void setShiftId(String shiftId)
    {
        this.shiftId = shiftId;
    }
    
    public String getShiftStartDate()
    {
        return shiftStartDate;
    }
    
    public void setShiftStartDate(String shiftStartDate)
    {
        this.shiftStartDate = shiftStartDate;
    }
    
    public String getShiftEndDate()
    {
        return shiftEndDate;
    }
    
    public void setShiftEndDate(String shiftEndDate)
    {
        this.shiftEndDate = shiftEndDate;
    }
    
    public String getSchmOnRegiSum()
    {
        return schmOnRegiSum;
    }
    
    public void setSchmOnRegiSum(String schmOnRegiSum)
    {
        this.schmOnRegiSum = schmOnRegiSum;
    }
    
    public String getSchmDownRegiSum()
    {
        return schmDownRegiSum;
    }
    
    public void setSchmDownRegiSum(String schmDownRegiSum)
    {
        this.schmDownRegiSum = schmDownRegiSum;
    }
    
    public String getSchmOnSum()
    {
        return schmOnSum;
    }
    
    public void setSchmOnSum(String schmOnSum)
    {
        this.schmOnSum = schmOnSum;
    }
    
    public String getSchmDownSum()
    {
        return schmDownSum;
    }
    
    public void setSchmDownSum(String schmDownSum)
    {
        this.schmDownSum = schmDownSum;
    }
    
    public String getDocmIdtocontext()
    {
        return docmIdtocontext;
    }
    
    public void setDocmIdtocontext(String docmIdtocontext)
    {
        this.docmIdtocontext = docmIdtocontext;
    }
    
    public String getDeptIdtocontext()
    {
        return deptIdtocontext;
    }
    
    public void setDeptIdtocontext(String deptIdtocontext)
    {
        this.deptIdtocontext = deptIdtocontext;
    }
    
    public String getSchmDeptIDtocontext()
    {
        return schmDeptIDtocontext;
    }
    
    public void setSchmDeptIDtocontext(String schmDeptIDtocontext)
    {
        this.schmDeptIDtocontext = schmDeptIDtocontext;
    }
    
    public List<SchmDetailInfo> getDetails()
    {
        return details;
    }
    
    public void setDetails(List<SchmDetailInfo> details)
    {
        this.details = details;
    }
    public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}


    public String getRegcategoryId() {
        return regcategoryId;
    }

    public void setRegcategoryId(String regcategoryId) {
        this.regcategoryId = regcategoryId;
    }

    public String getRegcategory() {
        return regcategory;
    }

    public void setRegcategory(String regcategory) {
        this.regcategory = regcategory;
    }

    public String getServcoding() {
        return servcoding;
    }

    public void setServcoding(String servcoding) {
        this.servcoding = servcoding;
    }
}