package ru.geekbrains;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class Testing {

    @AfterSuite
    public void afterTest() {
        System.out.println("After test");
    }

    @Test(priority = 9)
    public void test4() {
        System.out.println("Test priority 9");
    }

    @Test(priority = 1)
    public void test1() {
        System.out.println("Test priority 1");
    }

    @Test(priority = 4)
    public void test2() {
        System.out.println("Test priority 4");
    }

    @Test(priority = 7)
    public void test3() {
        System.out.println("Test priority 7");
    }

    @BeforeSuite
    public void beforeTest() {
        System.out.println("Before test");
    }
}

