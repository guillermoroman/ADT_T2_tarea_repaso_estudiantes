import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class GestorFicherosXML{

    public static void guardarLista(ArrayList<Estudiante> estudiantes, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<estudiantes>\n");

            for (Estudiante estudiante : estudiantes) {
                writer.write("\t<estudiante>\n");

                writer.write("\t\t<id>" + estudiante.getId() + "</id>\n");
                writer.write("\t\t<nombre>" + estudiante.getNombre() + "</nombre>\n");
                writer.write("\t\t<calificacion>" + estudiante.getCalificacion() + "</calificacion>\n");
                writer.write("\t\t<beca>" + estudiante.getBeca() + "</beca>\n");

                writer.write("\t</estudiante>\n");
            }

            writer.write("</estudiantes>");
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo XML: " + e.getMessage());
        }
    }

    public static ArrayList<Estudiante> cargarLista(String nombreArchivo) {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();

        try {
            File archivoXML = new File(nombreArchivo);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(archivoXML);

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("estudiante");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    int id = Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent());
                    String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
                    double calificacion = Double.parseDouble(eElement.getElementsByTagName("calificacion").item(0).getTextContent());
                    boolean beca = Boolean.parseBoolean(eElement.getElementsByTagName("beca").item(0).getTextContent());

                    estudiantes.add(new Estudiante(id, nombre, calificacion, beca));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return estudiantes;
    }
}
