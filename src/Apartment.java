import utilities.CalendarUtils;
import utilities.DateTime;

public class Apartment extends RentalProperty
{
    //Constructor for Apartment
    public Apartment(String propertyID, String streetNum, String streetName,
                     String suburb, int numBedrooms, PropertyStatus currentPropertyStatus)

    //Super because we are calling the constructor from the super class
    {
        super(propertyID, streetNum, streetName, suburb, numBedrooms, currentPropertyStatus);
    }

    @Override
    public String getPropertyType() //Accessor for determining property type
    {
        return "Apartment";
    }

    @Override
    public double getRentalRate() // If statement for 1, 2 or 3 bedrooms
    {
        if (getNumBedrooms() == 1)
        {
            return 143;
        }
        else if (getNumBedrooms() == 2)
        {
            return 210;
        }
        else
        {
            return 319;
        }
    }

    @Override
    // canRent method - can the property be rented or not?
    public boolean canRent(String customerID, DateTime rentDate, int numOfRentDay)
    {
        if (getCurrentPropertyStatus() != PropertyStatus.Available)
        {
            return false;
        }

        // Logic for
        if (CalendarUtils.getDayOfWeek(rentDate) >= CalendarUtils.SUNDAY &&
                CalendarUtils.getDayOfWeek(rentDate) <= CalendarUtils.THURSDAY &&
                numOfRentDay < 2)
        {
            return false;
        }

        if (CalendarUtils.getDayOfWeek(rentDate) >= CalendarUtils.FRIDAY &&
                CalendarUtils.getDayOfWeek(rentDate) <= CalendarUtils.SATURDAY &&
                numOfRentDay < 3)
        {
            return false;
        }

        if (numOfRentDay > 28)
        {
            return false;
        }

        return true;
    }

    public String getDetails()
    {
        String s = super.getDetails();
        s += getRentalRecordDetails();
        return s;
    }

    // rentalFee - SUB C.
    public void updateRentalFee(RentalRecord record)
    {
        int numOfRentDays = DateTime.diffDays(record.getActualReturnDate(), record.getRentDate());
        int bookedRentDays = DateTime.diffDays(record.getEstimatedReturnDate(), record.getRentDate());

        if (numOfRentDays > bookedRentDays)
        {
            numOfRentDays = bookedRentDays;
        }

        double fee = getRentalRate() * numOfRentDays;
        record.setRentalFee(fee);
    }

    // lateFee - SUB C.
    public void updateLateFee(RentalRecord record)
    {

        int numOfRentDays = DateTime.diffDays(record.getActualReturnDate(), record.getRentDate());
        //
        int bookedRentDays = DateTime.diffDays(record.getEstimatedReturnDate(), record.getRentDate());
        int numOfLateDays = 0;

        if (numOfRentDays > bookedRentDays)
        {
            numOfLateDays = numOfRentDays - bookedRentDays;
        }

        double fee = getRentalRate() * 1.15 * numOfLateDays;
        record.setLateFee(fee);
    }

}
