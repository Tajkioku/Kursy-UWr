// Punkt
class Punkt {
    final double x;
    final double y;

    public Punkt(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Punkt przesun(Wektor v) {
        return new Punkt(x + v.dx, y + v.dy);
    }

    public Punkt obroc(Punkt p, double kat) {
        double rad = Math.toRadians(kat);
        double newX = p.x + (x - p.x) * Math.cos(rad) - (y - p.y) * Math.sin(rad);
        double newY = p.y + (x - p.x) * Math.sin(rad) + (y - p.y) * Math.cos(rad);
        return new Punkt(newX, newY);
    }

    public Punkt odbij(Prosta p) {
        double a = p.A, b = p.B, c = p.C;
        double d = (a * x + b * y + c) / (a * a + b * b);
        double newX = x - 2 * a * d;
        double newY = y - 2 * b * d;
        return new Punkt(newX, newY);
    }
}

// Odcinek
class Odcinek {
    final Punkt p1;
    final Punkt p2;

    public Odcinek(Punkt p1, Punkt p2) {
        if (p1.equals(p2)) {
            throw new IllegalArgumentException("Punkty nie mogą być takie same.");
        }
        this.p1 = p1;
        this.p2 = p2;
    }

    public Odcinek przesun(Wektor v) {
        return new Odcinek(p1.przesun(v), p2.przesun(v));
    }

    public Odcinek obroc(Punkt p, double kat) {
        return new Odcinek(p1.obroc(p, kat), p2.obroc(p, kat));
    }

    public Odcinek odbij(Prosta p) {
        return new Odcinek(p1.odbij(p), p2.odbij(p));
    }
}

// Trojkat
class Trojkat {
    final Punkt p1;
    final Punkt p2;
    final Punkt p3;

    public Trojkat(Punkt p1, Punkt p2, Punkt p3) {
        if (p1.equals(p2) || p2.equals(p3) || p1.equals(p3)) {
            throw new IllegalArgumentException("Punkty muszą być różne.");
        }
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Trojkat przesun(Wektor v) {
        return new Trojkat(p1.przesun(v), p2.przesun(v), p3.przesun(v));
    }

    public Trojkat obroc(Punkt p, double kat) {
        return new Trojkat(p1.obroc(p, kat), p2.obroc(p, kat), p3.obroc(p, kat));
    }

    public Trojkat odbij(Prosta p) {
        return new Trojkat(p1.odbij(p), p2.odbij(p), p3.odbij(p));
    }
}

// Wektor
class Wektor {
    final double dx;
    final double dy;

    public Wektor(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Wektor dodaj(Wektor v1, Wektor v2) {
        return new Wektor(v1.dx + v2.dx, v1.dy + v2.dy);
    }
}

// Klasa Prosta
class Prosta {
    final double A;
    final double B;
    final double C;

    public Prosta(double A, double B, double C) {
        if (A == 0 && B == 0) {
            throw new IllegalArgumentException("A i B nie mogą być równe 0 jednocześnie.");
        }
        this.A = A;
        this.B = B;
        this.C = C;
    }
    

    public static Prosta przechodzacaPrzezPunkty(Punkt p1, Punkt p2) {
        if (p1.equals(p2)) {
            throw new IllegalArgumentException("Punkty nie mogą być takie same.");
        }
        double A = p2.y - p1.y;
        double B = p1.x - p2.x;
        double C = A * p1.x + B * p1.y;
        return new Prosta(A, B, -C);
    }

    public boolean czyPunktNaProstej(Punkt p) {
        return A * p.x + B * p.y + C == 0;
    }

    public boolean czyRownolegle(Prosta p) {
        return A * p.B == B * p.A;
    }

    public boolean czyProstopadle(Prosta p) {
        return A * p.A + B * p.B == 0;
    }

    public Punkt punktPrzeciecia(Prosta p) {
        double W = A * p.B - B * p.A;
        if (W == 0) {
            throw new IllegalArgumentException("Proste są równoległe.");
        }
        double Wx = -C * p.B + B * p.C;
        double Wy = A * p.C - C * p.A;
        return new Punkt(Wx / W, Wy / W);
    }
}
