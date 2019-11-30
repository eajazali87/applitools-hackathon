import java.awt.image.BufferedImage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestUtilities {

  WebDriver driver;
  WebElement webElement = null;
  JavascriptExecutor js = null;

  public TestUtilities(WebDriver driver) {
    this.driver = driver;
  }

  public void getUrl(String url) {
    driver.get(url);
  }

  //click
  public void click(By element) {
    try {
      webElement = driver.findElement(element);
      webElement.click();
    } catch (NoSuchElementException e) {
      System.out.println(
          Thread.currentThread().getStackTrace()[2].getMethodName() + ":"
              + Thread.currentThread().getStackTrace()[2].getLineNumber() + " - " + element
              + ": no such element, click operation didn't happen");
      throw new RuntimeException("no such element, click operation didn't happen");
    }
  }

  //click using js
  public void clickUsingJS(By element) {
    try {
      webElement = driver.findElement(element);
      js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].click();", webElement);
    } catch (NoSuchElementException e) {
      System.out.println(
          Thread.currentThread().getStackTrace()[2].getMethodName() + ":"
              + Thread.currentThread().getStackTrace()[2].getLineNumber() + " - " + element
              + ": no such element, click operation using JS didn't happen");
    }
  }

  //send keys
  public void sendKeys(By element, String text) {
    try {
      webElement = driver.findElement(element);
      webElement.clear();
      webElement.sendKeys(text);
    } catch (NoSuchElementException e) {
      System.out.println(
          Thread.currentThread().getStackTrace()[2].getMethodName() + ":"
              + Thread.currentThread().getStackTrace()[2].getLineNumber() + " - " + element
              + ": no such element, sendkeys operation didn't happen");
      throw new RuntimeException(e.getMessage());
    }
  }

  //is element displayed
  public boolean isElementDisplayed(By element) {
    try {
      return driver.findElement(element).isDisplayed();
    } catch (NoSuchElementException e) {
      System.out.println(
          Thread.currentThread().getStackTrace()[2].getMethodName() + ":"
              + Thread.currentThread().getStackTrace()[2].getLineNumber() + " - " + element
              + ": element is not displayed");
      return false;
    }
  }

  //get Text
  public String getText(By element) {
    try {
      webElement = driver.findElement(element);
      return webElement.getText();
    } catch (NoSuchElementException e) {
      System.out.println(
          Thread.currentThread().getStackTrace()[2].getMethodName() + ":"
              + Thread.currentThread().getStackTrace()[2].getLineNumber() + " - " + element
              + ": no such element, unable to get the text value for the element");
      return null;
    }
  }

  //compare 2 images
  public boolean compareImage(BufferedImage imgA, BufferedImage imgB) {

    boolean comparison = false;

    int width1 = imgA.getWidth();
    int width2 = imgB.getWidth();
    int height1 = imgA.getHeight();
    int height2 = imgB.getHeight();

    if ((width1 != width2) || (height1 != height2)) {
      System.out.println("Error: Images dimensions" +
          " mismatch");
      return false;
    } else {
      long difference = 0;
      for (int y = 0; y < height1; y++) {
        for (int x = 0; x < width1; x++) {
          int rgbA = imgA.getRGB(x, y);
          int rgbB = imgB.getRGB(x, y);
          int redA = (rgbA >> 16) & 0xff;
          int greenA = (rgbA >> 8) & 0xff;
          int blueA = (rgbA) & 0xff;
          int redB = (rgbB >> 16) & 0xff;
          int greenB = (rgbB >> 8) & 0xff;
          int blueB = (rgbB) & 0xff;
          difference += Math.abs(redA - redB);
          difference += Math.abs(greenA - greenB);
          difference += Math.abs(blueA - blueB);
        }
      }

      double total_pixels = width1 * height1 * 3;

      // Normalizing the value of different pixels
      // for accuracy(average pixels per color
      // component)
      double avg_different_pixels = difference /
          total_pixels;

      // There are 255 values of pixels in total
      double percentage = (avg_different_pixels /
          255) * 100;
      System.out.println("percentage: " + percentage);
      if ((percentage < 1.0) && (percentage > 0.0)) { // setting a minimal difference
        comparison = true;
      }
    }
    return comparison;
  }

}