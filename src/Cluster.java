import java.util.ArrayList;
import java.util.List;

public class Cluster {
    List<Punto2D> puntos;

    public Cluster() {
        puntos = new ArrayList<>();
    }

    public void agregarPunto(Punto2D punto) {
        puntos.add(punto);
    }

    public boolean contiene(Punto2D punto) {
        return puntos.contains(punto);
    }

    public void fusionar(Cluster otro) {
        puntos.addAll(otro.puntos);
    }

    public int getTamaño() {
        return puntos.size();
    }

    public List<Punto2D> getPuntos() {
        return puntos;
    }

    public double distanciaMedia(Cluster otro) {
        double sumaDistancia = 0;
        int count = 0;
        for (Punto2D p1 : puntos) {
            for (Punto2D p2 : otro.puntos) {
                sumaDistancia += p1.distancia(p2);
                count++;
            }
        }
        return sumaDistancia / count;
    }
}

