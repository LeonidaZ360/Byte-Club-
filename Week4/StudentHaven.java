import java.util.Scanner;

public class StudentHaven{

    private static final String ADMIN_PASSWORD = "admin123"; //hardcoded for now

    public static void main(String[] args){

        Scanner input = new Scanner(System.in);

        //arrays now hold the data for many listings instead of just one
        //the same index in every array belongs to the same listing
        int maxListings = 50;
        int[] listingID = new int[maxListings]; //a permanent ID, unlike the array index
        String[] landlordName = new String[maxListings];
        String[] phoneNumber = new String[maxListings];
        double[] rentPrice = new double[maxListings];
        double[] distancefromCampus = new double[maxListings]; //now a number (km) so we can calculate with it
        int[] maxBedspace = new int[maxListings];
        int[] totalOccupants = new int[maxListings];
        int[] availableSpace = new int[maxListings];
        String[] description = new String[maxListings];
        int listingCount = 0; //how many listings have been added so far
        int nextListingID = 1; //only ever goes up, so IDs are never reused

        int choice;

        do {
            displayStudentMenu();
            choice = readMenuChoice(input);

            switch (choice) {
                case 1:
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        //traversal: visits every listing once - O(n)
                        for (int i = 0; i < listingCount; i++) {
                            displayListing(listingID[i], landlordName[i], phoneNumber[i], rentPrice[i],
                                    distancefromCampus[i], maxBedspace[i], totalOccupants[i],
                                    availableSpace[i], description[i]);
                        }
                        System.out.println("---------------------------------------------------------------------------------------");
                    }
                    break;

                case 2:
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        double totalRent = 0;
                        double totalDistance = 0;
                        double minRent = rentPrice[0];
                        double maxRent = rentPrice[0];
                        double closest = distancefromCampus[0];

                        //one loop through all listings to work everything out - O(n)
                        for (int i = 0; i < listingCount; i++) {
                            totalRent = totalRent + rentPrice[i];
                            totalDistance = totalDistance + distancefromCampus[i];

                            if (rentPrice[i] < minRent) {
                                minRent = rentPrice[i];
                            }
                            if (rentPrice[i] > maxRent) {
                                maxRent = rentPrice[i];
                            }
                            if (distancefromCampus[i] < closest) {
                                closest = distancefromCampus[i];
                            }
                        }

                        System.out.println("---------------------------------------------------------------------------------------");
                        System.out.println("Number of listings: " + listingCount);
                        System.out.println("Total rent: " + String.format("%.2f", totalRent));
                        System.out.println("Average rent: " + String.format("%.2f", totalRent / listingCount));
                        System.out.println("Cheapest rent: " + String.format("%.2f", minRent));
                        System.out.println("Most expensive rent: " + String.format("%.2f", maxRent));
                        System.out.println("Average distance: " + (totalDistance / listingCount) + " km");
                        System.out.println("Closest listing: " + closest + " km");
                        System.out.println("---------------------------------------------------------------------------------------");
                    }
                    break;

                case 3:
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        System.out.println("Enter the landlord's name: ");
                        String name = input.nextLine();

                        int index = linearSearch(landlordName, listingCount, name);

                        if (index == -1) {
                            System.out.println("No listing found for that landlord.");
                        } else {
                            System.out.println("Found: Listing ID " + listingID[index] + ", Phone: " + phoneNumber[index]
                                    + ", Rent: " + String.format("%.2f", rentPrice[index]) + ", Available space: " + availableSpace[index]);
                        }
                    }
                    break;

                case 4:
                    System.out.println("Enter the admin password: ");
                    String password = input.nextLine();

                    if (password.equals(ADMIN_PASSWORD)) {
                        System.out.println("Logged in as admin.");
                        int[] result = adminMenu(input, listingID, landlordName, phoneNumber, rentPrice,
                                distancefromCampus, maxBedspace, totalOccupants, availableSpace,
                                description, listingCount, maxListings, nextListingID);
                        listingCount = result[0];
                        nextListingID = result[1];
                        System.out.println("Logged out of admin mode.");
                    } else {
                        System.out.println("Incorrect password.");
                    }
                    break;

                case 5:
                    System.out.println("Goodbye.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 5);

        input.close();
    }

