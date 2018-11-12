package com.skynet.rimp.filemanage.action;

import org.apache.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.skynet.rimp.common.utils.file.FileUtil;

/**
 * <p>Title: 文件下载</p>
 * <p>Description: </p>
 *
 * @author Liujian
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Controller
@RequestMapping(value = "/rimp/download")
public class DownloadAction {

	private static final Logger logger = Logger.getLogger(DownloadAction.class);

	/**
	 * 文件下载,Spring自带处理，目前消息转换器有问题，请使用simpleDownload方法
	 * @param filePath 文件路径
	 * @param attachment 附件文件名
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "download.do", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(String fileDir, String attachment) throws UnsupportedEncodingException {
		attachment = URLDecoder.decode(attachment, "UTF-8");
		String filePath = System.getProperty("java.io.tmpdir") + File.separator + fileDir + attachment;
		File file = new File(filePath);
        HttpHeaders headers = new HttpHeaders();    
		attachment = new String(attachment.getBytes(), "ISO8859-1");
        headers.setContentDispositionFormData("attachment", attachment);   
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
        return new ResponseEntity<byte[]>(FileUtil.readFileAsByteArray(file), headers, HttpStatus.CREATED); 
	}
	
	/**
	 * 文件下载,依赖于Servlet API
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "simpleDownload.do", method = RequestMethod.GET)  
	public void simpleDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {  
	    OutputStream os = response.getOutputStream();  
	    try {  
	    	String fileDir = request.getParameter("fileDir");
	    	String attachment = request.getParameter("attachment");
	    	attachment = URLDecoder.decode(attachment, "UTF-8");
	    	String filePath = System.getProperty("java.io.tmpdir") + File.separator + fileDir + attachment;
	    	logger.info("文件路径： " + filePath);
	    	File file = new File(filePath);
	    	response.reset();  
	    	attachment = new String(attachment.getBytes(), "ISO8859-1");
	    	response.setHeader("Content-Disposition", "attachment; filename=" + attachment);  
	    	response.setContentType("application/x-xls; charset=utf-8");  
	        os.write(FileUtil.readFileAsByteArray(file));  
	        os.flush();  
	    } finally {  
	        if (os != null) {  
	            os.close();  
	        }  
	    }  
	}  
}
