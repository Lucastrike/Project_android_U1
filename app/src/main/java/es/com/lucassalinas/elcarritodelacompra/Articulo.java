package es.com.lucassalinas.elcarritodelacompra;

/**
 * Created by alumno_solvam on 9/11/16.
 */

//	Clase que se usa para definir las opciones del ListView
public class Articulo {

    //	Cada opción tiene un título y un subtítulo
    private String nombre;
    private boolean comprado;

    public Articulo(String	nombre){
        this.nombre	= nombre;
        this.comprado = false;
    }

    public String getNombre()	{
        return this.nombre;
    }

    public void setNombre(String nombre)	{
        this.nombre	= nombre;
    }

    public boolean isComprado()	{
        return this.comprado;
    }

    public void setComprado(boolean comprado)	{
        this.comprado =	comprado;
    }

}
