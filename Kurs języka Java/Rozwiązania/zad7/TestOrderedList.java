import java.util.Date;

public class TestOrderedList {

    public static void main(String[] args) {

        // Test z Integer
        OrderedList<Integer> intList = new OrderedList<>();
        intList.insert(5);
        intList.insert(2);
        intList.insert(8);
        intList.insert(3);
        intList.insert(5); // Duplikat, nie powinien być dodany

        System.out.println("Lista Integer: " + intList);
        System.out.println("Rozmiar: " + intList.size());
        System.out.println("Min: " + intList.min());
        System.out.println("Max: " + intList.max());
        System.out.println("Element na pozycji 2: " + intList.at(2));
        System.out.println("Indeks elementu 8: " + intList.index(8));
        System.out.println("Czy zawiera 3: " + intList.search(3));
        System.out.println("Czy zawiera 10: " + intList.search(10));

        System.out.println("Iteracja po liście Integer:");
        for (Integer i : intList) {
            System.out.println(i);
        }

        intList.remove(3);
        System.out.println("Po usunięciu 3: " + intList);

        // Test z String
        OrderedList<String> strList = new OrderedList<>();
        strList.insert("banan");
        strList.insert("jabłko");
        strList.insert("czereśnia");
        strList.insert("daktyl");
        strList.insert("jabłko"); // Duplikat, nie powinien być dodany

        System.out.println("Lista String: " + strList);
        System.out.println("Rozmiar: " + strList.size());
        System.out.println("Min: " + strList.min());
        System.out.println("Max: " + strList.max());
        System.out.println("Element na pozycji 2: " + strList.at(2));
        System.out.println("Indeks elementu 'czereśnia': " + strList.index("czereśnia"));
        System.out.println("Czy zawiera 'daktyl': " + strList.search("daktyl"));
        System.out.println("Czy zawiera 'figa': " + strList.search("figa"));

        System.out.println("Iteracja po liście String:");
        for (String s : strList) {
            System.out.println(s);
        }

        strList.remove("banan");
        System.out.println("Po usunięciu 'banan': " + strList);

        // Test z Date
        OrderedList<Date> dateList = new OrderedList<>();
        Date now = new Date();
        Date earlier = new Date(now.getTime() - 1000000); // Wcześniejszy czas
        Date later = new Date(now.getTime() + 1000000);   // Późniejszy czas

        dateList.insert(now);
        dateList.insert(earlier);
        dateList.insert(later);

        System.out.println("Lista Date: " + dateList);
        System.out.println("Rozmiar: " + dateList.size());
        System.out.println("Min: " + dateList.min());
        System.out.println("Max: " + dateList.max());
        System.out.println("Element na pozycji 1: " + dateList.at(1));
        System.out.println("Indeks elementu 'now': " + dateList.index(now));
        System.out.println("Czy zawiera 'later': " + dateList.search(later));
        System.out.print("Próba wyszukania null: ");

        try {
            dateList.search(null);
        } catch (NullPointerException e) {
            System.out.println("Złapano NullPointerException zgodnie z oczekiwaniem");
        }

        System.out.println("Iteracja po liście Date:");
        for (Date d : dateList) {
            System.out.println(d);
        }

        dateList.remove(earlier);
        System.out.println("Po usunięciu 'earlier': " + dateList);
    }
}
