package com.logbug.webdriver.membean;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.logbug.webdriver.entity.Rootcasts;
import com.logbug.webdriver.entity.Roots;
import com.logbug.webdriver.service.RootcastsService;
import com.logbug.webdriver.service.RootsService;
import org.dozer.DozerBeanMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : lin.chen1
 * @version : 1.0.0.0
 * @date : Created at 2022/6/9
 */
@Component
public class GetRootsRunner implements CommandLineRunner {
    ChromeDriver driver = new ChromeDriver();
    DataWriter writer;
    RootsService service;
    RootcastsService rootcastsService;
    DozerBeanMapper mapper = new DozerBeanMapper();

    int limit = 12345;
    @Autowired
    public GetRootsRunner(DataWriter writer, RootsService service, RootcastsService rootcastsService) {
        this.writer = writer;
        this.service = service;
        this.rootcastsService = rootcastsService;
    }



    @Override
    public void run(String... args) throws Exception {

       getRootcasts();
       getRoots();
        writer.writeData();
        driver.quit();

    }

    void getRoots() {
        driver.get("https://membean.com/roots");

        List<WebElement> elements = driver.findElements(By.cssSelector(".roots.container>.root"));
        Queue<RootVO> queue = new LinkedList<>();
        System.out.println("total roots : " + elements.size());
        System.out.printf("find root             ");
        for (int i = 0; i < elements.size() && i < limit; i++) {
            printRate(i,elements.size());

            WebElement element = elements.get(i);


            RootVO rootVO = new RootVO();
            rootVO.setRoot(element.getAttribute("data-root"));
            rootVO.setHasAudio(Boolean.parseBoolean(element.getAttribute("data-audio")));
            rootVO.setCommon(Boolean.parseBoolean(element.getAttribute("data-common")));
            WebElement a = element.findElement(By.tagName("a"));
            String href = a.getAttribute("href");
            rootVO.setHref(href);
            rootVO.setKey(href.substring(href.lastIndexOf('/') + 1));
            String title = a.findElement(By.tagName("h2")).getText();
            String description = a.findElement(By.tagName("p")).getText();
            rootVO.setTitle(title);
            rootVO.setDescription(description);
            queue.add(rootVO);

        }
        Set<RootVO> tmpList = new HashSet<>();
        System.out.println();
        System.out.printf("download root             ");
        Set<RootVO> roots = new LinkedHashSet(queue);

        for (int i = 0; queue.size()>0; i++) {
            printRate(i,roots.size());
            RootVO root = queue.poll();
            if(service.lambdaQuery().select(Roots::getKey).eq(Roots::getKey,root.getKey()).count()>0){
                continue;
            }
            root.setContent(getRootContent(root.href));
            Roots rootDTO = mapper.map(root, Roots.class);
            service.getBaseMapper().insert(rootDTO);
           /* List<WebElement> related = driver.findElements(By.cssSelector(".related-roots a"));
            if (related != null) {
                for (WebElement relatedRoot : related) {

                    String href = relatedRoot.getAttribute("href");
                    RootVO relatedRootVO = new RootVO();
                    relatedRootVO.setRoot(relatedRoot.findElement(By.tagName("h3")).getText());
                    relatedRootVO.setHref(href);
                    relatedRootVO.setKey(href.substring(href.lastIndexOf('/') + 1));
                    relatedRootVO.setTitle(relatedRoot.findElement(By.tagName("p")).getText());
                    relatedRootVO.setRelated(true);
                    if (!roots.contains(relatedRootVO)){
                        roots.add(relatedRootVO);
                        queue.add(relatedRootVO);
                    }
                }
            }*/
        }
        System.out.println();
    }

    void getRootcasts() {
        driver.get("https://membean.com/rootcasts");

        List<WebElement> elements = driver.findElements(By.cssSelector(".resources.container>.resource"));
        List<RootcastVO> rootcast = new LinkedList<>();
        System.out.println("total rootcast : " + elements.size());
        System.out.printf("find rootcast             ");

        for (int i = 0; i < elements.size() && i < limit; i++) {
            printRate(i,elements.size());

            WebElement element = elements.get(i);
            RootcastVO rootcastVO = new RootcastVO();
            rootcastVO.setTitle(element.findElement(By.tagName("h2")).getText());
            String href = element.findElement(By.tagName("a")).getAttribute("href");
            rootcastVO.setHref(href);
            rootcastVO.setKey(href.substring(href.lastIndexOf('/') + 1));
            List<WebElement> ps = element.findElements(By.tagName("p"));
            rootcastVO.setSubtitle(ps.get(0).getText());
            rootcastVO.setDescription(ps.get(1).getAttribute("outerHTML"));
            rootcast.add(rootcastVO);

        }
        System.out.println();
        System.out.printf("dowload rootcast             ");
        for (int i = 0; i < rootcast.size(); i++) {
            RootcastVO root = rootcast.get(i);
            if(rootcastsService.lambdaQuery().eq(Rootcasts::getKey,root.getKey()).count()>0){
                continue;
            }

            System.out.print("\b\b\b\b\b\b\b\b\b\b\b");
            System.out.printf("%04d / %04d", i, elements.size());
            root.setContent(getRootcastContent(root.href));
            rootcastsService.getBaseMapper().insert(mapper.map(root, Rootcasts.class));

        }
    }

    String getRootContent(String href) {
        String html = null;
        for (int i = 0; i < 3; i++) {
            try {
                html = getRootContentNg(href);
            } catch (Exception e) {
                System.err.println("retry " + i + " " + href);
                try {
                    Thread.sleep(3 * i);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                continue;
            }
            break;
        }
        return html;

    }

    String getRootContentNg(String href) {
        driver.get(href);
        List<WebElement> elements = driver.findElements(By.cssSelector("main>:not(.pitch)"));
        String html = elements.stream().map(it -> it.getAttribute("outerHTML")).reduce(String::concat).get();
        html = html.replaceAll("(href=\")(.*?)(\")", "$1#$2$3");
        return html;
    }


    void printRate(int i,int t){
        System.out.print("\b\b\b\b\b\b\b\b\b\b\b");
        System.out.printf("%04d / %04d", i+1, t);
    }
    String getRootcastContent(String href) {
        return getRootContent(href);
    }
}
