package com.iotat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;

public class HttpClient {
	public static String GetHttp(String httpUrl, Map<String, Object> data) {
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedReader br = null;
		String result = null;// ���ؽ���ַ���
		String urls = httpUrl;
		urls = urls + getUrlParamsByMap(data);
		try {
			// ����Զ��url���Ӷ���
			URL url = new URL(urls);
			// ͨ��Զ��url���Ӷ����һ�����ӣ�ǿת��httpURLConnection��
			connection = (HttpURLConnection) url.openConnection();
			// �������ӷ�ʽ��get
			connection.setRequestMethod("GET");
			// �������������������ĳ�ʱʱ�䣺15000����
			connection.setConnectTimeout(15000);
			// ���ö�ȡԶ�̷��ص�����ʱ�䣺60000����
			connection.setReadTimeout(60000);
			// ��������
			connection.connect();
			// ͨ��connection���ӣ���ȡ������
			if (connection.getResponseCode() == 200) {
				is = connection.getInputStream();
				// ��װ������is����ָ���ַ���
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				// �������
				StringBuffer sbf = new StringBuffer();
				String temp = null;
				while ((temp = br.readLine()) != null) {
					sbf.append(temp);
					sbf.append("\r\n");
				}
				result = sbf.toString();
			}
		} catch (MalformedURLException e) {
			Logger logger = Logger.getLogger(HttpClient.class);
			logger.error(e);
		} catch (IOException e) {
			Logger logger = Logger.getLogger(HttpClient.class);
			logger.error(e);
		} finally {
			// �ر���Դ
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					Logger logger = Logger.getLogger(HttpClient.class);
					logger.error(e);
				}
			}

			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					Logger logger = Logger.getLogger(HttpClient.class);
					logger.error(e);
				}
			}
			connection.disconnect();// �ر�Զ������
		}
		return result;
	}

	public static String getUrlParamsByMap(Map<String, Object> map) {
		if (map == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue());
			sb.append("&");
		}

		String s = sb.toString();
		if (s.endsWith("&")) {
			s = org.apache.commons.lang.StringUtils.substringBeforeLast(s, "&");
		}
		return s;
	}

}
