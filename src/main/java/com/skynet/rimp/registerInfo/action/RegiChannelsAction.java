package com.skynet.rimp.registerInfo.action;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.skynet.rimp.common.utils.string.IdentityCard;
import com.skynet.rimp.common.utils.string.StringUtil;
import com.skynet.rimp.registerInfo.service.IRegiChannelsInfoService;
import com.skynet.rimp.registerInfo.vo.PayMedicalCardTransInfo;
import com.skynet.rimp.registerInfo.vo.RegiChannelsInfo;
import com.skynet.rimp.report.RegiChannelsInfoData;
import com.skynet.rimp.schmInfo.service.ISchmQueueInfoService;

/**
 * <p>
 * Title: 预约信息查询
 * </p>
 * <p>
 * Description:
 * </p>
 *
 * @author Liujian
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Controller
@RequestMapping("/rimp/regiChannels")
public class RegiChannelsAction {

	private static final Logger logger = Logger
			.getLogger(RegiChannelsAction.class);

	@Autowired
	private IRegiChannelsInfoService regiChannelsInfoService;

	@Autowired
	private ISchmQueueInfoService schmQueueInfoService;

	/**
	 * 跳转预约信息列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "registerInfo/regi_channels_index";
	}

	/**
	 * 返回列表json数据
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<RegiChannelsInfo> list(SearchParams params) {
		Pagination<RegiChannelsInfo> pageList = null;
		try {
			pageList = this.regiChannelsInfoService.findPageByCondition(params);
			List<RegiChannelsInfo> list = pageList.getRows();
			for (RegiChannelsInfo rci : list) {
				int patientType = 0;
				if (rci.getPatientType() != null) {
					patientType = rci.getPatientType();
				}
				if (patientType == 1) { // 成人
					String identityCard = rci.getIdentitycard();
					if (identityCard != null) {
						IdentityCard ic = new IdentityCard(identityCard);
						rci.setPatientSex(ic.getSex());
					}
				}
				// 屏蔽身份证号
//				String idCard = rci.getIdentitycard();
//				try {
//					String ic = StringUtil.screenIdentityCard(idCard);
//					rci.setIdentitycard(ic);
//				} catch (Exception e) {
//					rci.setIdentitycard(idCard);
//				}
				// 屏蔽电话
//				String telephone = rci.getTelephone();
//				try {
//					String telStr = StringUtil.screenTelephone(telephone);
//					rci.setTelephone(telStr);
//				} catch (Exception e) {
//					rci.setTelephone(telephone);
//				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageList;
	}

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @RequestMapping(value = "exportExcel.do", method = RequestMethod.GET)
	 * public String exportExcel(SearchParams params) { String fileDir =
	 * "report" + File.separator; ReportMetadata metadata = null; String
	 * fileName = ""; try { 读取报表配置文件 // String rootPath =
	 * System.getProperty("skynet.web.root") + "report" + File.separator;
	 * HttpServletRequest request = ((ServletRequestAttributes)
	 * RequestContextHolder.getRequestAttributes()).getRequest(); String
	 * rootPath = request.getSession().getServletContext().getRealPath("/") +
	 * "report" + File.separator; if
	 * (!rootPath.endsWith(java.io.File.separator)) { rootPath = rootPath +
	 * java.io.File.separator; } Map<String, ReportMetadata> reportMetadata =
	 * ConfigParser.parse(new FileInputStream(new File(rootPath +
	 * "regi_channels_setting.xml"))); metadata =
	 * reportMetadata.get("report_regi_channels"); List<RegiChannelsInfo> list =
	 * this.regiChannelsInfoService.findByCondition(params); if
	 * (!list.isEmpty()) { String hosId = list.get(0).getHosId(); Boolean boo =
	 * false; if (StringUtils.isNotBlank(hosId)) { boo =
	 * "1".equals(schmQueueInfoService.getQueuePushMark(hosId)); }
	 * RegiChannelsInfoData reportData = new RegiChannelsInfoData(boo, list);
	 * //生成报表 String tempPath = System.getProperty("java.io.tmpdir"); if
	 * (!tempPath.endsWith(java.io.File.separator)) { tempPath = tempPath +
	 * java.io.File.separator; } String savePath = tempPath + fileDir;
	 * reportData.creatReport(metadata, rootPath, savePath); fileName =
	 * URLEncoder.encode(URLEncoder.encode(metadata.getReportName(), "UTF-8"),
	 * "UTF-8"); } } catch (Exception e) { e.printStackTrace(); } return
	 * "redirect:/rimp/download/simpleDownload.do?fileDir=" + fileDir +
	 * "&attachment=" + fileName; }
	 */

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "exportExcel.do", method = RequestMethod.GET)
	public String exportExcel(SearchParams params, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			this.regiChannelsInfoService.getExportExcel(request, response,
					params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
