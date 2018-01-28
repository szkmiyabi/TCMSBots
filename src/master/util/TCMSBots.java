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

public class TCMSBots {
	
	//メンバ
	private WebDriver wd;
	private final String url = "https://www.pref.tokushima.lg.jp/cms/";
	private String chrome_driver_path;
	
	private String uid;
	private String pswd;
	private String projectID;
	
	//コンストラクタ
	public TCMSBots(String uid, String pswd, int systemWait, String os, String projectID) {
		this.uid = uid;
		this.pswd = pswd;
		this.projectID = projectID;
		
		//windowsとmac(unix)対応化
		if(os.equals("windows")) {
			this.chrome_driver_path = "./chromedriver.exe";
		} else if(os.equals("mac") || os.equals("unix")) {
			this.chrome_driver_path = "chromedriver";
		}
		
		System.setProperty("webdriver.chrome.driver", chrome_driver_path);
		ChromeOptions copt = new ChromeOptions();
		copt.addArguments("--disable-xss-auditor");
		copt.addArguments("--window-size=1280,900");
		wd = new ChromeDriver(copt);
		wd.manage().timeouts().implicitlyWait(systemWait, TimeUnit.SECONDS);
		wd.get(url);
	}
	
	//ゲッター
	public WebDriver getWd() {
		return wd;
	}

	//セッター
	public void setWd(WebDriver wd) {
		this.wd = wd;
	}
	
	//シャットダウン
	public void shutdown() {
		wd.quit();
	}
	
	//ログイン
	public void login() {
		wd.findElement(By.id("number")).sendKeys(uid);
		wd.findElement(By.id("password")).sendKeys(pswd);
		List<WebElement> submits = wd.findElements(By.className("submit"));
		WebElement btn = submits.get(0).findElements(By.tagName("input")).get(0);
		btn.click();
	}
	
	//ログアウト
	public void logout() {
		List<WebElement> targets = wd.findElements(By.className("top-bar-section"));
		WebElement atag = targets.get(0).findElements(By.tagName("li")).get(2).findElements(By.tagName("a")).get(0);
		atag.click();
	}
	
	//サイト選択
	public void selectProject() {
		WebElement siteSelect = wd.findElement(By.id("site-groups"));
		List<WebElement> opts = siteSelect.findElements(By.tagName("option"));
		for(int i=0; i<opts.size(); i++) {
			WebElement opt = opts.get(i);
			String opt_val = opt.getAttribute("value");
			if(opt_val.equals(projectID)) {
				opt.click();
				break;
			}
		}
	}
	
	//Menu - サイトマップをクリック
	public void clickSitemapMenu() {
		List<WebElement> li = wd.findElements(By.className("side-nav")).get(0).findElements(By.tagName("li"));
		WebElement atag = li.get(0).findElements(By.tagName("a")).get(0);
		atag.click();
	}

	//指定したキーワードのリンクがあるか判定
	public boolean findTreeLinkByKeywd(String keywd) {
		boolean flg = false;
		WebElement div = wd.findElements(By.className("tree")).get(0);
		WebElement ul = div.findElements(By.tagName("ul")).get(0);
		List<WebElement> ttas = ul.findElements(By.className("page-name"));
		Pattern pt = Pattern.compile(".*" + keywd + ".*");
		for(int i=0; i<ttas.size(); i++) {
			WebElement tta = ttas.get(i);
			String sts = tta.getText();
			System.out.println(sts);
			Matcher mt = pt.matcher(sts);
			if(mt.find()) {
				flg = true;
				break;
			}
		}
		return flg;
	}
	
	//キーワード指定してサイトツリーをクリック
	public void clickTreeLinkByKeywd(String keywd) {
		WebElement div = wd.findElements(By.className("tree")).get(0);
		WebElement ul = div.findElements(By.tagName("ul")).get(0);
		List<WebElement> ttas = ul.findElements(By.className("page-name"));
		Pattern pt = Pattern.compile(".*" + keywd + ".*");
		for(int i=0; i<ttas.size(); i++) {
			WebElement tta = ttas.get(i);
			String sts = tta.getText();
			Matcher mt = pt.matcher(sts);
			if(mt.find()) {
				System.out.println(sts + " を削除します。");
				tta.click();
				break;
			}
		}
	}
	
	//確認ダイアログOKを押す
	public void clickConfirmOK() {
		WebElement diag = wd.findElements(By.className("ui-dialog")).get(0);
		WebElement btnline = diag.findElements(By.className("ui-dialog-buttonset")).get(0);
		WebElement btn = btnline.findElements(By.className("ui-button")).get(0);
		btn.click();
	}
	
	//削除リンクをクリック
	public void clickDeleteLink() {
		WebElement pr = wd.findElements(By.className("line")).get(0);
		WebElement btn = pr.findElements(By.tagName("li")).get(1).findElements(By.tagName("button")).get(0);
		btn.click();
	}


}
