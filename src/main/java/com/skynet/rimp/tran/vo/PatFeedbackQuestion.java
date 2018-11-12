package com.skynet.rimp.tran.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @ClassName:		PatFeedbackQuestion.java
 * @Description:	TODO info实体层
 * @Date:           2017-04-14 16:31:06
 * @author:		陈学朋
 * @since :			JDK1.7
 */
 
public class PatFeedbackQuestion implements Serializable 
{	
    private static final long serialVersionUID = 1L;
	
    /**
     * 
     */
    private String pfsid;      
    /**
     * 
     */
    private String pfid;      
    /**
     * 
     */
    private Integer qid;      
    /**
     * 问题
     */
    private String question;      
    /**
     * 问题选项
     */
    private String option;      
    /**
     *答案
     */
    private String value;      
    
    /**
     * 获取 
     */
    public String getPfsid()
    {
        return pfsid;
    } 
        
    /**
     * 设置 
     */
    public void setPfsid(String pfsid) 
    {
        this.pfsid = pfsid;
    }   
    
    /**
     * 获取 
     */
    public String getPfid()
    {
        return pfid;
    } 
        
    /**
     * 设置 
     */
    public void setPfid(String pfid) 
    {
        this.pfid = pfid;
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
    public String getOption()
    {
        return option;
    } 
        
    /**
     * 设置 
     */
    public void setOption(String option) 
    {
        this.option = option;
    }   
    
    /**
     * 获取 
     */
    public String getValue()
    {
        return value;
    } 
        
    /**
     * 设置 
     */
    public void setValue(String value) 
    {
        this.value = value;
    }   
    
}