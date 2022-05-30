package net.sqlitetutorial;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

public class Car {

    private static int CARS_COUNT;
    private final Race race;
    private final int speed;
    private final String name;
    private static CyclicBarrier cb;
    private static CountDownLatch cdStart;
    private static CountDownLatch cdFinish;
    private static boolean winnerFound;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    private static synchronized void checkWinner(Car c) {
        if (!winnerFound) {
            System.out.println(c.name + " - WIN");
            winnerFound = true;
        }
    }

    public void carRun() {
        cb = new CyclicBarrier(CARS_COUNT);
        cdStart = MainClass.cdStart;
        cdFinish = MainClass.cdFinish;

        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            cb.await();

            System.out.println(this.name + " готов");
            cb.await();

            cdStart.countDown();

        } catch (Exception e) {
            e.printStackTrace();
        }

        IntStream.range(0, race.getStages().size())
                .forEach(i -> race.getStages().get(i).go(this));

        checkWinner(this);

        cdFinish.countDown();
    }
}