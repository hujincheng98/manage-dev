package com.skynet.rimp.common.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
/**
 * 
 * @ClassName:		HttpRequest.java
 * @Description:	HTTP请求工具类
 * @Date:           2017-3-14 下午3:59:05 
 * @author:			cxp
 * @version:		1.0 
 * @since :			JDK 1.7
 */
@SuppressWarnings("deprecation")
public class HttpRequest {
	
	public static DefaultHttpClient m_httpclient;  
    static {    
    	m_httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());    
    } 
    
    /**
   	 * POST请求HIS接口
   	 * @param url 请求URL
   	 * @param param 请求param
   	 * @return 返回为空字符表示程序出现错误
   	 */
	public static String doPost(String url, String param) {
		HttpPost httpPost = new HttpPost(url);
		try {
			m_httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
			m_httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
			m_httpclient.getParams().setParameter(CoreConnectionPNames.MAX_LINE_LENGTH, 999999999);
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.setEntity(new StringEntity(param, "utf-8"));
			HttpResponse response = m_httpclient.execute(httpPost);
			if(response.getStatusLine().getStatusCode()!=200){
				httpPost.abort();
				return "";
			}else{
				String result = EntityUtils.toString(response.getEntity(),"utf-8");
				httpPost.abort();
				return result;
			}
		} catch (Exception e) {
			System.out.println("http请求工具异常>>>>"+e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
	
	 /**
   	 * POST请求HIS接口
   	 * @param url 请求URL
   	 * @param param 请求param
   	 * @return 返回为空字符表示程序出现错误
   	 */
	public static String post(String url, String param) {
		HttpPost httpPost = new HttpPost(url);
		try {
			m_httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
			m_httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
			m_httpclient.getParams().setParameter(CoreConnectionPNames.MAX_LINE_LENGTH, 999999999);
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.setEntity(new StringEntity(param, "utf-8"));
			HttpResponse response = m_httpclient.execute(httpPost);
			if(response.getStatusLine().getStatusCode()!=200){
				httpPost.abort();
				return "";
			}else{
				String result = EntityUtils.toString(response.getEntity(),"utf-8");
				httpPost.abort();
				return result;
			}
		} catch (Exception e) {
			System.out.println("http请求工具异常>>>>"+e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
	
}




