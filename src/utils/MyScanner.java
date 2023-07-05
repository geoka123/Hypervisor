package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyScanner {
    private Scanner scanner;

    public MyScanner() {
        this.scanner = new Scanner(System.in);
    }

    public String readStr(String prompt) {
        System.out.println(prompt + ": ");
        String input = scanner.next();
        return input;
    }

    public int readInt(String prompt) {
        System.out.println(prompt + ": ");
        try {
            int input = scanner.nextInt();
            if (input >= 0)
                return input;
            else {
                System.out.println("Cannot take a negative number as input");
                return -1;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            return -1;
        }
    }

    public void close() {
        scanner.close();
    }
}
