package com.iotat.main;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.iotat.tray.TrayConfig;
import com.iotat.utils.PropertiesUtils;

public class MainConfig {

	public static void main(String[] args) {
		String usrHome = System.getProperty("user.home");

		if (!PropertiesUtils.checkExist())
			PropertiesUtils.createProperties();

		try {
			PropertyConfigurator.configure(new URL("file:\\" + usrHome + "\\.iotat\\log4j.properties"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Logger logger = Logger.getLogger(MainConfig.class);
		logger.info("Program startup.");
		TrayConfig trayConfig = new TrayConfig();
		trayConfig.Tray();
	}

}
