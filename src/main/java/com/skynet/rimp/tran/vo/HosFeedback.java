package com.skynet.rimp.tran.vo;

import java.io.Serializable; 
import java.util.Date;


/**
 * 
 * @ClassName:		HosFeedback.java
 * @Description:	TODO 就医反馈题库维护实体层
 * @Date:           2017-04-14 16:30:04
 * @author:		陈学朋
 * @since :			JDK1.7
 */
 
public class HosFeedback  implements Serializable 
{	
    private static final long serialVersionUID = 1L;
	
    /**
     * 
     */
    private String hfid;      
    /**
     * 
     */
    private Integer qid;      
    /**
     * 
     */
    private String question;      
    /**
     * 
     */
    private String option1;      
    /**
     * 
     */
    private String option2;      
    /**
     * 
     */
    private String option3;      
    /**
     * 
     */
    private String option4;      
    /**
     * 
     */
    private String option5;      
    /**
     * 
     */
    private String hfstate; 
    /**
     * 
     */
    private String orgid;
    /**
     * 非表字段
     */
    private String appId;
    
	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	/**
     * 获取 
     */
    public String getHfid()
    {
        return hfid;
    } 
        
    /**
     * 设置 
     */
    public void setHfid(String hfid) 
    {
        this.hfid = hfid;
    }   
    
    /**
     * 获取 
     */
    public Integer getQid()
    {
        return qid;
    } 
        
    /**
     * 设置 
     */
    public void setQid(Integer qid) 
    {
        this.qid = qid;
    }   
    
    /**
     * 获取 
     */
    public String getQuestion()
    {
        return question;
    } 
        
    /**
     * 设置 
     */
    public void setQuestion(String question) 
    {
        this.question = question;
    }   
    
    /**
     * 获取 
     */
    public String getOption1()
    {
        return option1;
    } 
        
    /**
     * 设置 
     */
    public void setOption1(String option1) 
    {
        this.option1 = option1;
    }   
    
    /**
     * 获取 
     */
    public String getOption2()
    {
        return option2;
    } 
        
    /**
     * 设置 
     */
    public void setOption2(String option2) 
    {
        this.option2 = option2;
    }   
    
    /**
     * 获取 
     */
    public String getOption3()
    {
        return option3;
    } 
        
    /**
     * 设置 
     */
    public void setOption3(String option3) 
    {
        this.option3 = option3;
    }   
    
    /**
     * 获取 
     */
    public String getOption4()
    {
        return option4;
    } 
        
    /**
     * 设置 
     */
    public void setOption4(String option4) 
    {
        this.option4 = option4;
    }   
    
    /**
     * 获取 
     */
    public String getOption5()
    {
        return option5;
    } 
        
    /**
     * 设置 
     */
    public void setOption5(String option5) 
    {
        this.option5 = option5;
    }   
    
    /**
     * 获取 
     */
    public String getHfstate()
    {
        return hfstate;
    } 
        
    /**
     * 设置 
     */
    public void setHfstate(String hfstate) 
    {
        this.hfstate = hfstate;
    }

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}   
    
}