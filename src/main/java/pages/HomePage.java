package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    public void clickServiceLocator() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            By closePopup = By.id("onetrust-close-btn-container");
            WebElement popup = wait.until(ExpectedConditions.elementToBeClickable(closePopup));
            popup.click();
        } catch (Exception ignored) {
            System.out.println("No popup appeared");
        }

        try {
            By serviceLocator = By.xpath("//a[contains(text(),'איתור שירותים')]");
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(serviceLocator));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to click on איתור שירותים");
        }
    }


    private By topSearchButton = By.xpath("//a[contains(@class, 'btn-search')]");
    private By searchInput = By.xpath("//*[@id='ClalitNewMaster_ClalitSearchBoxUC_mainSearchField']");
    private By searchSubmitButton = By.xpath("//*[@id='searchButton']");
    private By searchResults = By.xpath("//*[@id='search_results_list']");

    public void openTopSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(topSearchButton)).click();
    }

    public void searchFor(String query) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(query);

        driver.findElement(searchSubmitButton).click();
    }

    public boolean searchResultsExist() {
        List<WebElement> results = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchResults));
        return !results.isEmpty();
    }
}
