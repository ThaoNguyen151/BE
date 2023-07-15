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
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MyUtils;

/**
 *
 * @author Suko
 */
public class Shop extends ArrayList<Product1> {

    private String shopName;
    private String shopDesc;

    public Shop() {
    }

    public Shop(String shopName, String shopDesc) {
        this.shopName = shopName;
        this.shopDesc = shopDesc;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String toString() {
        return shopName + " - " + shopDesc;
    }

    private static final String FILENAME = "src/data/product";

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
                if (row.length >= 7) {
                    String ID = row[0];
                    String name = row[1];
                    int ratingCount = Integer.parseInt(row[2]);
                    float starRating = Float.parseFloat(row[3]);
                    int quantity = Integer.parseInt(row[4]);
                    int soldQuantity = Integer.parseInt(row[5]);
                    int price = Integer.parseInt(row[6]);

                    Product1 prod = new Product1(ID, name, ratingCount, starRating, quantity, soldQuantity, price);
                    this.add(prod);
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
            out.println("ID, Name, RatingCount, StarRating, Quantity, SoldQuantity");
            for (Product1 prod : this) {
                out.println(prod.getID() + ", " + prod.getName() + ", " + prod.getRatingCount() + ", " + prod.getStarRating() + ", " + prod.getQuantity() + ", " + prod.getSoldQuantity());
            }
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createProd() {
        String code;
        boolean check = true;

        do {
            System.out.println(this.size());

            code = MyUtils.inputString("Enter Code:", "P[\\d]{6}", "Invalid code, must be in the <P000000> Format)");
            for (int i = 0; i < this.size(); i++) {
                if (code.equals(this.get(i).getID())) {
                    check = false;
                }
            }

            if (check) {
                String name = MyUtils.inputString("Enter product name: ", "Name can't be empty");
                int quantity = MyUtils.inputInt("Input product Quantity: ", "Quantity can't be empty", 0);
                int price = MyUtils.inputInt("Input price of Product", "Price can't be empty", 0);

                Product1 prod = new Product1(code, name, 0, 0, quantity, 0, price);
                this.add(prod);
                writeToFile();
                return;
            } else {
                System.out.println("Code already existed, try again");
                check = true;
            }
        } while (true);

    }

    public void viewProd(Shop shop) {

        System.out.println("\n");

        Collections.sort(shop, new Comparator<Product1>() {
            @Override
            public int compare(Product1 t, Product1 t1) {
                return t.getID().compareTo(t1.getID());
            }
        });

        shop.writeToFile();
        
        for (int i = 0; i < shop.size(); i++) {
            System.out.println((i + 1 + ". ") + shop.get(i).toString());
        }
    }

    public void deleteProd() {
        if (this.size() == 0) {
            System.out.println("No Product to Delete");
            return;
        }
        String code = MyUtils.inputString("Enter Code of product to Delete:", "P[\\d]{6}", "Invalid code, must be in the <P000000> Format)");
        for (int i = 0; i < this.size(); i++) {
            if (code.equals(this.get(i).getID())) {
                this.remove(i);
                writeToFile();
            } 
        }
    }

    public void updateProd() {

        if (this.size() == 0) {
            System.out.println("No Product to put to Update");
            return;
        }
        
        String code = MyUtils.inputString("Enter ID of product to Update: ", "P[\\d]{6}", "Invalid code, must be in the <P000000> Format)");

        for (int i = 0; i < this.size(); i++) {
            if (code.equals(this.get(i).getID())) {
                //Update Smart
                String name = MyUtils.inputString("Enter Name: ", "Name can't be empty");
                int quantity = MyUtils.inputInt("Enter Quantity: ", "Brand can't be empty");

                this.get(i).setName(name);
                this.get(i).setQuantity(quantity);

                writeToFile();
            }
        }
    }

    public void listStar() {
        Collections.sort(this, new Comparator<Product1>() {
            @Override
            public int compare(Product1 t, Product1 t1) {
                return t.getRatingCount() - t1.getRatingCount();
            }

        });

        writeToFile();

        for (int i = 0; i < this.size(); i++) {
            System.out.println((i + 1 + ". ") + this.get(i).toString());
        }
    }

    public void listSold() {
        Collections.sort(this, new Comparator<Product1>() {
            @Override
            public int compare(Product1 t, Product1 t1) {
                return t.getSoldQuantity() - t1.getSoldQuantity();
            }
        });

        writeToFile();

        for (int i = 0; i < this.size(); i++) {
            System.out.println((i + 1 + ". ") + this.get(i).toString());
        }
    }

    public void putInCart(Cart cart) {
        ArrayList<String> mmList = new ArrayList();

        if (this.size() == 0) {
            System.out.println("No Product to put in Cart");
            return;
        }

        for (int i = 0; i < this.size(); i++) {
            mmList.add(this.get(i).toString());
        }

        System.out.println();
        for (int i = 0; i < mmList.size(); i++) {
            System.out.println((i + 1 + ". ") + mmList.get(i));
        }
        System.out.println();

        int choice2 = MyUtils.inputInt("Enter your option: ", "Invalid choice", 1, mmList.size());
        int quantity = MyUtils.inputInt("Enter Quantity to Buy: ", "Invalid Quantity", 0, this.get(choice2 - 1).getQuantity());

        cart.addToCart(this.get(choice2 - 1), quantity);
    }
    
    public void shopMenu(Shop shop, User user) {
        boolean isUserAdmin = user.isAdminPerm();
        
        Cart cart = new Cart();
        ShopList shopList = new ShopList();
        ArrayList<String> mList = new ArrayList();
        Userlist userlist = new Userlist();
        userlist.readFromfile();
        
       

        //???
        if (shop.isEmpty()) {
            shop.readFromfile();
        }

        System.out.println("============" + shop.getShopName() + "============");

        mList.add("Put Product into Cart");
        mList.add("View all Product");
        mList.add("Update a Product");
        mList.add("Add a product");
        mList.add("Remove a Product");
        mList.add("List by Star Rating");
        mList.add("List by Sold Quantity");
        mList.add("Go to Cart");
        mList.add("Return");

        do {

            System.out.println();

            for (int i = 0; i < mList.size(); i++) {
                System.out.println((i + 1 + ". ") + mList.get(i));
            }

            System.out.println();

            int choice = MyUtils.inputInt("Enter your option: ", "Invalid choice", 1, mList.size());

            switch (choice) {
                case 1:
                    putInCart(cart);
                    //Put in Cart
                    break;
                case 2:
                    viewProd(shop);
                    break;
                case 3:
                    updateProd();
                    break;
                case 4:
                    createProd();
                    break;
                case 5:
                    deleteProd();
                    break;
                case 6:
                    listStar();
                    //List Star Rating
                    break;
                case 7:
                    listSold();
                //List Sold Quantity
                case 8:
                    cart.cartMenu(shop, user);
                    break;
                case 9:
                    cart.clear();
                    shopList.shopListMenu(user);
                    
            }

        } while (true);

    }

}
