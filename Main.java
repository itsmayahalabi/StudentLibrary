/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

/**
 *
 * @author maya
 */
import java.util.*;
import java.io.*;

public class Main {

    /**
     * @param args the command line arguments
     */
    // GLOBAL VARIABLE 
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // CREATE ARRAYLISTS TO STORE LIBRARY ITEMS AND MEMBERS
        ArrayList<LibraryItem> myItems = new ArrayList<>();
        ArrayList<Person> myMembers = new ArrayList<>();

        try {

            // Attempt to load data from files into myMembers and myItems lists
            LoadFromFiles(myMembers, myItems);

        } catch (FileNotFoundException e) {

            //If the file is not found, wrap the exception in a RuntimeException and rethrow
            throw new RuntimeException(e);
        }

        while (true) {
            System.out.println("\n\n*******************************\n\n");
            int choice = getChoice(); // Get user's choice
            long serialNb;

            System.out.println();

            switch (choice) {

                // ADDING AN ITEM TO THE LIBRARY
                case 1:
                    addNewLibraryItem(myItems);
                    break;

                // MODIFYING AN ITEM IN THE LIBRARY
                case 2:

                    modifyItem(myItems);
                    break;

                // DELETE AN ITEM
                case 3:
                    deleteItem(myItems);
                    break;

                // ADD NEW MEMBER 
                case 4:

                    addNewMember(myMembers);
                    break;

                // MODIFY A MEMBER INFO
                case 5:

                    modifyMember(myMembers);
                    break;

                // DELETE A MEMBER INFO
                case 6:

                    deleteMember(myMembers);
                    break;

                // SEARCH AN ITEM
                case 7:
                    searchItem(myItems);
                    break;

                // SEARCH A MEMBER
                case 8:
                    searchMember(myMembers);
                    break;

                // BORROW AN ITEM
                case 9:

                    borrowItem(myItems, myMembers);
                    break;

                // RETURN AN ITEM
                case 10:

                    returnItem(myItems, myMembers);
                    break;

                // DISPLAY ALL ITEMS
                case 11:
                    displayItems(myItems);
                    break;

                // DISPLAY ALL MEMBERS
                case 12:
                    displayMembers(myMembers);
                    break;
            }

            // EXIT
            if (choice == 13) {
                try {
                    SaveAllToFiles(myMembers, myItems);
                } catch (Exception e) {
                    System.out.println("error in save");
                }
                break;
            }
        }
    }

    /**
     * Saves information about members and items to a text file.
     *
     * @param myMembers List of members (including Students and Civilians).
     * @param myItems List of library items (including Books and DVDs).
     * @throws FileNotFoundException If the output file cannot be created.
     */
    public static void SaveAllToFiles(ArrayList<Person> myMembers, ArrayList<LibraryItem> myItems) throws FileNotFoundException {

        // Define the output file path
        File outputFile = new File("C:\\Users\\mayam\\Downloads\\output.txt");

        // Check if the output file already exists
        if (!outputFile.exists()) {

            // Create a PrintWriter to write to the output file
            PrintWriter print = new PrintWriter(outputFile);

            // Write header information
            print.write("Information: \n\nMembers Info:\nStudents:\n");

            // Iterate through members and write Student information
            for (Person member : myMembers) {
                if (member instanceof Student) {
                    print.write(((Student) member).toString() + "\n");
                }
            }

            // Write Civilian information
            print.write("\nCivilian Info:\n");

            for (Person member : myMembers) {
                if (member instanceof Civilian) {
                    print.write(((Civilian) member).toString() + "\n");
                }
            }

            // Write item information (Books)
            print.write("\n\nItems Info:\nBooks:\n");
            for (LibraryItem items : myItems) {
                if (items instanceof Book) {
                    print.write(((Book) items).toString() + "\n");
                }
            }

            // Write item information (DVDs)
            for (LibraryItem items : myItems) {
                if (items instanceof DVD) {
                    print.write(((DVD) items).toString() + "\n");
                }
            }

            // Close the PrintWriter
            print.close();
            System.out.println("Data saved to output.txt successfully.");

        } else {
            System.out.println("Data already written to file output.txt!");
        }

    }

    public static void LoadFromFiles(ArrayList<Person> members, ArrayList<LibraryItem> items) throws FileNotFoundException {
        ArrayList<String> borrowed = new ArrayList<>();
        ArrayList<String> owners = new ArrayList<>();
        loadAllmembers(members, borrowed, "members.txt");
        loadAllItems(items, owners, "items.txt");
        adjustOwners(members, items, owners);
        adjustBorrowed(members, items, borrowed);

    }

    public static void adjustBorrowed(ArrayList<Person> members, ArrayList<LibraryItem> items,
            ArrayList<String> borrowed) {
        for (int i = 0; i < members.size(); i++) {
            if (borrowed.get(i) != null) {
                String[] itemsBorrowed = borrowed.get(i).split("##");
                for (String serial : itemsBorrowed) {
                    if (serial != null) {
                        LibraryItem item = searchItemBySerialNb(Long.parseLong(serial), items);
                        if (item != null) {
                            members.get(i).getBorrowedItem().add(item);
                        }
                    }
                }
            }
        }
    }

    public static void adjustOwners(ArrayList<Person> members, ArrayList<LibraryItem> items,
            ArrayList<String> owners) {
        for (int i = 0; i < items.size(); i++) {
            if (owners.get(i) != null) {
                String[] oldOwners = owners.get(i).split("&&");
                for (String id : oldOwners) {
                    if (id != null) {
                        Person member = searchMemberById(id, members);
                        if (member != null) {
                            items.get(i).getPastOwners().add(member);
                        }
                    }
                }
            }
        }
    }

    public static void loadAllmembers(ArrayList<Person> members, ArrayList<String> borrowed,
            String filePath) throws FileNotFoundException {
        File myFile = new File(filePath);
        if (!myFile.exists()) {
            return;
        }

        Scanner reader = new Scanner(myFile);
        while (reader.hasNext()) {
            String line = reader.nextLine();
            String[] tokens = line.split("&");
            if (tokens[0].equals("C")) {
                members.add(new Civilian(tokens[1], tokens[2], tokens[3].charAt(0),
                        Integer.parseInt(tokens[4]), tokens[5], tokens[6], Double.parseDouble(tokens[7])));
                borrowed.add(tokens.length == 9 ? tokens[tokens.length - 1] : null);
            } else {
                members.add(new Student(tokens[1], tokens[2], tokens[3].charAt(0),
                        Integer.parseInt(tokens[4]), tokens[5], tokens[6]));
                borrowed.add(tokens.length == 8 ? tokens[tokens.length - 1] : null);
            }
        }
    }

    public static void loadAllItems(ArrayList<LibraryItem> items, ArrayList<String> owners,
            String filePath) throws FileNotFoundException {
        File myFile = new File(filePath);
        if (!myFile.exists()) {
            return;
        }

        Scanner reader = new Scanner(myFile);
        while (reader.hasNext()) {
            String line = reader.nextLine();
            String[] tokens = line.split("#");
            if (tokens[0].equals("D")) {
                items.add(new DVD(Long.parseLong(tokens[1]), tokens[2], tokens[3], tokens[4],
                        tokens[5].charAt(0), tokens[6], new Date(Long.parseLong(tokens[7])),
                        Double.parseDouble(tokens[8])));
            } else {
                items.add(new Book(Long.parseLong(tokens[1]), tokens[2], tokens[3], tokens[4],
                        tokens[5].charAt(0), tokens[6], new Date(Long.parseLong(tokens[7])),
                        Integer.parseInt(tokens[8])));
            }

            if (tokens.length == 10) {
                owners.add(tokens[9]);
            } else {
                owners.add(null);
            }
        }
    }

    public static LibraryItem searchItemBySerialNb(long serialNb, ArrayList<LibraryItem> items) {

        // Iterate through the list of items
        for (int i = 0; i < items.size(); i++) {

            // Check if the item's serial number matches the provided serial number
            if (items.get(i).getSerialNumber() == serialNb) {
                // If a match is found, return the item
                return items.get(i);
            }
        }
        // If no match is found, return null
        return null;
    }

    public static Person searchMemberById(String id, ArrayList<Person> members) {

        // Iterate through the list of members
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i) instanceof Student) {

                // If the Student's ID matches the provided ID, return the Student
                if (((Student) members.get(i)).getStudentId().equals(id)) {
                    return members.get(i);
                }
            }
            if (members.get(i) instanceof Civilian) {

                // If the Civilian's ID matches the provided ID, return the Civilian
                if (((Civilian) members.get(i)).getId().equals(id)) {
                    return members.get(i);

                }
            }
        }
        // If no match is found, return null
        return null;
    }

    public static int getChoice() {
        int choice = -1;

        while (choice < 0 || choice > 13) {
            System.out.println("Choose a number: ");
            System.out.println("1- add new Library Item");
            System.out.println("2- modify an item");
            System.out.println("3- delete an item");
            System.out.println("4- add new member");
            System.out.println("5- modify a member info");
            System.out.println("6- delete a member info");
            System.out.println("7- Search an item// or check availability");
            System.out.println("8- Search a member");
            System.out.println("9- Borrow an item");
            System.out.println("10- Retrun an item");
            System.out.println("11- Display all items");
            System.out.println("12- Display all members");
            System.out.println("13- exit");
            System.out.print("Enter your choice: ");

            choice = input.nextInt();
            input.nextLine();
            if (choice < 0 || choice > 13) {
                System.out.println("Invalid choice!!");
            }
        }
        return choice;
    }

    // Method to add new library item
    public static void addNewLibraryItem(ArrayList<LibraryItem> myItems) {
        char type;
        String title;
        String authorOrDirector;
        String publisherOrProducer;
        char status;
        String genre;
        int numberOfPages;
        int sizeInMb;

        System.out.print("Enter the new item information:\n------------------------\n");

        // Prompt user for item type (Book or DVD)
        do {

            System.out.print("What is the type of the new item [B] Book or [D] DVD? ");
            type = Character.toLowerCase(input.next().charAt(0));
            input.nextLine();

            if (type != 'b' && type != 'd') {
                System.out.println("Invalid type of an item! Try again.");
            }

        } while (type != 'b' && type != 'd');

        // Input details for a Book if user chose adding a book
        if (type == 'b') {

            System.out.print("Enter the Book title: ");
            title = input.nextLine();

            System.out.print("Enter the author name: ");
            authorOrDirector = input.nextLine();

            System.out.print("Enter the publisher: ");
            publisherOrProducer = input.nextLine();

            System.out.print("Enter the status [a] for available or [r] for reference: ");
            status = Character.toLowerCase(input.next().charAt(0));
            input.nextLine();

            System.out.print("Enter the book's genre: ");
            genre = input.next();

            System.out.print("Enter the number of pages in the book: ");
            numberOfPages = input.nextInt();

            // Create and add a new Book to the library
            myItems.add(new Book(title, authorOrDirector, publisherOrProducer, status, genre, numberOfPages));

            System.out.println("\nNew Book added correctly.");

        } else {

            // Input details for a DVD if user chose adding a DVD
            System.out.print("Enter the DVD's title: ");
            title = input.nextLine();

            System.out.print("Enter the director name: ");
            authorOrDirector = input.nextLine();

            System.out.print("Enter the producer name: ");
            publisherOrProducer = input.nextLine();

            System.out.print("Enter the status [a] for available or [r] for reference: ");
            status = Character.toLowerCase(input.next().charAt(0));

            System.out.print("Enter the DVD's genre: ");
            genre = input.next();

            System.out.print("Enter the DVD's size in MB: ");
            sizeInMb = input.nextInt();
            input.nextLine();

            // Create and add a new DVD to the library
            myItems.add(new DVD(title, authorOrDirector, publisherOrProducer, status, genre, sizeInMb));

            System.out.println("\nNew DVD added correctly.");
        }
    }

    // Method to madify an item
    public static void modifyItem(ArrayList<LibraryItem> myItems) {
        char modification;

        boolean toStop = true;

        System.out.print("Modifying:\n-----------------------------\nEnter the serial number of the prefered item to modify: ");
        long serialNb = input.nextLong();
        input.nextLine();

        // Check if the item with the given serial number exists
        if (searchItemBySerialNb(serialNb, myItems) != null) {
            do {

                // Prompt the user to choose the information to modify
                System.out.println("Enter the data you want to modify:\nN: Serial Number\nT: Title\nA: Author/Director\n"
                        + "P: Publisher/Producer\nS: Status\nG: Genre\nE: Exit");
                modification = Character.toUpperCase(input.next().charAt(0));

                switch (modification) {

                    // Modify the serial number of the item if user chose this option
                    case 'N':
                        long newSerialNb;

                        System.out.print("Enter the new Serial Number: ");
                        newSerialNb = input.nextLong();

                        searchItemBySerialNb(serialNb, myItems).setSerialNumber(newSerialNb);
                        System.out.println("Modified successfully!");
                        break;

                    // Modify the title of the item if user chose this option
                    case 'T':
                        String newTitle;

                        System.out.print("Enter the new Title: ");
                        input.nextLine();
                        newTitle = input.nextLine();

                        searchItemBySerialNb(serialNb, myItems).setTitle(newTitle);

                        System.out.println("Modified successfully!");
                        break;

                    // Modify the Author/Director of the item if user chose this option
                    case 'A':
                        String newAuthorOrDirector;

                        System.out.print("Enter the name of the new author/director: ");
                        input.nextLine();
                        newAuthorOrDirector = input.nextLine();

                        searchItemBySerialNb(serialNb, myItems).setAuthorOrDirector(newAuthorOrDirector);
                        System.out.println("Modified successfully!");
                        break;

                    // Modify the Publisher/Producer of the item if user chose this option
                    case 'P':
                        String newPublisherOrProducer;

                        System.out.print("Enter the name of the new publisher/producer: ");
                        input.nextLine();
                        newPublisherOrProducer = input.nextLine();

                        searchItemBySerialNb(serialNb, myItems).setPublisherOrProducer(newPublisherOrProducer);
                        System.out.println("Modified successfully!");
                        break;

                    // Modify the status of the item if user chose this option
                    case 'S':
                        char newStatus;

                        System.out.print("Enter the new status, [a] for available, [r] for reference, or [o] for occupied: ");
                        newStatus = Character.toLowerCase(input.next().charAt(0));

                        searchItemBySerialNb(serialNb, myItems).setStatus(newStatus);
                        searchItemBySerialNb(serialNb, myItems).setRegistration();

                        break;

                    // Modify the genre of the item if user chose this option
                    case 'G':
                        String newGenre;

                        System.out.print("Enter the new Genre: ");
                        input.nextLine();
                        newGenre = input.nextLine();

                        searchItemBySerialNb(serialNb, myItems).setGenre(newGenre);
                        System.out.println("Modified successfully!");

                        break;

                    // Exit the modification loop
                    case 'E':

                        System.out.println("Exiting modification mode.");
                        toStop = false;
                        break;
                    default:
                        System.out.println("Invalid input! Try again.");
                        break;

                }

            } while (toStop);

            // display if item was not found. 
        } else {
            System.out.println("Item was not found.");
        }
    }

    // Method to delete an item
    public static void deleteItem(ArrayList<LibraryItem> myItems) {

        // Prompt the user to enter the serial number of the item to delete
        System.out.print("Enter the serial number of the prefered item to delete: ");
        long serialNb = input.nextLong(); // Read the user input

        // Search for the item with the specified serial number
        LibraryItem foundingItem = searchItemBySerialNb(serialNb, myItems);

        if (foundingItem != null) {

            // If the item is found, remove it from the list
            myItems.remove(foundingItem);
            System.out.println("Deleted Successfully. ");

        } else {

            // If the item is not found, display an error message
            System.out.println("Item was not found. ");

        }

    }

    // Method to add a new member
    public static void addNewMember(ArrayList<Person> myMembers) {

        // Variables to store member information
        String id;
        char type;
        String name;
        String address;
        char gender;
        int age;
        String phoneNumber;
        double balance;

        // Prompt the user to enter the new member's information
        System.out.println("Adding a new member. Enter the new member information:\n------------------------------");

        // Loop until the user provides a valid type (s for student, c for civilian)
        do {
            System.out.print("Choose [s} for student and [c] for civilian: ");
            type = Character.toLowerCase(input.next().charAt(0));

            // keep track on the input
            if (type != 'c' && type != 's') {
                System.out.println("Invalid input! Must be [s] or [c]!");
            }

        } while (type != 'c' && type != 's');

        // Prompt the user to enter the new member's ID
        System.out.print("Enter the new id: ");
        id = input.next();
        input.nextLine();

        // Prompt the user to enter the new member's name
        System.out.print("Enter the member name: ");
        name = input.nextLine();

        // Prompt the user to enter the new member's address
        System.out.print("Enter the address: ");
        address = input.next();

        // Prompt the user to enter the new member's gender
        System.out.print("Male [M] or Female [F]: ");
        gender = Character.toLowerCase(input.next().charAt(0));

        // Prompt the user to enter the new member's age
        System.out.print("Enter the age: ");
        age = input.nextInt();

        // Prompt the user to enter the new member's phone number
        System.out.print("Enter phone number: ");
        phoneNumber = input.next();

        // Check the type of member and add accordingly
        // If Civilian
        if (type == 'c') {

            // Prompt the user to enter the initial balance for civilian members
            System.out.print("Enter the initial balance: ");
            balance = input.nextDouble();

            myMembers.add(new Civilian(name, address, gender, age, phoneNumber, id, balance));
        } else {
            // If Student, add the new student member to the list of library member
            myMembers.add(new Student(name, address, gender, age, phoneNumber, id));
        }
        System.out.println("Added Succesfully.");

        /**
         * Modifies the information of a library member based on their ID.
         *
         * @param myMembers The list of library members to modify.
         */
    }

    // Method to modify a member
    public static void modifyMember(ArrayList<Person> myMembers) {

        // variables
        boolean toExit = true; // Flag to control the modification loop
        char modification;

        // Prompt the user to enter the ID of the member to modify
        System.out.print("Modifying:\n----------------------------\nEnter id of the member: ");
        String id = input.next();

        // Check if the member with the specified ID exists and the list of members is not empty
        if (searchMemberById(id, myMembers) != null && myMembers != null) {

            do {

                // Prompt the user to choose the information to modify
                System.out.print("Enter the data you want to modify:\nN: Name\nD: Address\n"
                        + "G: Gender\nA: Age\nP: Phone Number\nE: Exit\n");
                modification = Character.toLowerCase(input.next().charAt(0));

                switch (modification) {
                    // Modify the member's name
                    case 'n':
                        String newName;

                        System.out.print("Enter the new Name: ");
                        input.nextLine();
                        newName = input.nextLine();

                        searchMemberById(id, myMembers).setName(newName);
                        System.out.println("Modified successfully!");

                        break;

                    // Modify the member's address
                    case 'd':
                        String newAddress;

                        System.out.print("Enter the new Address: ");
                        input.nextLine();
                        newAddress = input.next();

                        searchMemberById(id, myMembers).setAddress(newAddress);
                        System.out.println("Modified successfully!");

                        break;

                    // Modify the member's gender
                    case 'g':
                        char newGender;

                        System.out.print("Enter the new Gender: ");
                        input.nextLine();
                        newGender = input.next().charAt(0);

                        searchMemberById(id, myMembers).setGender(newGender);
                        System.out.println("Modified successfully!");

                        break;

                    // Modify the member's age
                    case 'a':
                        int newAge;

                        System.out.print("Enter the new Age: ");
                        input.nextLine();
                        newAge = input.nextInt();

                        searchMemberById(id, myMembers).setAge(newAge);
                        System.out.println("Modified successfully!");

                        break;

                    // Modify the member's phone number
                    case 'p':
                        String newPhoneNumber;

                        System.out.print("Enter the new Phone Number: ");
                        input.nextLine();
                        newPhoneNumber = input.next();

                        searchMemberById(id, myMembers).setPhoneNumber(newPhoneNumber);
                        System.out.println("Modified successfully!");

                        break;

                    // Exit modification mode
                    case 'e':
                        System.out.println("Exiting modification mode. ");
                        toExit = false;
                        break;

                    // Invalid input
                    default:
                        System.out.println("Invalid input! Try again. ");
                        break;

                }

            } while (toExit);

        } else {
            // Print a message indicating that the member was not found
            System.out.println("Item was not found. ");
        }
    }

    // method to delete a member
    public static void deleteMember(ArrayList<Person> myMembers) {
        String id;

        // Prompt the user to choose between continuing deletion or exiting
        System.out.println("E: If you want to delete or modify a specific information of a member go to 5th choice.(Exit)\nC: Continue deletion. ");

        // Read the user's choice and convert it to lowercase
        char option = Character.toLowerCase(input.next().charAt(0));

        // Perform actions based on the user's choice
        switch (option) {

            // Exit the deletion process
            case 'e':
                System.out.println("Exiting deletion. ");
                break;

            // Continue with deletion
            case 'c':
                System.out.print("Enter the id of the member: ");
                id = input.next();

                // Check if the member with the specified ID exists and the list of members is not empty
                if (searchMemberById(id, myMembers) != null && myMembers != null) {

                    // Remove the member from the list of members
                    myMembers.remove(searchMemberById(id, myMembers));
                    System.out.println("Successfully Deleted. ");

                } else {

                    // Print a message indicating that the member was not found
                    System.out.println("Member was not found. ");
                    break;
                }

            default:

                // Print a message indicating an invalid input
                System.out.println("Invalid input. ");
        }
    }

    // Method to search an item 
    public static void searchItem(ArrayList<LibraryItem> myItems) {

        // Prompt the user to enter the serial number of the item to search for
        System.out.print("Enter the serial number of the prefered item: ");
        long serialNb = input.nextLong();

        // Check if the item was found
        if (searchItemBySerialNb(serialNb, myItems) != null) {

            // Print the status of the item based on its status code
            if (searchItemBySerialNb(serialNb, myItems).getStatus() == 'a') {
                System.out.println("Item available.");

            } else if (searchItemBySerialNb(serialNb, myItems).getStatus() == 'o') {
                System.out.println("Item occupied.");

            } else {
                System.out.println("Item rented.");
            }
        } else {
            // Print a message indicating that the item was not found
            System.out.println("Item Was Not Found.");
        }
    }

    public static void searchMember(ArrayList<Person> myMembers) {

        // Prompt the user to enter the ID of the member to search for
        System.out.print("Enter the Id of the member:");
        String id = input.next();

        // Check if the member was found
        if (searchMemberById(id, myMembers) != null) {
            // Print a message indicating that the member was found
            System.out.println("Member Was Found");
        } else {
            // Print a message indicating that the member was not found
            System.out.println("Member Was Not Found");
        }
    }

    // Method to borrow an item
    public static void borrowItem(ArrayList<LibraryItem> myItems, ArrayList<Person> myMembers) {

        // Initialize variables
        String borrowItem;
        String borrowMember;

        // Boolean flag to track if the item was returned
        boolean itemFound = false;

        // Promt the user to enter the item title or serial number for return
        System.out.print("Borrow Item: \nEnter the item title or serialNumber: ");
        borrowItem = input.nextLine();

        // Loop through the list of items to find the item to be returned
        for (int i = 0; i < myItems.size(); i++) {

            // Check if the current item matches the specified returnItem
            if (borrowItem.equalsIgnoreCase(myItems.get(i).getTitle()) || borrowItem.equals(String.valueOf(myItems.get(i).getSerialNumber()))) {
                if (myItems.get(i).getStatus() != 'r' || myItems.get(i).getSerialNumber() != 'o') {
                    LibraryItem theItem = searchItemBySerialNb(myItems.get(i).getSerialNumber(), myItems);

                    // Prompt the user to enter their ID or name
                    System.out.print("Enter your ID or name: ");
                    borrowMember = input.next();

                    // Loop through the list of members to find the returning member
                    for (int j = 0; j < myMembers.size(); j++) {
                        if (myMembers.get(j) instanceof Student) {
                            Person thePerson = searchMemberById(((Student) myMembers.get(j)).getStudentId(), myMembers);

                            // Check if the returning member matches the Student's ID or name
                            if (borrowMember.equals(((Student) myMembers.get(j)).getStudentId()) || borrowMember.equals(thePerson.getName())) {
                                ((Student) thePerson).borrowItem(theItem);
                                myItems.get(j).setRegistration();

                                // Print confirmation message
                                System.out.println("Item borrowed. Return date: " + myItems.get(j).getDateAvailable());
                                itemFound = true; // Set the flag to true
                                break; // Exit the loop
                            }

                        }
                        if (myMembers.get(j) instanceof Civilian) {
                            Person thePerson = searchMemberById(((Civilian) myMembers.get(j)).getId(), myMembers);

                            // Check if the returning member matches the Civilian's ID or name
                            if (borrowMember.equals(((Civilian) myMembers.get(j)).getId()) || borrowMember.equals(thePerson.getName())) {
                                ((Civilian) thePerson).borrowItem(theItem);
                                myItems.get(j).setRegistration();

                                // Print confirmation message
                                System.out.println("Item borrowed. Return date: " + myItems.get(j).getDateAvailable());
                                itemFound = true; // Set the flag to true
                                break; // Exit the loop
                            }
                        }
                    }
                }
            }
        }

        // Display the "Item Was Not Found." message only if the item was not found
        if (!itemFound) {
            System.out.println("Item Was Not Found.");
        }
    }

    // Method to return an item
    public static void returnItem(ArrayList<LibraryItem> myItems, ArrayList<Person> myMembers) {

        // Initialize variables
        String returnItem;
        String returnMember;

        // Boolean flag to track if the item was returned
        boolean found = false;

        // Prompt the user to enter the item title or serial number
        System.out.print("Return Item: \nEnter the item title or serialNumber: ");
        returnItem = input.nextLine();

        // Loop through the list of items to find the item to be returned
        for (int i = 0; i < myItems.size(); i++) {

            // Check if the current item matches the specified returnItem
            if (returnItem.equals(myItems.get(i).getTitle()) || returnItem.equals(myItems.get(i).getSerialNumber())) {
                LibraryItem theItem = searchItemBySerialNb(myItems.get(i).getSerialNumber(), myItems);

                // Prompt the user to enter their ID or name
                System.out.print("Enter your ID or name: ");
                returnMember = input.next();

                // Loop through the list of members to find the returning member
                for (int j = 0; j < myMembers.size(); j++) {
                    if (myMembers.get(j) instanceof Student) {

                        // Check if the returning member matches the Student's ID or name
                        if (returnMember.equals(((Student) searchMemberById(((Student) myMembers.get(j)).getStudentId(), myMembers)).getStudentId())
                                || returnMember.equalsIgnoreCase(searchMemberById(((Student) myMembers.get(j)).getStudentId(), myMembers).getName())) {

                            // Return the item for the Student
                            ((Student) searchMemberById(((Student) myMembers.get(j)).getStudentId(), myMembers)).returnItem(theItem);

                            // Print confirmation message
                            System.out.println("Item reterned. Return date: " + myItems.get(j).getDateAvailable());

                            // Set found flag to true
                            found = true;

                            // Exit the loop
                            break;
                        }

                    }
                    if (myMembers.get(j) instanceof Civilian) {

                        // Check if the returning member matches the Civilian's ID or name
                        if (returnMember.equals(((Civilian) searchMemberById(((Civilian) myMembers.get(j)).getId(), myMembers)).getId())
                                || returnMember.equalsIgnoreCase(searchMemberById(((Civilian) myMembers.get(j)).getId(), myMembers).getName())) {

                            // Return the item for the Civilian
                            ((Civilian) searchMemberById(((Civilian) myMembers.get(j)).getId(), myMembers)).returnItem(theItem);

                            // Print confirmation message
                            System.out.println("Item returned. Return date: " + myItems.get(j).getDateAvailable());

                            // Set found flag to true
                            found = true;

                            // Exit the loop
                            break;
                        }

                    }
                }

            }
        }

        if (!found) {
            System.out.println("Item was not returned(error)");
        }
    }

    // Method to display items
    public static void displayItems(ArrayList<LibraryItem> myItems) {

        // Check if the list of items is not null
        if (myItems != null) {

            System.out.println("Display all items: \n-------------------------\n\n");

            // Iterate through the list of items
            for (int i = 0; i < myItems.size(); i++) {

                if (myItems.get(i) instanceof Book) {
                    // Print book details
                    System.out.println("\n" + ((Book) myItems.get(i)).toString() + "\n");
                } else {
                    // Print DVD details
                    System.out.println("\n" + ((DVD) myItems.get(i)).toString() + "\n");
                }
            }
        } else {
            // If no items are entered, print a message
            System.out.println("No Items Entered Yet. ");
        }
    }

    // Method to display the members
    public static void displayMembers(ArrayList<Person> myMembers) {

        // Check if the list of members is not null
        if (myMembers != null) {

            // Check if the list of members is not null
            System.out.println("Display all members: \n-------------------------\n\n");

            // Iterate through the list of members
            for (int i = 0; i < myMembers.size(); i++) {

                if (myMembers.get(i) instanceof Student) {
                    // Print student details
                    System.out.println("Student: \n" + ((Student) myMembers.get(i)).toString() + "\n");
                } else {
                    // Print civilian details
                    System.out.println("Civilian: \n" + ((Civilian) myMembers.get(i)).toString() + "\n");
                }
            }
        } else {
            // If no members are entered, print a message
            System.out.println("No Members Entered Yet. ");
        }

    }

}
