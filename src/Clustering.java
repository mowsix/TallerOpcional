import java.util.*;

public class Clustering {
    private double Dmax;
    List<Cluster> clusters;

    public Clustering(double Dmax) {
        this.Dmax = Dmax;
        clusters = new ArrayList<>();
    }

    // Inicializa los clústeres con los puntos iniciales
    private void inicializarClusters(List<Punto2D> puntos) {
        for (Punto2D punto : puntos) {
            Cluster cluster = new Cluster();
            cluster.agregarPunto(punto);
            clusters.add(cluster);
        }
    }

    // Clusteriza los puntos y retorna el número de clústeres
    public int clusterizar(List<Punto2D> puntos) {
        inicializarClusters(puntos);

        // Cola de mínima prioridad para las parejas de puntos ordenadas por distancia
        PriorityQueue<Pair> minPQ = new PriorityQueue<>(Comparator.comparingDouble(Pair::getDistancia));
        for (int i = 0; i < puntos.size(); i++) {
            for (int j = i + 1; j < puntos.size(); j++) {
                Punto2D p1 = puntos.get(i);
                Punto2D p2 = puntos.get(j);
                double distancia = p1.distancia(p2);
                minPQ.add(new Pair(p1, p2, distancia));
            }
        }

        // Procesar pares de puntos en orden creciente según su distancia
        while (!minPQ.isEmpty()) {
            Pair par = minPQ.poll();
            if (par.distancia <= Dmax) {
                Punto2D p1 = par.p1;
                Punto2D p2 = par.p2;

                Cluster cluster1 = null;
                Cluster cluster2 = null;

                for (Cluster cluster : clusters) {
                    if (cluster.contiene(p1)) {
                        cluster1 = cluster;
                    }
                    if (cluster.contiene(p2)) {
                        cluster2 = cluster;
                    }
                }

                if (cluster1 != null && cluster2 != null && cluster1 != cluster2) {
                    cluster1.fusionar(cluster2);
                    clusters.remove(cluster2);
                }
            } else {
                break;
            }
        }

        return clusters.size();
    }

    public int clasificar(Punto2D p, int k) {
        // Lista para almacenar distancias y puntos
        List<Neighbor> neighbors = new ArrayList<>();

        // Calcula la distancia entre el punto `p` y todos los puntos de los clústeres existentes
        for (int i = 0; i < clusters.size(); i++) {
            Cluster cluster = clusters.get(i);
            for (Punto2D punto : cluster.getPuntos()) {
                double distancia = p.distancia(punto);
                neighbors.add(new Neighbor(distancia, i));
            }
        }

        // Ordena los vecinos por distancia (de menor a mayor)
        Collections.sort(neighbors, Comparator.comparingDouble(Neighbor::getDistancia));

        // Contar las ocurrencias de cada clúster en los `k` vecinos más cercanos
        Map<Integer, Integer> clusterCount = new HashMap<>();
        for (int i = 0; i < k; i++) {
            Neighbor neighbor = neighbors.get(i);
            int clusterIndex = neighbor.getClusterIndex();
            clusterCount.put(clusterIndex, clusterCount.getOrDefault(clusterIndex, 0) + 1);
        }

        // Encuentra el clúster que más se repite entre los `k` vecinos más cercanos
        int maxCount = 0;
        int assignedCluster = -1;
        for (Map.Entry<Integer, Integer> entry : clusterCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                assignedCluster = entry.getKey();
            }
        }

        // Devuelve el índice del clúster asignado
        return assignedCluster;
    }

    public void randomTest(int k) {
        Random rand = new Random();

        System.out.println("Realizando pruebas con 10 puntos aleatorios:");

        // Genera 10 puntos aleatorios
        for (int i = 0; i < 10; i++) {
            // Generar un punto aleatorio
            double x = rand.nextDouble(); // Genera un valor entre 0 y 1
            double y = rand.nextDouble(); // Genera un valor entre 0 y 1
            Punto2D puntoAleatorio = new Punto2D(x, y);

            // Clasificar el punto usando el método clasificar

            int clusterIndex = clasificar(puntoAleatorio, k);

            // Imprimir a qué clúster pertenece el punto
            System.out.printf("Punto aleatorio (%f, %f) pertenece al clúster %d%n", x, y, clusterIndex);
        }
    }
}


