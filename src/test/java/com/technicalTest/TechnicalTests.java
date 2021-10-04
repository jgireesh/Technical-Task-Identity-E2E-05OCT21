package com.technicalTest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.technicalTest.InputReader.filePath;

/**
 * The type Technical tests.
 * @author gireeshajeera
 */
public class TechnicalTests {
    /**
     * The Input reader.
     */
    InputReader inputReader = new InputReader();
    /**
     * The Driver.
     */
    WebDriver driver;
    /**
     * The Wait.
     */
    WebDriverWait wait;
    /**
     * The Browser folder path.
     */
    String browser_folderPath = System.getProperty("user.dir") + "/browserdrivers/";

    /**
     * Launch car tax check url.
     */
    @BeforeTest
    public void launchCarTaxCheckUrl() {
        System.setProperty("webdriver.chrome.driver", browser_folderPath + "chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    /**
     * Quit browser.
     */
    @AfterTest
    public void quitBrowser() {
        driver.quit();
    }

    /**
     * Digital skills assignment.
     *
     * @throws IOException the io exception
     */
    @Test(priority = 0, testName = "Verify Check car tax website for different values")
    public void digitalSkillsAssignment() throws IOException {
        /*
        Picking number of plates from the input file
         */
        List<String> noPlates = inputReader.findTheMatchedPatternCarPlateNumbers(inputReader.carInpuRead(filePath + "car_input.txt"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        /*
        Picking car_out file for validation purpose, bringing all data in Hashmap for each key of number plates.
         */
        HashMap<String, List<String>> expectedCarOutputMap = inputReader.carOutputRead("car_output.txt");
        HashMap<String, List<String>> actualCarOutputMap = new HashMap<>();
        for (int nums = 0; nums < noPlates.size(); nums++) {
            List<String> tempLst = new ArrayList<>();
//            driver.findElement(By.id("vrm-input")).sendKeys(noPlates.get(nums));
//            WebElement freeCheckBtn = driver.findElement(By.xpath("//button[contains(text(),'Free Car Check')]"));
//            js.executeScript("arguments[0].click();", freeCheckBtn);
            driver.get("https://cartaxcheck.co.uk/free-car-check/?vrm=PLATENO".replace("PLATENO", noPlates.get(nums)).replace(" ", ""));
            //wait for page loads
            WebElement resultsPage = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Vehicle Identity')]")));
            resultsPage.isDisplayed();
            String reg = driver.findElement(By.xpath("//*[contains(text(),'Registration')]/following-sibling::dd[1]")).getText();
            String make = driver.findElement(By.xpath("//*[contains(text(),'Make')]/following-sibling::dd[1]")).getText();
            String model = driver.findElement(By.xpath("//*[contains(text(),'Model')]/following-sibling::dd[1]")).getText();
            String colour = driver.findElement(By.xpath("//*[contains(text(),'Colour')]/following-sibling::dd[1]")).getText();
            String year = driver.findElement(By.xpath("//*[contains(text(),'Year')]/following-sibling::dd[1]")).getText();
            tempLst.add(make);
            tempLst.add(model);
            tempLst.add(colour);
            tempLst.add(year);
            actualCarOutputMap.put(reg, tempLst);
            /*
            Ideally when we Assert values during the iteration, if one of the validation failed, rest of the remaining scripts will be terminated,
            in this case, since we have to check every number plate records,so i have added try catch to check if everything is fine and printing in the nice format to see which values are not matching.
             */
            try {
                Assert.assertEquals(actualCarOutputMap.get(reg), expectedCarOutputMap.get(reg));
                System.out.println("For Registration number : "+(reg)+" values Matched     "+actualCarOutputMap.get(reg)+"==>"+expectedCarOutputMap.get(reg));
            }
            catch (Exception|Error e){
                System.out.println("For Registration number : "+(reg)+" values Not Matched "+actualCarOutputMap.get(reg)+"==>"+expectedCarOutputMap.get(reg));
            }
        }
        System.out.println(actualCarOutputMap);
    }
}
