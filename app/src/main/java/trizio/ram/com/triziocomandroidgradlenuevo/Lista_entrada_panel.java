package trizio.ram.com.triziocomandroidgradlenuevo;

/*
 * Decompiled with CFR 0_92.
 *
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */

public class Lista_entrada_panel {
    private int idImagen;
    private String textoEncima;
    private String textoDebajo;
    private int proceso;

    public Lista_entrada_panel(int n, String string, String debajo , int proceso) {
        this.idImagen = n;
        this.textoEncima = string;
        this.proceso = proceso;
        this.textoDebajo = debajo;
    }

    public int get_idImagen() {
        return this.idImagen;
    }

    public String get_textoEncima() {
        return this.textoEncima;
    }

    public String get_textoDebajo() {
        return this.textoDebajo;
    }

    public int get_proceso() {
        return this.proceso;
    }

    public void set_texto_encima(String texto) {
        this.textoEncima = texto;
    }

    public void set_idImagen(int id) {

         this.idImagen = id;
    }

    public void set_proceso(int proc) {
        this.proceso = proc;
    }
}
