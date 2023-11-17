import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class IndiceEstudiantes {
    private Map<Integer, Long> indice; // Integer representa la clave y Long la posición del archivo.

    public IndiceEstudiantes() {
        indice = new HashMap<>();
    }

    public void agregarIndice(int idEstudiante, long posicion) {
        indice.put(idEstudiante, posicion);
    }

    public Long obtenerPosicion(int idEstudiante) {
        return indice.get(idEstudiante);
    }

    public void eliminarIndice(int idEstudiante) {
        //indice.remove(idEstudiante);
        indice.put(idEstudiante, -1L);
    }

    // Método para guardar el índice en un archivo
    public void guardarIndice(String nombreArchivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(indice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar el índice de un archivo
    @SuppressWarnings("unchecked")
    public void cargarIndice(String nombreArchivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            indice = (Map<Integer, Long>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Map<Integer, Long> getIndice() {
        return indice;
    }
}
