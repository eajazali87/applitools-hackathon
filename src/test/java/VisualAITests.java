import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class VisualAITests {

  WebDriver driver;
  TestUtilities testUtil = null;
  Eyes eyes = null;

  By userNameInputField = By.xpath("//div[1][@class='form-group']/input");
  By passwordInputField = By.xpath("//div[2][@class='form-group']/input");
  By compareExpenses = By.id("showExpensesChart");

  public String suiteName = "", url = "";

  @Test()
  public void loginPageUIElementsTest() {
    eyes.open(driver, "Login Page", "Login page UI elements Test");
    eyes.checkWindow();
    eyes.closeAsync();
  }

  @DataProvider(name = "data-provider")
  public Object[][] dataProviderMethod() {
    return new Object[][]{
        {"", "", "Both Username and Password must be present",
            "Login Test - Both username and password fields is empty"},
        //empty username and password
        {"testuname", "", "Password must be present", "Login Test - password is empty"},
        //only username and no password
        {"", "testpwd", "Username must be present", "Login Test - username is empty"},
        //only password and no username
        {"testuname", "testpwd", "Login Test - Success", "Login Test - Valid username and password"}
        //valid username and password
    };
  }

  @Test(dataProvider = "data-provider")
  public void userLoginDataDrivenTest(String uname, String pwd, String alertBanner,
      String testName) {

    eyes.open(driver, "Login Page Validation", testName, new RectangleSize(800, 800));

    //enter username and password
    testUtil.sendKeys((userNameInputField), uname);
    testUtil.sendKeys((passwordInputField), pwd);

    //login
    testUtil.click(By.xpath("//div[@class='buttons-w']/button"));
    eyes.checkWindow(testName);
    eyes.closeAsync();
  }

  @Test
  public void tableSortTest() {
    eyes.setForceFullPageScreenshot(true);
    eyes.open(driver, "Table Sort Test", "Amount column sort by ascending Test",
        new RectangleSize(800, 800));

    //enter username and password
    testUtil.sendKeys((userNameInputField), "test");
    testUtil.sendKeys((passwordInputField), "test");

    //login
    testUtil.click(By.xpath("//div[@class='buttons-w']/button"));

    //click amount
    testUtil.click(By.id("amount"));

    eyes.checkWindow("Window with table");

    eyes.closeAsync();

  }

  @Test
  public void canvasChartTest() throws Exception {
    eyes.open(driver, "Canvas Chart Test", "Canvas Sort Data Test", new RectangleSize(800, 800));

    //enter username and password
    testUtil.sendKeys((userNameInputField), "test");
    testUtil.sendKeys((passwordInputField), "test");

    //login
    testUtil.click(By.xpath("//div[@class='buttons-w']/button"));

    //click compare expenses
    testUtil.click(compareExpenses);
    testUtil.click(By.id("addDataset"));
    //Thread.sleep(2000);

    eyes.checkWindow("Compare Charts");

    eyes.closeAsync();

  }

  @Test
  public void dynamicContentTest() {

    eyes.setMatchLevel(MatchLevel.LAYOUT2);

    eyes.open(driver, "Dynamic Content Test", "Dynamic Content GIF Test",
        new RectangleSize(800, 800));

    if (suiteName.contains("V2")) {
      testUtil.getUrl("https://demo.applitools.com/hackathonV2.html?showAd=true");
    } else {
      testUtil.getUrl("https://demo.applitools.com/hackathon.html?showAd=true");
    }

    //enter username and password
    testUtil.sendKeys((userNameInputField), "test");
    testUtil.sendKeys((passwordInputField), "test");

    //login
    testUtil.click(By.xpath("//div[@class='buttons-w']/button"));

    eyes.checkWindow("Compare GIFs");

    eyes.closeAsync();
  }

  @BeforeClass
  public void beforeClass() {
    Properties properties = System.getProperties();
    try {
      properties.load(new FileReader(new File("resources/test.properties")));

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);

    }
    System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
    driver = new ChromeDriver();
    testUtil = new TestUtilities(driver);

    eyes = new Eyes();
    eyes.setApiKey(System.getProperty("applitools.api.key"));
    eyes.setBatch(new BatchInfo("Visual AI Tests"));
  }

  @BeforeMethod
  public void beforeMethod() {
    testUtil.getUrl(url);
  }

  @AfterMethod
  public void afterMethod() {
    eyes.abortIfNotClosed();
  }


  @AfterClass
  public void afterClass() {
    driver.close();
    driver.quit();
  }

  @BeforeSuite(alwaysRun = true)
  public void beforeSuite(final ITestContext testContext) {
    XmlSuite suite = testContext.getSuite().getXmlSuite();
    suiteName = suite.getName();
    if (suiteName.contains("V2")) {
      url = "https://demo.applitools.com/hackathonV2.html";
    } else {
      url = "https://demo.applitools.com/hackathon.html";
    }
  }
}