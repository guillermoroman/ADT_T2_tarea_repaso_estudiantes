import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Map;

public abstract class GestorFicherosDATIndexado {
    static int tamanoRegistro = (Integer.BYTES + (20 * Character.BYTES) + Double.BYTES + 1);
    private static IndiceEstudiantes indiceEstudiantes = new IndiceEstudiantes();

    // Cargar índice desde archivo
    public static void cargarIndice() {
        indiceEstudiantes.cargarIndice("indice.dat");
    }

    // Guardar índice en archivo
    public static void guardarIndice() {
        indiceEstudiantes.guardarIndice("indice.dat");
    }

    public static void guardarLista(ArrayList<Estudiante> estudiantes, String nombreArchivo) {
        try (RandomAccessFile raf = new RandomAccessFile(nombreArchivo, "rw")) {
            //raf.setLength(0); // Limpiar el archivo antes de escribir nuevos datos
            long posicion;

            for (Estudiante estudiante : estudiantes) {
                posicion = raf.getFilePointer(); // Obtener la posición actual del puntero de archivo
                raf.writeInt(estudiante.getId());
                raf.writeChars(String.format("%-20s", estudiante.getNombre()));
                raf.writeDouble(estudiante.getCalificacion());
                raf.writeBoolean(estudiante.getBeca());

                indiceEstudiantes.agregarIndice(estudiante.getId(), posicion);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    public static ArrayList<Estudiante> cargarLista(String fileName){
        ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();

        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {

            while (raf.getFilePointer()<raf.length()) {
                int id = raf.readInt();
                char[] nombreChars = new char[20];
                for (int i = 0; i < nombreChars.length; i++) {
                    nombreChars[i] = raf.readChar();
                }
                String nombre = new String(nombreChars).trim();
                double calificacion = raf.readDouble();
                boolean beca = raf.readBoolean();

                estudiantes.add(new Estudiante(id, nombre, calificacion, beca));
            }
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error de lectura");

        }
        return estudiantes;
    }
*/
    public static ArrayList<Estudiante> cargarLista(String fileName) {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();

        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
            for (Map.Entry<Integer, Long> entrada : indiceEstudiantes.getIndice().entrySet()) {
                long posicionRegistro = entrada.getValue();
                // Ignorar registros marcados como borrados (-1L)
                if (posicionRegistro == -1L) {
                    continue;
                }

                raf.seek(posicionRegistro);
                int id = raf.readInt();
                char[] nombreChars = new char[20];
                for (int i = 0; i < nombreChars.length; i++) {
                    nombreChars[i] = raf.readChar();
                }
                String nombre = new String(nombreChars).trim();
                double calificacion = raf.readDouble();
                boolean beca = raf.readBoolean();

                estudiantes.add(new Estudiante(id, nombre, calificacion, beca));
            }
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error de lectura");
        }
        return estudiantes;
    }
    public static Estudiante cargarRegistro(String fileName, int idEstudiante) {
        Long posicionRegistro = indiceEstudiantes.obtenerPosicion(idEstudiante);
        if (posicionRegistro == null) {
            System.out.println("Estudiante no encontrado en el índice.");
            return null;
        } else if (posicionRegistro < 0){
            System.out.println("El registro ha sido borrado.");
            return null;
        }

        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
            raf.seek(posicionRegistro);

            int id = raf.readInt();
            char[] nombreChars = new char[20];
            for (int i = 0; i < nombreChars.length; i++) {
                nombreChars[i] = raf.readChar();
            }
            String nombre = new String(nombreChars).trim();
            double calificacion = raf.readDouble();
            boolean beca = raf.readBoolean();

            return new Estudiante(id, nombre, calificacion, beca);

        } catch (IOException e) {
            System.out.println("Ha ocurrido un error de lectura");
            return null;
        }
    }


    // Método para añadir un nuevo registro
    public static void crearRegistro(Estudiante estudiante) {
        try (RandomAccessFile raf = new RandomAccessFile("estudiantes.dat", "rw")) {
            long posicion = raf.length();
            raf.seek(posicion);

            raf.writeInt(estudiante.getId());
            raf.writeChars(String.format("%-20s", estudiante.getNombre()));
            raf.writeDouble(estudiante.getCalificacion());
            raf.writeBoolean(estudiante.getBeca());

            indiceEstudiantes.agregarIndice(estudiante.getId(), posicion);
            guardarIndice();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Método para modificar un registro
    public static void modificarRegistro(Estudiante estudiante) {
        Long posicion = indiceEstudiantes.obtenerPosicion(estudiante.getId());
        if (posicion != null && posicion > 0) {
            try (RandomAccessFile raf = new RandomAccessFile("estudiantes.dat", "rw")) {
                raf.seek(posicion);

                raf.writeInt(estudiante.getId());
                raf.writeChars(String.format("%-20s", estudiante.getNombre()));
                raf.writeDouble(estudiante.getCalificacion());
                raf.writeBoolean(estudiante.getBeca());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para borrar un registro
    public static void borrarRegistro(int idEstudiante) {
        Long posicion = indiceEstudiantes.obtenerPosicion(idEstudiante);
        if (posicion != null) {
            try (RandomAccessFile raf = new RandomAccessFile("estudiantes.dat", "rw")) {
                // En lugar de eliminar completamente el índice, lo marcamos como borrado.
                indiceEstudiantes.eliminarIndice(idEstudiante);
                guardarIndice();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}