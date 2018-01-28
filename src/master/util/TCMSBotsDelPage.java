package master.util;

public class TCMSBotsDelPage {

	public static void main(String[] args) {

		String uid = "";
		String pswd = "";
		int systemWait = 0;
		int longWait = 0;
		int midWait = 0;
		int shortWait = 0;
		String os = "";
		String projectID = "29";

		TCMSBotsFiles fo = new TCMSBotsFiles();
		String[] prop = fo.getUserProperties("user.properties");
		uid = prop[0].toString();
		pswd = prop[1].toString();
		systemWait = Integer.parseInt(prop[2].toString());
		longWait = Integer.parseInt(prop[3].toString());
		midWait = Integer.parseInt(prop[4].toString());
		shortWait = Integer.parseInt(prop[5].toString());
		os = prop[6].toString();
		
		TCMSBotsManager tcbtm = new TCMSBotsManager(uid, pswd, os, systemWait, longWait, midWait, shortWait, projectID);
		tcbtm.doWait(shortWait);
		tcbtm.doLogIn();
		tcbtm.doSelectProject();
		tcbtm.doWait(shortWait);
		tcbtm.browseThisUrl("https://www.pref.tokushima.lg.jp/cms/site_maps/frame?site_groups=29");
		tcbtm.deletePageInListByKeywd("テキスト");
		
	}

}
