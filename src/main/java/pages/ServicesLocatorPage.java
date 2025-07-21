package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class ServicesLocatorPage {
    private WebDriver driver;

    public ServicesLocatorPage(WebDriver driver) {
        this.driver = driver;
    }

    // טאב "בתי מרקחת"
    private By pharmacyTab = By.xpath("//a[@id='pharmacies']");

    // צ'קבוקס "פתוחים עכשיו"
    private By openNowCheckbox = By.xpath("//label[@for='checkbox10' and contains(text(), 'פתוח עכשיו')]");

    // כפתור "חיפוש"
    private By searchButton = By.xpath("//a[contains(@class, 'sefersherut-search-btn') and text()='חיפוש']");

    // רשימת תוצאות – לפי אלמנט שמופיע רק בתוצאה
    private By resultItems = By.cssSelector("#search_results_list li.result-list-item");

    // אלמנט שמכיל את מספר התוצאות המדויק
    private By resultsCount = By.cssSelector("strong.result-list-title-num");

    public void clickPharmacyTab() {
        driver.findElement(pharmacyTab).click();
    }

    public void checkOpenNow() {
        WebElement checkbox = driver.findElement(openNowCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void clickSearchButton() {
        driver.findElement(searchButton).click();
    }


    public int resultsExist() {
        try {
            WebElement countElement = driver.findElement(resultsCount);
            String countText = countElement.getText().trim();
            return Integer.parseInt(countText);
        } catch (Exception e) {
            return 0;
        }
    }


}
