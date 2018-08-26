import utilities.DateTime;
import java.util.*;


public class FlexiRentSystem
{
    private RentalProperty[] rentalProperties;
    private int numRentalProperties;


    public FlexiRentSystem()
    {
        this.rentalProperties = new RentalProperty[50];
        this.numRentalProperties = 0;
    }

    public void run()
    {
        String optionSelected = "";
        Scanner console = new Scanner(System.in);

        while (!optionSelected.equals("7"))
        {
            System.out.println("**** FLEXIRENT SYSTEM MENU ****");
            System.out.println("Add Property:                 1");
            System.out.println("Rent Property:                2");
            System.out.println("Return Property:              3");
            System.out.println("Property Maintenance:         4");
            System.out.println("Complete Maintenance:         5");
            System.out.println("Display All Properties:       6");
            System.out.println("Exit Program:                 7");
            System.out.println("Enter your choice:             ");

            optionSelected = console.nextLine();

            switch (optionSelected)
            {
                case "1":
                    addProperty();
                    break;

                case "2":
                    rentProperty();
                    break;

                case "3":
                    returnProperty();
                    break;

                case "4":
                    startMaintenance();
                    break;

                case "5":
                    completeMaintenance();
                    break;

                case "6":
                    displayAllProperties();
                    break;

                case "7":
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid Input!");
                    break;

            }
        }

    }

    private void addProperty()
    {
        String optionSelected = "";
        Scanner console = new Scanner(System.in);

        System.out.println("Apartment:                    1");
        System.out.println("Premium Suite:                2");

        optionSelected = console.nextLine();

        if (optionSelected.equals("1")) // If for apartment
        {
            // PropertyID
            System.out.println("Please Enter Property ID: ");
            String propertyID = console.nextLine();
            // StreetNum
            System.out.println("Please Enter Street Number: ");
            String streetNum = console.nextLine();
            // StreetName
            System.out.println("Please Enter Street Name: ");
            String streetName = console.nextLine();
            // Suburb
            System.out.println("Please Enter Suburb: ");
            String suburb = console.nextLine();
            // NumBedRooms (1, 2 or 3)
            System.out.println("1, 2 or 3 bedrooms? ");
            int numBedrooms = console.nextInt();
            if (numBedrooms < 1 || numBedrooms > 3)
            {
                System.out.println("Wrong number of bedrooms entered.");
                System.out.println("Returning to main menu.");
                return;
            }
            // Property Status
            Apartment apartment = new Apartment(propertyID, streetNum,
                    streetName, suburb, numBedrooms, PropertyStatus.Available);

            rentalProperties[numRentalProperties] = apartment;
            this.numRentalProperties ++;

        }
        else if (optionSelected.equals("2"))
        {
            // PropertyID
            System.out.println("Please Enter Property ID: ");
            String propertyID = console.nextLine();
            // StreetNum
            System.out.println("Please Enter Street Number: ");
            String streetNum = console.nextLine();
            // StreetName
            System.out.println("Please Enter Street Name: ");
            String streetName = console.nextLine();
            // Suburb
            System.out.println("Please Enter Suburb: ");
            String suburb = console.nextLine();

            System.out.println("Please Enter Last Maintenance Date: ");
            System.out.println("Enter year as YYYY: ");
            int yearYYYY = console.nextInt();
            System.out.println("Enter month as MM: ");
            int monthMM = console.nextInt();
            System.out.println("Enter day as DD: ");
            int dayDD = console.nextInt();

            PremiumSuite premiumSuite = new PremiumSuite(propertyID, streetNum,
                    streetName, suburb, PropertyStatus.Available, new DateTime(dayDD, monthMM, yearYYYY));

            rentalProperties[numRentalProperties] = premiumSuite;
            this.numRentalProperties ++;
        } else
        {
            System.out.println("Invalid Selection");
            return;
        }

    }

    private RentalProperty findProperty(String propertyID)
    {
        // Loop through the rentalProperties array (0 - 50)
        // Match the passed in propertyID with the propertyID field
        for (int index = 0; index < numRentalProperties; index++)
        {
            if (rentalProperties[index].getPropertyID().equals(propertyID))
                return rentalProperties[index];
        }
        return null;
    }

