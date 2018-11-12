package com.skynet.rimp.hisBaseInfo.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.hisBaseInfo.service.IHisDeptInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HisDeptInfo;

@Controller
@RequestMapping(value = "/rimp/hisDept/")
public class HisDeptInfoAction {
	@Autowired
	private IHisDeptInfoService HisDeptInfoService;
	@RequestMapping(value = "list.json",method=RequestMethod.POST)
	@ResponseBody
	public Pagination<HisDeptInfo> list(SearchParams params,HttpServletRequest req) throws Exception {
		String q = req.getParameter("q");
		if(q!=null){
			Map<String, Object> map = params.getSearchParams();
			if(map==null){
				map = new HashMap<String, Object>();
				params.setSearchParams(map);
			}
			map.put("deptName", q);
		}
		Pagination<HisDeptInfo> list = HisDeptInfoService.findPageByCondition(params);
		
		return list;
	}
}
