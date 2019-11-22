package com.iotat.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PropertiesUtils {

	public static void createProperties() {
		String usrHome = System.getProperty("user.home");
		File logPah = new File(usrHome + "\\.iotat");
		if (!logPah.exists()) {
			logPah.mkdir();
		}
		File propertiFile = new File(usrHome + "\\.iotat\\log4j.properties");

		try {
			FileWriter fileWriter = new FileWriter(propertiFile);
			String content = "log4j.rootLogger = DEBUG,Console,Stdout\r\n" + "\r\n"
					+ "log4j.appender.Console=org.apache.log4j.ConsoleAppender\r\n"
					+ "log4j.appender.Console.layout=org.apache.log4j.PatternLayout\r\n"
					+ "log4j.appender.Console.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} [%p] %m%n\r\n" + "\r\n"
					+ "log4j.logger.java.sql.ResultSet=INFO\r\n" + "log4j.logger.org.apache=INFO\r\n"
					+ "log4j.logger.java.sql.Connection=DEBUG\r\n" + "log4j.logger.java.sql.Statement=DEBUG\r\n"
					+ "log4j.logger.java.sql.PreparedStatement=DEBUG\r\n" + "\r\n"
					+ "log4j.appender.Stdout=org.apache.log4j.DailyRollingFileAppender\r\n"
					+ "log4j.appender.Stdout.File=./log/run.log\r\n" + "log4j.appender.Stdout.Append=true\r\n"
					+ "log4j.appender.Stdout.Threshold = DEBUG\r\n"
					+ "log4j.appender.Stdout.layout=org.apache.log4j.PatternLayout\r\n"
					+ "log4j.appender.Stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} [%p] %m%n";
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public static boolean checkExist() {
		File propertyFile = new File(System.getProperty("user.home") + "\\.iotat\\log4j.properties");
		if (propertyFile.exists()) {
			return true;
		}
		return false;

	}
}
