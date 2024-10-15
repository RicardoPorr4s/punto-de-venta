/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inicio;

import java.util.Date;

/**
 *
 * @author porra
 */
public class Ventas {
    private int folio;
    private Date fecha;
    private int id_e;

    public Ventas()
    {
        
    }
    public Ventas(int folio, Date fecha, int id_e) {
        this.folio = folio;
        this.fecha = fecha;
        this.id_e = id_e;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getId_e() {
        return id_e;
    }

    public void setId_e(int id_e) {
        this.id_e = id_e;
    }
    
}
