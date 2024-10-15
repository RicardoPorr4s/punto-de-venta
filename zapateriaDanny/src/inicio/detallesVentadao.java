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
import javax.swing.JOptionPane;
import controlador.Conexion;
/**
 *
 * @author porra
 */
public class detallesVentadao {
    Conexion cone= new Conexion();
    Connection cn= cone.getConexion();
    PreparedStatement ps;
    ResultSet rs;
    int res;
    public int registrarVenta(detallesVenta venta)
    {
        
        String sql="INSERT INTO danny_zapateria.detalle_venta (folio_v,codigo,cantidad,total) VALUES(?,?,?,?)";
        try
        {
            ps=cn.prepareStatement(sql);
            ps.setInt(1,venta.getFolio());
            ps.setInt(2,venta.getCod());
            ps.setInt(3,venta.getCant());
            ps.setInt(4, venta.getTotal());
            ps.execute();
          JOptionPane.showMessageDialog(null, "registrado");
    
            
        }catch(SQLException e){
            System.out.print(e.toString());
              JOptionPane.showMessageDialog(null, "falla");
            
        }
        return res;
    }
    
    
}
