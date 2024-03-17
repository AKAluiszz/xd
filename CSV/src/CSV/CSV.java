package CSV;

import Clases.listaRutas;

import Ventanas.Admin;

public class CSV {

    public static listaRutas lista_Rutas = new listaRutas();
    public static int contadorRutas;

    public static void main(String[] args) {

        Admin pestaña = new Admin();
        pestaña.setVisible(true);
    }

}
