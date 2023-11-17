import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // Creación de una lista de muestra.
        ArrayList<Estudiante> estudiantes = crearListaDeMuestra();

        // Guardamos en los distintos formatos
        GestorFicherosDAT.guardarLista(estudiantes, "estudiantes.dat");
        GestorFicherosXML.guardarLista(estudiantes, "estudiantes.xml");
        GestorFicherosCSV.guardarLista(estudiantes, "estudiantes.csv");

        ///////////////////////////
        // DAT indexado
        GestorFicherosDATIndexado.guardarLista(estudiantes, "estudiantesIndexados.dat");
        System.out.println("Acceso a registro a través de índice: ");
        Estudiante estudiante = GestorFicherosDATIndexado.cargarRegistro("estudiantesIndexados.dat", 3);
        System.out.println(estudiante);

        //borrar registro
        GestorFicherosDATIndexado.borrarRegistro(3);
        Estudiante estudianteBorrado = GestorFicherosDATIndexado.cargarRegistro("estudiantesIndexados.dat", 3);
        System.out.println(estudianteBorrado);

        //Ver que el registro sigue pudiendo ver si no se utiliza el indice para recorrerlo.
        ArrayList<Estudiante> estudiantesIndexados = GestorFicherosDATIndexado.cargarLista("estudiantesIndexados.dat");
        imprimirLista(estudiantesIndexados, "Estudiantes indexados tras borrar");


        //////////////////////////
        // CSV métodos borrarRegistro,  modificarRegistro y anadirRegistro
        /*
        GestorFicherosCSV.borrarRegistro("estudiantes.csv", 3);
        ArrayList<Estudiante> estudiantesMenosTres = GestorFicherosCSV.cargarLista("estudiantes.csv");
        imprimirLista(estudiantesMenosTres, "Estudiantes menos registro 3");

        Estudiante estudianteModificado = new Estudiante(2, "Margarita",  9.99, true);
        GestorFicherosCSV.modificarRegistro("estudiantes.csv", estudianteModificado );
        ArrayList<Estudiante> estudiantesDosModificado = GestorFicherosCSV.cargarLista("estudiantes.csv");
        imprimirLista(estudiantesDosModificado, "Estudiantes con miembro 2 modificado");

        Estudiante nuevoEstudiante = new Estudiante (45, "David", 8.88, false);
        GestorFicherosCSV.anadirRegistro("estudiantes.csv", nuevoEstudiante);
        ArrayList<Estudiante> estudiantesConAnadido = GestorFicherosCSV.cargarLista("estudiantes.csv");
        imprimirLista(estudiantesConAnadido, "Estudiantes con miembro añadido");
        */

        //GestorFicherosDATIndexado.guardarIndice();

        // DAT
        //Estudiante estudiante = GestorFicherosDAT.cargarRegistro("estudiantes.dat", 5);
        //System.out.println("Acceso directo a registro");
        //System.out.println(estudiante);

        ArrayList<Estudiante> estudiantesDAT = GestorFicherosDAT.cargarLista("estudiantes.dat");
        //imprimirLista(estudiantesDAT, "Lista en DAT");


        // CSV
        ArrayList<Estudiante> estudiantesCSV = GestorFicherosCSV.cargarLista("estudiantes.csv");
        //imprimirLista(estudiantesCSV, "Lista en CSV");


        // XML
        ArrayList<Estudiante> estudiantesXML = GestorFicherosCSV.cargarLista("estudiantes.csv");
        //imprimirLista(estudiantesXML, "Lista en XML");

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