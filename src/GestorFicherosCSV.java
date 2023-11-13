import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class GestorFicherosCSV{
    public static void guardarLista(ArrayList<Estudiante> estudiantes, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            // Escribir la cabecera del CSV
            writer.append("ID,Nombre,Calificación\n");

            // Escribir los datos de cada estudiante
            for (Estudiante estudiante : estudiantes) {
                writer.append(String.format("%d,%s,%.2f,%b\n", estudiante.getId(), estudiante.getNombre(), estudiante.getCalificacion(), estudiante.getBeca()));
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir el archivo CSV: " + e.getMessage());
        }
    }


    public static ArrayList<Estudiante> cargarLista(String nombreArchivo) {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;

            // Omitir la cabecera si está presente
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                // Asegurarse de que hay suficientes datos para crear un Estudiante
                if (datos.length >= 3) {
                    int id = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    double calificacion = Double.parseDouble(datos[2]);
                    boolean beca = Boolean.parseBoolean(datos[3]);

                    estudiantes.add(new Estudiante(id, nombre, calificacion, beca));
                }
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo CSV: " + e.getMessage());
        }
        return estudiantes;
    }
}
