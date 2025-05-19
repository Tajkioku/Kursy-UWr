import java.util.GregorianCalendar;
import java.util.Calendar;

public class CalendarUtil {
    // Nazwy miesięcy
    private static final String[] MONTH_NAMES = {
            "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", 
            "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"
    };
    // Nazwy dni tygodnia (od poniedziałku)
    private static final String[] DAY_NAMES = {
            "Poniedziałek","Wtorek","Środa","Czwartek","Piątek","Sobota","Niedziela"
    };

    public static boolean isJulianCalendar(int year, int month) {
        if (year < 1582) return true;
        if (year > 1582) return false;
        // rok == 1582
        if (month < 9) return true; // przed październikiem
        return false;
    }

    public static String getMonthName(int month) {
        return MONTH_NAMES[month];
    }

    public static String getDayName(int dayOfWeek) {
        // Wyjaśnienie: w GregorianCalendar NIEDZIELA=1, a w naszej tablicy NIEDZIELA powinna być na końcu.
        // GCalendar: Sunday=1 => Niedziela (index=6)
        // Monday=2 => Poniedziałek (index=0)
        // ...
        switch(dayOfWeek) {
            case Calendar.MONDAY: return DAY_NAMES[0];
            case Calendar.TUESDAY: return DAY_NAMES[1];
            case Calendar.WEDNESDAY: return DAY_NAMES[2];
            case Calendar.THURSDAY: return DAY_NAMES[3];
            case Calendar.FRIDAY: return DAY_NAMES[4];
            case Calendar.SATURDAY: return DAY_NAMES[5];
            case Calendar.SUNDAY: return DAY_NAMES[6];
        }
        return "";
    }

    public static int getDaysInMonth(int year, int month) {
        if (year == 1582 && month == 9) { // październik = 9
            return 21; 
        }
        // W innym przypadku używamy standardowej metody:
        GregorianCalendar gc = createCalendar(year, month, 1);
        return daysInMonth(gc);
    }

    /** 
     * Jeśli index = 1, dzień = 1
     * index = 4, dzień = 4
     * index = 5, dzień = 15 (przeskok)
     * index = 6, dzień = 16 
     * ...
     */
    public static int getNthDayInMonth(int year, int month, int n) {
        if (year == 1582 && month == 9) {
            if (n <= 4) return n;
            return n + 10;
        }
        return n;
    }

    public static int getDayOfWeek(int year, int month, int day) {
        GregorianCalendar gc = createCalendar(year, month, day);
        return gc.get(Calendar.DAY_OF_WEEK);
    }

    private static GregorianCalendar createCalendar(int year, int month, int day) {
        // Specjalna obsługa października 1582:
        // jeśli żądany dzień jest między 5 a 14 października 1582, to była przerwa.
        if (year == 1582 && month == 9 && day > 4 && day < 15) {
            day = 4;
        }
        return new GregorianCalendar(year, month, day);
    }

    private static int daysInMonth(GregorianCalendar gc) {
        return gc.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
