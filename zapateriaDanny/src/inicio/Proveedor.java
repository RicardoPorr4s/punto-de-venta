/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inicio;

/**
 *
 * @author porra
 */
public class Proveedor {
    private int id;
    private String nombre;
    private String razon;
    private String ciudad;
    private String telefono;
    private String e_mail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public Proveedor(int id, String nombre, String razon, String ciudad, String telefono, String e_mail) {
        this.id = id;
        this.nombre = nombre;
        this.razon = razon;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.e_mail = e_mail;
    }
    public Proveedor()
    {
        
    }
}
