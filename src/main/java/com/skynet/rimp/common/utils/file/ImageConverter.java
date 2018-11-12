package com.skynet.rimp.common.utils.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.coobird.thumbnailator.Thumbnails;
import net.iharder.Base64;

public class ImageConverter {
	/**
	 * @param filename 图片文件名
	 * @param width 压缩宽度
	 * @param height 压缩高度
	 * @return
	 * @throws IOException
	 */
	public static String toBase64(String filename,int width,int height) throws IOException{
		return toBase64(new FileInputStream(filename),width,height);
	}
	/**
	 * @param filename 图片文件
	 * @param width 压缩宽度
	 * @param height 压缩高度
	 * @return
	 * @throws IOException
	 */
	public static String toBase64(File file,int width,int height) throws IOException{
		return toBase64(new FileInputStream(file),width,height);
	}
	/**
	 * @param filename 输入流
	 * @param width 压缩宽度
	 * @param height 压缩高度
	 * @return
	 * @throws IOException
	 */
	public static String toBase64(InputStream inputStream,int width,int height) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream() ;
		Thumbnails.of(inputStream)
		.size(width, height)
		.toOutputStream(out);
		return Base64.encodeBytes(out.toByteArray());
	}
	/**
	 * 不压缩
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static String toBase64(InputStream inputStream) throws IOException{
		byte[] data = new byte[inputStream.available()];
		inputStream.read(data);
		return Base64.encodeBytes(data);
	}
	public static String toBase64(File file) throws IOException{
		return toBase64(new FileInputStream(file));
	}
	public static String toBase64(String filename) throws IOException{
		return toBase64(new FileInputStream(filename));
	}
	
}
