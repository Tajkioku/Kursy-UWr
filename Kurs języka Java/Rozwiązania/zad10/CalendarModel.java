import javax.swing.AbstractListModel;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarModel extends AbstractListModel<String> {
    private int year;
    private int month; // 0 - styczeń, 11 - grudzień

    public CalendarModel() {
        GregorianCalendar gc = new GregorianCalendar();
        this.year = gc.get(Calendar.YEAR);
        this.month = gc.get(Calendar.MONTH);
    }

    public void setYear(int year) {
        this.year = year;
        fireContentsChanged(this, 0, getSize()-1);
    }

    public int getYear() {
        return year;
    }

    public void setMonth(int month) {
        this.month = month;
        fireContentsChanged(this, 0, getSize()-1);
    }

    public int getMonth() {
        return month;
    }

    @Override
    public int getSize() {
        return CalendarUtil.getDaysInMonth(year, month);
    }

    @Override
    public String getElementAt(int index) {
        // index od 0 do getSize()-1
        // Każdy element to kolejny dzień miesiąca (index+1)
        int day = CalendarUtil.getNthDayInMonth(year, month, index+1);
        int dayOfWeek = CalendarUtil.getDayOfWeek(year, month, day);
        String dayName = CalendarUtil.getDayName(dayOfWeek);
        String monthName = CalendarUtil.getMonthName(month);
        return dayName + " " + day + " " + monthName;
    }
}
