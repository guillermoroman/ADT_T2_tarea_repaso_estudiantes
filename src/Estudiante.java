import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

// Ejemplo de uso de RandomAccessFile como pequeña base de datos
public class Estudiante {
    private int id;
    private String nombre;
    private double calificacion;
    private boolean beca;

    // Constructor
    public Estudiante (int id, String nombre, double calificacion, boolean beca) {
        this.id = id;
        this.nombre = nombre;
        this.calificacion = calificacion;
        this.beca = beca;
    }
    public Estudiante (){
        this.id = 0;
        this.nombre = "";
        this.calificacion = 0.0;
        this.beca = false;
    }

    // Getters
    public String getNombre() {
        return this.nombre;
    }
    public double getCalificacion(){
        return this.calificacion;
    }
    public int getId(){
        return this.id;
    }
    public boolean getBeca(){
        return this.beca;
    }

    public String toString() {
        return "Nombre: " + this.getNombre() + ". ID: " + this.getId() + ". Calif.: " + this.getCalificacion() + ". Beca: " + this.getBeca();
    }
}
