/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Scanner;

/**
 *
 * @author Suko
 */
public class MyUtils {
     public static int inputInt(String message, String error) {
        do {
            try {
                System.out.print(message);
                Scanner sc = new Scanner(System.in);
                int value = Integer.parseInt(sc.nextLine());

                return value;

            } catch (Exception e) {
                System.out.println(error);
            }
        } while (true);
    }
     
     public static int inputInt(String message, String error, int min, int max) {
        do {
            try {
                System.out.print(message);
                Scanner sc = new Scanner(System.in);
                int value = Integer.parseInt(sc.nextLine());

                if (value >= min && value <= max) {
                    return value;
                }

                System.out.println("Value must be between " + min + " and " + max);

            } catch (Exception e) {
                System.out.println(error);
            }
        } while (true);
    }
     
     public static int inputInt(String message, String error, int min) {
        do {
            try {
                System.out.print(message);
                Scanner sc = new Scanner(System.in);
                int value = Integer.parseInt(sc.nextLine());

                if (value > min) {
                    return value;
                }

                System.out.println("Value must be above " + min);

            } catch (Exception e) {
                System.out.println(error);
            }
        } while (true);
    }
     
    public static String inputString(String message, String error) {
        do {

            System.out.print(message);
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            if (!str.isEmpty()) {
                return str;
            }
            System.out.println(error);

        } while (true);
    }
    
    public static String inputString(String message, String regex, String error) {
        do {

            System.out.print(message);
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            
            if (str.matches(regex)) {
                return str;
            } else {
                System.out.println(error);
            }

        } while (true);
    }
}
