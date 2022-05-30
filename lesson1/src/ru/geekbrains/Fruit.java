package ru.geekbrains;

public abstract class Fruit<S, F extends Number> {
    public S name;
    public F weight;

    public Fruit(S name, F weight) {
        this.weight = weight;
        this.name = name;
    }

    public F getWeight() {
        return weight;
    }

    public S getName() {
        return name;
    }
}
