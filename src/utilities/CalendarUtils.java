package utilities;
import java.util.Calendar;

public class CalendarUtils
{
    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 3;
    public static final int WEDNESDAY = 4;
    public static final int THURSDAY = 5;
    public static final int FRIDAY = 6;
    public static final int SATURDAY = 7;

    public static int getDayOfWeek(DateTime date)
    // Accessing methods directly, so must be STATIC
    {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

}
