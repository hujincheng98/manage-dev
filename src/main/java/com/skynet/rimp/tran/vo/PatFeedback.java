package com.skynet.rimp.tran.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 
 * @ClassName:		PatFeedback.java
 * @Description:	TODO 就医反馈实体层
 * @Date:           2017-04-14 16:28:58
 * @author:		陈学朋
 * @since :			JDK1.7
 */
 
public class PatFeedback  implements Serializable 
{	
    private static final long serialVersionUID = 1L;
	
    /**
     * 
     */
    private String pfid;      
    /**
     * 
     */
    private String hosid;      
    /**
     * 渠道id
     */
    private String chid;      
    /**
     * 患者姓名
     */
    private String patientname;      
    /**
     * 电话
     */
    private String telephone;      
    /**
     * 科室
     */
    private String department;      
    /**
     *就医反馈
     */
    private String content;      
    /**
     * 回复内容
     */
    private String reply;      
    /**
     * 回复时间
     */
    private Date replydate;      
    /**
     * 回复人
     */
    private String replyuser;      
    /**
     * 是否回复，0：未回复，1:已回复
     */
    private Integer isreply;
    /**
     * 标题
     */
    private String title;
    /**
     * 反馈类型
     * /adtype_01:咨询,adtype_02:建议,adtype_03:投诉,adtype_04:其他
     */
    private String fbtype;
    
    /**
     * 反馈渠道名称
     */
    private String chName;
    /**
     * 创建时间
     */
    private Date createdate;
    public String getFbtype() {
		return fbtype;
	}

	public void setFbtype(String fbtype) {
		this.fbtype = fbtype;
	}

	public String getTitle() {
		return title;
	}
    /**
     * 回复状态
     */
    private String status;
    
    private String orgid;   

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private List<PatFeedbackQuestion> patFeedbackQuestion;
    
    
    public List<PatFeedbackQuestion> getPatFeedbackQuestion() {
		return patFeedbackQuestion;
	}

	public void setPatFeedbackQuestion(List<PatFeedbackQuestion> patFeedbackQuestion) {
		this.patFeedbackQuestion = patFeedbackQuestion;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
    public String getHosid()
    {
        return hosid;
    } 
        
    /**
     * 设置 
     */
    public void setHosid(String hosid) 
    {
        this.hosid = hosid;
    }   
    
    /**
     * 获取 
     */
    public String getChid()
    {
        return chid;
    } 
        
    /**
     * 设置 
     */
    public void setChid(String chid) 
    {
        this.chid = chid;
    }   
    
    /**
     * 获取 
     */
    public String getPatientname()
    {
        return patientname;
    } 
        
    /**
     * 设置 
     */
    public void setPatientname(String patientname) 
    {
        this.patientname = patientname;
    }   
    
    /**
     * 获取 
     */
    public String getTelephone()
    {
        return telephone;
    } 
        
    /**
     * 设置 
     */
    public void setTelephone(String telephone) 
    {
        this.telephone = telephone;
    }   
    
    /**
     * 获取 
     */
    public String getDepartment()
    {
        return department;
    } 
        
    /**
     * 设置 
     */
    public void setDepartment(String department) 
    {
        this.department = department;
    }   
    
    /**
     * 获取 
     */
    public String getContent()
    {
        return content;
    } 
        
    /**
     * 设置 
     */
    public void setContent(String content) 
    {
        this.content = content;
    }   
    
    /**
     * 获取 
     */
    public String getReply()
    {
        return reply;
    } 
        
    /**
     * 设置 
     */
    public void setReply(String reply) 
    {
        this.reply = reply;
    }   
    
    /**
     * 获取 
     */
    public Date getReplydate()
    {
        return replydate;
    } 
        
    /**
     * 设置 
     */
    public void setReplydate(Date replydate) 
    {
        this.replydate = replydate;
    }   
    
    /**
     * 获取 
     */
    public String getReplyuser()
    {
        return replyuser;
    } 
        
    /**
     * 设置 
     */
    public void setReplyuser(String replyuser) 
    {
        this.replyuser = replyuser;
    }   
    
    /**
     * 获取 
     */
    public Integer getIsreply()
    {
        return isreply;
    } 
        
    /**
     * 设置 
     */
    public void setIsreply(Integer isreply) 
    {
        this.isreply = isreply;
    }

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
    
}