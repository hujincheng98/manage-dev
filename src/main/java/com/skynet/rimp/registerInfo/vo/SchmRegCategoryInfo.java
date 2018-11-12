package com.skynet.rimp.registerInfo.vo;



import java.io.Serializable;

/**
 *
 * <p>Title: SchmRegCategoryInfo</p>
 * <p>Description: TODO 挂号类别Entity层
 *
 * @author LiYang
 * @version 1.00.00 创建时间：2018-07-05 17:34:48
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 *
 */
public class SchmRegCategoryInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 挂号类别
     */
    private String regcategory;
    /**
     * 业务系统编码
     */
    private String servcoding;
    /**
     * 挂号费
     */
    private String regfee;
    /**
     * 诊金
     */
    private String fee;
    /**
     * 工本费
     */
    private String flatfee;
    /**
     * 备用1
     */
    private String ext1;
    /**
     * 备用2
     */
    private String ext2;
    /**
     * 备用3
     */
    private String ext3;

    private String id;

    private String orgId;


    /**
     * 获取 挂号类别
     */
    public String getRegcategory()
    {
        return regcategory;
    }

    /**
     * 设置 挂号类别
     */
    public void setRegcategory(String regcategory)
    {
        this.regcategory = regcategory;
    }

    /**
     * 获取 业务系统编码
     */
    public String getServcoding()
    {
        return servcoding;
    }

    /**
     * 设置 业务系统编码
     */
    public void setServcoding(String servcoding)
    {
        this.servcoding = servcoding;
    }

    /**
     * 获取 挂号费
     */
    public String getRegfee()
    {
        return regfee;
    }

    /**
     * 设置 挂号费
     */
    public void setRegfee(String regfee)
    {
        this.regfee = regfee;
    }

    /**
     * 获取 诊金
     */
    public String getFee()
    {
        return fee;
    }

    /**
     * 设置 诊金
     */
    public void setFee(String fee)
    {
        this.fee = fee;
    }

    /**
     * 获取 工本费
     */
    public String getFlatfee()
    {
        return flatfee;
    }

    /**
     * 设置 工本费
     */
    public void setFlatfee(String flatfee)
    {
        this.flatfee = flatfee;
    }

    /**
     * 获取 备用1
     */
    public String getExt1()
    {
        return ext1;
    }

    /**
     * 设置 备用1
     */
    public void setExt1(String ext1)
    {
        this.ext1 = ext1;
    }

    /**
     * 获取 备用2
     */
    public String getExt2()
    {
        return ext2;
    }

    /**
     * 设置 备用2
     */
    public void setExt2(String ext2)
    {
        this.ext2 = ext2;
    }

    /**
     * 获取 备用3
     */
    public String getExt3()
    {
        return ext3;
    }

    /**
     * 设置 备用3
     */
    public void setExt3(String ext3)
    {
        this.ext3 = ext3;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}