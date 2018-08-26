import utilities.DateTime;

import java.util.Date;


abstract class RentalProperty // Abstract class -> as will never instantiate 'property'
{
    //Defining the Attributes
    private String propertyID; // Field for propertyID (the user must maintain the A_ or S_ convention)
    private String streetNum; // Field for street number - String because could be 55A (for example)
    private String streetName; // Field for street name
    private String suburb; // Field for suburb
    private int numBedrooms; // For Apartment 1, 2 or 3 'getRentalRate' in Apartment Class
    private PropertyStatus currentPropertyStatus; // Enumeration - Available, Rented, Maintenance
    private RentalRecord[] rentalHistory; //As per instruction MUST be type array (list would have allowed
    //old values to drop without extra code...
    private int countOfRecord;

    //Constructor
    public RentalProperty(String propertyID, String streetNum, String streetName,
                          String suburb, int numBedrooms, PropertyStatus currentPropertyStatus)
    //Create Constructor (which is taking 6 arguments)
    {
        this.propertyID = propertyID;
        this.streetNum = streetNum;
        this.streetName = streetName;
        this.suburb = suburb;
        this.numBedrooms = numBedrooms;
        this.currentPropertyStatus = currentPropertyStatus;
        this.rentalHistory = new RentalRecord[10];
        countOfRecord = 0; //Count will always start at 0 for our system
    }

    // Accessor for PropertyID
    public String getPropertyID()
    {
        return propertyID;
    }

    // Accessor for StreetNum - not currently used as FlexiRent System not complete (confirm)
    public String getStreetNum()
    {
        return streetNum;
    }

    // Accessor for StreetNum - not currently used as FlexiRent System not complete (confirm)
    public String getStreetName()
    {
        return streetName;
    }

    // Accessor for StreetNum - not currently used as FlexiRent System not complete (confirm)
    public String getSuburb()
    {
        return suburb;
    }

    public int getNumBedrooms()
    {
        return numBedrooms;
    }

    public PropertyStatus getCurrentPropertyStatus()
    {
        return currentPropertyStatus;
    }

    public abstract String getPropertyType();

    //Placeholder for countOfRecord accessor!

    public abstract double getRentalRate(); //Abstract because 'rental property' doesn't
    //"know what this is" BUT the subclasses do "know what this is"
    //Method to construct the ID - MUST be unique

    public void addRentalRecord(RentalRecord newRecord)
    {
        if (countOfRecord == 10)
        {
            countOfRecord = 9;
        }

        for (int index = countOfRecord; index >= 1; index--)
        {
            rentalHistory[index] = rentalHistory[index - 1];
        }

        rentalHistory[0] = newRecord;
        countOfRecord++;
    }

    //Logic for loop above
    // Count = 0
    // if count = 0
    // add at index 0
    // count
    // count = 1
    // if count = 1
    // move 0 to 1
    // add at index 0
    // count
    // if count = 2
    // move 1 to 2
    // move 0 to 1
    // add at index 0
    // count
    // if count = 3
    // move index 2 to 3
    // move index 1 to 2
    // move index 0 to 1
    // add at index 0 nb

    public abstract boolean canRent(String customerID, DateTime rentDate, int numOfRentDay);
    // canRent method (called inside rent, ln 123), and the logic is deferred until it is
    // used in an instance of either apartment or premium suite
    // --> We defer the logic because it varies for each child

    // Method for confirming the conditions for renting"
    public boolean rent(String customerID, DateTime rentDate, int numOfRentDay)
    {
        if (canRent(customerID, rentDate, numOfRentDay))
        {
            currentPropertyStatus = PropertyStatus.Rented;

            double rentalFee = getRentalRate() * numOfRentDay;

            DateTime estReturnDate = new DateTime(rentDate, numOfRentDay);

            RentalRecord aRecordi = new RentalRecord(rentDate, estReturnDate,
                    null, rentalFee,
                    0.0, customerID, propertyID);

            addRentalRecord(aRecordi);
            return true;
        }
        else
        {
            return false;
        }
    }


    public String toString()
    {
        String s = propertyID + ":" + streetNum + ":" + streetName + ":" +
                suburb + ":" + getPropertyType() + ":" + numBedrooms + ":" +
                currentPropertyStatus;

        return s;
    }

    public String getDetails()
    {
        String s = "Property ID:       " + propertyID + '\n';
        s +=       "Address:           " + streetNum + " " + streetName + " " + suburb + '\n';
        s +=       "Type:              " + getPropertyType() + '\n';
        s +=       "Bedroom:           " + numBedrooms + '\n';
        s +=       "Status:            " + currentPropertyStatus + '\n';
        return s;
    }

    protected String getRentalRecordDetails()
    {
        String s =  "RENTAL RECORD" + (countOfRecord == 0 ? ":     empty" : "") + '\n';

        for (int index = 0; index < countOfRecord; index++)
        {
            s += rentalHistory[index].getDetails(); // We now 'have access' to all the stuff in rental rec.
            s += "--------------------------------------";
        }
        return s;
    }

    // Assumption: We can never prevent someone form returning the keys, this will be
    // reflected in the rental fee

    private boolean canReturn(DateTime returnDate)
    {
        DateTime rentDate = rentalHistory[0].getRentDate();
        // Pulling the rent date from the most recent record

        if (currentPropertyStatus == PropertyStatus.Rented &&
                returnDate.getTime() >= rentDate.getTime())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public abstract void updateRentalFee(RentalRecord record);

    public abstract void updateLateFee(RentalRecord record);

    public boolean returnProperty(DateTime returnDate)
    {
        if (canReturn(returnDate))
        {
            currentPropertyStatus = PropertyStatus.Available;


            // Update corresponding rental record
            RentalRecord correspondingRecord = rentalHistory[0];

            // actualReturnDate - COMMON
            correspondingRecord.setActualReturnDate(returnDate);

            // rentalFee - SUB C.
            updateRentalFee(correspondingRecord);

            // lateFee - SUB C.
            updateLateFee(correspondingRecord);

            return true;
        }
        return false;
    }

    // Rental Property

    public boolean performMaintenance()
    {
        if (currentPropertyStatus == PropertyStatus.Available)
        {
            currentPropertyStatus = PropertyStatus.Maintenance;
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean completeMaintenance(DateTime completionDate)
    {
        if (currentPropertyStatus == PropertyStatus.Maintenance)
        {
            currentPropertyStatus = PropertyStatus.Available;
            return true;
        }
        else
        {
            return false;
        }
    }

    public String getCustomerID()
    {
        return rentalHistory[0].getCustomerID();
    }

}