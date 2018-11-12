package com.skynet.rimp.common.utils;

import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * <p>
 * Title: 返回数据工具类
 * </p>
 * <p>
 * Description:</p>
 * 
 * @author wangshen
 * @version 1.00.00
 * @date 下午3:13:05
 *       <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
public class ResponseEntity<T> implements Serializable {

	private static final long serialVersionUID = 4281230659824659225L;

	public static final int SUCCEED = 0;
	public static final int FAIL = 1;
	public static final int NULL = 2;
	public static final int TEST_ERROR = 3;
	private int code;
	private T responseData;

	public ResponseEntity(int code, T responseData) {
		this.code = code;
		this.responseData = responseData;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	
	//测试webservice是否合法时，返回此提示

	public static final String createErrorJsonResponse(String msg) {
		return JSONObject.fromObject(new ResponseEntity<String>(ResponseEntity.FAIL, msg)).toString();
	}

	public static final String createNormalJsonResponse(String msg) {
		return JSONObject.fromObject(new ResponseEntity<String>(ResponseEntity.SUCCEED, msg)).toString();
	}

}
