package MyArrays;

import java.util.Arrays;

public class Ex1 {

    public static void main(String[] args) {
        int[] arr = {1, 2, 4, 4, 2, 3, 4, 1, 7};
        System.out.println("Base array: " + Arrays.toString(arr));
        System.out.println("Finish array : " + Arrays.toString(backArrAfterFour(arr)));
    }

    public static int[] backArrAfterFour(int[] arr) {
        int[] finishArray = null;
        int i = arr.length - 1;
        while (!(arr[i] == 4)) {
            try {
                finishArray = new int[arr.length - i];
                int k = 0;
                for (int j = i; j < arr.length; j++) {
                    finishArray[k] = arr[j];
                    k++;
                }
            } catch (MyException e) {
                throw new RuntimeException("Array has no elements initialized 4");
            }
            i--;
        }
        return finishArray;
    }

    class MyException extends RuntimeException {
        public MyException(String message) {
            super(message);
        }
    }
}
