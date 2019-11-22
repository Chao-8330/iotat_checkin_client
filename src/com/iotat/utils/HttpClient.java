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
		String result = null;// 返回结果字符串
		String urls = httpUrl;
		urls = urls + getUrlParamsByMap(data);
		try {
			// 创建远程url连接对象
			URL url = new URL(urls);
			// 通过远程url连接对象打开一个连接，强转成httpURLConnection类
			connection = (HttpURLConnection) url.openConnection();
			// 设置连接方式：get
			connection.setRequestMethod("GET");
			// 设置连接主机服务器的超时时间：15000毫秒
			connection.setConnectTimeout(15000);
			// 设置读取远程返回的数据时间：60000毫秒
			connection.setReadTimeout(60000);
			// 发送请求
			connection.connect();
			// 通过connection连接，获取输入流
			if (connection.getResponseCode() == 200) {
				is = connection.getInputStream();
				// 封装输入流is，并指定字符集
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				// 存放数据
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
			// 关闭资源
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
			connection.disconnect();// 关闭远程连接
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
