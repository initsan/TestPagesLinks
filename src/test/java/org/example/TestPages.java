package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    public void testPagesLinks() {
        openStartPage();
        List<StandartPage> pages = goThroughAllPages();
        printPagesWithoutLinks(pages);
    }

    private void printPagesWithoutLinks(List<StandartPage> list) {
        List<StandartPage> resultCollection = list.stream().filter(page -> !page.hasLink()).collect(Collectors.toList());
        System.out.println("Pages without links:");
        resultCollection.forEach(System.out::println);
    }

    private List<StandartPage> goThroughAllPages() {
        List<StandartPage> allList = new ArrayList<>();
        int numPage = 0;
        while (!listContainsLastPage(allList)){
            StandartPage currentPage = new StandartPage(driver);
            currentPage.setPageNumber(numPage);
            currentPage.setHasLink();
            allList.add(currentPage);
            if (!currentPage.hasLink()) {
                currentPage.setLast();
            }
            numPage++;
            String nextUrl = baseUrl + numPage + ".html";
            driver.get(nextUrl);
        }
        return allList;
    }

    private boolean listContainsLastPage(List<StandartPage> allList) {
        return allList.stream().anyMatch(StandartPage::isLast);
    }

    private void openStartPage() {
        driver.get(baseUrl);
    }

}
