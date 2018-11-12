package com.skynet.rimp.hisBaseInfo.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.dom4j.tree.AbstractEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skynet.platform.common.util.CacheCoreUtil;
import com.skynet.platform.core.entity.DictionaryEntity;


/**
 * 
 * <p>Title: 医院通知公告管理</p>
 * <p>Description: </p>
 *
 * @author liuletian
 * @version 1.00.00 创建时间：2017-03-13 15:30:45
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 *
 */
public class NoticeInfo extends AbstractEntity implements Serializable 
{	
    private static final long serialVersionUID = 1L;
	
    private String id; //主键
    private String noticename;  //通告名称
    private String noticetype;  //通告类型     
    private String hoscoding;  //所属医院编码(医院id)  
    private String affiliatedhos;  //所属医院    
    private String briefcontent;  //简要内容 
    private String sortorder;  //排序顺序
    private String uploadpictures;  //上传图片 
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date releasedate;  //发布时间 
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date offlinedate;  //下线日期      
    private String remark;  //备注  
    private String details;  //详情
    private String updateuser;  //修改人id
    private String updatedate;  //修改日期
    private String createuser;  //创建人id
    private String createdate;  //创建日期
   
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getNoticename() {
		return noticename;
	}
	public void setNoticename(String noticename) {
		this.noticename = noticename == null ? null : noticename.trim();
	}
	public String getNoticetype() {
		return noticetype;
	}
	public void setNoticetype(String noticetype) {
		this.noticetype = noticetype == null ? null : noticetype.trim();
	}
	public String getHoscoding() {
		return hoscoding;
	}
	public void setHoscoding(String hoscoding) {
		this.hoscoding = hoscoding == null ? null : hoscoding.trim();
	}
	public String getAffiliatedhos() {
		return affiliatedhos;
	}
	public void setAffiliatedhos(String affiliatedhos) {
		this.affiliatedhos = affiliatedhos == null ? null : affiliatedhos.trim();
	}
	public String getBriefcontent() {
		return briefcontent;
	}
	public void setBriefcontent(String briefcontent) {
		this.briefcontent = briefcontent == null ? null : briefcontent.trim();
	}
	public String getSortorder() {
		return sortorder;
	}
	public void setSortorder(String sortorder) {
		this.sortorder = sortorder == null ? null : sortorder.trim();
	}
	public String getUploadpictures() {
		return uploadpictures;
	}
	public void setUploadpictures(String uploadpictures) {
		this.uploadpictures = uploadpictures == null ? null : uploadpictures.trim();
	}
	public Date getReleasedate() {
		return releasedate;
	}
	public void setReleasedate(Date releasedate) {
		this.releasedate = releasedate;
	}
	public Date getOfflinedate() {
		return offlinedate;
	}
	public void setOfflinedate(Date offlinedate) {
		this.offlinedate = offlinedate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details == null ? null : details.trim();
	}
	public String getUpdateuser() {
		return updateuser;
	}
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser == null ? null : updateuser.trim();
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate == null ? null : updatedate.trim();
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser == null ? null : createuser.trim();
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate == null ? null : createdate.trim();
	}
	//参数转换
    public String getNoticetypeName() {
    	 if (StringUtils.isNotEmpty(getNoticetype()))
	        {
	            DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(getNoticetype());
	            if (dictionary != null)
	            {
	                return dictionary.getDictName(); 
	            }
	        }
	        return null;
    	
    	
	}
}