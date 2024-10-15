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
public class Tipo {
    private int idTipo;
    private String nomTipo;

    public Tipo() {
    }

    public Tipo(int idTipo, String nomTipo) {
        this.idTipo = idTipo;
        this.nomTipo = nomTipo;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNomTipo() {
        return nomTipo;
    }

    public void setNomTipo(String nomTipo) {
        this.nomTipo = nomTipo;
    }
    
}
