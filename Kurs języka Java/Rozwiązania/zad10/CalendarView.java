import javax.swing.*;
import java.awt.*;

/**
 * Poprzedni, bieżący, następny miesiąc
 * Oraz panele
 */
public class CalendarView extends JFrame {
    private CalendarModel currentModel;
    private CalendarModel prevModel;
    private CalendarModel nextModel;
    private CalendarController controller;
    
    private JLabel yearLabel;
    private JLabel iconLabel;
    
    private JList<String> prevList;
    private JList<String> currentList;
    private JList<String> nextList;
    
    private JButton prevMonthButton;
    private JButton nextMonthButton;
    private JSpinner yearSpinner;
    private JScrollBar monthScrollBar;
    
    public CalendarView(CalendarModel model, CalendarController controller) {
        super("Uniwersalny Kalendarz");
        this.currentModel = model;
        this.controller = controller;

        // Modele poprzedniego i następnego miesiąca
        this.prevModel = new CalendarModel();
        this.nextModel = new CalendarModel();
        
        // Ustawiamy modele na aktualne wartości
        syncModels();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Górny panel z rokiem i ikoną
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        yearLabel = new JLabel();
        iconLabel = new JLabel();
        topPanel.add(iconLabel);
        topPanel.add(yearLabel);

        // Środkowy panel
        JPanel centerPanel = new JPanel(new GridLayout(1,3));
        
        prevList = new JList<>(prevModel);
        currentList = new JList<>(currentModel);
        nextList = new JList<>(nextModel);

        // Czerwone niedziele
        DayListCellRenderer renderer = new DayListCellRenderer();
        prevList.setCellRenderer(renderer);
        currentList.setCellRenderer(renderer);
        nextList.setCellRenderer(renderer);

        centerPanel.add(new JScrollPane(prevList));
        centerPanel.add(new JScrollPane(currentList));
        centerPanel.add(new JScrollPane(nextList));

        // Dolny panel
        JToolBar toolBar = new JToolBar();
        prevMonthButton = new JButton("<<");
        nextMonthButton = new JButton(">>");
        
        yearSpinner = new JSpinner(new SpinnerNumberModel(controller.getYear(), 1, 3999, 1));
        monthScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, controller.getMonth(), 1, 0, 12);

        toolBar.add(prevMonthButton);
        toolBar.add(nextMonthButton);
        toolBar.add(new JLabel("Rok:"));
        toolBar.add(yearSpinner);
        toolBar.add(new JLabel("Miesiąc:"));
        toolBar.add(monthScrollBar);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(toolBar, BorderLayout.SOUTH);

        updateView();

        // Nasłuchiwacze myszki
        prevMonthButton.addActionListener(e -> {
            controller.prevMonth();
            syncModels();
            updateView();
        });

        nextMonthButton.addActionListener(e -> {
            controller.nextMonth();
            syncModels();
            updateView();
        });

        yearSpinner.addChangeListener(e -> {
            int val = (Integer)yearSpinner.getValue();
            controller.setYear(val);
            syncModels();
            updateView();
        });

        monthScrollBar.addAdjustmentListener(e -> {
            controller.setMonth(monthScrollBar.getValue());
            syncModels();
            updateView();
        });
    }

    private void syncModels() {
        int year = controller.getYear();
        int month = controller.getMonth();

        // Poprzedni miesiąc
        int prevYear = year;
        int prevMonth = month - 1;
        if (prevMonth < 0) {
            prevMonth = 11;
            prevYear--;
        }

        // Następny miesiąc
        int nextYear = year;
        int nextMonth = month + 1;
        if (nextMonth > 11) {
            nextMonth = 0;
            nextYear++;
        }

        prevModel.setYear(prevYear);
        prevModel.setMonth(prevMonth);

        currentModel.setYear(year);
        currentModel.setMonth(month);

        nextModel.setYear(nextYear);
        nextModel.setMonth(nextMonth);
    }

    private void updateView() {
        yearLabel.setText(String.valueOf(controller.getYear()));
        iconLabel.setText(CalendarUtil.isJulianCalendar(controller.getYear(), controller.getMonth()) ? "Jul" : "Greg");
        yearSpinner.setValue(controller.getYear());
        monthScrollBar.setValue(controller.getMonth());
        
        prevList.repaint();
        currentList.repaint();
        nextList.repaint();
    }
}