    // The admin's own menu loop. Only reachable after a correct password.
    // Returns {listingCount, nextListingID} so main() stays up to date, since
    // both can change while adding or removing listings.
    public static int[] adminMenu(Scanner input, int[] listingID, String[] landlordName, String[] phoneNumber,
                                   double[] rentPrice, double[] distancefromCampus, int[] maxBedspace,
                                   int[] totalOccupants, int[] availableSpace, String[] description,
                                   int listingCount, int maxListings, int nextListingID) {

        int choice;

        do {
            displayAdminMenu();
            choice = readMenuChoice(input);

            switch (choice) {
                case 1: // Add a new listing
                    if (listingCount == maxListings) {
                        System.out.println("The directory is full.");
                    } else {
                        int i = listingCount; //the next empty slot in the arrays - O(1)

                        listingID[i] = nextListingID;
                        nextListingID = nextListingID + 1;

                        landlordName[i] = readNonBlankLine(input,
                                "what's the Landlords name: ",
                                "Landlord name cannot be blank. Please enter again: ");

                        phoneNumber[i] = readNonBlankLine(input,
                                "what's the Landlords phone number: ",
                                "Phone number cannot be blank. Please enter again: ");

                        rentPrice[i] = readDouble(input, "what's the monthly rent of the boarding house: ");
                        while (rentPrice[i] <= 0) {
                            rentPrice[i] = readDouble(input, "Rent price must be greater than zero. Please enter again: ");
                        }

                        distancefromCampus[i] = readDouble(input, "How far is the Property from CBU campus (in km): ");

                        maxBedspace[i] = readIntInRange(input,
                                "How many bedspaces in the bh: ",
                                "Number of bedspaces must be greater than zero. Please enter again: ",
                                1, Integer.MAX_VALUE);

                        totalOccupants[i] = readIntInRange(input,
                                "How many people are currently living in the boarding house: ",
                                "Total occupants must be between 0 and max bedspaces (" + maxBedspace[i] + "). Please enter again: ",
                                0, maxBedspace[i]);

                        availableSpace[i] = (maxBedspace[i] - totalOccupants[i]);

                        System.out.println("give a brief description of the boarding house amenities:  \n");
                        description[i] = input.nextLine();

                        listingCount = listingCount + 1;
                        System.out.println("Listing added. Listing ID: " + listingID[i]);
                    }
                    break;

                case 2: // Remove a listing
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        int i = readValidListingID(input, listingID, listingCount, "Enter the listing ID to remove: ");

                        displayListing(listingID[i], landlordName[i], phoneNumber[i], rentPrice[i],
                                distancefromCampus[i], maxBedspace[i], totalOccupants[i],
                                availableSpace[i], description[i]);

                        System.out.println("Are you sure you want to remove this listing? (y/n): ");
                        String confirm = input.nextLine();

                        if (confirm.equalsIgnoreCase("y")) {
                            //shift every later listing back one slot to close the gap - O(n)
                            for (int j = i; j < listingCount - 1; j++) {
                                listingID[j] = listingID[j + 1];
                                landlordName[j] = landlordName[j + 1];
                                phoneNumber[j] = phoneNumber[j + 1];
                                rentPrice[j] = rentPrice[j + 1];
                                distancefromCampus[j] = distancefromCampus[j + 1];
                                maxBedspace[j] = maxBedspace[j + 1];
                                totalOccupants[j] = totalOccupants[j + 1];
                                availableSpace[j] = availableSpace[j + 1];
                                description[j] = description[j + 1];
                            }

                            listingCount = listingCount - 1;
                            System.out.println("Listing removed.");
                        } else {
                            System.out.println("Removal cancelled.");
                        }
                    }
                    break;

                case 3: // Edit a listing
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        int i = readValidListingID(input, listingID, listingCount, "Enter the listing ID to edit: ");

                        landlordName[i] = readNonBlankLine(input,
                                "what's the Landlords name: ",
                                "Landlord name cannot be blank. Please enter again: ");

                        phoneNumber[i] = readNonBlankLine(input,
                                "what's the Landlords phone number: ",
                                "Phone number cannot be blank. Please enter again: ");

                        rentPrice[i] = readDouble(input, "what's the monthly rent of the boarding house: ");
                        while (rentPrice[i] <= 0) {
                            rentPrice[i] = readDouble(input, "Rent price must be greater than zero. Please enter again: ");
                        }

                        distancefromCampus[i] = readDouble(input, "How far is the Property from CBU campus (in km): ");

                        maxBedspace[i] = readIntInRange(input,
                                "How many bedspaces in the bh: ",
                                "Number of bedspaces must be greater than zero. Please enter again: ",
                                1, Integer.MAX_VALUE);

                        totalOccupants[i] = readIntInRange(input,
                                "How many people are currently living in the boarding house: ",
                                "Total occupants must be between 0 and max bedspaces (" + maxBedspace[i] + "). Please enter again: ",
                                0, maxBedspace[i]);

                        availableSpace[i] = (maxBedspace[i] - totalOccupants[i]);

                        System.out.println("give a brief description of the boarding house amenities:  \n");
                        description[i] = input.nextLine();

                        System.out.println("Listing updated.");
                    }
                    break;

                case 4: // Add occupant
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        int i = readValidListingID(input, listingID, listingCount, "Enter the listing ID: ");

                        if (isAvailable(availableSpace[i])) {
                            totalOccupants[i] = totalOccupants[i] + 1;
                            availableSpace[i] = availableSpace[i] - 1;
                            System.out.println("Occupant added.");
                        } else {
                            System.out.println("No available space.");
                        }
                    }
                    break;

                case 5: // Remove occupant
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        int i = readValidListingID(input, listingID, listingCount, "Enter the listing ID: ");

