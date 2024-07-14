/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author maya
 */

import java.util.Date;

public class DVD extends LibraryItem {

    // Constant representing the cost of renting a DVD
    private final static double DvdCost = 5;

    // Size of the DVD in megabytes (Attribute specific to DVD)
    private double sizeInMB;

    // Parameterized constructor
    public DVD(long serialNumber, String title, String authorOrDirector, String publisherOrProducer, char status, String genre, Date dateAvailable, double sizeInMB) {
        // Call superclass constructor to initialize common attributes
        super(serialNumber, title, authorOrDirector, publisherOrProducer, status, genre, dateAvailable);

        // Set DVD specific attribute
        setSizeInMB(sizeInMB);

        setSerialNumber(-1);
    }

    // Constructor with fewer parameters
    public DVD(String title, String author, String publisher, char status, String genre, double sizeInMB) {
        // Call the parameterized constructor with default values
        this(0, title, author, publisher, status, genre, new Date(), sizeInMB);
    }

    // no-arg constructor with default values
    public DVD() {
        // Call the parameterized constructor with default values
        this(0, "", "", "", ' ', "", new Date(), 1);
    }

    // Setter method for sizeInMB
    public void setSizeInMB(double sizeInMB) {
        if (sizeInMB > 0) {
            this.sizeInMB = sizeInMB;
        } else {
            this.sizeInMB = 1;
        }
    }

    // Getter method for sizeInMB
    public double getSizeInMB() {
        return sizeInMB;
    }

    // Getter method for DVD_COST constant
    public static double getDvdCost() {
        return DvdCost;
    }

    // Calculate the total price including general cost and DVD cost
    public double getPrice() {
        return getGeneralCost() + getDvdCost();
    }

    // Override toString() method to provide a string representation of DVD object
    @Override
    public String toString() {
        return "DVD: \n"
                + super.toString()
                + "\nDVD size: " + sizeInMB + "\n--------\n";
    }
}
