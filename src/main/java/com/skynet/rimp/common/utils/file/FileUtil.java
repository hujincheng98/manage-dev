package com.skynet.rimp.common.utils.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtil {

	/** 允许上传的图片类型 **/
	public static final String IMG_TYPE = ".jpg|.gif|.png|.bmp";
	/** 允许上传的所有文件类型 **/
	public static final String ALL_TYPE = ".jpg|.jepg|.gif|.png|.bmp|.gz|.rar|.zip|.pdf|.txt|.swf|.mp3|.jar|.apk|.ipa";

	/**
	 * 检测文件大小
	 * 
	 * @param file
	 *            文件
	 * @param size
	 *            限制大小
	 * @return true 超过限制
	 */
	public static boolean checkFileSize(File file, int kb) {
		long size = file.length();
		if (size > 1024 * kb) {
			return true;
		}
		return false;
	}

	/**
	 * 检查文件类型
	 * 
	 * @param 文件名
	 * @param isImg
	 *            是否检查图片
	 * @return true=后缀合法
	 * @throws
	 */
	public static boolean checkFileType(String fileName, boolean isImg) {
		String fileType = getFileType(fileName);
		if (isImg) {
			return IMG_TYPE.indexOf(fileType.toLowerCase()) != -1;
		} else {
			return ALL_TYPE.indexOf(fileType.toLowerCase()) != -1;
		}
	}

	/**
	 * 获取文件类型
	 * 
	 * @param fileName
	 * @throws
	 */
	public static String getFileType(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."), fileName.length());
	}

	/**
	 * 读取文件返回Byte数组
	 * @param file
	 * @return
	 */
	public static byte[] readFileAsByteArray(File file) {
		BufferedInputStream in = null;
		byte[] content = null;
		ByteArrayOutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			out = new ByteArrayOutputStream(1024);        
	        byte[] temp = new byte[1024];        
	        int size = 0;        
	        while ((size = in.read(temp)) != -1) {        
	                out.write(temp, 0, size);        
	        }        
	        content = out.toByteArray();        
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

	public static void main(String[] args) {
		String s = getFileType("12321.jpg");
		System.out.println(s);
	}
}
