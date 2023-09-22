package ru.hogwarts.school.temp;

/**
 * Test task for the  test interview.
 * Task: Write an example of handling multiple exceptions in a single catch block.
 */
public class TestMain {

    public static void main(String[] args){
        /**
         * Let's create a precedent where we can get two or more exceptions.
         */
        Integer[] arr = {1, 2, 3, 4, null};
        int i = 0;

        try{
            /**
             *If we pass to the variable "i" a number greater than the number of elements in the array (for example 6),
             * we will get ArrayIndexOutOfBoundsException.
             *If we pass 4 into the "i" variable, we will get a NullPointerException when we try to add null to 5.
             */
            int result = 5 + arr[i];
            System.out.println(result);
        }
        /**
         * Here is the handling of two exceptions in one block catch.
         */
        catch(NullPointerException | ArrayIndexOutOfBoundsException e){
            System.out.println(e.toString());
        }
    }
}
