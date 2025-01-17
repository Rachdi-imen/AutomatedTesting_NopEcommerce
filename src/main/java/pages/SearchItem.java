package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchItem {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By searchBox = By.id("small-searchterms");
    private final By searchButton = By.cssSelector("button.button-1.search-box-button");
    private final By productTitle = By.xpath("//a[contains(text(),'Apple MacBook Pro 13-inch')]");
    private final By productImage = By.xpath("//img[contains(@alt,'MacBook Pro')]");
    private final By noResultMessage = By.className("no-result");

    public SearchItem(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void searchForProduct(String productName) {
        enterSearchTerm(productName);
        clickSearchButton();
    }

    private void enterSearchTerm(String productName) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(productName);
    }

    private void clickSearchButton() {
        driver.findElement(searchButton).click();
    }

    public String verifyProductDisplay() {
        // Scroll down to ensure the product title is in view
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 300);");

        // Check for product title visibility
        if (!driver.findElements(productTitle).isEmpty()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productTitle));
            return driver.findElement(productTitle).getText() +
                    " - Image is displayed: " + driver.findElement(productImage).isDisplayed();
        } else {
            return failedSearch();
        }
    }

    public String failedSearch() {
        // Check if no result message is displayed
        if (!driver.findElements(noResultMessage).isEmpty()) {
            return driver.findElement(noResultMessage).getText();
        }
        return "No result message found.";
    }
}
