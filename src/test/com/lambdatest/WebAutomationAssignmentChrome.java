package com.lambdatest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebAutomationAssignmentChrome {

	private WebDriver driver;
	
	public void setup() throws MalformedURLException {

		String hubURL = "@hub.lambdatest.com/wd/hub";
		String username = "abhisekghosh";
		String accessKey = "LT_94yiLLgnCv1dm2ZnD0hwZbhRKBsc4xri3rWIpJU3SOYg39y";

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", "Chrome");

		ChromeOptions browserOptions = new ChromeOptions();
		browserOptions.setPlatformName("Windows 10");
		browserOptions.setBrowserVersion("137.0");
		HashMap<String, Object> ltOptions = new HashMap<String, Object>();
		ltOptions.put("username", "abhisekghosh");
		ltOptions.put("accessKey", "LT_94yiLLgnCv1dm2ZnD0hwZbhRKBsc4xri3rWIpJU3SOYg39y");
		ltOptions.put("visual", true);
		ltOptions.put("video", true);
		ltOptions.put("seCdp", true);
		ltOptions.put("network", true);
		ltOptions.put("build", "Automation_Build");
		ltOptions.put("project", "LambdaTestAutomationAssignment");
		ltOptions.put("smartUI.project", "Test_Automation_ChromeWindows");
		ltOptions.put("name", "Test_Automation_ChromeWindows");
		ltOptions.put("tunnel", true);
		ltOptions.put("console", true);
		ltOptions.put("selenium_version", "4.0.0");
		ltOptions.put("w3c", true);
		ltOptions.put("accessibility", true);
		browserOptions.setCapability("LT:Options", ltOptions);

		driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + hubURL), capabilities);
	}

	public void automationAssignment() throws InterruptedException {

		// Navigate to https://www.lambdatest.com.
		driver.get("https://www.lambdatest.com");

		// Perform an explicit wait till the time all the elements in the DOM are available.
		WebElement bookADemoButton  = driver.findElement(By.xpath("//button[@role='button'][text()='Book a Demo']"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(bookADemoButton));

		// Scroll to the WebElement ‘Explore all Integrations’ using the scrollIntoView() method
		
		WebElement getStartedForFree = driver.findElement(By.xpath("(//a[contains(text(),'Get Started For Free')])[4]"));
		WebElement exploreAllIntegrations = driver.findElement(By.xpath("//a[contains(text(),'Explore all Integrations')]"));
		
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].scrollIntoView();", exploreAllIntegrations);
		js.executeScript("arguments[0].scrollIntoView();", getStartedForFree);
		Thread.sleep(3000);

		// Click on the link and ensure that it opens in a new tab
		String exploreAllIntegrationsLink = exploreAllIntegrations.getAttribute("href");
		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
		exploreAllIntegrations.sendKeys(selectLinkOpeninNewTab);

		// Save the window handles in a List (or array).
		Set<String> handles = driver.getWindowHandles();

		// Print the window handles of the opened windows
		List<String> handlesList = new ArrayList<>(handles);
		for (String s : handlesList) {
			System.out.println(s);
		}

		//Switching to Child window
		
		driver.switchTo().window(handlesList.get(1).toString());
		WebElement lambdaTestIntegrationText = driver.findElement(By.xpath("//h1[text() = 'LambdaTest Integrations']"));
		wait.until(ExpectedConditions.visibilityOf(lambdaTestIntegrationText));

		// Verify whether the URL is the same as the expected URL
		if (exploreAllIntegrationsLink.equalsIgnoreCase(driver.getCurrentUrl().toString())) {
			markStatus("passed", "URL verified and Confirm Match", driver);
			Thread.sleep(150);

			System.out.println("TestFinished");
		} else {
			markStatus("failed", "URL is not matching", driver);
		}

	}

	public void tearDown() {
		driver.close();
	}

	public static void markStatus(String status, String reason, WebDriver driver) {
		JavascriptExecutor jsExecute = (JavascriptExecutor) driver;
		jsExecute.executeScript("lambda-status=" + status);
		System.out.println(reason);
	}

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		WebAutomationAssignmentChrome test = new WebAutomationAssignmentChrome();
		test.setup();
		test.automationAssignment();
		test.tearDown();
	}

}
