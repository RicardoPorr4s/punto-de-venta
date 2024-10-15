
package inicio;


public class Zapato {
    private int cod;
    private String desc;
    private int id_prov;
    private String material;
    private double precioC;
    private double precioV;
    private int tipo;
    private String nombreTipo;
    private int stock;
    private double talla;
    private int id_talla;
    private String nomprov;
    private String color;
    private int id_inv;

    public int getId_inv() {
        return id_inv;
    }

    public void setId_inv(int id_inv) {
        this.id_inv = id_inv;
    }
    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    public void zapato(int cod, String desc, int id_prov, String material,
                       double precioC, double precioV, int tipo)
    {
        this.cod=cod;
        this.desc=desc;
        this.id_prov=id_prov;
        this.material=material;
        this.precioC=precioC;
        this.precioV=precioV;
        this.tipo=tipo;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId_prov() {
        return id_prov;
    }

    public void setId_prov(int id_prov) {
        this.id_prov = id_prov;
    }

  public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getPrecioC() {
        return precioC;
    }

    public void setPrecioC(double precioC) {
        this.precioC = precioC;
    }

    public double getPrecioV() {
        return precioV;
    }

    public void setPrecioV(double precioV) {
        this.precioV = precioV;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    public void setStock(int stock)
    {
        this.stock=stock;
    }
    public int getStock()
    {
        return stock;
    }
    public void setTalla(double talla)
    {
        this.talla=talla;
    }
    public double getTalla()
    {
        return talla;
    }
    public void setIDt(int id_talla)
    {
        this.id_talla=id_talla;
    }
    public int getIDt()
    {
        return id_talla;
    }
    public void setNomProv(String nomprov)
    {
        this.nomprov=nomprov;
    }
    public String getNomProv()
    {
        return nomprov;
    }
    
}
