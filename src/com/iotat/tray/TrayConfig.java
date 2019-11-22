package com.iotat.tray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

public class TrayConfig {
	private static Logger logger = Logger.getLogger(TrayConfig.class);

	public TrayConfig() {
		logger.info("Already System tray.");
	}

	public void Tray() {
		/*
		 * 添加系统托盘
		 */
		if (SystemTray.isSupported()) {
			// 获取当前平台的系统托盘
			SystemTray tray = SystemTray.getSystemTray();
			// 加载一个图片用于托盘图标的显示
			Image image = Toolkit.getDefaultToolkit().getImage("checkin.PNG");
			// 创建点击图标时的弹出菜单
			PopupMenu popupMenu = new PopupMenu();
			MenuItem exitItem = new MenuItem("退出");
			MenuItem abttItem = new MenuItem("关于");

			exitItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 点击退出菜单时退出程序
					logger.info("Program exit");
					System.exit(0);
				}
			});
			abttItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					popUp();
				}
			});
			popupMenu.add(exitItem);
			popupMenu.add(abttItem);

			// 创建一个托盘图标
			TrayIcon trayIcon = new TrayIcon(image, "Loading...", popupMenu);
			StartThread startThread = new StartThread(trayIcon);
			startThread.start();

			// 托盘图标自适应尺寸
			trayIcon.setImageAutoSize(true);

			trayIcon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					logger.info("Program startup.");
				}
			});
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					switch (e.getButton()) {
					case MouseEvent.BUTTON1: {
						logger.info("Tray icon clicked with left mouse button.");
						break;
					}
					case MouseEvent.BUTTON2: {
						logger.info("Tray icon is right clicked.");
						break;
					}
					default: {
						break;
					}
					}
				}
			});

			// 添加托盘图标到系统托盘
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				logger.error(e);
			}

		} else {
			JOptionPane.showMessageDialog(null, "系统不支持托盘运行,请联系开发人员.");
			logger.debug("System tray failed.");
			System.exit(0);
		}

	}

	public void changeStart(boolean isStartAtLogon) throws IOException {
		// 注册表内添加的路径
		String regKey = "HKEY_CURRENT_USER\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run";
		// 选项之下要添加或删除的值名
		String myAppName = "mgtest";
		// 分配给添加的注册表ValueName
		String exePath = "\"E:\\pengzh\\BeyondCompare3\\BCompare.exe\"";
		Runtime.getRuntime().exec("reg " + (isStartAtLogon ? "add " : "delete ") + regKey + " /v " + myAppName
				+ (isStartAtLogon ? " /t reg_sz /d " + exePath : " /f"));
	}

	public void popUp() {
		JOptionPane.showMessageDialog(null,
				"版本号: 1.0.0-beta\n\n" + "开发组：" + "\n" + "谭本超：1149284750@qq.com" + "\n" + "缪玲：2236103111@qq.com" + "\n",
				"关于", JOptionPane.INFORMATION_MESSAGE);

	}
}
