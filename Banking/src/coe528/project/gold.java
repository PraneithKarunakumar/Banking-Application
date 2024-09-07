/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author praneith Karunakumar
 */
public class gold extends levels{
    private String level= "gold";
    private int fee = 10;
    public gold(){
        super();
    }
    
    public int getfee(){
        return this.fee;
    }
    public String getLevel(){
        return this.level;
    }
}
