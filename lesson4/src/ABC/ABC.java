package ABC;

import java.util.concurrent.atomic.AtomicReference;

public class ABC {

    private static Object monitor = new Object();

    public static void main(String[] args) throws InterruptedException {

        AtomicReference<Character> letter = new AtomicReference<>('A');

        new Thread(() -> {
            synchronized (monitor) {
                try {
                    for (int i = 0; i < 5; i++) {
                        while (letter.get() != 'A') {
                            monitor.wait();
                        }
                        System.out.print("A");
                        letter.set('B');
                        monitor.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (monitor) {
                try {
                    for (int i = 0; i < 5; i++) {
                        while (letter.get() != 'B') {
                            monitor.wait();
                        }
                        System.out.print("B");
                        letter.set('C');
                        monitor.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (monitor) {
                try {
                    for (int i = 0; i < 5; i++) {
                        while (letter.get() != 'C') {
                            monitor.wait();
                        }
                        System.out.print("C ");
                        letter.set('A');
                        monitor.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
