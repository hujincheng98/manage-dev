package com.skynet.his.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

/**
 * <p>Title: 向HIS推送数据http请求工具</p>
 * <p>Description: </p>
 *
 * @author wangshen
 * @version 1.0
 * @date 上午11:48:46
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@SuppressWarnings("deprecation")
public class PushHisHttpRequest {
	
	private static DefaultHttpClient m_httpclient;
	
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
			m_httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30*60000);
			m_httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30*60000);
			m_httpclient.getParams().setParameter(CoreConnectionPNames.MAX_LINE_LENGTH, 999999999);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			httpPost.setEntity(new StringEntity("json="+param, "utf-8"));
			HttpResponse response = m_httpclient.execute(httpPost);
			int httpCode = response.getStatusLine().getStatusCode();
			if(httpCode==200){
				return EntityUtils.toString(response.getEntity(),"UTF-8");
			}else{
				return "http-his-respCode >> "+httpCode +" >> responseStr "+ EntityUtils.toString(response.getEntity(),"UTF-8");
			}
		} catch (Exception e) {
			return "";
		}finally{
			//m_httpclient.getConnectionManager().shutdown();
		}
	}
	public static String doMessPost(String url, String param) {
		HttpPost httpPost = new HttpPost(url);
		try {
			m_httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
			m_httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
			m_httpclient.getParams().setParameter(CoreConnectionPNames.MAX_LINE_LENGTH, 999999999);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			httpPost.setEntity(new StringEntity(param, "utf-8"));
			HttpResponse response = m_httpclient.execute(httpPost);
			if(response.getStatusLine().getStatusCode()!=200){
				httpPost.abort();
				String str=(EntityUtils.toString(response.getEntity(),"UTF-8")).trim();
				String err="<string xmlns=\"http://www.01.cn/\">{\"code\":\"3\",\"responseData\":\""+"http返回状态码:"+response.getStatusLine().getStatusCode()+"返回值:"+str+"\"}</string>";
				return err;
			}else{
				String result = EntityUtils.toString(response.getEntity(),"UTF-8");
				httpPost.abort();
				return result;
			}
		} catch (Exception e) {
			System.out.println("http请求工具异常>>>>"+e.getMessage());
			e.printStackTrace();
			String err="<string xmlns=\"http://www.01.cn/\">{\"code\":\"3\",\"responseData\":\""+e.getMessage()+"\"}</string>";
			return err;
		}
	}
	
}
