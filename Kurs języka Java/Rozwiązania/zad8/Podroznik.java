public class Podroznik {
    private int x, y;

    public Podroznik(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void przemiesc(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
