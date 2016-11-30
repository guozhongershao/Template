package com.template.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class FileUtils {
	private final static Logger LOGGER = Logger.getLogger(FileUtils.class);

	public static void writeFileLine(String filePath, String content) {
		FileWriter fw = null;
		try {
			int len = filePath.lastIndexOf(File.separator);
			File folder = new File(filePath.substring(0, len));
			if (!folder.exists()) {
				folder.mkdirs();
			}
			fw = new FileWriter(filePath, true);
			String[] contentArr = content.split("\r\n");
			for (String tmpStr : contentArr) {
				if (!StringUtils.isBlank(tmpStr)) {
					tmpStr += "\r\n";
					fw.write(tmpStr);
				}
			}
			fw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error("写入文件出错");
		}
	}

	public static final String readFile(String filePath) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
		String result = "";
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			result += line + "\r\n";
		}
		br.close();
		return result;
	}

	public static final boolean writeFile(String filePath, String content) {
		FileWriter fw;
		boolean writeSuccess = true;
		try {
			int len = filePath.lastIndexOf(File.separator);
			if (len == -1) {
				len = filePath.lastIndexOf("//");
			}
			File file = new File(filePath.substring(0, len));
			if (!file.exists()) {
				file.mkdirs();
			}
			fw = new FileWriter(filePath);
			fw.write(content, 0, content.length());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			writeSuccess = false;
		}
		return writeSuccess;
	}

	// 复制文件
	public static void copyFile(String sourceFilePath, String targetFilePath) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			File sourceFile = new File(sourceFilePath);
			int tmpLen = targetFilePath.lastIndexOf(File.separator);
			File file = new File(targetFilePath.substring(0, tmpLen));
			if (!file.exists()) {
				file.mkdirs();
			}
			File targetFile = new File(targetFilePath);
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null) {
				inBuff.close();
			}
			if (outBuff != null) {
				outBuff.close();
			}
		}
	}

	public static void main(String args[]) throws Exception {
		// writeFile("D:\\ccc\\dd\\dd.txt","中中化人11\r\n中化人2\r\n中化人3\r\n中化人4");
		// copyFile("D:\\ccc\\dd\\dd.txt","D:\\xx\\xx\\bb.txt");
		String str = readFile("D:\\xx\\xx\\bb.txt");
		writeFile("D:\\xx\\xx\\bb.txt", str.replaceAll("$3", "去就够吧"));
		// readFile("D:\\dd\\dd\\dd.txt");

	}
	/**
	 * 生成随机文件名，时间缀+五位随机数
	 * @return
	 */
	public static String generateFileName() {
		Date dt = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateStr =  sdf.format(dt);
		String str = Double.toString(Math.random() * 90000 + 10000);
		return dateStr + "_" + str.split("\\.")[0];
	}
	
	public static List parseRequest(HttpServletRequest request)
	throws Exception {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		Iterator iter = servletFileUpload.parseRequest(request).iterator();
		
		ArrayList rtnList = new ArrayList();
		ArrayList fileNameList = new ArrayList();
		ArrayList fileIsList = new ArrayList();
		HashMap paraMap = new HashMap();
		while (iter.hasNext()) {
			FileItem fileItem = (FileItem) iter.next();
			if (!fileItem.isFormField()) {
				if ("".equals(fileItem.getName())) {
					continue;
				}
				fileNameList.add(fileItem.getName());
				fileIsList.add(fileItem.getInputStream());
			} else {
				paraMap.put(fileItem.getFieldName(), fileItem.getString());
			}
		}
		rtnList.add(fileNameList);
		rtnList.add(fileIsList);
		rtnList.add(paraMap);
		return rtnList;
	} 
}
