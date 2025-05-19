public class TestLabirynt {

    public static void main(String[] args) {
        TestLabirynt tester = new TestLabirynt();
        tester.testGenerowanieLabiryntu();
        tester.testPrzejscia();
    }

    public void testGenerowanieLabiryntu() {
        Labirynt labirynt = new Labirynt(10, 10);
        labirynt.generujLabirynt();

        boolean[][] odwiedzone = new boolean[10][10];
        dfs(labirynt, 0, 0, odwiedzone);

        boolean wszystkieOdwiedzone = true;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (!odwiedzone[x][y]) {
                    wszystkieOdwiedzone = false;
                    System.out.println("Błąd: Komnata (" + x + "," + y + ") nie jest osiągalna");
                }
            }
        }

        if (wszystkieOdwiedzone) {
            System.out.println("Test testGenerowanieLabiryntu: SUKCES");
        } else {
            System.out.println("Test testGenerowanieLabiryntu: PORAŻKA");
        }
    }

    private void dfs(Labirynt labirynt, int x, int y, boolean[][] odwiedzone) {
        odwiedzone[x][y] = true;
        Komnata komnata = labirynt.getKomnata(x, y);

        for (Kierunek kierunek : Kierunek.values()) {
            if (komnata.czyPrzejscie(kierunek)) {
                Komnata sasiednia = labirynt.getSasiedniaKomnata(komnata, kierunek);
                if (sasiednia != null && !odwiedzone[sasiednia.getX()][sasiednia.getY()]) {
                    dfs(labirynt, sasiednia.getX(), sasiednia.getY(), odwiedzone);
                }
            }
        }
    }

    public void testPrzejscia() {
        Labirynt labirynt = new Labirynt(5, 5);
        labirynt.generujLabirynt();
        Komnata start = labirynt.getKomnata(0, 0);

        int liczbaPrzejsc = 0;
        for (Kierunek kierunek : Kierunek.values()) {
            if (start.czyPrzejscie(kierunek)) {
                liczbaPrzejsc++;
            }
        }

        if (liczbaPrzejsc > 0) {
            System.out.println("Test testPrzejscia: SUKCES");
        } else {
            System.out.println("Test testPrzejscia: PORAŻKA - Komnata startowa nie ma żadnych przejść");
        }
    }
}
