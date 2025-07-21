package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PharmacySearchPage {
    private WebDriver driver;

    public PharmacySearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isPageLoaded() {
        try {
            WebElement title = driver.findElement(By.cssSelector("h1, h2, .title"));
            return title.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int countResults() {
        return driver.findElements(By.cssSelector(".searchResult, .pharmacy-item")).size();
    }

    //public boolean containsPharmacyNamed(String name) {
    //  return driver.getPageSource().contains(name);
    // }
}
