import java.util.Scanner;

public class OCSHDver1 {

    public static void main(String[] args){

        Scanner input = new Scanner(System.in);

        //data for the current listing (will be changed upon implementation of arrays or file handling)
        int listingID = 0;
        String landlordName = "";
        String phoneNumber = "";
        double rentPrice = 0;
        String distancefromCampus = "";
        int maxBedspace = 0;
        int totalOccupants = 0;
        int availableSpace = 0;
        String description = "";
        boolean hasListing = false;

        int choice;

        do {
            System.out.println("\n--- Off-Campus Student Housing Directory ---");
            System.out.println("1. Add a new listing");
            System.out.println("2. Display listing");
            System.out.println("3. Add occupant");
            System.out.println("4. Remove occupant");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("what's the Landlords name: ");
                    landlordName = input.nextLine();

                    System.out.println("what's the Landlords phone number: ");
                    phoneNumber = input.nextLine();

                    System.out.println("what's the monthly rent of the boarding house: ");
                    rentPrice = input.nextDouble();
                    input.nextLine();
                    while (rentPrice <= 0) {
                        System.out.println("Rent price must be greater than zero. Please enter again: ");
                        rentPrice = input.nextDouble();
                        input.nextLine();
                    }

                    System.out.println("How far is the Property from CBU campus: ");
                    distancefromCampus = input.nextLine();

                    System.out.println("How many bedspaces in the bh: ");
                    maxBedspace = input.nextInt();
                    input.nextLine();
                    while (maxBedspace <= 0) {
                        System.out.println("Number of bedspaces must be greater than zero. Please enter again: ");
                        maxBedspace = input.nextInt();
                        input.nextLine();
                    }

                    System.out.println("How many people are currently living in the boarding house: ");
                    totalOccupants = input.nextInt();
                    input.nextLine();
                    while (totalOccupants > maxBedspace) {
                        System.out.println("Total occupants cannot be more than max bedspaces (" + maxBedspace + "). Please enter again: ");
                        totalOccupants = input.nextInt();
                        input.nextLine();
                    }

                    availableSpace = (maxBedspace - totalOccupants);

                    System.out.println("give a brief description of the boarding house amenities:  \n");
                    description = input.nextLine();

                    hasListing = true;
                    break;

                case 2:
                    if (hasListing) {
                        System.out.println("---------------------------------------------------------------------------------------");
                        System.out.println("Listing ID: " + listingID);
                        System.out.println("Landlord name: " + landlordName);
                        System.out.println("Phone number: " + phoneNumber);
                        System.out.println("Rent: " + rentPrice);
                        System.out.println("Distance: " + distancefromCampus);
                        System.out.println("Max bedspaces: " + maxBedspace);
                        System.out.println("Total Occupants: " + totalOccupants);
                        System.out.println("Available space: " + availableSpace);
                        if (availableSpace > 0) {
                            System.out.println("Status: Available");
                        } else {
                            System.out.println("Status: Full");
                        }
                        System.out.println("description: \n " + description);
                        System.out.println("---------------------------------------------------------------------------------------");
                    } else {
                        System.out.println("No listing has been added yet.");
                    }
                    break;

                case 3:
                    if (!hasListing) {
                        System.out.println("No listing has been added yet.");
                    } else if (availableSpace > 0) {
                        availableSpace = availableSpace - 1;
                        System.out.println("Occupant added.");
                    } else {
                        System.out.println("No available space.");
                    }
                    break;

                case 4:
                    if (hasListing) {
                        availableSpace = availableSpace + 1;
                        System.out.println("Occupant removed.");
                    } else {
                        System.out.println("No listing has been added yet.");
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
}
