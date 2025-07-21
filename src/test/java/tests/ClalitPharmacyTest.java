package tests;
import static org.testng.Assert.assertTrue;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.HomePage;
import pages.ServicesLocatorPage;
import pages.PharmacySearchPage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class ClalitPharmacyTest {
    private WebDriver driver;
    private HomePage homePage;
    private ServicesLocatorPage servicesPage;
    protected static ExtentReports extent;
    protected static ExtentTest test;


    public String captureScreenshot(String name) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String screenshotPath = "target/screenshots/" + name + "_" + date + ".png";
        try {
            FileUtils.copyFile(src, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotPath;
    }
   // @BeforeClass
    @BeforeMethod
    public void setUp(Method method) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        ExtentSparkReporter htmlReporter=new ExtentSparkReporter("target/test-output/extent-report.html");
        extent=new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Tester","Automation Bot");
        test=extent.createTest(method.getName());

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get("https://www.clalit.co.il/he/Pages/default.aspx");

        homePage = new HomePage(driver);


    }

    @Test
    public void pharmacySearchTest() {

        test.info("לחיצה על איתור שירותים");
        homePage.clickServiceLocator();

        servicesPage = new ServicesLocatorPage(driver);

        servicesPage.clickPharmacyTab();
        test.info("לחיצה על טאב בתי מרקחת");

        servicesPage.checkOpenNow();
        test.info("בחירה בפתוח עכשיו");

        servicesPage.clickSearchButton();

        int hasResults = servicesPage.resultsExist();
        test.pass("בוצע חיפוש, נמצאו: " + hasResults + " תוצאות.");
        //System.out.println("האם נמצאו תוצאות? " + hasResults);

        PharmacySearchPage resultsPage = new PharmacySearchPage(driver);
        assertTrue(resultsPage.isPageLoaded(), "עמוד התוצאות לא נטען כראוי.");
        test.pass("עמוד התוצאות נטען בהצלחה");

        int count = resultsPage.countResults();
        test.pass("נמצאו " + count + " בתי מרקחת.");

        System.out.println("כמות בתי המרקחת שנמצאו: " + count);
    }

    @Test
    public void testSearchFromTopBar() {
        test.info("חיפוש דרך סרגל עליון");

        homePage.openTopSearch();
        homePage.searchFor("בית חולים");

        boolean found = homePage.searchResultsExist();
       // System.out.println(" chearch תוצאות בחיפוש העליון: " + found);
        assertTrue(found, "לא נמצאו תוצאות בחיפוש העליון.");
        test.pass("נמצאו תוצאות בחיפוש העליון.");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = captureScreenshot(result.getName());
            test.fail("הטסט נכשל. צילום מסך צורף.")
                    .addScreenCaptureFromPath(screenshotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("הטסט עבר בהצלחה.");
        }

        if (driver != null) {
            driver.quit();
        }

        extent.flush();
    }

}
