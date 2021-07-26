package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class BalanceScalePage 
{
	private WebDriver driver ;
	public BalanceScalePage(WebDriver driver) 
	{
		this.driver = driver;
	}

	public List<String> getCoinsList()
	{
		Reporter.log("Get Coins");
		List<WebElement> coins = driver.findElements(By.xpath("//div[@class='coins']/button"));
		List<String> coinNames = new ArrayList<String>();
		for(WebElement coin: coins)
			coinNames.add(coin.getText());
		return coinNames;
	}
	
	public void fillBowl(List<String> coinList, String side)
	{
		Reporter.log("Fill "+side+" bowl of Weighing scale");
		List<WebElement> rows = driver.findElements(By.xpath("//div[contains(text(),'"+side+"')]/following-sibling::div[@class ='board-row']"));
		int listSize = coinList.size();
		int i=0;
		for(WebElement row: rows)
		{
			if (i<listSize)
			{
				List<WebElement> cells = row.findElements(By.tagName("input"));
				for(WebElement cell: cells)
					{
						if (i<listSize)
						{
							cell.sendKeys(coinList.get(i));
							i++;
						}
						else
							break;						
					}
			}
			else
				break;
		}

		
	}
	
	public void waitForResultToLoad(int noOfWeighings) 
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Weighings']/following-sibling::ol/li["+noOfWeighings+"]")));
		
    }
	public void getWeighings()
	{
		List<WebElement> weighings = driver.findElements(By.xpath("//div[text()='Weighings']/following-sibling::ol/li"));
		Reporter.log("Total Number of  Weighings : " + weighings.size());
		//List<String> WeighingList = new LinkedList<String>();
		Reporter.log("List of  Weighings");
		int i =1;
		for(WebElement weigh: weighings)
		{
			Reporter.log("Iteration "+i+" : "+ weigh.getText());
			//WeighingList.add(weigh.getText());
			i++;
		}
		//return WeighingList;
	}
	
	public int getNoOfWeighings()
	{
		List<WebElement> weighings = driver.findElements(By.xpath("//div[text()='Weighings']/following-sibling::ol/li"));		
		return weighings.size();
	}
	
	public void clickOnFakeCoin(String fakeCoin)
	{
		Reporter.log("Click on Fake Coin");
		driver.findElement(By.xpath("//div[@class='coins']/button[text()='"+ fakeCoin +"']")).click();
	}
	
	public void clickWeigh()
	{
		Reporter.log("Button Click: Weigh");
		driver.findElement(By.id("weigh")).click();
	}
	
	public void clickReset()
	{
		Reporter.log("Button Click: Reset");
		driver.findElement(By.xpath("//button[text()='Reset']")).click();
	}
	
	public String getResult()
	{
		Reporter.log("Getting Weighing result");
		return driver.findElement(By.xpath("//div[@class='result']/button[@id='reset']")).getText();
	}

}
