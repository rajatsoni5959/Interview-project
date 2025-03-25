package com.saucedemo;
import com.utils.ScreenshotUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SauceDemoSortingTest {
    private WebDriver driver; 
    private static Logger logger = Logger.getLogger(SauceDemoSortingTest.class);

    @BeforeClass
    public void setup() {
       
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        
       
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        logger.info("Navigating to SauceDemo...");
        
       
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        logger.info("Logged in successfully.");
    }

    @Test
    public void testSortByPriceLowToHigh() {
        try {
           
            Thread.sleep(5000);
       
            WebElement sortDropdown = driver.findElement(By.className("product_sort_container"));
            Select select = new Select(sortDropdown);
            select.selectByVisibleText("Price (low to high)");
            logger.info("Sorting by 'Price (low to high)'...");
        
            Thread.sleep(5000);

            List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
            List<Float> prices = new ArrayList<>();
                       
            for (WebElement priceElement : priceElements) {
                String priceText = priceElement.getText().replace("$", "").trim();
                prices.add(Float.parseFloat(priceText));
            }
           
            List<Float> sortedPrices = new ArrayList<>(prices);
            Collections.sort(sortedPrices);

            Assert.assertEquals(prices, sortedPrices, "The prices are not sorted in ascending order.");
            logger.info("Prices are correctly sorted in ascending order.");
        } catch (Exception e) {
            logger.error("Test failed: " + e.getMessage());
            ScreenshotUtil.captureScreenshot(driver, "testSortByPriceLowToHigh_Failure");
            Assert.fail("Test failed due to an exception.");
        }
    }

    @AfterClass
    public void teardown() {
        
        if (driver != null) {
            driver.quit();
            logger.info("Test completed. Browser closed.");
        }
    }
}
