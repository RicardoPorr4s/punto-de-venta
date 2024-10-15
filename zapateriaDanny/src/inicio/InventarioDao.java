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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import controlador.Conexion;

/**
 *
 * @author porra
 */
public class InventarioDao {
    Conexion cone= new Conexion();
    Connection cn= cone.getConexion();
    PreparedStatement ps;
    ResultSet rs;
    public boolean regitraEnInventario(Zapato zap)
    {
        String consulta="INSERT INTO inventario(codigo,id_talla,color,stock) VALUES(?,?,?,?)";
            try
            {
                
            ps=cn.prepareStatement(consulta);
            ps.setInt(1, zap.getCod());
            ps.setInt(2, zap.getIDt());
            ps.setString(3, zap.getColor());
            ps.setInt(4, zap.getStock());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Registado en inventario");
            return true;
            
            }
            catch(SQLException e){
            System.out.println(e);return false;}
           
    }
    public boolean regitraProductoEnInventario(Zapato zap)
    {
        String consulta="INSERT INTO inventario(id,codigo,id_talla,stock) VALUES(?,?,?,?)";
            try
            {
                
            ps=cn.prepareStatement(consulta);
            ps.setInt(1, zap.getId_inv());
            ps.setInt(2, zap.getCod());
            ps.setInt(3, zap.getIDt());
            ps.setInt(4, zap.getStock());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Registado en inventario");
            return true;
            
            }
            catch(SQLException e){
            System.out.println(e);return false;}
           
    }
    public List listaInventario()
    {
        List<Zapato> zapito= new ArrayList();
       // String sql="SELECT * FROM zapato z JOIN inventario i ON z.codigo = i.codigo JOIN talla t ON t.id_talla = i.id_talla";
        //String sql="select * from inventario i JOIN zapato z ON i.codigo=z.codigo JOIN talla t ON t.id_talla=i.id_talla JOIN proveedor p ON p.id_p=z.prov";
      String sql="select * from inventario i JOIN zapato z ON i.codigo=z.codigo JOIN talla t ON t.id_talla=i.id_talla JOIN proveedor p ON p.id_p=z.prov JOIN tipos tp ON tp.id_tipos=z.tipo";

      try{
        ps=cn.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next())
        {
            Zapato zap= new Zapato();
            zap.setCod(rs.getInt("codigo"));
            zap.setDesc(rs.getString("descripcion"));
            zap.setId_prov(rs.getInt("prov"));
            zap.setMaterial(rs.getString("material"));
            zap.setPrecioC(rs.getDouble("precio_C"));
            zap.setPrecioV(rs.getDouble("precio_V"));
            zap.setTipo(rs.getInt("tipo"));
            zap.setNomProv(rs.getString("nombre"));
            zap.setNombreTipo(rs.getString("tipo"));
            zap.setStock(rs.getInt("stock"));
            zap.setTalla(rs.getDouble("talla"));
            zapito.add(zap);
        }
        }catch(SQLException e){
        System.out.print(e.toString());}
        return zapito;
        
    }
    //sirve
     public Zapato bucarZapatoinv(int cod, double talla)
    {
        Zapato zap = new Zapato();
        String sql="select * from inventario i JOIN zapato z ON i.codigo=z.codigo JOIN talla t ON t.id_talla=i.id_talla JOIN proveedor p ON p.id_p=z.prov JOIN tipos tp ON tp.id_tipos=z.tipo where z.codigo=? and t.talla=?";
        try{
        ps=cn.prepareStatement(sql);
        ps.setInt(1, cod);
        ps.setDouble(2, talla);
        rs=ps.executeQuery();
        if(rs.next())
        {
            zap.setCod(cod);
            zap.setDesc(rs.getString("descripcion"));
            zap.setId_prov(rs.getInt("prov"));
            zap.setMaterial(rs.getString("material"));
            zap.setPrecioC(rs.getDouble("precio_C"));
            zap.setPrecioV(rs.getDouble("precio_V"));
            zap.setTipo(rs.getInt("tipo"));
            zap.setStock(rs.getInt("stock"));
            zap.setTalla(rs.getDouble("talla"));
            zap.setIDt(rs.getInt("t.id_talla"));
            zap.setId_inv(rs.getInt("id"));
           // zap.setTalla(rs.getDouble("t.talla"));
            zap.setNomProv(rs.getString("nombre"));
          // System.out.println(""+zap.getCod());
        }
        }catch(SQLException e){
        System.out.print(e.toString());}
        return zap;
    }
     //no sirve
      public boolean actualizaStock(Zapato z)
    {
        String query="update danny_zapateria.inventario set stock=? where id=?";
      //  String query="UPDATE inventario SET stock=? WHERE id=?";
        try
        {
            ps=cn.prepareStatement(query);
            ps.setInt(1, z.getStock());
            ps.setInt(2, z.getId_inv());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Stock actualizado");
            return true;
            
        }catch(SQLException e){System.out.print(e);return false;}
    }
    
}
