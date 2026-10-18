import java.util.Scanner;


class BoardingHouse {

    public int listingID; 
    public String landlordName;
    public String phoneNumber;
    public double rentPrice;
    public double distanceFromCampus; 
    public int maxBedspace;
    public int totalOccupants;
    public String description;

 
    public BoardingHouse(int listingID) {
        this.listingID = listingID;}

    public int getAvailableSpace() {
        return maxBedspace - totalOccupants;
    }


    public boolean isAvailable() {
        return getAvailableSpace() > 0;
    }

    public boolean addOccupant() {
        if (isAvailable()) {
            totalOccupants = totalOccupants + 1;
            return true;
        }
        return false;
    }


    public boolean removeOccupant() {
        if (totalOccupants > 0) {
            totalOccupants = totalOccupants - 1;
            return true;
        }
        return false;
    }

    public void display() {
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("Listing ID: " + listingID);
        System.out.println("Landlord name: " + landlordName);
        System.out.println("Phone number: " + phoneNumber);
        System.out.println("Rent: " + String.format("%.2f", rentPrice));
        System.out.println("Distance: " + distanceFromCampus + " km");
        System.out.println("Max bedspaces: " + maxBedspace);
        System.out.println("Total Occupants: " + totalOccupants);
        System.out.println("Available space: " + getAvailableSpace());

        if (isAvailable()) {
            System.out.println("Status: Available");
        } else {
            System.out.println("Status: Full");
        }
        System.out.println("description: \n " + description);
    }
}

public class StudentHaven {

    private static final String ADMIN_PASSWORD = "admin123";

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        
        int maxListings = 50;
        BoardingHouse[] houses = new BoardingHouse[maxListings];
        int listingCount = 0; 
        int nextListingID = 1; 

        int choice;

