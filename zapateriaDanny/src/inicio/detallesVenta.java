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
public class detallesVenta {
     private int folio;
    private int cod;
    private int cant;
    private int total;

    public detallesVenta()
    {
        
    }
    public detallesVenta(int folio, int cod, int cant, int total) {
        this.folio = folio;
        this.cod = cod;
        this.cant = cant;
        this.total = total;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
}
