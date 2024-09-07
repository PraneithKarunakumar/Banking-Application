/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author praneith Karunakumar
 */
public abstract class levels {
    private String level;
    private int fee;
    public levels(){
       
    }
    abstract public int getfee();
    abstract public String getLevel();
}