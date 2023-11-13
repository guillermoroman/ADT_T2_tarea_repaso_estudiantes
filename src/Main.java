import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // Creación de una lista de muestra.
        ArrayList<Estudiante> estudiantes = crearListaDeMuestra();

        // Guardamos en los distintos formatos
        GestorFicherosDAT.guardarLista(estudiantes);
        GestorFicherosXML.guardarLista(estudiantes, "estudiantes.xml");
        GestorFicherosCSV.guardarLista(estudiantes, "estudiantes.csv");


        // DAT
        Estudiante estudiante = GestorFicherosDAT.cargarRegistro("estudiantes.dat", 5);
        System.out.println("Acceso directo a registro");
        System.out.println(estudiante);

        ArrayList<Estudiante> estudiantesDAT = GestorFicherosDAT.cargarLista("estudiantes.dat");
        imprimirLista(estudiantesDAT, "Lista en DAT");


        // CSV
        ArrayList<Estudiante> estudiantesCSV = GestorFicherosCSV.cargarLista("estudiantes.csv");
        imprimirLista(estudiantesCSV, "Lista en CSV");


        // XML
        ArrayList<Estudiante> estudiantesXML = GestorFicherosCSV.cargarLista("estudiantes.csv");
        imprimirLista(estudiantesXML, "Lista en XML");

    }

    private static ArrayList<Estudiante> crearListaDeMuestra() {
        ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
        estudiantes.add(new Estudiante(1, "Juan",  8.56, true));
        estudiantes.add(new Estudiante(2, "María", 6.83, false));
        estudiantes.add(new Estudiante(3, "Sara", 9.23, false));
        estudiantes.add(new Estudiante(4, "Jacinto", 5.45, true));
        estudiantes.add(new Estudiante(5, "Mario", 7.83, false));
        return estudiantes;
    }

    private static void imprimirLista (ArrayList<Estudiante> estudiantes, String nombreLista){
        System.out.println("\n\n");
        System.out.println("===============================");
        System.out.println(nombreLista);
        System.out.println("===============================");
        for(Estudiante estudiante: estudiantes) {
            System.out.println(estudiante);
        }
    }



}