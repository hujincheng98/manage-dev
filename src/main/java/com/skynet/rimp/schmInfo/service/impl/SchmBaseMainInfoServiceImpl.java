package com.skynet.rimp.schmInfo.service.impl;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import com.skynet.rimp.schmInfo.dao.SchmBaseMainInfoDao;
import com.skynet.rimp.schmInfo.dao.SchmShiftInfoDao;
import com.skynet.rimp.schmInfo.service.ISchmBaseMainInfoService;
import com.skynet.rimp.schmInfo.vo.SchmBaseMainInfoEntity;
import com.skynet.rimp.schmInfo.vo.SchmMainInfo;
import com.skynet.rimp.schmInfo.vo.SchmShiftInfoEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SchmBaseMainInfoServiceImpl extends BaseServiceImpl<SchmBaseMainInfoEntity> implements
    ISchmBaseMainInfoService
{
    
    @Autowired
    private SchmBaseMainInfoDao schmBaseMainInfoDao;
    @Autowired
    private SchmShiftInfoDao shiftDao;
   
    @Override
    public List<SchmBaseMainInfoEntity> findAll()
        throws Exception
    {
        return schmBaseMainInfoDao.findAll();
    }
    
    @Override
    public List<SchmBaseMainInfoEntity> findByCondition(SearchParams arg0)
        throws Exception
    {
        return schmBaseMainInfoDao.findByCondition(arg0);
    }

	@Override
	public void deleteByKeyArr(String[] schmId) throws Exception {
		schmBaseMainInfoDao.deleteByKeyArr(schmId);
	}



	@Override
	public SchmBaseMainInfoEntity save(SchmBaseMainInfoEntity vo) throws Exception {
		
		if(vo !=null && StringUtils.isBlank(vo.getSchmId())){
			vo.setSchmId(UUIDGenerator.getUUID());
			vo.setCreateDate(new Date());
			vo.setCreateUser(UserUtil.getUserId());
			vo.setOrgId(UserUtil.getAuthCode());
			schmBaseMainInfoDao.insert(vo);
		}else{
			vo.setUpdateDate(new Date());
			schmBaseMainInfoDao.update(vo);
		}
		return vo;
	}

	@Override
	public void delete(String schmId) throws Exception {
		if (StringUtils.isNotBlank(schmId)) {
			this.schmBaseMainInfoDao.deleteByPrimaryKey(schmId);
		}
	}

	@Override
	public void update(SchmBaseMainInfoEntity entity) {
		entity.setUpdateDate(new Date());
		entity.setUpdateUser(UserUtil.getUserId());
		this.schmBaseMainInfoDao.update(entity);
	}

	@Override
	public boolean checkDocmShiftTime(SchmBaseMainInfoEntity basemain) {
		//获取该医生以安排的值班 id 和起止时间
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("docmId", basemain.getDocmId());
		params.put("schmWeek",basemain.getSchmWeek());
		params.put("shiftId", basemain.getShiftId());
		params.put("schmId", basemain.getSchmId());

		params.put("schmDeptId", basemain.getSchmDeptId());
		SimpleDateFormat format = new SimpleDateFormat("HH.mm");
		int count = this.schmBaseMainInfoDao.findCountBydocmAndWeek(params);
		if(count >0){
			return false;
		}
		List<SchmShiftInfoEntity> shiftList = shiftDao.findByDocmAndWeek(params);
		if(shiftList ==null || shiftList.size()==0){
			return true;
		}
		boolean flag = true;
		SchmShiftInfoEntity shift = shiftDao.selectByPrimaryKey(basemain.getShiftId());
		float star = Float.valueOf(format.format(shift.getShiftStartDate()));
		float end = Float.valueOf(format.format(shift.getShiftEndDate()));
		for(SchmShiftInfoEntity data : shiftList){
			if(!StringUtils.isBlank(basemain.getSchmId()) && basemain.getShiftId().equalsIgnoreCase(data.getShiftId())){
				continue;
			}
			if(shift.getShiftId().equalsIgnoreCase(data.getShiftId())){
				flag = false;
				break;
			}
			float db_star = Float.valueOf(format.format(data.getShiftStartDate()));
			float db_end = Float.valueOf(format.format(data.getShiftEndDate()));
			if((star > db_star && star < db_end ) ||(end > db_star && end < db_end) ||  (db_star > star && db_star < end) || (db_end > star && db_end < end)){
				flag = false;
				break;
			}
		}
		return flag;
	}

   
	/**
	 * 根据排班科室id、医生id查询是否存在排班模板信息
	 */
	@Override
	public List<SchmBaseMainInfoEntity> findByConditionBydel(SearchParams params) throws Exception{
		return schmBaseMainInfoDao.findByConditionBydel(params);
	}

	@Override
	public void getExportExcel(HttpServletRequest request, HttpServletResponse response, SearchParams params) throws Exception {
		List<SchmBaseMainInfoEntity> list = findByCondition(params);
		if(list.size()>0){
			//开始excel导出
			String fname = "排班模板";
			OutputStream os = response.getOutputStream();//取得输出流
			response.reset();//清空输出流
			//下面是对中文文件名的处理
			response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
			//fname = java.net.URLEncoder.encode(fname,"UTF-8");
			//response.setHeader("Content-Disposition","attachment;filename="+new String(fname.getBytes("UTF-8"),"GBK")+".xls");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fname.getBytes(),"iso-8859-1") + ".xls");
			response.setContentType("application/msexcel");//定义输出类型
			//创建工作薄
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			//创建新的一页
			WritableSheet sheet = workbook.createSheet("First Sheet",0);
			//设置字体种类和格式
			WritableFont bold = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD);
			WritableCellFormat wcfFormat = new WritableCellFormat(bold);
			//wcfFormat.setAlignment(jxl.format.Alignment.CENTRE);//单元格中的内容水平

			//设置第3列单元格宽
			//sheet.setColumnView(4, 50);
			//设置第三行单元格高
			//sheet.setRowView(4, 50);
			//创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容

			//创建表头
			Label schmWeekColumn = new Label(0,0,"星期",wcfFormat);
			sheet.addCell(schmWeekColumn);
			//设置某个单元格宽
			sheet.setColumnView(0, 20);

			Label shiftNameColumn = new Label(1,0,"班次",wcfFormat);
			sheet.addCell(shiftNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(1, 20);

			Label docmNameColumn = new Label(2,0,"医生",wcfFormat);
			sheet.addCell(docmNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(2, 20);

			Label docmTitleNameColumn = new Label(3,0,"职称",wcfFormat);
			sheet.addCell(docmTitleNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(3, 20);

			Label schmDeptNameColumn = new Label(4,0,"排版科室",wcfFormat);
			sheet.addCell(schmDeptNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(4, 20);

			Label deptNameColumn = new Label(5,0,"所属科室",wcfFormat);
			sheet.addCell(deptNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(5, 20);

			Label schmStateColumn = new Label(6,0,"排版模板状态",wcfFormat);
			sheet.addCell(schmStateColumn);
			//设置某个单元格宽
			sheet.setColumnView(6, 20);

			Label schmRegiSumColumn = new Label(7,0,"总限数",wcfFormat);
			sheet.addCell(schmRegiSumColumn);
			//设置某个单元格宽
			sheet.setColumnView(7, 20);

			Label schmOnSumColumn = new Label(8,0,"线上预约限数",wcfFormat);
			sheet.addCell(schmOnSumColumn);
			//设置某个单元格宽
			sheet.setColumnView(8, 20);

			Label schmDownSumColumn = new Label(9,0,"线下预约限数",wcfFormat);
			sheet.addCell(schmDownSumColumn);
			//设置某个单元格宽
			sheet.setColumnView(9, 20);

			Label schmOnRegiSumColumn = new Label(10,0,"线上挂号限数",wcfFormat);
			sheet.addCell(schmOnRegiSumColumn);
			//设置某个单元格宽
			sheet.setColumnView(10, 20);

			Label schmDownRegiSumColumn = new Label(11,0,"线下挂号限数",wcfFormat);
			sheet.addCell(schmDownRegiSumColumn);
			//设置某个单元格宽
			sheet.setColumnView(11, 20);



			//加载每行数据
			for (int i = 1; i <= list.size(); i++) {
				Label schmWeek = new Label(0,i,list.get(i-1).getSchmWeek(),wcfFormat);
				sheet.addCell(schmWeek);


				Label shiftName = new Label(1,i,list.get(i-1).getShiftName(),wcfFormat);
				sheet.addCell(shiftName);


				Label docmName = new Label(2,i,list.get(i-1).getDocmName(),wcfFormat);
				sheet.addCell(docmName);

				Label docmTitleName = new Label(3,i,list.get(i-1).getDocmTitleName(),wcfFormat);
				sheet.addCell(docmTitleName);


				Label schmDeptName = new Label(4,i,list.get(i-1).getSchmDeptName(),wcfFormat);
				sheet.addCell(schmDeptName);


				Label deptName = new Label(5,i,list.get(i-1).getDeptName(),wcfFormat);
				sheet.addCell(deptName);


				Label schmState = new Label(6,i,list.get(i-1).getSchmStateName(),wcfFormat);
				sheet.addCell(schmState);


				Label schmRegiSum = new Label(7,i,String.valueOf(list.get(i-1).getSchmRegiSum()),wcfFormat);
				sheet.addCell(schmRegiSum);

				Label schmOnSum = new Label(8,i,String.valueOf(list.get(i-1).getSchmOnSum()),wcfFormat);
				sheet.addCell(schmOnSum);

				Label schmDownSum = new Label(9,i,String.valueOf(list.get(i-1).getSchmDownSum()),wcfFormat);
				sheet.addCell(schmDownSum);

				Label schmOnRegiSum = new Label(10,i,String.valueOf(list.get(i-1).getSchmOnRegiSum()),wcfFormat);
				sheet.addCell(schmOnRegiSum);

				Label schmDownRegiSum = new Label(11,i,String.valueOf(list.get(i-1).getSchmDownRegiSum()),wcfFormat);
				sheet.addCell(schmDownRegiSum);


			}
			//把创建的内容写入到输出流中，并关闭输出流
			workbook.write();
			workbook.close();
			os.close();
		}



	}

    
}
