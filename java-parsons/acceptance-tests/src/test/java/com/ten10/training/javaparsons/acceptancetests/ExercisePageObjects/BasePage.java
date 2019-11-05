package com.ten10.training.javaparsons.acceptancetests.ExercisePageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class BasePage {

    final ChromeDriver driver;
    protected final WebDriverWait wait;
    private String host = "localhost:";
    private String port;
    private String url;

    BasePage(ChromeDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 20);
        setURL();
    }

    void waitAndClick(By selector) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(selector));
        element.click();
    }

    void findAndType(By elementSelector, String inputString) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementSelector));
        element.sendKeys(inputString);
    }

    void waitForDivToBeVisible(By selector) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    /*
    This code is written to enable the dynamic setting of the host in the url
    Once a system property is able to be set dynamically then this can be implemented
    as for port below
     */
    private void setHost() {
//        String host = System.getProperty("YOUR_PROPERTY_NAME");
//        if (host.equals("")){
        host = "localhost:";
//        } else
//            host = host;
    }

    private void setPort() {
        try {
            String port = System.getProperty("test.server.port");
            if (port.equals("")) {
                this.port = "8080";
            } else
                this.port = port;
        } catch (NullPointerException e) {
            port = "8080";
        }
    }

    private void setURL() {
        setHost();
        setPort();
        url = "http://" + host + port;
    }

    public void goToHomepage() {
        driver.get(url);
    }


}
