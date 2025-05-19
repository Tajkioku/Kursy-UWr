public class CalendarController {
    private CalendarModel model;
    
    public CalendarController(CalendarModel model) {
        this.model = model;
    }
    
    public void nextMonth() {
        int month = model.getMonth();
        int year = model.getYear();
        
        month++;
        if (month > 11) {
            month = 0;
            year++;
        }
        model.setYear(year);
        model.setMonth(month);
    }
    
    public void prevMonth() {
        int month = model.getMonth();
        int year = model.getYear();
        
        month--;
        if (month < 0) {
            month = 11;
            year--;
        }
        model.setYear(year);
        model.setMonth(month);
    }
    
    public void setYear(int y) {
        model.setYear(y);
    }
    
    public void setMonth(int m) {
        model.setMonth(m);
    }
    
    public int getYear() {
        return model.getYear();
    }
    
    public int getMonth() {
        return model.getMonth();
    }
}
