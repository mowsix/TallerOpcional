class Pair {
    Punto2D p1;
    Punto2D p2;
    double distancia;

    public Pair(Punto2D p1, Punto2D p2, double distancia) {
        this.p1 = p1;
        this.p2 = p2;
        this.distancia = distancia;
    }

    public double getDistancia() {
        return distancia;
    }
}