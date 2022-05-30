package ru.geekbrains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String[] arr = {"1", "2", "3", "4", "5", "6", "7"};
        System.out.println(arrayTransformList(arr));
        System.out.println(Arrays.toString(changeElements(arr, 2, 4)));

        Orange orange = new Orange();
        Apple apple = new Apple();

        Box<Apple> boxA1 = new Box();
        Box<Apple> boxA2 = new Box();
        Box<Orange> boxO = new Box();

        for (int i = 0; i < 5; i++) {
            boxA1.addFruit(new Apple());
            boxA2.addFruit(new Apple());
            boxO.addFruit(new Orange());
        }

        boxA1.info();
        boxA2.info();
        boxO.info();

        System.out.println(boxA1.compare(boxA2));
        System.out.println(boxA2.compare(boxO));

        boxA1.pourFruit(boxA2);
        boxA1.info();
        boxA2.info();

        boxA2.addFruit(new Apple());
        boxA2.info();
    }

    private static <T> T[] changeElements(T[] arr, int i, int j) {
        T a = arr[i];
        arr[i] = arr[j];
        arr[j] = a;
        return arr;
    }

    private static <T> List<T> arrayTransformList(T[] arr) {
        List<T> list = new ArrayList<T>();
        list.addAll(Arrays.asList(arr));
        return list;
    }
}
