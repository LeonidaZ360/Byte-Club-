import java.util.Scanner;


class Boardinghouse {
            private int listingID = 0;//will be changed upon implementation of arrays or file handling
            //private String ownerUsername;
            private String landlordName;
            private String phoneNumber;
            private double rentPrice;
            private String distancefromCampus;
            private int maxBedspace;
            private int totalOccupants;
            private int availableSpace;
            private String description;
            //will add classes for user accounts depending on group consensus  

        public Boardinghouse(Scanner input){

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
        
        }
        //public String getownerUsername() { return ownerUsername;}
        public int getlistingID() { return listingID;}
        public String getLandlordName() { return landlordName; }
        public String getPhoneNumber() { return phoneNumber; }
        public double getRentPrice() { return rentPrice; }
        public String getDistanceFromCampus() { return distancefromCampus; }
        public int getMaxBedspace() { return maxBedspace; }
        public int getTotalOccupants() { return totalOccupants; }
        public int getAvailableSpace() { return availableSpace; }
        public String getDescription() { return description; }

        public void setListingID(int listingID){
        this.listingID = listingID;
    }
        
        public boolean isAvailable(){
            return availableSpace > 0;
        }
        public void addOccupant(){
            this.availableSpace = (this.availableSpace - 1);
        }
        public void removeOccupant(){
            this.availableSpace = (this.availableSpace + 1);
        }
        public void displaylisting(){
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("Listing ID: " + getlistingID());
            System.out.println("Landlord name: " + getLandlordName());
            System.out.println("Phone number: " + getPhoneNumber());
            System.out.println("Rent: " + getRentPrice());
            System.out.println("Distance: " + getDistanceFromCampus());
            System.out.println("Max bedspaces: " + getMaxBedspace());
            System.out.println("Total Occupants: " + getTotalOccupants());
            System.out.println("Available space: " + getAvailableSpace());
            System.out.println("description: \n " + getDescription());
            System.out.println("---------------------------------------------------------------------------------------");
        }

 }

public class OCSHDver1 {

    public static void main(String[] args){
    
    Scanner input = new Scanner(System.in);

//test code. to verify everything is in order

    Boardinghouse bh = new Boardinghouse(input);
    bh.displaylisting();


    input.close();
    }
}
