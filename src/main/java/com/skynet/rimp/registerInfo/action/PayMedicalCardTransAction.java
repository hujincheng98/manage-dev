package com.skynet.rimp.registerInfo.action;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.common.excel.metadata.ReportMetadata;
import com.skynet.common.excel.util.ConfigParser;
import com.skynet.rimp.common.utils.string.StringUtil;
import com.skynet.rimp.registerInfo.service.IPayMedicalCardTransService;
import com.skynet.rimp.registerInfo.vo.PayClinicRecipeTrans;
import com.skynet.rimp.registerInfo.vo.PayMedicalCardTransInfo;
import com.skynet.rimp.report.PayMedicalCardTransInfoData;

/**
 * <p>Title: 预交金充值记录查询</p>
 * <p>Description: </p>
 *
 * @author Liujian
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Controller
@RequestMapping("/rimp/payMedicalCardTrans")
public class PayMedicalCardTransAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PayMedicalCardTransAction.class);

	@Autowired
	private IPayMedicalCardTransService payMedicalCardTransService;
	
	/**
	 * 跳转到预交金充值记录查询页面
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "registerInfo/pay_medicalcard_trans_index";
	}
	
	/**
	 * 数据列表
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<PayMedicalCardTransInfo> list(SearchParams params) {
		Pagination<PayMedicalCardTransInfo> pageList = null;
		try {
			Map map = params.getSearchParams();
			if (map != null && map.get("startDate") != null && !"".equals(map.get("startDate")) && map.get("endDate") != null && !"".equals(map.get("endDate"))) {
				String startDate = map.get("startDate") + " 00:00:00";
				String endDate = map.get("endDate") + " 23:59:59";
				map.put("startDate", startDate);
				map.put("endDate", endDate);
				params.setSearchParams(map);
			}
			pageList = this.payMedicalCardTransService.findPageByCondition(params);
			List<PayMedicalCardTransInfo> list = pageList.getRows(); 
			for (PayMedicalCardTransInfo pmti : list) {
				//屏蔽身份证号
	        	String idCard = pmti.getTransMedicardIdencard();
	        	String ic = idCard;//StringUtil.screenIdentityCard(idCard);
	        	pmti.setTransMedicardIdencard(ic);
	        	//屏蔽电话
	        	String telephone = pmti.getTransMedicardPhone();
	        	String telStr = telephone;//StringUtil.screenTelephone(telephone);
	        	pmti.setTransMedicardPhone(telStr);
			   }
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageList;
	}
	
	/**
	 * 导出excel
	 * @return
	 */
	/*@SuppressWarnings("unchecked")
	@RequestMapping(value = "exportExcel.do", method = RequestMethod.GET)
	public String exportExcel(SearchParams params) {
		String fileDir = "report" + File.separator;
		ReportMetadata metadata = null;
		String fileName = "";
		try {
			//读取报表配置文件 
			//String rootPath = System.getProperty("skynet.web.root") + "report" + File.separator;
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String rootPath = request.getSession().getServletContext().getRealPath("/") + "report" + File.separator;
			if (!rootPath.endsWith(java.io.File.separator)) {
				rootPath = rootPath + java.io.File.separator;
	        }
			logger.info("根路径： " + rootPath);
			Map<String, ReportMetadata> reportMetadata = ConfigParser.parse(new FileInputStream(new File(rootPath + "pay_medicalcard_trans_setting.xml")));
			metadata = reportMetadata.get("report_pay_medicalcard_trans"); 
			Map<String, Object> searchParams = params.getSearchParams();
			if (searchParams.get("startDate") != null) {
				searchParams.put("startDate", searchParams.get("startDate") + " 00:00:00");
			}
			if (searchParams.get("endDate") != null) {
				searchParams.put("endDate", searchParams.get("endDate") + " 23:59:59");
			}
			List<PayMedicalCardTransInfo> list = this.payMedicalCardTransService.findByCondition(params);
			logger.info("List Size： " + list.size());
			if (!list.isEmpty()) {
				PayMedicalCardTransInfoData reportData = new PayMedicalCardTransInfoData(list);
				//生成报表  
				String tempPath = System.getProperty("java.io.tmpdir");
				if (!tempPath.endsWith(java.io.File.separator)) {
					tempPath = tempPath + java.io.File.separator;
		        }
				String savePath = tempPath + fileDir;
				logger.info("保存路径：" + savePath);
				reportData.creatReport(metadata, rootPath, savePath);
				fileName = URLEncoder.encode(URLEncoder.encode(metadata.getReportName(), "UTF-8"), "UTF-8");
				logger.info("文件名称：" + fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/rimp/download/simpleDownload.do?fileDir=" + fileDir + "&attachment=" + fileName;
	}*/
	/**
	 * 导出excel
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "exportExcel.do", method = RequestMethod.GET)
	public String exportExcel(SearchParams params, HttpServletRequest request, HttpServletResponse response) {
		try {
			this.payMedicalCardTransService.getExportExcel(request, response, params);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return null;
	}
}
