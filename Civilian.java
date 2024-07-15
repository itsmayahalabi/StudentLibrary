/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author maya
 */

public class Civilian extends Person {

    // Attributes specific to Civilian
    private String id;
    private double currentBal;

    // Constructor with parameters
    public Civilian(String name, String address, char gender, int age, String phoneNumber, String id, double currentBal) {
        super(name, address, gender, age, phoneNumber);
        this.id = id;
        setCurrentBal(currentBal);
    }

    // no-arg constructor
    public Civilian() {
        this("unkown", "unknown", 'm', 18, "00-000000", "unknown", 40);
    }

    //setters
    private void setCurrentBal(double currentBal) {
        if (currentBal > 0) {
            this.currentBal = currentBal;
        } else {
            this.currentBal = 40;
        }
    }

    // getters
    public String getId() {
        return id;
    }

    public double getCurrentBal() {
        return currentBal;
    }

    // Method to add credit to current balance
    public void addCredit(double amount) {
        if (amount > 0) {
            setCurrentBal(currentBal + amount);
        }
    }

    // Method to borrow an item from the library
    public boolean borrowItem(LibraryItem item) {
        if (item.getPrice() <= currentBal && item.getStatus() == 'a') {
            currentBal -= item.getPrice();
            item.setStatus('o');
            item.setRegistration();
            item.getPastOwners().add(this);
            getBorrowedItem().add(item);
            return true;
        }
        return false;
    }

    // Method to return an item to the library
    public boolean returnItem(LibraryItem item) {
        if (getBorrowedItem().contains(item)) {
            item.getStatus('a')
            item.setRegistration();
            getBorrowedItem().remove(item);
            return true;
        }
        return false;
    }

    // Override toString method to provide a string representation of the 
    //Civilian object
    @Override
    public String toString() {
        return "ID: " + id + "\n" + super.toString() + "\ncurrent Balance: " + currentBal
                + "\nnumber of borrowed items: " + super.getBorrowedItem().size() + "\n--------\n";
    }
}
