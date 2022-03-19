package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestPages {

    private final String baseUrl = "http://qa-web-test-task.s3-website.eu-central-1.amazonaws.com/";
    private ChromeDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",".\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterEach
    public void setDown() {
        driver.quit();
    }

    @Test
    public void testpagesLinks () {
        openStartPage();
        List<StandarPage> pages = goThroughAllPages();
        printPagesWithoutLinks(pages);
    }

    private void printPagesWithoutLinks(List<StandarPage> list) {
        List<StandarPage> resultCollection = list.stream().filter(page -> !page.hasLink()).collect(Collectors.toList());
        System.out.println("Pages without links:");
        resultCollection.forEach(System.out::println);
    }

    private List<StandarPage> goThroughAllPages() {
        List<StandarPage> allList = new ArrayList<>();
        int numPage = 0;
        while (!listContainsLastPage(allList)){
            StandarPage currentPage = new StandarPage(driver);
            currentPage.setPageNumber(numPage);
            currentPage.setHasLink(currentPage.containsLink());
            allList.add(currentPage);
            if (!currentPage.hasLink()) {
                WebElement lastElement;
                try {
                    lastElement = driver.findElement(By.xpath("//*[contains(text(), 'последняя')]"));
                } catch (NoSuchElementException nsee) {
                    lastElement = null;
                }
                if (lastElement != null) {
                    currentPage.setLast(true);
                }
            }
            numPage++;
            String nextUrl = baseUrl + numPage + ".html";
            driver.get(nextUrl);
        }
        return allList;
    }

    private boolean listContainsLastPage(List<StandarPage> allList) {
        return allList.stream().anyMatch(StandarPage::isLast);
    }

    private void openStartPage() {
        driver.get(baseUrl);
    }

}
