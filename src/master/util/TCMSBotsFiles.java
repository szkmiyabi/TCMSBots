package master.util;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class TCMSBotsFiles {

	//設定ファイルの読み込み
	public String[] getUserProperties(String fileName) {
		String[] ret = new String[7];
		Properties properties = new Properties();
		try {
			InputStream is = new FileInputStream(fileName);
			InputStreamReader in = new InputStreamReader(is, "UTF-8");
			properties.load(in);
			is.close();
			ret[0] = properties.getProperty("uid");
			ret[1] = properties.getProperty("pswd");
			ret[2] = properties.getProperty("systemWait");
			ret[3] = properties.getProperty("longWait");
			ret[4] = properties.getProperty("midWait");
			ret[5] = properties.getProperty("shortWait");
			ret[6] = properties.getProperty("os");

		} catch(Exception ex) {}
		return ret;
	}
}
