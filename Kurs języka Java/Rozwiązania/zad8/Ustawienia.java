import java.awt.Color;

public class Ustawienia {
    private int szerokosc = 10;
    private int wysokosc = 10;
    private boolean oznaczeniePol = false;
    private Color kolorTla = Color.WHITE;
    private Color kolorScian = Color.BLACK;

    public int getSzerokosc() {
        return szerokosc;
    }

    public void setSzerokosc(int szerokosc) {
        this.szerokosc = szerokosc;
    }

    public int getWysokosc() {
        return wysokosc;
    }

    public void setWysokosc(int wysokosc) {
        this.wysokosc = wysokosc;
    }

    public boolean isOznaczeniePol() {
        return oznaczeniePol;
    }

    public void setOznaczeniePol(boolean oznaczeniePol) {
        this.oznaczeniePol = oznaczeniePol;
    }

    public Color getKolorTla() {
        return kolorTla;
    }

    public void setKolorTla(Color kolorTla) {
        this.kolorTla = kolorTla;
    }

    public Color getKolorScian() {
        return kolorScian;
    }

    public void setKolorScian(Color kolorScian) {
        this.kolorScian = kolorScian;
    }
}
