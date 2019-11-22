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
		 * ���ϵͳ����
		 */
		if (SystemTray.isSupported()) {
			// ��ȡ��ǰƽ̨��ϵͳ����
			SystemTray tray = SystemTray.getSystemTray();
			// ����һ��ͼƬ��������ͼ�����ʾ
			Image image = Toolkit.getDefaultToolkit().getImage("checkin.PNG");
			// �������ͼ��ʱ�ĵ����˵�
			PopupMenu popupMenu = new PopupMenu();
			MenuItem exitItem = new MenuItem("�˳�");
			MenuItem abttItem = new MenuItem("����");

			exitItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// ����˳��˵�ʱ�˳�����
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

			// ����һ������ͼ��
			TrayIcon trayIcon = new TrayIcon(image, "Loading...", popupMenu);
			StartThread startThread = new StartThread(trayIcon);
			startThread.start();

			// ����ͼ������Ӧ�ߴ�
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

			// �������ͼ�굽ϵͳ����
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				logger.error(e);
			}

		} else {
			JOptionPane.showMessageDialog(null, "ϵͳ��֧����������,����ϵ������Ա.");
			logger.debug("System tray failed.");
			System.exit(0);
		}

	}

	public void changeStart(boolean isStartAtLogon) throws IOException {
		// ע�������ӵ�·��
		String regKey = "HKEY_CURRENT_USER\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run";
		// ѡ��֮��Ҫ��ӻ�ɾ����ֵ��
		String myAppName = "mgtest";
		// �������ӵ�ע���ValueName
		String exePath = "\"E:\\pengzh\\BeyondCompare3\\BCompare.exe\"";
		Runtime.getRuntime().exec("reg " + (isStartAtLogon ? "add " : "delete ") + regKey + " /v " + myAppName
				+ (isStartAtLogon ? " /t reg_sz /d " + exePath : " /f"));
	}

	public void popUp() {
		JOptionPane.showMessageDialog(null,
				"�汾��: 1.0.0-beta\n\n" + "�����飺" + "\n" + "̷������1149284750@qq.com" + "\n" + "���᣺2236103111@qq.com" + "\n",
				"����", JOptionPane.INFORMATION_MESSAGE);

	}
}
