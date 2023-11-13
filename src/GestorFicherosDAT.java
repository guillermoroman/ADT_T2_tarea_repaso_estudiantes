import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public abstract class GestorFicherosDAT{
    static int tamanoRegistro = (Integer.BYTES + (20 * Character.BYTES) + Double.BYTES + 1);

    public static void guardarLista(ArrayList<Estudiante> estudiantes) {
        try (RandomAccessFile raf = new RandomAccessFile("estudiantes.dat", "rw")) {
            for (Estudiante estudiante : estudiantes) {
                raf.writeInt(estudiante.getId());
                raf.writeChars(String.format("%-20s", estudiante.getNombre()));
                raf.writeDouble(estudiante.getCalificacion());
                raf.writeBoolean(estudiante.getBeca());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public static Estudiante cargarRegistro(String fileName, int numReg) {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {

            //Saltar al registro N
            raf.seek((numReg - 1) * tamanoRegistro);

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
}