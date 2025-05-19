import javax.swing.*;
import java.awt.*;

/**
 * Renderer komórki listy, koloruje niedziele na czerwono, pozostałe dni na czarno.
 */
public class DayListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        JLabel lbl = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        // Sprawdzamy czy w tekście występuje niedziela (załóżmy, że niedziela = "Niedziela" w getDayName)
        String text = value.toString();
        if (text.startsWith("Niedziela")) {
            lbl.setForeground(Color.RED);
        } else {
            lbl.setForeground(Color.BLACK);
        }
        return lbl;
    }
}