        do {
            displayStudentMenu();
            choice = readMenuChoice(input);

            switch (choice) {
                case 1:
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        
                        for (int i = 0; i < listingCount; i++) {
                            houses[i].display();
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
                        double minRent = houses[0].rentPrice;
                        double maxRent = houses[0].rentPrice;
                        double closest = houses[0].distanceFromCampus;

                        
                        for (int i = 0; i < listingCount; i++) {
                            double rent = houses[i].rentPrice;
                            double distance = houses[i].distanceFromCampus;

                            totalRent = totalRent + rent;
                            totalDistance = totalDistance + distance;

                            if (rent < minRent) {
                                minRent = rent;
                            }
                            if (rent > maxRent) {
                                maxRent = rent;
                            }
                            if (distance < closest) {
                                closest = distance;
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

                        int index = linearSearch(houses, listingCount, name);

                        if (index == -1) {
                            System.out.println("No listing found for that landlord.");
                        } else {
                            BoardingHouse found = houses[index];
                            System.out.println("Found: Listing ID " + found.listingID + ", Phone: " + found.phoneNumber
                                    + ", Rent: " + String.format("%.2f", found.rentPrice)
                                    + ", Available space: " + found.getAvailableSpace());
                        }
                    }
                    break;

                case 4:
                    System.out.println("Enter the admin password: ");
                    String password = input.nextLine();

                    if (password.equals(ADMIN_PASSWORD)) {
                        System.out.println("Logged in as admin.");
                        int[] result = adminMenu(input, houses, listingCount, maxListings, nextListingID);
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


    public static int[] adminMenu(Scanner input, BoardingHouse[] houses, int listingCount,
                                  int maxListings, int nextListingID) {

        int choice;

        do {
            displayAdminMenu();
            choice = readMenuChoice(input);

            switch (choice) {
                case 1: 
                    if (listingCount == maxListings) {
                        System.out.println("The directory is full.");
                    } else {
                        BoardingHouse bh = new BoardingHouse(nextListingID);
                        nextListingID = nextListingID + 1;

                        readListingDetails(input, bh);

                        houses[listingCount] = bh;
                        listingCount = listingCount + 1;
                        System.out.println("Listing added. Listing ID: " + bh.listingID);
                    }
                    break;

                case 2: 
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        int i = readValidListingID(input, houses, listingCount, "Enter the listing ID to remove: ");

                        houses[i].display();

                        System.out.println("Are you sure you want to remove this listing? (y/n): ");
                        String confirm = input.nextLine();

                        if (confirm.equalsIgnoreCase("y")) {
                           
                            for (int j = i; j < listingCount - 1; j++) {
                                houses[j] = houses[j + 1];
                            }

                            houses[listingCount - 1] = null; 
                            listingCount = listingCount - 1;
                            System.out.println("Listing removed.");
                        } else {
                            System.out.println("Removal cancelled.");
                        }
                    }
                    break;

                case 3: 
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        int i = readValidListingID(input, houses, listingCount, "Enter the listing ID to edit: ");

                        readListingDetails(input, houses[i]);

                        System.out.println("Listing updated.");
                    }
                    break;

                case 4:
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        int i = readValidListingID(input, houses, listingCount, "Enter the listing ID: ");

                        if (houses[i].addOccupant()) {
                            System.out.println("Occupant added.");
                        } else {
                            System.out.println("No available space.");
                        }
                    }
                    break;

                case 5: 
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        int i = readValidListingID(input, houses, listingCount, "Enter the listing ID: ");

                        if (houses[i].removeOccupant()) {
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

   
    public static void readListingDetails(Scanner input, BoardingHouse bh) {

        bh.landlordName = readNonBlankLine(input,
                "what's the Landlords name: ",
                "Landlord name cannot be blank. Please enter again: ");

        bh.phoneNumber = readNonBlankLine(input,
                "what's the Landlords phone number: ",
                "Phone number cannot be blank. Please enter again: ");

        double rent = readDouble(input, "what's the monthly rent of the boarding house: ");
        while (rent <= 0) {
            rent = readDouble(input, "Rent price must be greater than zero. Please enter again: ");
        }
        bh.rentPrice = rent;

        bh.distanceFromCampus = readDouble(input, "How far is the Property from CBU campus (in km): ");

        int bedspaces = readIntInRange(input,
                "How many bedspaces in the bh: ",
                "Number of bedspaces must be greater than zero. Please enter again: ",
                1, Integer.MAX_VALUE);
        bh.maxBedspace = bedspaces;

        bh.totalOccupants = readIntInRange(input,
                "How many people are currently living in the boarding house: ",
                "Total occupants must be between 0 and max bedspaces (" + bedspaces + "). Please enter again: ",
                0, bedspaces);

        System.out.println("give a brief description of the boarding house amenities:  \n");
        bh.description = input.nextLine();
    }

    public static int readInt(Scanner input, String prompt) {
        System.out.println(prompt);
        while (!input.hasNextInt()) {
            System.out.println("That's not a whole number. Please enter again: ");
            input.next(); 
        }
        int value = input.nextInt();
        input.nextLine();
        return value;
    }

    public static double readDouble(Scanner input, String prompt) {
        System.out.println(prompt);
        while (!input.hasNextDouble()) {
            System.out.println("That's not a number. Please enter again: ");
            input.next(); 
        }
        double value = input.nextDouble();
        input.nextLine();
        return value;
    }

 
    public static int readIntInRange(Scanner input, String prompt, String errorMessage, int min, int max) {
        int value = readInt(input, prompt);
        while (value < min || value > max) {
            value = readInt(input, errorMessage);
        }
        return value;
    }


    public static String readNonBlankLine(Scanner input, String prompt, String errorMessage) {
        System.out.println(prompt);
        String value = input.nextLine();
        while (value.trim().isEmpty()) {
            System.out.println(errorMessage);
            value = input.nextLine();
        }
        return value;
    }


    public static int readMenuChoice(Scanner input) {
        System.out.print("Enter your choice: ");
        while (!input.hasNextInt()) {
            System.out.println("That's not a whole number.");
            input.next(); 
            System.out.print("Enter your choice: ");
        }
        int choice = input.nextInt();
        input.nextLine();
        return choice;
    }

  
    public static int findIndexByListingID(BoardingHouse[] houses, int count, int id) {
        for (int i = 0; i < count; i++) {
            if (houses[i].listingID == id) {
                return i;
            }
        }
        return -1;
    }

   
    public static int readValidListingID(Scanner input, BoardingHouse[] houses, int listingCount, String prompt) {
        int id = readInt(input, prompt);
        int index = findIndexByListingID(houses, listingCount, id);
        while (index == -1) {
            id = readInt(input, "No listing with that ID. Please enter again: ");
            index = findIndexByListingID(houses, listingCount, id);
        }
        return index;
    }

   
    public static void displayStudentMenu() {
        System.out.println("\n--- Off-Campus Student Housing Directory ---");
        System.out.println("1. Display all listings");
        System.out.println("2. Rent and distance statistics");
        System.out.println("3. Search by landlord name");
        System.out.println("4. Login as admin");
        System.out.println("5. Exit");
    }

    
    public static void displayAdminMenu() {
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. Add a new listing");
        System.out.println("2. Remove a listing");
        System.out.println("3. Edit a listing");
        System.out.println("4. Add occupant");
        System.out.println("5. Remove occupant");
        System.out.println("6. Logout");
    }

    
    public static int linearSearch(BoardingHouse[] houses, int count, String target) {
        for (int i = 0; i < count; i++) {
            if (houses[i].landlordName.equalsIgnoreCase(target)) {
                return i;
            }
        }
        return -1;
    }
}
