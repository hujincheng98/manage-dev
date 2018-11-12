package com.skynet.his.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: http请求测试工具类，请将请求地址，参数填写在main函数内
 *
 * @author Torlay
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 *
 */

public class HttpRequest {
	private static boolean debug = true;

	public static String sendGet(String url, String param) {
		if (!debug) {
			return "";
		}
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);

			URLConnection connection = realUrl.openConnection();

			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			connection.connect();

			Map<String, List<String>> map = connection.getHeaderFields();

			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}

			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("异常" + e);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	// [start] 服务注入

	// [end]

	public static String sendPost(String url, String param) {
		if (!debug) {
			return "";
		}
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true); 
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
			out.print(param);
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			System.out.println("sendPost"+result);
		} catch (Exception e) {
			System.out.println("异常" + e);
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

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
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
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