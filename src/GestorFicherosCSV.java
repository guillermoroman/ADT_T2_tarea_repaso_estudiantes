import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.text.NumberFormat;

public abstract class GestorFicherosCSV{
    //private static NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
    public static void guardarLista(ArrayList<Estudiante> estudiantes, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            // Escribir la cabecera del CSV
            //writer.append("sep=,\n");
            writer.append("ID;Nombre;Calificacion;Beca\n");

            // Escribir los datos de cada estudiante
            for (Estudiante estudiante : estudiantes) {
                writer.append(String.format("%d;%s;%.2f;%b\n", estudiante.getId(), estudiante.getNombre(), estudiante.getCalificacion(), estudiante.getBeca()));
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
                String[] datos = linea.split(";");

                // Asegurarse de que hay suficientes datos para crear un Estudiante
                if (datos.length >= 3) {
                    int id = Integer.parseInt(datos[0]);
                    String nombre = datos[1];

                    /*
                    //double calificacion = Double.parseDouble(datos[2]);
                    */
                    /*
                    Number number;
                    double calificacion = 0.00;
                    try{
                        number = format.parse(datos[2]);
                        calificacion = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    */
                    String calificacionString = datos[2];
                    calificacionString = calificacionString.replace(',', '.');
                    double calificacion = Double.parseDouble(calificacionString);

                    boolean beca = Boolean.parseBoolean(datos[3]);

                    estudiantes.add(new Estudiante(id, nombre, calificacion, beca));
                }
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo CSV: " + e.getMessage());
        }
        return estudiantes;
    }

    public static void borrarRegistro(String nombreArchivo, int idBorrar) {
        ArrayList<Estudiante> estudiantes = cargarLista(nombreArchivo);

        //Opción 1, utilizando una expresión lambda dentro de un removeIf
        estudiantes.removeIf(estudiante -> estudiante.getId() == idBorrar);

        //Opción 2, sin removeIf ni expresión lambda
        /*
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getId() == idBorrar) {
                estudiantes.remove(i); // Eliminar el estudiante de la lista
                break; // Salir del bucle una vez que se encuentra y elimina el estudiante
            }
        }*/

        guardarLista(new ArrayList<>(estudiantes), nombreArchivo);
    }

    public static void modificarRegistro(String nombreArchivo, Estudiante estudianteModificado) {
        ArrayList<Estudiante> estudiantes = cargarLista(nombreArchivo);
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getId() == estudianteModificado.getId()) {
                estudiantes.set(i, estudianteModificado);
                break;
            }
        }
        guardarLista(new ArrayList<>(estudiantes), nombreArchivo);
    }

    public static void anadirRegistro(String nombreArchivo, Estudiante nuevoEstudiante) {
        try (FileWriter writer = new FileWriter(nombreArchivo, true)) { // 'true' para anexar al archivo
            writer.append(String.format("%d;%s;%.2f;%b\n",
                    nuevoEstudiante.getId(),
                    nuevoEstudiante.getNombre(),
                    nuevoEstudiante.getCalificacion(),
                    nuevoEstudiante.getBeca()));
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir en el archivo CSV: " + e.getMessage());
        }
    }
}

