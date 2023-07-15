/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MyUtils;

/**
 *
 * @author Suko
 */
public class ShopList extends ArrayList<Shop> {

    private static final String FILENAME = "src/data/shop";

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
                if (row.length >= 2) {
                    String name = row[0];
                    String desc = row[1];

                    Shop shop = new Shop(name, desc);
                    this.add(shop);
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
            out.println("ShopName, Description");
            for (Shop shop : this) {
                out.println(shop.getShopName() + ", " + shop.getShopDesc());
            }
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createShop() {
        String name;
        boolean check = true;
        ShopList shopList = new ShopList();
        shopList.readFromfile();
        
        do {
            name = MyUtils.inputString("Enter Shop name: ", "Name can't be empty");
            for (int i = 0; i < shopList.size(); i++) {
                if (name.equals(shopList.get(i).getShopName())) {
                    check = false;
                }
            }
            
            if (check){
                String desc = MyUtils.inputString("Enter Shop Description: ", "Description can't be empty");

                Shop shop = new Shop(name, desc);
                this.add(shop);
                writeToFile();
            } else {
                System.out.println("Shop name already existed, try again");
                check = true;
            }
        } while (true);
    }

    public void viewShop() {
        
        
        System.out.println();
        for (int i = 0; i < this.size(); i++) {
            System.out.println(this.get(i).toString());
        }
        System.out.println();
    }

    public void deleteShop() {
        if (this.size()==0){
            System.out.println("No Shop to Delete");
            return;
        }
        
        String code = MyUtils.inputString("Enter Name of Shop to Delete:", "Name can't be empty");
        for (int i = 0; i < this.size(); i++) {
            if (code.equals(this.get(i).getShopName())) {
                this.remove(i);
                writeToFile();
            }
        }
    }

    public void updateShop() {

        String code = MyUtils.inputString("Enter Name of Shop to Update:", "Name of Shop can't be empty");

        for (int i = 0; i < this.size(); i++) {
            if (code.equals(this.get(i).getShopName())) {
                //Update Smart
                String desc = MyUtils.inputString("Enter Description of Shop: ", "Description can't be empty");

                this.get(i).setShopDesc(desc);

                writeToFile();
            }
        }
    }
    

    public void shopListMenu(User user) {
        ArrayList<String> mList = new ArrayList();

        System.out.println("Choose Action");

        mList.add("Enter Shop");
        mList.add("View all Shops");
        mList.add("Update Shops");
        mList.add("Create Shop");
        mList.add("Delete Shop");
        mList.add("Exit");

        do {

            for (int i = 0; i < mList.size(); i++) {
                System.out.println((i + 1 + ". ") + mList.get(i));
            }

            int choice = MyUtils.inputInt("Enter your option: ", "Invalid choice", 1, mList.size());

            switch (choice) {
                case 1:
                    
                    
                    ArrayList<String> mmList = new ArrayList();

                    for (int i = 0; i < this.size(); i++) {
                        mmList.add(this.get(i).toString());
                    }
                    
                    
                    System.out.println();
                    for (int i = 0; i < mmList.size(); i++) {
                        System.out.println((i + 1 + ". ") + mmList.get(i));
                    }
                    System.out.println();

                    int choice2 = MyUtils.inputInt("Enter your option: ", "Invalid choice", 1, mmList.size());
                    
                    this.get(choice2 - 1).shopMenu(this.get(choice2 - 1), user);
                    //Enter shop
                    break;
                case 2:
                    viewShop();
                    break;
                case 3:
                    updateShop();
                    break;
                case 4:
                    createShop();
                    break;
                case 5:
                    deleteShop();
                    break;
                case 6:
                    return;

            }

        } while (true);
    
    }
}
