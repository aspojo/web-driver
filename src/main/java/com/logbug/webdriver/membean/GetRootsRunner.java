package com.logbug.webdriver.membean;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : lin.chen1
 * @version : 1.0.0.0
 * @date : Created at 2022/6/9
 */
@Component
public class GetRootsRunner implements CommandLineRunner {
    ChromeDriver driver = new ChromeDriver();
    DataWriter writer;

    int limit = 10;

    public GetRootsRunner(DataWriter writer) {
        this.writer = writer;
    }

    @Override
    public void run(String... args) throws Exception {

        writer.setRoots(getRoots());
        writer.setRootcasts(getRootcasts());
        writer.writeData();
        driver.quit();

    }

    List<RootVO> getRoots() {
        driver.get("https://membean.com/roots");

        System.out.println("page title = " + driver.getTitle());
        List<WebElement> elements = driver.findElements(By.cssSelector(".roots.container>.root"));
        List<RootVO> roots = new LinkedList<>();
        for (int i = 0; i < elements.size() && i < limit; i++) {

            WebElement element = elements.get(i);


            RootVO rootVO = new RootVO();
            rootVO.setRoot(element.getAttribute("data-root"));
            rootVO.setHasAudio(Boolean.parseBoolean(element.getAttribute("data-audio")));
            rootVO.setCommon(Boolean.parseBoolean(element.getAttribute("data-common")));
            WebElement a = element.findElement(By.tagName("a"));
            String href = a.getAttribute("href");
            rootVO.setHref(href);
            String title = a.findElement(By.tagName("h2")).getText();
            String description = a.findElement(By.tagName("p")).getText();
            rootVO.setTitle(title);
            rootVO.setDescription(description);
            System.out.println("rootVO = " + rootVO);
            roots.add(rootVO);

        }
        for (RootVO root : roots) {
            root.setContent(getRootContent(root.href));
        }
        return roots;
    }

    List<RootcastVO> getRootcasts() {
        driver.get("https://membean.com/rootcasts");

        List<WebElement> elements = driver.findElements(By.cssSelector(".resources.container>.resource"));
        List<RootcastVO> rootcast = new LinkedList<>();
        for (int i = 0; i < elements.size() && i < limit; i++) {

            WebElement element = elements.get(i);
            RootcastVO rootcastVO = new RootcastVO();
            rootcastVO.setTitle(element.findElement(By.tagName("h2")).getText());
            String href = element.findElement(By.tagName("a")).getAttribute("href");
            rootcastVO.setHref(href);
            rootcastVO.setKey(href.substring(href.lastIndexOf('/')+1));
            List<WebElement> ps = element.findElements(By.tagName("p"));
            rootcastVO.setSubtitle(ps.get(0).getText());
            rootcastVO.setDescription(ps.get(1).getAttribute("outerHTML"));
            rootcast.add(rootcastVO);

        }
        for (RootcastVO root : rootcast) {
            root.setContent(getRootcastContent(root.href));
        }
        return rootcast;
    }

    String getRootContent(String href) {
        driver.get(href);
        List<WebElement> elements = driver.findElements(By.cssSelector("main>:not(.pitch)"));
        String html = elements.stream().map(it -> it.getAttribute("outerHTML")).reduce(String::concat).get();
        html = html.replaceAll("(href=\")(.*?)(\")", "$1#$2$3");
        System.out.println("html = " + html);
        return html;
    }

    String getRootcastContent(String href) {
        return getRootContent(href);
    }
}
