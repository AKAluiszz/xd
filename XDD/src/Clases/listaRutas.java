package Clases;

import java.io.Serializable;
import java.util.ArrayList;

public class listaRutas implements Serializable {

    ArrayList<Rutas> lista_rutas;

    public listaRutas() {
        lista_rutas = new ArrayList<>();
    }

    public ArrayList<Rutas> getLista_rutas() {
        return lista_rutas;
    }

    public void setLista_rutas(ArrayList<Rutas> lista_rutas) {
        this.lista_rutas = lista_rutas;
    }

    public void guardarRutas(Rutas rutas) {
        this.lista_rutas.add(rutas);
    }

    public String[] obtenerInicios() {
        ArrayList<String> listaInicios = new ArrayList<>();
        for (Rutas ruta : lista_rutas)
        {
            String inicio = ruta.getInicio();
            if (!listaInicios.contains(inicio))
            {
                listaInicios.add(inicio);
            }
        }

        return listaInicios.toArray(new String[0]);
    }

    public String[] obtenerFines() {
        ArrayList<String> listaFines = new ArrayList<>();
        for (Rutas ruta : lista_rutas)
        {
            String fin = ruta.getFin();
            if (!listaFines.contains(fin))
            {
                listaFines.add(fin);
            }
        }

        return listaFines.toArray(new String[0]);
    }

    // Método para calcular la distancia entre dos puntos
    public int calcularDistancia(String inicio, String fin) {
        int distancia = -1; // Valor predeterminado si no se encuentra la ruta

        // Buscar la ruta con el punto de inicio y fin dados
        for (Rutas ruta : lista_rutas)
        {
            if (ruta.getInicio().equals(inicio) && ruta.getFin().equals(fin))
            {
                distancia = ruta.getDistancia();
                break;
            }
        }

        return distancia;
    }

}
