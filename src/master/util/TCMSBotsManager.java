package master.util;

import java.util.concurrent.TimeUnit;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;

import master.util.TCMSBots;

public class TCMSBotsManager {
	
	//メンバ
	private TCMSBots tcbt;
	private int longWait;
	private int midWait;
	private int shortWait;
	
	//コンストラクタ
	public TCMSBotsManager(String uid, String pswd, String os, int systemWait, int longWait, int midWait, int shortWait, String projectID) {
		tcbt = new TCMSBots(uid, pswd, systemWait, os, projectID);
		this.longWait = longWait;
		this.midWait = midWait;
		this.shortWait = shortWait;
	}
	
	//シャットダウンのラッパー
	public void doShutdown() {
		tcbt.shutdown();
	}
	
	//ログアウトのラッパー
	public void doLogOut() {
		tcbt.logout();
	}
	
	//ログインのラッパー
	public void doLogIn() {
		tcbt.login();
	}
	
	//サイト選択のラッパー
	public void doSelectProject() {
		tcbt.selectProject();
	}
	
	
	//ウェイト
	public void doWait(int milisecond) {
		try { Thread.sleep(milisecond); } catch(InterruptedException e) {}
	}
	
	//URL参照
	public void browseThisUrl(String url) {
		tcbt.getWd().navigate().to(url);
		doWait(shortWait);
	}
	
	//xpathで指定した箇所をクリック
	public void clickByXpath(String xpath) {
		tcbt.getWd().findElement(By.xpath(xpath)).click();
	}
	
	//キーワードしてしてページを削除
	public void deletePageByKeywd(String keywd) {
		while(true) {
			clickByXpath("/html/body/div[1]/div/div[2]/ul/li/ul/li[1]/label/span");
			doWait(shortWait);
			
			if(!tcbt.findTreeLinkByKeywd(keywd)) break;
			
			tcbt.clickTreeLinkByKeywd(keywd);
			doWait(shortWait);
			tcbt.clickDeleteLink();
			doWait(midWait);
			tcbt.clickConfirmOK();
			doWait(midWait);
			tcbt.clickSitemapMenu();
			doWait(midWait);
		}
	}
	
	//県報一覧の配下ページをキーワードしてしてページを削除
	public void deletePageInListByKeywd(String keywd) {
		while(true) {
			clickByXpath("/html/body/div[1]/div/div[2]/ul/li/ul/li[1]/label/span");
			doWait(shortWait);
			clickByXpath("/html/body/div[1]/div/div[2]/ul/li/ul/li[1]/a[3]");
			doWait(longWait);
			
			if(!tcbt.findTreeLinkByKeywd(keywd)) break;
			
			tcbt.clickTreeLinkByKeywd(keywd);
			doWait(shortWait);
			tcbt.clickDeleteLink();
			doWait(midWait);
			tcbt.clickConfirmOK();
			doWait(midWait);
			tcbt.clickSitemapMenu();
			doWait(midWait);
		}
	}
	
	
}
