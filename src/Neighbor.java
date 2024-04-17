public class Neighbor {
    double distancia;
    int clusterIndex;

    public Neighbor(double distancia, int clusterIndex) {
        this.distancia = distancia;
        this.clusterIndex = clusterIndex;
    }

    public double getDistancia() {
        return distancia;
    }

    public int getClusterIndex() {
        return clusterIndex;
    }
}
