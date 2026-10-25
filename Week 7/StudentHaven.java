import java.util.Scanner;


class BoardingHouse {

    // Rules this class enforces (its invariants):
    //  - landlord name and phone number are never blank
    //  - rent is always greater than zero
    //  - there is always at least 1 bedspace
    //  - total occupants is always between 0 and the number of bedspaces

    private int listingID; // never changes after creation, so it has no setter
    private String landlordName;
    private String phoneNumber;
    private double rentPrice;
    private double distanceFromCampus; // in km
    private int maxBedspace;
    private int totalOccupants;
    private String description;

    // Main constructor: a listing can only be created with valid data.
    public BoardingHouse(int listingID, String landlordName, String phoneNumber, double rentPrice,
                         double distanceFromCampus, int maxBedspace, int totalOccupants,
                         String description) {
        this.listingID = listingID;
        setLandlordName(landlordName);
        setPhoneNumber(phoneNumber);
        setRentPrice(rentPrice);
        this.distanceFromCampus = distanceFromCampus;

        if (maxBedspace < 1) {
            throw new IllegalArgumentException("Number of bedspaces must be greater than zero");
        }
        if (totalOccupants < 0 || totalOccupants > maxBedspace) {
            throw new IllegalArgumentException("Total occupants must be between 0 and max bedspaces");
        }
        this.maxBedspace = maxBedspace;
        this.totalOccupants = totalOccupants;
        this.description = description;
    }

    // A new listing can start with nobody living there and no description yet.
    // this(...) reuses the main constructor, so the checks are only written once.
    public BoardingHouse(int listingID, String landlordName, String phoneNumber, double rentPrice,
                         double distanceFromCampus, int maxBedspace) {
        this(listingID, landlordName, phoneNumber, rentPrice, distanceFromCampus,
                maxBedspace, 0, "No description yet");
    }

    // ---------- Getters ----------
    public int getListingID() {
        return listingID;
    }

    public String getLandlordName() {
        return landlordName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public double getDistanceFromCampus() {
        return distanceFromCampus;
    }

    public int getMaxBedspace() {
        return maxBedspace;
    }

    public int getTotalOccupants() {
        return totalOccupants;
    }

    public String getDescription() {
        return description;
    }

    public int getAvailableSpace() {
        return maxBedspace - totalOccupants;
    }

    // ---------- Setters ----------
    // Only the fields that have a rule get a setter. The occupants and bedspaces
    // have no setter: occupants only change through addOccupant() and removeOccupant(),
    // so the rules above cannot be broken from outside the class.
    public void setLandlordName(String landlordName) {
        if (landlordName == null || landlordName.trim().isEmpty()) {
            throw new IllegalArgumentException("Landlord name cannot be blank");
        }
        this.landlordName = landlordName;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be blank");
        }
        this.phoneNumber = phoneNumber;
    }

    public void setRentPrice(double rentPrice) {
        if (rentPrice <= 0) {
            throw new IllegalArgumentException("Rent price must be greater than zero");
        }
        this.rentPrice = rentPrice;
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

    // Displays this listing's details.
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
                        double minRent = houses[0].getRentPrice();
                        double maxRent = houses[0].getRentPrice();
                        double closest = houses[0].getDistanceFromCampus();

                        
                        for (int i = 0; i < listingCount; i++) {
                            double rent = houses[i].getRentPrice();
                            double distance = houses[i].getDistanceFromCampus();

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
                            System.out.println("Found: Listing ID " + found.getListingID() + ", Phone: " + found.getPhoneNumber()
                                    + ", Rent: " + String.format("%.2f", found.getRentPrice())
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
                        BoardingHouse bh = readListing(input, nextListingID);
                        nextListingID = nextListingID + 1;

                        houses[listingCount] = bh;
                        listingCount = listingCount + 1;
                        System.out.println("Listing added. Listing ID: " + bh.getListingID());
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

                        houses[i] = readListing(input, houses[i].getListingID());

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

   
    // Asks for every detail, then builds the listing through the constructor.
    // The loops here re-ask until the input is acceptable; the constructor
    // still checks the same rules, so a BoardingHouse can never hold invalid data.
    public static BoardingHouse readListing(Scanner input, int listingID) {

        String landlordName = readNonBlankLine(input,
                "what's the Landlords name: ",
                "Landlord name cannot be blank. Please enter again: ");

        String phoneNumber = readNonBlankLine(input,
                "what's the Landlords phone number: ",
                "Phone number cannot be blank. Please enter again: ");

        double rent = readDouble(input, "what's the monthly rent of the boarding house: ");
        while (rent <= 0) {
            rent = readDouble(input, "Rent price must be greater than zero. Please enter again: ");
        }

        double distance = readDouble(input, "How far is the Property from CBU campus (in km): ");

        int bedspaces = readIntInRange(input,
                "How many bedspaces in the bh: ",
                "Number of bedspaces must be greater than zero. Please enter again: ",
                1, Integer.MAX_VALUE);

        int occupants = readIntInRange(input,
                "How many people are currently living in the boarding house: ",
                "Total occupants must be between 0 and max bedspaces (" + bedspaces + "). Please enter again: ",
                0, bedspaces);

        System.out.println("give a brief description of the boarding house amenities:  \n");
        String description = input.nextLine();

        return new BoardingHouse(listingID, landlordName, phoneNumber, rent,
                distance, bedspaces, occupants, description);
    }

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
            input.next(); //discard the invalid token
            System.out.print("Enter your choice: ");
        }
        int choice = input.nextInt();
        input.nextLine();
        return choice;
    }

  
    public static int findIndexByListingID(BoardingHouse[] houses, int count, int id) {
        for (int i = 0; i < count; i++) {
            if (houses[i].getListingID() == id) {
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
            if (houses[i].getLandlordName().equalsIgnoreCase(target)) {
                return i;
            }
        }
        return -1;
    }
}
