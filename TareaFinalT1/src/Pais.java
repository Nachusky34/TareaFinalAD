import java.io.Serializable;

public class Pais implements Serializable {

    private String nombre;
    private String presidente;
    private long pib;
    private double coGini;

    public Pais(String nombre, String presidente, long pib, double coGini) {
        this.nombre = nombre;
        this.presidente = presidente;
        this.pib = pib;
        this.coGini = coGini;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPresidente() {
        return presidente;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public long getPib() {
        return pib;
    }

    public void setPib(long pib) {
        this.pib = pib;
    }

    public double getCoGini() {
        return coGini;
    }

    public void setCoGini(double coGini) {
        this.coGini = coGini;
    }

    @Override
    public String toString() {
        return "Pais{" +
                "nombre='" + nombre + '\'' +
                ", presidente='" + presidente + '\'' +
                ", pib=" + pib +
                ", coGini=" + coGini +
                '}';
    }
}
