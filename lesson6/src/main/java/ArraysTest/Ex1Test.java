package ArraysTest;

import MyArrays.Ex1;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)

public class Ex1Test {

    private int[] arrayWithout4;
    @Before
    public void testOnException (){
        arrayWithout4 = new int[]{1,2,3};
    }

    Ex1 arr1 = new Ex1();
    @Test (expected = RuntimeException.class)
    public void testArrException(){
        arr1.backArrAfterFour(arrayWithout4);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        System.out.println("Parametrized test");
        return Arrays.asList(new Object[][]{
                {new int []{1, 1, 4}, new int []{}},
                {new int []{1, 1, 4, 4},new int []{}},
                {new int []{1, 2, 3, 4, 5}, new int []{5}},
                {new int []{1, 2, 3, 4, 5, 6, 7}, new int []{5, 6, 7}},
        });
    }

    private int[] arrIn;
    private int[] arrOut;

    public Ex1Test(int[] arrIn, int[] arrOut) {
        this.arrIn = arrIn;
        this.arrOut = arrOut;
    }

    Ex1 arr2 = new Ex1();

    @Test
    public void ex1Test() {
        Assert.assertArrayEquals(arrOut, arr2.backArrAfterFour(arrIn));
    }
}