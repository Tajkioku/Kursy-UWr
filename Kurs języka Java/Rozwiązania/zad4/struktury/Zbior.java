package struktury;

public interface Zbior {
    Para szukaj(String klucz);

    void wstaw(Para para);

    void usun(String klucz);

    void czysc();

    boolean pusty();

    int ile();
}

