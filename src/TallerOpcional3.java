import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.util.Random;

public class TallerOpcional3 {


    public static void visualizar(List<Cluster> clusters) {
        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (Cluster cluster : clusters) {
            for (Punto2D punto : cluster.getPuntos()) {
                if (punto.x < minX) {
                    minX = punto.x;
                }
                if (punto.x > maxX) {
                    maxX = punto.x;
                }
                if (punto.y < minY) {
                    minY = punto.y;
                }
                if (punto.y > maxY) {
                    maxY = punto.y;
                }
            }
        }

        // Establece la escala para mostrar todos los puntos
        StdDraw.setXscale(minX - 1, maxX + 1);
        StdDraw.setYscale(minY - 1, maxY + 1);

        // Define una matriz de colores de tipo Color
        Color[] colores = {
                Color.RED,
                Color.BLUE,
                Color.BLACK,
                Color.GREEN,
                Color.GRAY,
                Color.MAGENTA,
                Color.CYAN,
                Color.YELLOW
        };

        // Visualiza cada clúster con un color diferente
        for (int i = 0; i < clusters.size(); i++) {
            Cluster cluster = clusters.get(i);
            // Establece el color del lápiz según el índice del clúster
            StdDraw.setPenColor(colores[i % colores.length]);
            for (Punto2D punto : cluster.getPuntos()) {
                StdDraw.filledCircle(punto.x, punto.y, 0.028); // Dibuja un punto con el color establecido
            }
        }
    }


    public static List<Punto2D> leerPuntos(String filename) throws IOException {
        List<Punto2D> puntos = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            double x = Double.parseDouble(partes[0]);
            double y = Double.parseDouble(partes[1]);
            puntos.add(new Punto2D(x, y));
        }
        br.close();
        return puntos;
    }






    public static void main(String[] args) {

        /* TALLER OPCIONAL 3
        SANTIAGO GALLEGO HENAO
        SEBASTIAN FORERO DUQUE
        SEBASTIAN VILLA VARGAS
         */

        //String filename = "datapoints-k=2-n=200.csv";
        String filename = "datapoints-100.csv";
        //String filename = "datapoints-120.csv";
        //String filename = "datapoints-150.csv";
        //String filename = "datapoints-1000.csv";
        //String filename = "datapoints-2500.csv";
        //String filename = "datapoints-5000.csv";
        double Dmax = 0.3; // Configurar el umbral máximo de distancia

        try {
            List<Punto2D> puntos = leerPuntos(filename);
            Clustering clustering = new Clustering(Dmax);

            // Visualizar los puntos iniciales
            List<Cluster> clustersIniciales = new ArrayList<>();
            for (Punto2D punto : puntos) {
                Cluster cluster = new Cluster();
                cluster.agregarPunto(punto);
                clustersIniciales.add(cluster);
            }
            System.out.println("Visualizando los puntos iniciales...");
            visualizar(clustersIniciales);

            long startTime = System.currentTimeMillis();
            int numClusters = clustering.clusterizar(puntos);
            long endTime = System.currentTimeMillis();

            System.out.println("Número de clústeres obtenidos: " + numClusters);
            System.out.println("Tiempo de clusterización: " + (endTime - startTime) + " ms");

            // Visualizar los clústeres después de la clusterización
            System.out.println("Visualizando los clústeres después de la clusterización...");
            visualizar(clustering.clusters);

            System.out.println("PRUEBA CON 10 PUNTOS");
            clustering.randomTest(10);


        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    }




