/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.text.Text;

/**
 *
 * @author praneith Karunakumar
 */
public class manager {
    private String role;
    private double balance;
    private String user;
    private String password;
    // Overview: manager are mutable
    //
    // mananger class contains all the properties of a manager and has to ability to add and remove customers 
    // and has access to the manager screen in the gui.
    //
    // The abstraction function is:
    // AF(c)={ c.balance.DoubleValue | c.role.stringValue | c.user.stringValue| c.password.stringValue}
    //
    //
    //
    //
    // The rep invariant is:
    // 0 <= c.balance &&  c.role != null &&  c.user != null &&  c.password != null 
    //
    //
    //
    //
    public manager(String user, String password, String role){
        // EFFECTS: Creates a manager object
        this.balance=100;
        this.user=user;
        this.password=password;
        this.role=role;
    }
    public double getBalance(){
        // EFFECTS: Returns the managers balance
        return this.balance;
    }
    public String getRole(){
        // EFFECTS: Returns the managers role
        return this.role;
    }
    
    public String getUser(){
        // EFFECTS: Returns the managers username
        return this.user;
    }
    
    public String getPass(){
        // EFFECTS: Returns the managers password
        return this.password;
    }
    
    public void addCustomer(String username, String password, Double balance, Text state) {
        // REQUIRES: username !=null and  password != null and 0<=balance and state != null
        // MODIFIES: state(this is just the displayed text on the GUI that is being modified)
        // EFFECTS: Creates a file that has all the customers info(pass,user,balance) with the name of the user
        // and will change state based wether it succesful(can succesfully make a file of the account) 
        // or not(cant add the account due to the file with the same user existing)

        
        String customerInfo = username + " " + password + " " + balance;
        try {
            File file = new File(username + ".txt");
            if (file.exists()) {
                state.setText("username already used");
                System.out.println("File already exists.");
            } 
            else {
                FileWriter Manipulator = new FileWriter(file);
                Manipulator.write(customerInfo);
                Manipulator.flush(); // Flush the writer
                Manipulator.close(); // Close the writer
                state.setText("added");
                System.out.println("Customer added successfully.");
            }
        } catch (IOException e) {
            state.setText("An error occurred while adding the customer");
            e.printStackTrace();
        }
    }
    
 
    
    public void deleteCustomer(String username, Double balance, Text state) {
        // REQUIRES: username !=null and  password != null and 0<=balance and state != null
        // MODIFIES: state(this is just the displayed text on the GUI that is being modified)
        // EFFECTS: attempts to find if a file of the customer(based on the user name) exists and 
        // will deletethat account whilst in the process changing state to display wether the outcome is 
        // succesful(deletes account) or not(cant delete account)

        File thisFile = new File(username + ".txt");
        if (thisFile.exists()) {
            if (thisFile.delete()) {
                state.setText("Customer " + username + " deleted successfully.");
            } 
            else {
                state.setText("Failed to delete customer " + username + ".");
            }
        } 
        else {
            state.setText("Customer " + username + " does not exist.");
        }
    }
    public boolean repOK() {
        // EFFECTS: Returns true if the rep invariant holds for this
        // object; otherwise returns false
        if(0<=this.balance && this.password != null && this.user != null && this.role != null){
            return true;
        }
        else{
            return false;
        }
    }
    public String toString() {
        // EFFECTS: Returns a string that contains the properties of abstraction function.
        return ""+this.balance+" "+this.password +" " +this.user +" "+ this.role+"" ;
    }
}
