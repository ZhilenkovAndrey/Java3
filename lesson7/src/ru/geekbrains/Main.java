package ru.geekbrains;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        start(Testing.class);
    }

    private static void start(Class Testing) {

        Method[] methods = Testing.getDeclaredMethods();

        try {

            int count1 = 0;
            for (Method o : methods) {
                if (o.isAnnotationPresent(BeforeSuite.class)) {
                    if (count1 == 1) throw new RuntimeException();
                    System.out.println();
                    System.out.println(o);
                    System.out.println();
                    count1++;
                }
            }

            for (int i = 1; i < 11; i++) {
                for (Method o : methods) {
                    if (o.isAnnotationPresent(Test.class)) {
                        Test test = o.getAnnotation(Test.class);
                        if (test.priority() == i) {
                            {
                                System.out.println(o);
                                System.out.println("value: " + test.value());
                                System.out.println("priority" + test.priority());
                                System.out.println();
                            }
                        }
                    }
                }
            }

            int count2 = 0;
            for (Method o : methods) {
                if (o.getAnnotation(AfterSuite.class) != null) {
                    if (count2 == 1) throw new RuntimeException();
                    System.out.println(o);
                    count2++;
                }
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
