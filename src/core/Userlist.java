package core;

import utils.MyUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Userlist extends ArrayList<User> {

    private static final String FILENAME = "src/data/user";

    public void readFromfile() {
        BufferedReader reader;
        String line;
        File file = new File(FILENAME);
        if (!file.exists()) {
            System.out.println("File not exist!!!");
            System.exit(0);
        }
        try {
            reader = new BufferedReader(new FileReader(file));
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(", ");
                if (row.length >= 5) {
                    String username = row[0];
                    String password = row[1];
                    String fullName = row[2];
                    String email = row[3];
                    boolean adminPerm = Boolean.getBoolean(row[4]);
                    User user = new User(username, password, fullName, email, adminPerm);
                    this.add(user);
                } else {
                    System.out.println("Invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeToFile() {
        try {
            PrintWriter out = new PrintWriter(FILENAME);
            out.println("username, password, fullName, email");
            for (User user : this) {
                out.println(user.getUsername() + ", " + user.getPassword() + ", " + user.getFullname() + ", " + user.getGmail() + ", " + user.isAdminPerm());
            }
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Userlist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User login() {
        String username = MyUtils.inputString("Enter your username: ", "Username can't be empty");
        String password = MyUtils.inputString("Enter your password: ", "Password can't be empty");
        for (User user : this) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User register() {
        String username;
        String password;

        do {
            username = MyUtils.inputString("Enter your username: ", "Username can't be empty");
            if (username.length() > 12 || username.length() < 4) {
                System.out.println("Username must be between 4 or 12 characters");
            } else {
                boolean usernameExists = false;
                for (User user : this) {
                    if (user.getUsername().equals(username)) {
                        System.out.println("Username already exists. Please choose a different username.");
                        usernameExists = true;
                        break;
                    }
                }
                if (!usernameExists) {
                    break;
                }
            }
        } while (true);

        do {
            password = MyUtils.inputString("Enter your password: ", "Password can't be empty");
            if (password.length() > 12 || password.length() < 6) {
                System.out.println("Password must be between 6 to 12 characters");
            } else {
                break;
            }
        } while (true);

        // Step2
        String fullname = MyUtils.inputString("Enter your full name: ", "Full name can't be empty");
        boolean validGmail = false;
        String gmail;
        while (!validGmail) {
            gmail = MyUtils.inputString("Enter your gmail (Ex: thaonguyen@gmail.com): 2", "Email can't be empty");
            String regex = "^(.+)@(.+)[.+](.+)$";
            if (gmail.matches(regex)) {
                int choice = MyUtils.inputInt("Is this account an admin? (1: Yes, 2: No)", "Invalid Input", 0, 1);
                boolean bool = (choice == 1);
                User newUser = new User(username, password, fullname, gmail, bool);
                this.add(newUser);
                writeToFile();
                return newUser;
            } else {
                System.out.println("Invalid gmail format. Please enter a valid Gmail address!!!");
            }
        }
        return null;
    }

    public void logout(User user) {
        user = null; // set the user object to null
    }

    public void logoutProcess() {

        Scanner sc = new Scanner(System.in);

        User logoutUser = null; // declare and initialize logoutUser as null
        Object loginUser = null;
        if (loginUser != null) { // check if loginUser is not null
            logoutUser = (User) loginUser; // assign the value of loginUser to logoutUser
            this.logout(logoutUser); // call the logout method with logoutUser as the argument
            System.out.println("You have logged out"); // print a message
        } else {
            System.out.println("You are not logged in"); // print a message if loginUser is null
        }

    }
}
