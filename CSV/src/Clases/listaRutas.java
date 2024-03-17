package Clases;

import java.util.ArrayList;

public class listaRutas {

    ArrayList<Rutas> lista_rutas;

    public listaRutas() {
        lista_rutas = new ArrayList<>();
    }

    public ArrayList<Rutas> getLista_rutas() {
        return lista_rutas;
    }

    public void guardarRutas(Rutas rutas) {
        this.lista_rutas.add(rutas);
    }

    public void setLista_rutas(ArrayList<Rutas> lista_rutas) {
        this.lista_rutas = lista_rutas;
    }

}
