package com.iotat.tray;

import java.awt.TrayIcon;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iotat.getmac.LocalMac;
import com.iotat.getmac.RouterMac;
import com.iotat.main.MainConfig;
import com.iotat.utils.HttpClient;

public class StartThread extends Thread {
	private static int Count = 1;

	private final String BASE_URL = "http://10.10.5.130:18887/online?";

	private static Logger logger = Logger.getLogger(MainConfig.class);

	private TrayIcon trayIcon;

	public StartThread(TrayIcon trayIcon) {
		this.trayIcon = trayIcon;
	}

	public void run() {
		while (true) {
			logger.info("第" + Count + "次请求.");
			Count++;
			String localMacAddress = null;
			String routerMacAddress = null;
			try {
				localMacAddress = LocalMac.getLocalMac();
				routerMacAddress = RouterMac.getRouterMac();
			} catch (SocketException e) {
				logger.error(e);
			}
			if ((localMacAddress == null) && (routerMacAddress == null)) {
				logger.error("No network connection.");
				JOptionPane.showMessageDialog(null, "未连接网络");
				trayIcon.setToolTip("未连接网络");
			} else {
				if ((localMacAddress == null) && (routerMacAddress != null)) {
					trayIcon.setToolTip("不在实验室环境");
				} else {
					Map<String, Object> urlData = new HashMap<String, Object>();
					urlData.put("selfMac", localMacAddress);
					urlData.put("commonMac", routerMacAddress);

					JSONObject jsonObject = JSON.parseObject(HttpClient.GetHttp(BASE_URL, urlData));
					if (jsonObject == null) {
						trayIcon.setToolTip("不在实验室环境");
					} else {
						logger.debug(localMacAddress + ":" + jsonObject.getString("message"));
						trayIcon.setToolTip(jsonObject.getString("message"));
					}
				}
			}

			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}
	}
}
