import java.awt.*;
import java.awt.event.*;

public class Powierzchnia extends Canvas implements MouseListener, MouseMotionListener {
    private Kreska[] kreski = new Kreska[10]; // max 10
    private int kreskiCount = 0;
    private Point currentStartPoint = null;
    private Point currentEndPoint = null;
    private Color currentColor = Color.BLACK;
    private boolean drawing = false;

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public Powierzchnia() {
        // Dodanie nasłuchiwaczy zdarzeń myszy
        addMouseListener(this);
        addMouseMotionListener(this);
   
        setBackground(Color.WHITE);
    }

    // Rysowanie
    public void paint(Graphics gr) {
        // Rysowanie wszystkich kresek z tablicy
        for (int i = 0; i < kreskiCount; i++) {
            gr.setColor(kreski[i].kolor);
            gr.drawLine(kreski[i].początek.x, kreski[i].początek.y, kreski[i].koniec.x, kreski[i].koniec.y);
        }
        // Rysowanie szarej linii podczas rysowania nowej kreski
        if (drawing && currentStartPoint != null && currentEndPoint != null) {
            gr.setColor(Color.GRAY);
            gr.drawLine(currentStartPoint.x, currentStartPoint.y, currentEndPoint.x, currentEndPoint.y);
        }
    }

    // Obsluga myszki
    public void mousePressed(MouseEvent e) {
        if (kreskiCount < kreski.length) { // czy mniej niz 10
            currentStartPoint = e.getPoint();
            currentEndPoint = currentStartPoint;
            drawing = true;
        } else {
            drawing = false; // limit rysowania
        }
    }

    // Puszczenie przycisku
    public void mouseReleased(MouseEvent e) {
        if (drawing) {
            currentEndPoint = e.getPoint();
            // Sprawdzenie, czy punkt końcowy znajduje się na powierzchni
            if (contains(currentEndPoint)) {
                // Dodanie nowej kreski do tablicy
                kreski[kreskiCount++] = new Kreska(currentStartPoint, currentEndPoint, currentColor);
            }
            drawing = false;
            currentStartPoint = null;
            currentEndPoint = null;
            repaint();
        }
    }

    // Przeciaganie myszy
    public void mouseDragged(MouseEvent e) {
        if (drawing) {
            currentEndPoint = e.getPoint();
            repaint();
        }
    }

    // Kursor poza obrazem
    public void mouseExited(MouseEvent e) {
        if (drawing) {
            // Anulowanie rysowania
            drawing = false;
            currentStartPoint = null;
            currentEndPoint = null;
            repaint();
        }
    }

    // Metody interfejsów MouseListener i MouseMotionListener, które nie są używane
    public void mouseEntered(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
    public void mouseMoved(MouseEvent e) { }

    public void removeAllKreski() {
        kreskiCount = 0;
        repaint();
    }

    public void removeFirstKreska() {
        if (kreskiCount > 0) {
            System.arraycopy(kreski, 1, kreski, 0, kreskiCount - 1);
            kreskiCount--;
            repaint();
        }
    }

    public void removeLastKreska() {
        if (kreskiCount > 0) {
            kreskiCount--;
            repaint();
        }
    }
}
