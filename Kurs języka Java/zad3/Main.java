public class Main {
    public static void main(String[] args) {
        
        Punkt p1 = new Punkt(0, 0);
        Punkt p2 = new Punkt(3, 4);
        Punkt p3 = new Punkt(0, 5);

        Odcinek odcinek = new Odcinek(p1, p2);
        Trojkat trojkat = new Trojkat(p1, p2, p3);

        // Definiujemy wektor przesunięcia
        Wektor wektor = new Wektor(1, 1);

        // Definiujemy prostą przechodzącą przez dwa punkty
        Prosta prostaPrzechodzacaPrzezPunkty = Prosta.przechodzacaPrzezPunkty(p1, p2);
        
        // Definiujemy inną prostą o równaniu x - y = 0 (czyli y = x)
        Prosta prostaYX = new Prosta(1, -1, 0);

        // Przesunięcie odcinka i trójkąta
        Odcinek przesunietyOdcinek = odcinek.przesun(wektor);
        Trojkat przesunietyTrojkat = trojkat.przesun(wektor);

        // Obrót trójkąta wokół punktu (90 stopni)
        Trojkat obroconyTrojkat = trojkat.obroc(p1, 90);

        // Odbicie odcinka względem prostej x - y = 0 (y = x)
        Odcinek odbityOdcinek = odcinek.odbij(prostaYX);

        // Wypisujemy wyniki testów
        System.out.println("Punkty przed przesunięciem:");
        System.out.println("Punkt 1: (" + p1.x + ", " + p1.y + ")");
        System.out.println("Punkt 2: (" + p2.x + ", " + p2.y + ")");
        System.out.println("Punkt 3: (" + p3.x + ", " + p3.y + ")");

        System.out.println("\nOdcinek po przesunięciu o wektor (1, 1):");
        System.out.println("Punkt 1: (" + przesunietyOdcinek.p1.x + ", " + przesunietyOdcinek.p1.y + ")");
        System.out.println("Punkt 2: (" + przesunietyOdcinek.p2.x + ", " + przesunietyOdcinek.p2.y + ")");

        System.out.println("\nTrojkat po przesunięciu o wektor (1, 1):");
        System.out.println("Punkt 1: (" + przesunietyTrojkat.p1.x + ", " + przesunietyTrojkat.p1.y + ")");
        System.out.println("Punkt 2: (" + przesunietyTrojkat.p2.x + ", " + przesunietyTrojkat.p2.y + ")");
        System.out.println("Punkt 3: (" + przesunietyTrojkat.p3.x + ", " + przesunietyTrojkat.p3.y + ")");

        System.out.println("\nTrojkat po obrocie o 90 stopni wokół punktu (0,0):");
        System.out.println("Punkt 1: (" + obroconyTrojkat.p1.x + ", " + obroconyTrojkat.p1.y + ")");
        System.out.println("Punkt 2: (" + obroconyTrojkat.p2.x + ", " + obroconyTrojkat.p2.y + ")");
        System.out.println("Punkt 3: (" + obroconyTrojkat.p3.x + ", " + obroconyTrojkat.p3.y + ")");

        System.out.println("\nOdcinek po odbiciu względem prostej x - y = 0 (y = x):");
        System.out.println("Punkt 1: (" + odbityOdcinek.p1.x + ", " + odbityOdcinek.p1.y + ")");
        System.out.println("Punkt 2: (" + odbityOdcinek.p2.x + ", " + odbityOdcinek.p2.y + ")");
    }
}
