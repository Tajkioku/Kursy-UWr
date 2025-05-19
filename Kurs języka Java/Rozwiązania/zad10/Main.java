import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalendarModel model = new CalendarModel();
            CalendarController controller = new CalendarController(model);
            CalendarView view = new CalendarView(model, controller);
            view.setVisible(true);
        });
    }
}
