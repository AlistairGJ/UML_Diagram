import utilities.CalendarUtils;
import utilities.DateTime;

import java.util.Date;

public class PremiumSuite extends RentalProperty
{
    private DateTime lastMaintenanceDate;
    //Constructor
    public PremiumSuite(String propertyID, String streetNum, String streetName,
                        String suburb, PropertyStatus currentPropertyStatus, DateTime lastMaintenanceDate)
    //Super because we are calling the constructor from the super class
    //Premium suite always has 3 bedrooms, so can remove from the parameter (above)
    {
        super(propertyID, streetNum, streetName, suburb, 3, currentPropertyStatus);
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public String getDetails()
    {
        String s = super.getDetails();
        s += "Last Maintenance:  " + lastMaintenanceDate + '\n';
        s += getRentalRecordDetails();
        return s;
    }

    @Override
    public String getPropertyType()
    {
        return "Premium Suite";
    }

    @Override
    public double getRentalRate()
    {
        return 554;
    }

    @Override
    public boolean canRent(String customerID, DateTime rentDate, int numOfRentDay)
    {
        if (getCurrentPropertyStatus() != PropertyStatus.Available)
        {
            return false;
        }

        if (numOfRentDay < 1)
        {
            return false;
        }

        //Function for maintenance and availability

        // Check last maintenance date on PS x
        // Start of rental period + y number of days
        // next maintenance date and rental period cannot intersect
        // next maintenance date = last maintenance date + 10
        // last maintenance date |||

        // Next maintenance ...
        long nextMaintenance = new DateTime(lastMaintenanceDate, 10).getTime();

        // Start of rental period = rent date
        long startOfRentalPeriod = rentDate.getTime();

        // End of rental period = rent date + numRentDays
        long endOfRentalPeriod = new DateTime(rentDate, numOfRentDay).getTime();

        if (startOfRentalPeriod < nextMaintenance && nextMaintenance < endOfRentalPeriod)
        {
            return false;
        }

        return true;
    }

    // rentalFee - SUB C.
    // Assumption: As we are not told how to handle an early return of a premium suite
    // I am assuming that we charge the full amount booked
    public void updateRentalFee(RentalRecord record)
    {
        int numOfRentDays = DateTime.diffDays(record.getEstimatedReturnDate(), record.getRentDate());
        double fee = getRentalRate() * numOfRentDays;
        record.setRentalFee(fee);
    }

    public void updateLateFee(RentalRecord record)
    {
        int numOfLateDays = DateTime.diffDays(record.getActualReturnDate(), record.getEstimatedReturnDate());
        if (numOfLateDays < 0)
        {
            numOfLateDays = 0;
        }
        double fee = numOfLateDays * 662.0;
        record.setLateFee(fee);
    }

    @Override
    public boolean completeMaintenance(DateTime completionDate)
    {
        if(super.completeMaintenance(completionDate)) // Run the version that is in the parent
        {
            lastMaintenanceDate = completionDate;
            return true;
        }
        return false;
    }
}