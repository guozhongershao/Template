package com.template.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.template.common.TemplateConstants;

public class MiscUtil {

	private static final MiscUtil instance = new MiscUtil();
	private final static Logger LOGGER = Logger.getLogger(MiscUtil.class);

	private MiscUtil() {
	}

	public static MiscUtil getInstance() {
		return instance;
	}

	public static String formatDateTimeAsNormal(Date date) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					TemplateConstants.SIMPLE_DATE_TIME_FORMAT_PATTERN);
			return sdf.format(date);
		} else {
			return "";
		}
	}

	public static String formatDateTimeAsNormal(Date date, String format) {
		if (date != null) {
			if (StringUtils.isBlank(format)) {
				format = TemplateConstants.SIMPLE_DATE_TIME_FORMAT_PATTERN;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return "";
		}
	}

	public static String formatDateAsNormal(Date date) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					TemplateConstants.SIMPLE_DATE_FORMAT_PATTERN);
			return sdf.format(date);
		} else {
			return "";
		}
	}
	
	public static String formatDateAsNumber(Date date) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					TemplateConstants.SIMPLE_DATE_FORMAT_NUMBER);
			return sdf.format(date);
		} else {
			return "";
		}
	}

	public static Date parseDateAsNormal(String dateStr) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		} else {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(
						TemplateConstants.SIMPLE_DATE_FORMAT_PATTERN);
				return sdf.parse(dateStr);
			} catch (Exception ex) {
				return null;
			}
		}
	}

	public static Date parseDateTimeAsNormal(String dateStr) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		} else {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(
						TemplateConstants.SIMPLE_DATE_TIME_FORMAT_PATTERN);
				return sdf.parse(dateStr);
			} catch (Exception ex) {
				return null;
			}
		}
	}

	public Properties loadProperties(String filename) {
		try {
			Properties props = new Properties();
			InputStream is = getClass().getResourceAsStream(filename);
			props.load(is);

			return props;
		} catch (Exception ex) {
			LOGGER.error("load properites failed: " + ex.getMessage(), ex);
			return null;
		}
	}

	public static Date addYear(Date date, int count) {
		Calendar resultDate = Calendar.getInstance();
		resultDate.setTime(date);
		resultDate.add(Calendar.YEAR, count);
		return resultDate.getTime();
	}

	public static Date addMonth(Date date, int count) {
		Calendar resultDate = Calendar.getInstance();
		resultDate.setTime(date);
		resultDate.add(Calendar.MONTH, count);
		return resultDate.getTime();
	}

	public static Date addDay(Date date, int count) {
		Calendar resultDate = Calendar.getInstance();
		resultDate.setTime(date);
		resultDate.add(Calendar.DAY_OF_YEAR, count);
		return resultDate.getTime();
	}

	public static void uploadFile(InputStream is, String fileName) throws Exception {
		try {
			writeFile(is, fileName);
		} catch (Exception e) {
			LOGGER.error("uploadFile error: " + e.getMessage(), e);
		}
	}

	public static void removeFile(String realPath) throws Exception {
		try {
			File file = new File(realPath);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			LOGGER.error("removeFile error: " + e.getMessage(), e);
		}
	}

	public static void writeFile(byte[] content, String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fop = new FileOutputStream(file);
		fop.write(content);
		fop.flush();
		fop.close();
	}

	public static void writeFile(InputStream is, String filename) throws Exception {
		File file = new File(filename);
		BufferedInputStream in = new BufferedInputStream(is, 8192);
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(file), 8192);
		byte[] buf = new byte[8192];
		int len = 0;
		while ((len = in.read(buf, 0, buf.length)) != -1) {
			out.write(buf, 0, len);
		}
		out.flush();
		in.close();
		in = null;
		out.close();
		out = null;
	}

	public static int calculateMBWithBytes(int byteSize) {
		return byteSize / 1024 / 1024;
	}


	public static Long generateId() {
		int random = (int) (Math.random() * 10000+10000);
		String str = formatDateAsNumber(new Date())+random;
		return Long.valueOf(str);
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) {
		if (smdate == null || bdate == null) {
			return 0;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days)) + 1;
	}

	/**
	 * 字符串的日期格式的计算
	 * 
	 * @throws ParseException
	 */
	public static int daysBetween(String smdate, String bdate) {
		if (StringUtils.isBlank(smdate) || StringUtils.isBlank(bdate)) {
			return 0;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(smdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long time1 = cal.getTimeInMillis();
		try {
			cal.setTime(sdf.parse(bdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days)) + 1;
	}

	/**
	 * 转空串
	 * 
	 * @param str
	 * @return
	 */
	public static String nvl(String str) {
		return str == null ? "" : str;
	}

	/**
	 * 字符串转换unicode
	 */
	public static String toUnicodeString(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				sb.append("\\u" + Integer.toHexString(c));
			}
		}
		return sb.toString();
	}
	
	public static String encoding(String str) {
		if(str == null){
			return null;
		}
		try {
			return new String(str.getBytes("ISO8859-1"),"UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void writeJsonToResponse(String json,HttpServletResponse response) {
		
		try {
			OutputStream out = response.getOutputStream();
			response.setCharacterEncoding("UTF8");
			response.setContentType("application/json;charset=UTF8");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Content-type", "application/json;charset=UTF-8");  
			out.write(json.getBytes("UTF8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static JsonConfig getJsonConfig() {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(java.util.Date.class,new JsonValueProcessor() {
        	  public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        	    return  value == null ?"" : formatDateTimeAsNormal((Date) value);
        	  }
        	  public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        		  return  value == null ?"" : formatDateTimeAsNormal((Date) value);
        	  }
        	});
        jsonConfig.registerJsonValueProcessor(java.lang.Long.class,new JsonValueProcessor() {
      	  public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
      	    return  value == null ?"" : value.toString();
      	  }
      	  public Object processArrayValue(Object value, JsonConfig jsonConfig) {
      		return  value == null ?"" : value.toString();
      	  }
      	});
        return jsonConfig;
	}

	public static int toInt(String str) {
		int value = 0;
		try {
			value = Integer.valueOf(str);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static Long toLong(String str) {
		Long value = 0l;
		try {
			value = Long.valueOf(str);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static boolean toBoolean(String str) {
		boolean value = false;
		if(str == null || "0".equals(str)) {
			value = false;
		} else {
			value = true;
		}
		return value;
	}
}
