package com.iotat.getmac;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.iotat.main.MainConfig;

public class LocalMac {
	private static Logger logger = Logger.getLogger(MainConfig.class);

	public static String getLocalMac() throws SocketException {
		InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			logger.error(e);
		}
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		StringBuffer macStringBuffer = new StringBuffer("");
		if (mac == null) {
			return null;
		} else {
			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
					macStringBuffer.append("-");
				}
				int temp = mac[i] & 0xff;
				String str = Integer.toHexString(temp);
				if (str.length() == 1) {
					macStringBuffer.append("0" + str);
				} else {
					macStringBuffer.append(str);
				}
			}
			return macStringBuffer.toString().toUpperCase();
		}

	}
}
