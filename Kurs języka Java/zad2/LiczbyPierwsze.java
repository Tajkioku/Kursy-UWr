public final class LiczbyPierwsze {
    private final static int POTEGA2 = 21;
    private final static int MAX = 1 << POTEGA2;
    private final static int[] SITO = new int[MAX];

    static {
        for (int i = 2; i < MAX; i++) {
            SITO[i] = i;
        }
        for (int i = 2; i * i < MAX; i++) {
            if (SITO[i] == i) {
                for (int j = i * i; j < MAX; j += i) {
                    if (SITO[j] == j) {
                        SITO[j] = i;
                    }
                }
            }
        }
    }

    public static boolean czyPierwsza(long n) {
        if (n < 2) return false;
        if (n < MAX) return SITO[(int) n] == n;
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static long[] naCzynnikiPierwsze(long n) {
        if (n == 0 || n == 1 || n == -1) return new long[] {n};

        boolean isNegative = n < 0;
        if (isNegative) n = -n;

        java.util.ArrayList<Long> factors = new java.util.ArrayList<>();
        if (isNegative) factors.add(-1L);

        for (long i = SITO[(int) n]; i * i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        if (n > 1) factors.add(n);

        long[] result = new long[factors.size()];
        for (int i = 0; i < factors.size(); i++) {
            result[i] = factors.get(i);
        }
        return result;
    }
}
