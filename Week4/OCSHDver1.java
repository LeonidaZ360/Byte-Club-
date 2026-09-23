import java.util.Scanner;

public class OCSHDver1 {

    public static void main(String[] args){

        Scanner input = new Scanner(System.in);

        //arrays now hold the data for many listings instead of just one
        //the same index in every array belongs to the same listing
        int maxListings = 50;
        String[] landlordName = new String[maxListings];
        String[] phoneNumber = new String[maxListings];
        double[] rentPrice = new double[maxListings];
        double[] distancefromCampus = new double[maxListings]; //now a number (km) so we can calculate with it
        int[] maxBedspace = new int[maxListings];
        int[] totalOccupants = new int[maxListings];
        int[] availableSpace = new int[maxListings];
        String[] description = new String[maxListings];
        int listingCount = 0; //how many listings have been added so far

        int choice;

        do {
            System.out.println("\n--- Off-Campus Student Housing Directory ---");
            System.out.println("1. Add a new listing");
            System.out.println("2. Display all listings");
            System.out.println("3. Add occupant");
            System.out.println("4. Remove occupant");
            System.out.println("5. Rent and distance statistics");
            System.out.println("6. Search by landlord name");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    if (listingCount == maxListings) {
                        System.out.println("The directory is full.");
                    } else {
                        int i = listingCount; //the next empty slot in the arrays - O(1)

                        System.out.println("what's the Landlords name: ");
                        landlordName[i] = input.nextLine();

                        System.out.println("what's the Landlords phone number: ");
                        phoneNumber[i] = input.nextLine();

                        System.out.println("what's the monthly rent of the boarding house: ");
                        rentPrice[i] = input.nextDouble();
                        input.nextLine();
                        while (rentPrice[i] <= 0) {
                            System.out.println("Rent price must be greater than zero. Please enter again: ");
                            rentPrice[i] = input.nextDouble();
                            input.nextLine();
                        }

                        System.out.println("How far is the Property from CBU campus (in km): ");
                        distancefromCampus[i] = input.nextDouble();
                        input.nextLine();

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
                        System.out.println("Listing added. Listing ID: " + listingCount);
                    }
                    break;

                case 2:
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        //traversal: visits every listing once - O(n)
                        for (int i = 0; i < listingCount; i++) {
                            System.out.println("---------------------------------------------------------------------------------------");
                            System.out.println("Listing ID: " + (i + 1));
                            System.out.println("Landlord name: " + landlordName[i]);
                            System.out.println("Phone number: " + phoneNumber[i]);
                            System.out.println("Rent: " + rentPrice[i]);
                            System.out.println("Distance: " + distancefromCampus[i] + " km");
                            System.out.println("Max bedspaces: " + maxBedspace[i]);
                            System.out.println("Total Occupants: " + totalOccupants[i]);
                            System.out.println("Available space: " + availableSpace[i]);
                            if (availableSpace[i] > 0) {
                                System.out.println("Status: Available");
                            } else {
                                System.out.println("Status: Full");
                            }
                            System.out.println("description: \n " + description[i]);
                        }
                        System.out.println("---------------------------------------------------------------------------------------");
                    }
                    break;

                case 3:
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        int id = readIntInRange(input,
                                "Enter the listing ID: ",
                                "No listing with that ID. Please enter again: ",
                                1, listingCount);
                        int i = id - 1; //the ID leads straight to its index, no searching - O(1)

                        if (availableSpace[i] > 0) {
                            totalOccupants[i] = totalOccupants[i] + 1;
                            availableSpace[i] = availableSpace[i] - 1;
                            System.out.println("Occupant added.");
                        } else {
                            System.out.println("No available space.");
                        }
                    }
                    break;

                case 4:
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        int id = readIntInRange(input,
                                "Enter the listing ID: ",
                                "No listing with that ID. Please enter again: ",
                                1, listingCount);
                        int i = id - 1; //O(1)

                        if (totalOccupants[i] > 0) {
                            totalOccupants[i] = totalOccupants[i] - 1;
                            availableSpace[i] = availableSpace[i] + 1;
                            System.out.println("Occupant removed.");
                        } else {
                            System.out.println("There are no occupants to remove.");
                        }
                    }
                    break;

                case 5:
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
                        System.out.println("Total rent: " + totalRent);
                        System.out.println("Average rent: " + (totalRent / listingCount));
                        System.out.println("Cheapest rent: " + minRent);
                        System.out.println("Most expensive rent: " + maxRent);
                        System.out.println("Average distance: " + (totalDistance / listingCount) + " km");
                        System.out.println("Closest listing: " + closest + " km");
                        System.out.println("---------------------------------------------------------------------------------------");
                    }
                    break;

                case 6:
                    if (listingCount == 0) {
                        System.out.println("No listing has been added yet.");
                    } else {
                        System.out.println("Enter the landlord's name: ");
                        String name = input.nextLine();

                        int index = linearSearch(landlordName, listingCount, name);

                        if (index == -1) {
                            System.out.println("No listing found for that landlord.");
                        } else {
                            System.out.println("Found: Listing ID " + (index + 1) + ", Phone: " + phoneNumber[index]
                                    + ", Rent: " + rentPrice[index] + ", Available space: " + availableSpace[index]);
                        }
                    }
                    break;

                case 7:
                    System.out.println("Goodbye.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 7);

        input.close();
    }

    // Keeps asking for a number until the user enters one that is allowed.
    public static int readIntInRange(Scanner input, String prompt, String errorMessage, int min, int max) {
        System.out.println(prompt);
        int value = input.nextInt();
        input.nextLine();
        while (value < min || value > max) {
            System.out.println(errorMessage);
            value = input.nextInt();
            input.nextLine();
        }
        return value;
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
