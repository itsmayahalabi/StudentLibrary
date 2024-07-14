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
import java.util.ArrayList;

public class LibraryItem {

    // Static variable to hold the general cost for all library items
    public static double generalCost;

    // Instance variables
    private long serialNumber;
    private String title;
    private String authorOrDirector;
    private String publisherOrProducer;
    private char status;
    private String genre;
    private Date dateAvailable;
    private ArrayList<Person> pastOwners;

    // Constructor with parameters
    public LibraryItem(long serialNumber, String title, String authorOrDirector, String publisherOrProducer, char status, String genre, Date dateAvailable) {
        this(title, authorOrDirector, publisherOrProducer, status, genre);
        this.dateAvailable = dateAvailable;
        this.pastOwners = new ArrayList<>();
        setRegistration();
    }

    // Constructor with less parameters
    public LibraryItem(String title, String authorOrDirector, String publisherOrProducer, char status, String genre) {
        setTitle(title);
        setAuthorOrDirector(authorOrDirector);
        setPublisherOrProducer(publisherOrProducer);
        setStatus(status);
        setGenre(genre);
        this.dateAvailable = new Date();
        this.pastOwners = new ArrayList<>();
        setRegistration();
    }

    // no-arg constructor
    public LibraryItem() {
        this("", "", "", ' ', "");
    }

    // Setter methods to set the data fields (no arraylist)
    public static void setGeneralCost(double generalCost) {
        if (generalCost >= 1) {
            LibraryItem.generalCost = generalCost;
        } else {
            System.out.println("Invalid cost! It must be positive!");
        }
    }

    public void setSerialNumber(long serialNumber) {

        if (serialNumber == -1) {
            // Get the current date and time
            Date now = new Date();

            // Extract the last digit of the year
            int year = (now.getYear() + 1900) % 10;

            // Extract other components of the date
            int month = now.getMonth() + 1; // Adding 1 because months are zero-based
            int day = now.getDate();
            int hours = now.getHours();
            int minutes = now.getMinutes();
            int seconds = now.getSeconds();

            // Format the serial number
            String formattedSerialNumber = String.format("%d%02d%02d%02d%02d%02d", year, month, day, hours, minutes, seconds);

            // Convert the formatted serial number to a long
            this.serialNumber = Long.parseLong(formattedSerialNumber);

        } else if (String.valueOf(serialNumber).length() == 11) {
            int year = Integer.valueOf(String.valueOf(String.valueOf(serialNumber).substring(0, 1)));
            int month = Integer.valueOf(String.valueOf(String.valueOf(serialNumber).substring(1, 3)));
            int day = Integer.valueOf(String.valueOf(String.valueOf(serialNumber).substring(3, 5)));
            int hour = Integer.valueOf(String.valueOf(String.valueOf(serialNumber).substring(5, 7)));
            int minute = Integer.valueOf(String.valueOf(String.valueOf(serialNumber).substring(7, 9)));
            int second = Integer.valueOf(String.valueOf(String.valueOf(serialNumber).substring(9)));

            if ((year >= 0 && year <= 9) && (month >= 1 && month <= 12)
                    && (day >= 1 && day <= 31) && (hour >= 0 && hour <= 24)
                    && (minute >= 0 && minute <= 60)
                    && (second >= 0 && second <= 60)) {
                this.serialNumber = serialNumber;
            }

        } else {
            this.serialNumber = 0;
        }
    }

    // setters for all the data fields
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorOrDirector(String authorOrDirector) {
        this.authorOrDirector = authorOrDirector;
    }

    public void setPublisherOrProducer(String publisherOrProducer) {
        this.publisherOrProducer = publisherOrProducer;
    }

    public void setStatus(char status) {
        status = Character.toLowerCase(status);
        if (status == 'a' || status == 'o' || status == 'r') {
            this.status = status;
        } else {
            this.status = 'a';
        }
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // getters for all the data fields
    public static double getGeneralCost() {
        return generalCost;
    }

    public long getSerialNumber() {
        return serialNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorOrDirector() {
        return authorOrDirector;
    }

    public String getPublisherOrProducer() {
        return publisherOrProducer;
    }

    public char getStatus() {
        return status;
    }

    public String getGenre() {
        return genre;
    }

    public Date getDateAvailable() {
        return dateAvailable;
    }

    public ArrayList<Person> getPastOwners() {
        return pastOwners;
    }

    // Method to set registration status based on item's status
    public void setRegistration() {
        // Convert status to lowercase for uniformity
        status = Character.toLowerCase(status);
        if (status == 'a') {
            dateAvailable = new Date();
        } else if (status == 'o') {
            dateAvailable = new Date();
            if (dateAvailable.getMonth() == 0) {
                dateAvailable.setMonth(dateAvailable.getMonth() + 4);
            } else {
                dateAvailable.setMonth(dateAvailable.getMonth() + 3);
            }
        }
    }

    // Method to calculate remaining days until available
    public long getTimeRemainingDays() {
        Date currentDate = new Date();
        long timeDifference = dateAvailable.getTime() - currentDate.getTime();
        long daysDifference = timeDifference / (1000 * 60 * 60 * 24); // Convert milliseconds to days

        if (daysDifference < 0) {
            daysDifference = 0;
        }

        return daysDifference;
    }

    // Method to get the price of the item
    public double getPrice() {
        return generalCost;
    }

    // Override toString method to provide a string representation of the object
    @Override
    public String toString() {
        return "SN: " + serialNumber + "\n" + title + (genre != null && !genre.isEmpty() ? (" [" + genre + "]\nby ") : "\nby ")
                + authorOrDirector + " published by " + publisherOrProducer + "\nis " + (status == 'r' ? "a reference item"
                        : status == 'a' ? "available"
                                : status == 'o' ? "is on loan, will be available on " + dateAvailable
                                        : "unknown status");
    }

}
