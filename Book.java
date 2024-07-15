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

public class Book extends LibraryItem {

    // Constant for the cost of a book (Attribute specific to Book)
    private final static double bookCost = 8;

    // Number of pages in the book (Attribute specific to Book)
    private int nbOfPages;

    // Constructor with all parameters
    public Book(long serialNumber, String title, String author, String publisher, char status, String genre, Date dateAvailable, int nbOfPages) {
        super(serialNumber, title, author, publisher, status, genre, dateAvailable);
        setNbOfPages(nbOfPages);
        setSerialNumber(-1);
    }

    // Constructor with basic information and default date and number of pages
    public Book(String title, String author, String publisher, char status, String genre, int numberOfPages) {
        // Call the parameterized constructor with default values
        this(0, title, author, publisher, status, genre, new Date(), numberOfPages);
    }

    // no-arg constructor with default values
    public Book() {
        // Call the parameterized constructor with default values
        this(0, "", "", "", ' ', "", new Date(), 1);
    }

    // Setter method for number of pages
    public void setNbOfPages(int nbOfPages) {
        if (nbOfPages >= 1) {
            this.nbOfPages = nbOfPages;
        } else {
            this.nbOfPages = 1;
        }
    }

    // Getter method for book cost
    public static double getBookCost() {
        return bookCost;
    }

    // Getter method for number of pages
    public int getNbOfPages() {
        return nbOfPages;
    }

    // Method to calculate the total price of the book (including book cost)
    public double getPrice() {
        return getPrice() + bookCost;
    }

    // Override toString method to display book details
    @Override
    public String toString() {
        return "Book: \n"
                + super.toString()
                + "\nnumber of pages: " + nbOfPages + "\n--------\n";
    }

}
