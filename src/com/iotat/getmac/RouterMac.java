package com.iotat.getmac;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.iotat.main.MainConfig;

public class RouterMac {
	private static Logger logger = Logger.getLogger(MainConfig.class);

	public static String run() {
		Runtime runtime = Runtime.getRuntime();
		String str = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(runtime.exec("arp -a").getInputStream()));
			String line = null;
			StringBuffer b = new StringBuffer();
			while ((line = br.readLine()) != null) {
				b.append(line + "\n");
			}
			str = b.toString();
		} catch (Exception e) {
			logger.error(e);
		}
		return str;
	}

	public static String getRouterMac() {
		String result = null;
		Pattern p = Pattern.compile("([a-f0-9]{2}-){5}[a-f0-9]{2}");
		Matcher m = p.matcher(run());
		if (m.find()) {
			result = m.group();
		}
		if (result == null) {
			return null;
		} else {
			result = result.toUpperCase();
			return result;
		}
	}
}