    private void rentProperty()
    {
        String optionSelected = "";
        Scanner console = new Scanner(System.in);
        System.out.println("Enter Property ID: ");
        String propertyID = console.nextLine();
        System.out.println("Enter Customer ID: ");
        String customerID = console.nextLine();
        System.out.println("Enter rent year as YYYY: ");
        int yearYYYY = console.nextInt();
        System.out.println("Enter rent month as MM: ");
        int monthMM = console.nextInt();
        System.out.println("Enter rent day as DD: ");
        int dayDD = console.nextInt();
        System.out.println("How many days?: ");
        int numDays = console.nextInt();

        RentalProperty property = findProperty(propertyID);

        if (property == null)
        {
            System.out.println("Invalid PropertyID Entered");
            return;
        }

        if (property.rent(customerID, new DateTime(dayDD, monthMM, yearYYYY), numDays))
        {
            System.out.println(property.getPropertyType() + " " + propertyID + " is now rented by customer " + customerID);
        }
        else
        {
            System.out.println(property.getPropertyType() + " " + propertyID + " could not be rented");
        }

    }

    private void returnProperty()
    {
        String optionSelected = "";
        Scanner console = new Scanner(System.in);

        System.out.println("Enter Property ID: ");
        String propertyID = console.nextLine();

        System.out.println("Enter return year as YYYY: ");
        int yearYYYY = console.nextInt();

        System.out.println("Enter return month as MM: ");
        int monthMM = console.nextInt();

        System.out.println("Enter return day as DD: ");
        int dayDD = console.nextInt();

        RentalProperty property = findProperty(propertyID);

        if (property == null) {
            System.out.println("Invalid PropertyID Entered");
            return;
        }

        if (property.returnProperty(new DateTime(dayDD, monthMM, yearYYYY)))
        {
            System.out.println(property.getPropertyType() + " " + propertyID + " is returned by customer " +
                    property.getCustomerID());
            System.out.println(property.getDetails());
        }
        else
        {
            System.out.println(property.getPropertyType() + " " + propertyID + " could not be returned");
        }
    }


    private void startMaintenance() // Assumption = started in REAL TIME (can't be schedualed)
    {
        String optionSelected = "";
        Scanner console = new Scanner(System.in);

        System.out.println("Enter Property ID: ");
        String propertyID = console.nextLine();

        RentalProperty property = findProperty(propertyID);

        if (property == null) {
            System.out.println("Invalid PropertyID Entered");
            return;
        }

        if (property.performMaintenance())
        {
            System.out.println(property.getPropertyType() + " " + propertyID + " is under maintenance");
        }
        else
        {
            System.out.println(property.getPropertyType() + " " + propertyID + " could not be maintained");
        }
    }

    private void completeMaintenance() // Assumption = completed in REAL TIME (can't be schedualed)
    {
        String optionSelected = "";
        Scanner console = new Scanner(System.in);

        System.out.println("Enter Property ID: ");
        String propertyID = console.nextLine();

        RentalProperty property = findProperty(propertyID);

        if (property == null) {
            System.out.println("Invalid PropertyID Entered");
            return;
        }

        int yearYYYY = 1971;
        int monthMM = 01;
        int dayDD = 01;

        if (property instanceof PremiumSuite)
        {
            System.out.println("Enter return year as YYYY: ");
            yearYYYY = console.nextInt();

            System.out.println("Enter return month as MM: ");
            monthMM = console.nextInt();

            System.out.println("Enter return day as DD: ");
            dayDD = console.nextInt();
        }

        if (property.completeMaintenance(new DateTime(dayDD, monthMM, yearYYYY)))
        {
            System.out.println(property.getPropertyType() + " " + propertyID
                    + " has all maintenance completed and ready for rent");
        }
        else
        {
            System.out.println(property.getPropertyType() + " " + propertyID
                    + " could not complete maintenance");
        }
    }

    private void displayAllProperties()
    {
        // Spit out property
        // --> Spit out corresponding records
        for (int index = 0; index < numRentalProperties; index++)
        {
            System.out.println(rentalProperties[index].getDetails());
        }
    }
}