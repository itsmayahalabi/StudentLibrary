/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author maya
 */

import java.util.ArrayList;

public class Student extends Person {

    // Attributes specific to Student
    private String studentId;
    private static int maxNumberToBorrow = 3; // Maximum number of items a student can borrow

    // Constructor with parameters
    public Student(String name, String address, char gender, int age, String phoneNumber, String studentId) {
        super(name, address, gender, age, phoneNumber); // Call to superclass constructor(Person)
        this.studentId = studentId;
    }

    // no-arg constructor
    public Student() {
        this("unkown", "unknown", 'm', 18, "00-000000", "unknown");
    }

    // setter method to change the maximum number of items a student can borrow
    public static void setMaxNumberToBorrow(int maxNumberToBorrow) {
        Student.maxNumberToBorrow = maxNumberToBorrow;
    }

    // Getter method for student ID
    public String getStudentId() {
        return studentId;
    }

    // Getter method for the maximum number of items a student can borrow
    public static int getMaxNumberToBorrow() {
        return maxNumberToBorrow;
    }

    // Method to borrow an item from the library
    public boolean borrowItem(LibraryItem item) {
        if (getBorrowedItem().size() < maxNumberToBorrow && (item.getStatus() == 'a' || item.getStatus() == 'A')) {
            // Check if the item is not already borrowed by this student
            if (!getBorrowedItem().contains(item)) {
                item.setStatus('o');
                item.setRegistration();
                item.getPastOwners().add(this);
                getBorrowedItem().add(item);
                return true;
            } else {
                System.out.println("You have already borrowed this item.");
            }
        } else {
            System.out.println("Item cannot be borrowed. Either maximum limit reached or item is not available.");
        }
        return false;
    }

    // Method to return an item to the library
    public boolean returnItem(LibraryItem item) {
        if (getBorrowedItem().contains(item)) {
            item.setStatus('a');
            item.setRegistration();
            getBorrowedItem().remove(item);
            return true;
        } else {
            return false;
        }
    }

    // Override toString method to provide a string representation of Student object
    @Override
    public String toString() {
        return "Student ID: " + studentId + "\n" + super.toString() + "\nnumber of borrowed items: "
                + super.getBorrowedItem().size() + "\n--------\n";
    }

}
