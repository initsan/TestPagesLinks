package org.example;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class StandartPage {

    private int pageNumber;
    private boolean hasLink;
    private boolean isLast;

    @FindBy(tagName = "a")
    private WebElement link;

    @FindBy(xpath = "//*[contains(text(), 'последняя')]")
    private WebElement last;

    protected StandartPage(ChromeDriver driver) {
        var locator = new DefaultElementLocatorFactory(driver);
        PageFactory.initElements(locator, this);
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast() {
        try {
            isLast = last.getText().contains("последняя");
        } catch (NoSuchElementException nsee) {
            isLast = false;
        }
    }

    public boolean hasLink() {
        return hasLink;
    }

    public void setHasLink() {
        try {
            hasLink = link.getAttribute("href").contains(".html");
        } catch (NoSuchElementException nsee) {
            hasLink = false;
        }
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        String page = "Page " + pageNumber +
                ", hasLink=" + hasLink +
                ", isLast=" + isLast;
        if (this.hasLink) {
            page = ", link=" + page + link.getAttribute("href");
        }
        return page;
    }
}
