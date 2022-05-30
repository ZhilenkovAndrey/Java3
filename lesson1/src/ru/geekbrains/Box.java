package ru.geekbrains;

import java.util.ArrayList;
import java.util.List;

public class Box<F extends Fruit> {

    private List<F> fruitList;

    public List<F> getFruitList() {
        return fruitList;
    }

    public Box() {
        fruitList = new ArrayList<>();
    }

    void addFruit(F Obj) {
        fruitList.add(Obj);
    }

    float getWeight() {
        return fruitList.size() * fruitList.get(0).getWeight().floatValue();
    }

    boolean compare(Box<? extends Fruit> box) {
        return this.getWeight() == box.getWeight();
    }

    void info() {
        if (fruitList.isEmpty()) {
            System.out.println("The box is empty.");
        } else {
            System.out.println("In box " + fruitList.size() + " qty of " +
                    fruitList.get(0).getName() + " has weight " +
                    fruitList.size() * fruitList.get(0).getWeight().floatValue());
        }
    }

    void pourFruit(Box<F> box) {
        box.getFruitList().addAll(fruitList);
        fruitList.clear();
    }
}
