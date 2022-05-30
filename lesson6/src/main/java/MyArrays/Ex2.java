package MyArrays;

import java.util.Arrays;

public class Ex2 {
    public static void main(String[] args) {
        int[] arr = {1,1,4,4,4,4,1,1,4,1,4,4,1};

        System.out.println(Arrays.toString(arr));
        System.out.println(checkArray(arr));
    }

    public static boolean checkArray(int[] arr) {
        boolean one = false;
        boolean four = false;

        for (int i = 0; i < arr.length; i++) {
            if (one & four) break;
            if (arr[i] == 1) one = true;
            if (arr[i] == 4) four = true;
        }

        return (one && four)?(true):(false);
    }
}


