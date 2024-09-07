/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author praneith Karunakumar
 */
public class customer {
    private String role;
    private double balance;
    private String user;
    private String password;
    private levels levels;
    public customer(String user, String password, String role, double balance){
        this.balance=balance;
        this.user=user;
        this.role=role;
        this.password=password;
        
        
        if( balance < 10000 && 0<=balance){
            this.levels = new silver();
        }
        else if(10000 <= balance && balance < 20000  ){
            this.levels = new gold();
        }
        
        else if(20000 <= balance){
            this.levels = new platinum();
        }
        
    }
    
    
    public void checkRole(double balance){
        if( balance < 10000 && 0<=balance){
            this.levels = new silver();
        }
        else if(10000 <= balance && balance < 20000  ){
            this.levels = new gold();
        }
        else if(20000 <= balance){
            this.levels = new platinum();
        }
    }
    
    public double getBalance(){
        return this.balance;
    }
   
    public void ChangeBalance(Double balance){
        this.balance = balance;
        checkRole(this.balance);
    }
    public String getRole(){
        return this.role;
    }
    public String getLevel(){
        return this.levels.getLevel();
    }
    public int getfee(){
        return this.levels.getfee();
    }

    public String getUser(){
        return this.user;
    }
    
    public String getPass(){
        return this.password;
    }   
    
    public void SaveData(String customerInfo, String User){
        FileWriter edit;
        try {
            edit = new FileWriter(User+".txt");
            edit.write(customerInfo);
            edit.flush();
            edit.close();
        } catch (IOException ex) {
            //Logger.getLogger(Banking.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
}
