/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import controlador.Conexion;
/**
 *
 * @author porra
 */
public class VentasDao {
    Conexion cone= new Conexion();
    Connection cn= cone.getConexion();
    PreparedStatement ps;
    ResultSet rs;
    int res;
    //Date fecha=new Date(System.currentTimeMillis());
    public int registrarVenta(Ventas venta)
    {
        String sql="INSERT INTO danny_zapateria.venta (folio_v, fecha, id_e) VALUES(?,curdate(),?)";
        try
        {
            ps=cn.prepareStatement(sql);
            ps.setInt(1,venta.getFolio());
            //ps.setDate(2, (java.sql.Date) venta.getFecha());
            ps.setInt(2,venta.getId_e());
            ps.execute();
            JOptionPane.showMessageDialog(null,"Venta registrada");
            
        }catch(SQLException e){
            System.out.print(e.toString());
            
        }
        return res;
    }
    public boolean actualizarStock(int stock, int cod, int talla)
    {
        String consulta="UPDATE inventario SET stock=? WHERE codigo=? and id_talla=?";
        try
        {
            ps=cn.prepareStatement(consulta);
            ps.setInt(1,stock);
            ps.setInt(2, cod);
            ps.setInt(3,talla);
            ps.execute();
           JOptionPane.showMessageDialog(null,"Stock actalizado");
            return true;
        }catch(SQLException e){
            System.out.print(e.toString());
            return false;
            
        }
        
    }
    
}