                        if (totalOccupants[i] > 0) {
                            totalOccupants[i] = totalOccupants[i] - 1;
                            availableSpace[i] = availableSpace[i] + 1;
                            System.out.println("Occupant removed.");
                        } else {
                            System.out.println("There are no occupants to remove.");
                        }
                    }
                    break;

                case 6:
                    System.out.println("Returning to the student menu.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);

        return new int[] { listingCount, nextListingID };
    }

    // Keeps asking until the user types a whole number, so bad input can never crash the program.
    public static int readInt(Scanner input, String prompt) {
        System.out.println(prompt);
        while (!input.hasNextInt()) {
            System.out.println("That's not a whole number. Please enter again: ");
            input.next(); //discard the invalid token
        }
        int value = input.nextInt();
        input.nextLine();
        return value;
    }

    // Keeps asking until the user types a number, so bad input can never crash the program.
    public static double readDouble(Scanner input, String prompt) {
        System.out.println(prompt);
        while (!input.hasNextDouble()) {
            System.out.println("That's not a number. Please enter again: ");
            input.next(); //discard the invalid token
        }
        double value = input.nextDouble();
        input.nextLine();
        return value;
    }

    // Keeps asking for a whole number until the user enters one that is both a
    // valid number and within the allowed range.
    public static int readIntInRange(Scanner input, String prompt, String errorMessage, int min, int max) {
        int value = readInt(input, prompt);
        while (value < min || value > max) {
            value = readInt(input, errorMessage);
        }
        return value;
    }

    // Keeps asking until the user enters some non-blank text.
    public static String readNonBlankLine(Scanner input, String prompt, String errorMessage) {
        System.out.println(prompt);
        String value = input.nextLine();
        while (value.trim().isEmpty()) {
            System.out.println(errorMessage);
            value = input.nextLine();
        }
        return value;
    }

    // Reads a menu choice, guarding against non-numeric input on the same line as the prompt.
    public static int readMenuChoice(Scanner input) {
        System.out.print("Enter your choice: ");
        while (!input.hasNextInt()) {
            System.out.println("That's not a whole number.");
            input.next(); //discard the invalid token
            System.out.print("Enter your choice: ");
        }
        int choice = input.nextInt();
        input.nextLine();
        return choice;
    }

    // Finds the array index that holds a given listing ID - O(n).
    // Returns -1 if no listing currently has that ID.
    public static int findIndexByListingID(int[] listingID, int count, int id) {
        for (int i = 0; i < count; i++) {
            if (listingID[i] == id) {
                return i;
            }
        }
        return -1;
    }

    // Keeps asking for a listing ID until it matches a listing that actually exists,
    // then returns that listing's current index in the arrays.
    public static int readValidListingID(Scanner input, int[] listingID, int listingCount, String prompt) {
        int id = readInt(input, prompt);
        int index = findIndexByListingID(listingID, listingCount, id);
        while (index == -1) {
            id = readInt(input, "No listing with that ID. Please enter again: ");
            index = findIndexByListingID(listingID, listingCount, id);
        }
        return index;
    }

    // Displays the student menu options. No input, no return value.
    public static void displayStudentMenu() {
        System.out.println("\n--- Off-Campus Student Housing Directory ---");
        System.out.println("1. Display all listings");
        System.out.println("2. Rent and distance statistics");
        System.out.println("3. Search by landlord name");
        System.out.println("4. Login as admin");
        System.out.println("5. Exit");
    }

    // Displays the admin menu options. No input, no return value.
    public static void displayAdminMenu() {
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. Add a new listing");
        System.out.println("2. Remove a listing");
        System.out.println("3. Edit a listing");
        System.out.println("4. Add occupant");
        System.out.println("5. Remove occupant");
        System.out.println("6. Logout");
    }

    // Determines whether a listing has room left. Parameter in, boolean out.
    public static boolean isAvailable(int availableSpace) {
        return availableSpace > 0;
    }

    // Displays one listing's details. Takes the listing's data as parameters, returns nothing.
    public static void displayListing(int listingID, String landlordName, String phoneNumber,
                                       double rentPrice, double distancefromCampus, int maxBedspace,
                                       int totalOccupants, int availableSpace, String description) {
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("Listing ID: " + listingID);
        System.out.println("Landlord name: " + landlordName);
        System.out.println("Phone number: " + phoneNumber);
        System.out.println("Rent: " + String.format("%.2f", rentPrice));
        System.out.println("Distance: " + distancefromCampus + " km");
        System.out.println("Max bedspaces: " + maxBedspace);
        System.out.println("Total Occupants: " + totalOccupants);
        System.out.println("Available space: " + availableSpace);
        if (isAvailable(availableSpace)) {
            System.out.println("Status: Available");
        } else {
            System.out.println("Status: Full");
        }
        System.out.println("description: \n " + description);
    }

    // Linear search: checks each name one by one until it finds a match - O(n)
    // Returns the index of the match, or -1 if the name is not found.
    public static int linearSearch(String[] names, int count, String target) {
        for (int i = 0; i < count; i++) {
            if (names[i].equalsIgnoreCase(target)) {
                return i;
            }
        }
        return -1;
    }
}
