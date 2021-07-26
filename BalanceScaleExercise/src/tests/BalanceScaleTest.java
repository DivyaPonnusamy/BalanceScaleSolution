package tests;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.Reporter;

import pages.*;

public class BalanceScaleTest 
{
 
	 private static balanceScalePage balancescalePageObj;
	 public String appUrl = "http://ec2-54-208-152-154.compute-1.amazonaws.com/";
	 String driverPath = "C:\\\\Selenium\\\\chromedriver_91\\\\chromedriver.exe";	 
	 public static WebDriver driver ;

	 @BeforeClass
		public static void initCalculator() {
			balancescalePageObj = new balanceScalePage(driver);
		}

	  @BeforeTest
	  public void LaunchApplication() 
	  {
		  Reporter.log("Launching Application");
		  System.setProperty("webdriver.chrome.driver",driverPath);
		  driver = new ChromeDriver();
		  driver.manage().window().maximize();
		  driver.get(appUrl);		  
		  String expectedAppTitle = "React App";
	      String actualAppTitle = driver.getTitle();
	      Assert.assertEquals(actualAppTitle, expectedAppTitle);		  
	  }
	  
	  @Test
	  public void FindTheFakeCoin() throws InterruptedException 
	  {
		  Reporter.log("Test Case: Finding the fake bar");
		  final List<String> COINNAMESLIST= balancescalePageObj.getCoinsList();
		  List<String> coinNames = COINNAMESLIST;
		  Reporter.log("List of Coins: "+ COINNAMESLIST);
		  int noOfCoins = coinNames.size();
		  final int NUMBEROFCOINS = noOfCoins;
		  Reporter.log("Total Number of Coins: "+ NUMBEROFCOINS);
		  int noOfLeftCoins = noOfCoins;
		  String strResult = "";
		  noOfLeftCoins = noOfCoins/2;
		  List<String> LeftBowlCoins = new ArrayList<String>();
		  List<String> RighBowlCoins = new ArrayList<String>();
		  if(noOfCoins!=1)
			  while (noOfLeftCoins!=0)
			  {	
				  LeftBowlCoins = coinNames.subList(0, noOfLeftCoins);
				  RighBowlCoins = coinNames.subList(noOfLeftCoins, noOfLeftCoins*2);
				  balancescalePageObj.fillBowl(LeftBowlCoins,"left");				  
				  balancescalePageObj.fillBowl(RighBowlCoins,"right");
				  int noOfWeighings = balancescalePageObj.getNoOfWeighings();
				  balancescalePageObj.clickWeigh();
				  balancescalePageObj.waitForResultToLoad(noOfWeighings+1);
				  strResult = balancescalePageObj.getResult();
				  if(strResult.equals("="))
					  coinNames = coinNames.subList((noOfLeftCoins*2), NUMBEROFCOINS);
				  else if(strResult.equals("<"))
					  coinNames = LeftBowlCoins;
				  else if(strResult.equals(">"))
					  coinNames = RighBowlCoins;
				  
				  noOfCoins= coinNames.size(); 
				  noOfLeftCoins = noOfCoins/2;
				  strResult = "";
				  balancescalePageObj.clickReset();
			  }
		  
		  Reporter.log("Fake Coin is: " + coinNames.get(0));
		  balancescalePageObj.clickOnFakeCoin(coinNames.get(0));		  
		  Assert.assertEquals(driver.switchTo().alert().getText(), "Yay! You find it!");	
		  driver.switchTo().alert().accept();
		  balancescalePageObj.getWeighings();
		 
	  }
	  
	  @AfterTest
	  public void CloseApplication() 
	  {
		  Reporter.log("Closing Application");
		  driver.close();
	  }
}
