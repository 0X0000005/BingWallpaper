package com.download;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class DownLoadPic {
	private final Logger logger = Logger.getLogger(DownLoadPic.class.getName());

	/**
	 * ͨ��url����ͼƬ
	 * 
	 * @param urlString
	 *            url·��
	 */
	public void downloadPic(String urlString, String picName) {
		logger.info("��ʼ����ͼƬ" + new Date());
		DataOutputStream os = null;
		InputStream is = null;
		int count = 0;
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 5s��ʱ
			connection.setConnectTimeout(5 * 1000);
			logger.info("�ļ���С:" + connection.getContentLength() / 1024 + "KB" + " " + picName);
			System.out.println(connection.getContentLength() / 1024 + "KB" + " " + picName);
			is = connection.getInputStream();
			byte[] bs = new byte[1024];
			int len;
			Date date = new Date();
			os = new DataOutputStream(new FileOutputStream(picName));
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			os.close();
			is.close();
			logger.info("ͼƬ���ؽ���:" + new Date());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("��ȡbing Pic��Ϣʧ��");
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ����ļ��Ƿ���Ҫ������
	 * 
	 * @param file
	 * @param picName
	 * @return
	 */
	public boolean checkDownload(File file,String picName) {
		File filePic = new File(file,picName);
		if (filePic.exists()) {
			System.out.println(picName + "�Ѿ�������");
			return true;
		}
		return false;
	}

	/**
	 * ��������ͼƬ������
	 * 
	 * @return
	 */
	public String getPicName() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSS");
		String time = sdf.format(date);
		System.out.println(time);
		return "bingͼƬ" + time + ".jpg";
	}
}
