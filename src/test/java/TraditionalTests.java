import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.Assert;
import org.testng.xml.XmlSuite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class TraditionalTests {

  WebDriver driver;
  TestUtilities testUtil = null;

  By logoImage = By.xpath("//img[@src='img/logo-big.png']");
  By logoImageClickable = By.xpath("//div[@class='logo-w']/a");
  By loginFormHeader = By.xpath("//h4[@class='auth-header']");

  By userNameLabel = By.xpath("//div[1][@class='form-group']/label");
  By userNameInputField = By.xpath("//div[1][@class='form-group']/input");
  By userNameIcon = By.xpath("//div[@class='pre-icon os-icon os-icon-user-male-circle']");

  By passwordLabel = By.xpath("//div[2][@class='form-group']/label");
  By passwordInputField = By.xpath("//div[2][@class='form-group']/input");
  By fingerPrintIcon = By
      .xpath("//div[2][@class='form-group']/div[@class='pre-icon os-icon os-icon-fingerprint']");

  By rememberMeLabel = By.xpath("//div[@class='buttons-w']/div[1]/label");
  By loginButton = By.xpath("//div[@class='buttons-w']/button");
  By twitterLogo = By.xpath("//div[@class='buttons-w']/div[2]/a[1]/img");
  By facebookLogo = By.xpath("//div[@class='buttons-w']/div[2]/a[2]/img");
  By linkedinLogo = By.xpath("//div[@class='buttons-w']/div[2]/a[3]/img");

  //public By errorBanner = By.className("alert alert-warning");
  By errorBanner = By.xpath("//div[@class='all-wrapper menu-side with-pattern']/div/div[3]");

  By compareExpenses = By.id("showExpensesChart");

  By flashSaleGif1 = By.xpath("//img[@src='img/flashSale.gif']");
  By flashSaleGif2 = By.xpath("//img[@src='img/flashSale2.gif']");

  public String suiteName = "", url = "";

  @BeforeClass
  public void beforeClass() {
    System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
    driver = new ChromeDriver();
    testUtil = new TestUtilities(driver);
  }

  @BeforeMethod
  public void beforeMethod() {
    testUtil.getUrl(url);
  }

  @Test(groups = "Hackathon")
  public void loginPageUIElementsTest() {
    testUtil.getUrl(url);
    //verify logo exists and if its clickable
    Assert.assertTrue(testUtil.isElementDisplayed(logoImage));
    Assert.assertTrue(testUtil.isElementDisplayed(logoImageClickable));

    //login form header
    Assert.assertEquals(testUtil.getText(loginFormHeader), "Login Form");

    //username label, input field and icon exists
    Assert.assertTrue(testUtil.isElementDisplayed(userNameLabel));
    Assert.assertTrue(testUtil.isElementDisplayed(userNameInputField));
    Assert.assertTrue((testUtil.isElementDisplayed(userNameIcon)),
        "\n user name icon not found ===> ");

    //password label, input field and icon exists
    Assert.assertTrue(testUtil.isElementDisplayed(passwordLabel));
    Assert.assertTrue(testUtil.isElementDisplayed(passwordInputField));
    Assert.assertTrue((testUtil.isElementDisplayed(fingerPrintIcon)),
        "\n finger print icon not found ===> ");

    //login button exists
    Assert.assertTrue(testUtil.isElementDisplayed(loginButton));

    //remember me label and check box exists
    Assert.assertEquals(testUtil.getText(rememberMeLabel), "Remember Me");

    //twitter logo exists
    Assert.assertTrue(testUtil.isElementDisplayed(twitterLogo));

    //facebook logo exists
    Assert.assertTrue(testUtil.isElementDisplayed(facebookLogo));

    //linked in logo exists
    Assert.assertTrue(testUtil.isElementDisplayed(linkedinLogo));
  }

  @DataProvider(name = "data-provider")
  public Object[][] dataProviderMethod() {
    return new Object[][]{
        {"", "", "Both Username and Password must be present"}, //empty username and password
        {"testuname", "", "Password must be present"}, //only username and no password
        {"", "testpwd", "Username must be present"}, //only password and no username
        {"testuname", "testpwd", ""} //valid username and password
    };
  }

  @Test(groups = "Hackathon", dataProvider = "data-provider")
  public void userLoginDataDrivenTest(String uname, String pwd, String alertBanner) {
    //enter username and password
    testUtil.sendKeys((userNameInputField), uname);
    testUtil.sendKeys((passwordInputField), pwd);

    //login
    testUtil.click(By.xpath("//div[@class='buttons-w']/button"));

    if (uname.equals("testuname") && pwd.equals("testpwd")) {
      Assert.assertFalse(testUtil.isElementDisplayed(errorBanner));

    } else {
      Assert.assertEquals(testUtil.getText(errorBanner), alertBanner);
    }
  }

  @Test(groups = "Hackathon")
  public void tableSortTest() {
    Map<String, String[]> tableValues = new LinkedHashMap<String, String[]>();
    String[] keys = {"- 320.00 USD", "- 244.00 USD", "+ 17.99 USD", "+ 340.00 USD", "+ 952.23 USD",
        "+ 1250.00 USD"};

    tableValues.put("+ 1250.00 USD",
        new String[]{"Complete", "Today", "Starbucks coffee", "Restaurant / Cafe",
            "+ 1250.00 USD"});
    tableValues.put("+ 952.23 USD",
        new String[]{"Declined", "Jan 19th3:22pm", "Stripe Payment Processing", "Finance",
            "+ 952.23 USD"});
    tableValues.put("- 320.00 USD",
        new String[]{"Pending", "Yesterday7:45am", "MailChimp Services", "Software",
            "- 320.00 USD"});
    tableValues.put("+ 17.99 USD",
        new String[]{"Pending", "Jan 23rd2:7pm", "Shopify product", "Shopping", "+ 17.99 USD"});
    tableValues.put("- 244.00 USD",
        new String[]{"Complete", "Jan 7th9:51am", "Ebay Marketplace", "Ecommerce", "- 244.00 USD"});
    tableValues.put("+ 340.00 USD",
        new String[]{"Pending", "Jan 9th7:45pm", "Templates Inc", "Business", "+ 340.00 USD"});

    //enter username and password
    testUtil.sendKeys((userNameInputField), "test");
    testUtil.sendKeys((passwordInputField), "test");

    //login
    testUtil.click(By.xpath("//div[@class='buttons-w']/button"));

    int noOfRows = driver.findElements(By.xpath("//tbody/tr")).size();
    int noOfColumns = driver.findElements(By.xpath("//tbody/tr[1]/td")).size();

    //click amount
    testUtil.click(By.id("amount"));

    for (int i = 1; i < noOfRows; i++) {
      String[] amountValue1 = testUtil.getText(By.xpath("//tr[" + i + "]/td[5]"))
          .replace(" USD", "").replace(",", "").split(" ");
      String[] amountValue2 = testUtil.getText(By.xpath("//tr[" + (i + 1) + "]/td[5]"))
          .replace(" USD", "").replace(",", "").split(" ");
      float amount1 = Float.parseFloat(amountValue1[0] + amountValue1[1]);
      float amount2 = Float.parseFloat(amountValue2[0] + amountValue2[1]);

      //verify that the amount column is in ascending order
      if (!(amount1 < amount2)) {
        Assert.assertFalse(true, "The table is not sorted based on the ascending amount");
      }

      //verify each row’s data stayed in tact after the sorting.
      for (int j = 1; j <= noOfColumns; j++) {
        if (!(tableValues.get(keys[i - 1])[j - 1]
            .equals(testUtil.getText(By.xpath("//tr[" + i + "]/td[" + j + "]"))))) {
          Assert.assertFalse(true, "Each row’s data is not intact after the sorting.");
        }
      }
    }
  }

  @Test(groups = "Hackathon")
  public void canvasChartTest() throws Exception {
    driver.manage().window().setSize(new Dimension(800, 480));

    //enter username and password
    testUtil.sendKeys((userNameInputField), "test");
    testUtil.sendKeys((passwordInputField), "test");

    //login
    testUtil.click(By.xpath("//div[@class='buttons-w']/button"));

    //click compare expenses
    testUtil.click(compareExpenses);
    testUtil.click(By.id("addDataset"));
    Thread.sleep(2000);

    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    File expectedImageScreenshotLocation = new File("src/main/Images/expected.png");
    FileUtils.copyFile(screenshot, expectedImageScreenshotLocation);

    BufferedImage imgA = ImageIO.read(new File("src/main/Images/actual.png"));
    BufferedImage imgB = ImageIO.read(new File("src/main/Images/expected.png"));

    //since the chart is a canvas element, we verify actual and expected data via screenshots ()
    boolean validateDataChart = testUtil.compareImage(imgA, imgB);
    Assert.assertTrue(validateDataChart, "The Data Chart Validation is incorrect");
  }

  @Test(groups = "Hackathon")
  public void dynamicContentTest() {
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

    Assert.assertTrue(testUtil.isElementDisplayed(flashSaleGif1), "flash gif 1 is not displayed");
    Assert.assertTrue(testUtil.isElementDisplayed(flashSaleGif2), "flash gif 2 is not displayed");
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