package com.skynet.rimp.schmInfo.service;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.schmInfo.vo.SchmAutoSchmInfoVo;
import com.skynet.rimp.schmInfo.vo.SchmDetailInfo;
import com.skynet.rimp.schmInfo.vo.SchmMainInfo;
import com.skynet.rimp.schmInfo.vo.SchmMainInfoVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;


public interface ISchmMainInfoService extends BaseService<SchmMainInfo>
{
    public int updateState(SchmMainInfo schmMainInfo)throws Exception;
    
    public String saveAll(SchmMainInfoVo schmMainInfoVo)throws Exception;
    
    public int deleteBySchmId(String schmId) throws Exception;

    public int deleteBatchBySchmId(String[] schmId) throws Exception;

    public int deleteDetailByDetailId(String detailId) throws Exception;
    
    public String saveAutoSchm(SchmAutoSchmInfoVo schmAutoSchmInfoVo)throws Exception;
    
    public SchmMainInfo findById(String schmId);
    
    public List<SchmDetailInfo> findDetailsBySchmId(String schmId);
    
    public int updateMainDetail(SchmMainInfoVo schmMainInfovo) throws ParseException;
    
    public void updateTaskSchm() throws Exception;
    
    public List<SchmMainInfo> findByDocmId(String docmId) throws Exception;
    
    public List<SchmMainInfo> findByConditionBydel(SearchParams params)   throws Exception;

    //导出excel
    public void getExportExcel(HttpServletRequest request, HttpServletResponse response, SearchParams params) throws Exception;

    public void updateRegCategory(SchmMainInfoVo schmMainInfovo,SchmMainInfo mainInfo) throws Exception;

    public List<SchmMainInfo> findByRegCategoryId(String ext1) throws Exception;

    public int findByShiftIdByDocmDate(SchmMainInfoVo schmMainInfoVo);

}
