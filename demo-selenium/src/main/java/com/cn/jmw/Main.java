package com.cn.jmw;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //设置驱动
        System.setProperty("webdriver.chrome.driver","D:\\coding\\deletedemo\\demo-selenium\\src\\main\\resources\\chromedriver.exe");

        // 1.创建webdriver驱动
        WebDriver driver = new ChromeDriver();
        // 2.打开百度首页
        driver.get("https://ones.eoitek.com/wiki/#/team/UK9JeLWj/space/Dq5FN92S/page/FA6Vsptw");

        List<WebElement> elements = driver.findElements(By.className("ones-tree-list-holder-inner"));

        //TODO 外部一级菜单
        for (WebElement element : elements) {
            System.out.println(element.getText());
        }
//        // 3.获取输入框，输入selenium
//        driver.findElement(By.id("kw")).sendKeys("selenium");
        // 4.获取“百度一下”按钮，进行搜索
//        driver.findElement(By.id("su")).click();
        // 5.退出浏览器
        driver.quit();
    }
}