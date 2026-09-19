import java.util.Scanner;


public class OCSHDver1 {

    public static void main(String[] args){
    
    Scanner input = new Scanner(System.in);
        
            int listingID = 0;//will be changed upon implementation of arrays or file handling
            //private String ownerUsername;
            String landlordName;
            String phoneNumber;
            double rentPrice;
            String distancefromCampus;
            int maxBedspace;
            int totalOccupants;
            int availableSpace;
            String description;
            //will add classes for user accounts depending on group consensus  

    

            //ownerUsername = accountUsername;
            //will be completed when accounts are implemented so owners can edit listings

            System.out.println("what's the Landlords name: ");
            landlordName = input.nextLine();

            System.out.println("what's the Landlords phone number: ");
            phoneNumber = input.nextLine();

            System.out.println("what's the monthly rent of the boarding house: ");
            rentPrice = input.nextDouble();
            input.nextLine();

            System.out.println("How far is the Property from CBU campus: ");
            distancefromCampus = input.nextLine();

            System.out.println("How many bedspaces in the bh: ");
            maxBedspace = input.nextInt();
            input.nextLine();

            System.out.println("How many people are currently living in the boarding house: ");
            totalOccupants = input.nextInt();//add selection statment to make sure it is smallet than total bedspaces
            input.nextLine();

            availableSpace  = (maxBedspace - totalOccupants);

            System.out.println("give a brief description of the boarding house amenities:  \n");
            description = input.nextLine();

    

            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("Listing ID: " + listingID);
            System.out.println("Landlord name: " + landlordName);
            System.out.println("Phone number: " + phoneNumber);
            System.out.println("Rent: " + rentPrice);
            System.out.println("Distance: " + distancefromCampus);
            System.out.println("Max bedspaces: " + maxBedspace);
            System.out.println("Total Occupants: " + totalOccupants);
            System.out.println("Available space: " + availableSpace);
            System.out.println("description: \t " + description);
            System.out.println("---------------------------------------------------------------------------------------");
        


    input.close();

}

}
