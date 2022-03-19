package org.example;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class StandarPage {

    private int pageNumber;
    private boolean hasLink;
    private boolean isLast;

    @FindBy(tagName = "a")
    private WebElement link;

    protected StandarPage(ChromeDriver driver) {
        var locator = new DefaultElementLocatorFactory(driver);
        PageFactory.initElements(locator, this);
    }

    public boolean containsLink(){
        try {
            return link.getAttribute("href").contains("http:");
        } catch (NoSuchElementException nsee) {
            return false;
        }
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public boolean hasLink() {
        return hasLink;
    }

    public void setHasLink(boolean hasLink) {
        this.hasLink = hasLink;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "StandarPage{" +
                "pageNumber=" + pageNumber +
                ", hasLink=" + hasLink +
                ", isLast=" + isLast +
                ", link=" + link +
                '}';
    }
}
