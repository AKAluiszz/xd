package Clases;

public class Rutas {

    private int codigo;
    private String Inicio;
    private String Fin;
    private int Distancia;

    public Rutas(String Inicio, String Fin, int Distancia) {

        this.Inicio = Inicio;
        this.Fin = Fin;
        this.Distancia = Distancia;
        this.codigo = CSV.CSV.contadorRutas++;
    }

    //Nueva Distancia
    public Rutas(int Distancia) {
        this.codigo = codigo;
        this.Distancia = Distancia;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getInicio() {
        return Inicio;
    }

    public void setInicio(String Inicio) {
        this.Inicio = Inicio;
    }

    public String getFin() {
        return Fin;
    }

    public void setFin(String Fin) {
        this.Fin = Fin;
    }

    public int getDistancia() {
        return Distancia;
    }

    public void setDistancia(int Distancia) {
        this.Distancia = Distancia;
    }

}
