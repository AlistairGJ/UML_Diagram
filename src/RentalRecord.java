import utilities.DateTime;

public class RentalRecord
{
    // Defining the attributes for RentalRecords
    private String recordID;
    private DateTime rentDate;
    private DateTime estimatedReturnDate;
    private DateTime actualReturnDate;
    private double rentalFee;
    private double lateFee;
    private String customerID;

    // Constructor for RentalRecords
    public RentalRecord(DateTime rentDate, DateTime estimatedReturnDate, DateTime actualReturnDate,
                        double rentalFee, double lateFee, String customerID, String propertyID)
    {
        this.rentDate = rentDate;
        this.estimatedReturnDate = estimatedReturnDate;
        this.actualReturnDate = actualReturnDate;
        this.rentalFee = rentalFee;
        this.lateFee = lateFee;
        this.customerID = customerID;
        this.recordID = propertyID + "_" + customerID + "_" + rentDate.getEightDigitDate();
    }

    // Accessor for RecordID
    public String getRecordID()
    {
        return recordID;
    }

    // Accessor for RentDate
    public DateTime getRentDate()
    {
        return rentDate;
    }

    public DateTime getEstimatedReturnDate()
    {
        return estimatedReturnDate;
    }

    public DateTime getActualReturnDate()
    {
        return actualReturnDate;
    }

    // Unused - haven't completed FlexiRent Menu System
    public double getRentalFee()
    {
        return rentalFee;
    }

    // Unused - haven't completed FlexiRent Menu System
    public double getLateFee()
    {
        return lateFee;
    }

    // Unused - haven't completed FlexiRent Menu System
    public String getCustomerID()
    {
        return customerID;
    }

    // ToString - get a string representation of an object
    public String toString()
    {
        String s = recordID + ":" + rentDate + ":" + estimatedReturnDate + ":";

        if (actualReturnDate == null)
        {
            s += "none:none:none";
        }

        else
        {
            s += actualReturnDate + ":" + rentalFee + ":" + lateFee;
        }

        return s;
    }

    public String getDetails()
    {
        String s = "Record ID:               " + recordID + '\n';
        s +=       "Rent Date:               " + rentDate + '\n';
        s +=       "Estimated Return Date:   " + estimatedReturnDate + '\n';
        if (actualReturnDate != null)
        {
            s +=   "Actual Return Date:      " + actualReturnDate + '\n';
            s +=   "Rental Fee:              " + String.format("%.2f", rentalFee) + '\n';
            s +=   "Late Fee:                " + String.format("%.2f", lateFee) + '\n';
        }

        return s;
    }

    // Mutator
    public void setActualReturnDate(DateTime actualReturnDate)
    {
        this.actualReturnDate = actualReturnDate;
    }

    // Mutator
    public void setRentalFee(double rentalFee)
    {
        this.rentalFee = rentalFee;
    }

    // Mutator
    public void setLateFee(double lateFee)
    {
        this.lateFee = lateFee;
    }
}