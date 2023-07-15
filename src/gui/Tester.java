package gui;

import core.Cart;
import core.Shop;
import core.ShopList;
import core.User;
import core.Userlist;
import java.io.File;
import java.io.IOException;
import utils.MyUtils;
import java.util.ArrayList;
import java.util.Scanner;


public class Tester {
    public static void main(String[] args) {
        Userlist userList = new Userlist();
        Shop shop = new Shop();
        ShopList shopList = new ShopList();
        Cart cart = new Cart();
        userList.readFromfile();
        shop.readFromfile();
        shopList.readFromfile();
        ArrayList<String> Menu = new ArrayList<>();
        Menu.add("Login");
        Menu.add("Register");
        Menu.add("Logout");
        Menu.add("Exit");
        Scanner sc = new Scanner(System.in);
        while (true) { // use a while loop with the boolean variable as the condition
            for (int i = 0; i < Menu.size(); i++) {
                System.out.println(i + 1 + ". " + Menu.get(i));
            }
            Integer choice = MyUtils.inputInt("Please enter your choice:", "Invalid number", 1, Menu.size());
            if (choice == 1) {
                User loginUser = userList.login();
                if(loginUser == null) {
                    System.out.println("Wrong username or password");
                } else {
                    System.out.println("Login success");
                    System.out.println("Your info: ");
                    System.out.println(loginUser.getUsername() + ", " + loginUser.getPassword() + ", " + loginUser.getFullname() + ", " + loginUser.getGmail());
                    
                    shopList.shopListMenu(loginUser);
                    userList.logoutProcess();
                }
            }
            if (choice == 2) {
                User registerUser = userList.register();
            }
            if (choice == 3) {
                userList.logoutProcess();
            } 
            if (choice == 4) {
                return;
            }
//                User logoutUser = null; // declare and initialize logoutUser as null
//                Object loginUser = null;
//                if (loginUser != null) { // check if loginUser is not null
//                    logoutUser = (User) loginUser; // assign the value of loginUser to logoutUser
//                    userList.logout(logoutUser); // call the logout method with logoutUser as the argument
//                    System.out.println("You have logged out"); // print a message
//                } else {
//                    System.out.println("You are not logged in"); // print a message if loginUser is null
//                }
//            }
//            System.out.println("Do you want to continue? (y/n)"); // ask the user if they want to continue
//            String answer = sc.nextLine(); // get the user input
//            if (answer.equalsIgnoreCase("y")) { // if the user input is y or Y
//                keepRunning = true; // set the boolean variable to true
//            } else if (answer.equalsIgnoreCase("n")) { // if the user input is n or N
//                keepRunning = false; // set the boolean variable to false
//            } else { // if the user input is invalid
//                System.out.println("Invalid input. Please enter y or n."); // print a message
//            }
        }
    }
}

