class Punto2D {
    double x;
    double y;

    public Punto2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Calcula la distancia entre dos puntos
    public double distancia(Punto2D otro) {
        double dx = this.x - otro.x;
        double dy = this.y - otro.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
