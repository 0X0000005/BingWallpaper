package com.getBingJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bean.ImgBean;

public class GetBingJson {
	private final Logger logger = Logger.getLogger(GetBingJson.class.getName());

	private final String downloadUrl = "http://cn.bing.com/HPImageArchive.aspx?format=js";
	/**
	 * 获取图片的url
	 * 
	 * @param num
	 *            获取的记录条数,最大为8
	 * @param day
	 *            距离现在的天数
	 * @return
	 * @throws MalformedURLException 
	 */
	public JSONObject getBingJson(int day, int num) {
		String url = downloadUrl+"&idx=" + day + "&n=" + num;
		String bingJson = readUrlContent(url);
		JSONObject obj = new JSONObject();
		if (null != bingJson && bingJson.length()>0) {
			try {
				obj = JSONObject.parseObject(bingJson);
			} catch (Exception e) {
				// TODO: handle exception
				throw new RuntimeException("获取下载json字符串异常,异常字符串 --> "+ bingJson);
			}
		}else {
			throw new RuntimeException("获取下载json字符串异常");
		}
		return obj;
	}
	
	/**
	 * 从url连接读取字符串内容
	 * 
	 * @param urlStr
	 * @return
	 */
	private String readUrlContent(String urlStr) {
		BufferedReader reader = null;
		String content = "";
		try {
			URL url = new URL(urlStr);
			URLConnection connection = url.openConnection();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String inputLine = null;
			while ((inputLine = reader.readLine()) != null) {
				content = content + inputLine;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("获取url内容异常",e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return content;
	}

	public JSONArray getImgArray(JSONObject obj) {
		JSONArray array = new JSONArray();
		try {
			ArrayList<String> list = new ArrayList<>();
			array = obj.getJSONArray("images");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("获取images路径失败");
		}
		return array;
	}

	/**
	 * 通过输入日期计算输入数据
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public String getTimeDiff(String date) {
		return null;
	}

	/**
	 * 解析获取的json获取url
	 * 
	 * @param jsonStr
	 * @return
	 */
	public String getBingUrl(JSONObject obj) {
		final String bing = "http://cn.bing.com";
		String url = "";
		for (Map.Entry<String, Object> entry : obj.entrySet()) {
			if (entry.getKey().equals("url")) {
				url = bing +  (String) entry.getValue();
			}
		}
		logger.info("解析出来的下载地址:"+url);
		return url;
	}

	/**
	 * 获取enddate做name
	 * 
	 * @param jsonStr
	 * @return
	 */
	public String getPicName(JSONObject obj) {
		String picName = "";
		for (Map.Entry<String, Object> entry : obj.entrySet()) {
			if (entry.getKey().equals("enddate")) {
				picName = (String) entry.getValue() + ".jpg";
			}
		}
		logger.info("解析图片名称为:"+picName);
		return picName;
	}
	
	/**
	 * 寻找key
	 * 
	 * @param obj
	 * @param key
	 * @return
	 */
	public String getJsonValue(JSONObject obj,String key) {
		for (Map.Entry<String, Object> entry : obj.entrySet()) {
			if (key.equals(entry.getKey())) {
				return (String) entry.getValue();
			}
		}	
		return "";
	}
	
	public String getBingDesc(JSONObject obj) {
		String desc = "";
		for (Map.Entry<String, Object> entry : obj.entrySet()) {
			if (entry.getKey().equals("copyright")) {
				String copyright = (String) entry.getValue();
				String[] copy = copyright.split("\\(\\?");
				desc = copy[0];
			}
		}
		logger.info("解析图片描述为:"+desc);
		return desc;
	}
	
	public int getFileSize(String urlString) {
		int fileSize = 0;
		try {
		 	URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			fileSize = connection.getContentLength();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("获取文件大小异常:",e);
		}
		return fileSize;
	}
	
	/**
	 * 生成ImgBean 数组
	 * 
	 * @return
	 */
	public JSONArray getImgBeanArray(JSONArray array) {
		JSONArray imgBeanArray = new JSONArray();
		for(int i=0;i<array.size();i++) {
			JSONObject obj = array.getJSONObject(i);
			imgBeanArray.add(createImgBean(obj));
		}
		return imgBeanArray;
	}
		
	public ImgBean createImgBean(JSONObject obj) {
		ImgBean ib = new ImgBean();
		String picUrl = getBingUrl(obj);
		ib.setPicUrl(picUrl);
		ib.setPicName(getPicName(obj));
		ib.setDesc(getBingDesc(obj));
		ib.setHashCode(getJsonValue(obj, "hsh"));
		ib.setFileSize(getFileSize(picUrl));
		ib.setPicJson(obj);
		System.out.println(ib);
		return ib;
	}
}
