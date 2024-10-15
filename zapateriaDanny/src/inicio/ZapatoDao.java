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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import controlador.Conexion;
/**
 *
 * @author porra
 */
public class ZapatoDao {
    
    Conexion cone= new Conexion();
    Connection cn= cone.getConexion();
    PreparedStatement ps;
    ResultSet rs;
    public boolean regitrarZapato(Zapato zap)
    {
        String consulta="INSERT INTO zapato(codigo,descripcion,prov,material,precio_C,precio_V,tipo) VALUES(?,?,?,?,?,?,?)";
            try
            {
                
            ps=cn.prepareStatement(consulta);
            ps.setInt(1, zap.getCod());
            ps.setString(2, zap.getDesc());
            ps.setInt(3, zap.getId_prov());
            ps.setString(4, zap.getMaterial());
            ps.setDouble(5, zap.getPrecioC());
            ps.setDouble(6, zap.getPrecioV());
            ps.setInt(7, zap.getTipo());
            ps.execute();
            
            JOptionPane.showMessageDialog(null, "Producto dado de alta correctamente");
            return true;
            }
            catch(SQLException e){
            System.out.println(e);return false;}
    }
    public Zapato bucarZapato(int cod)
    {
        Zapato zap = new Zapato();
        String sql="SELECT * FROM danny_zapateria.zapato where codigo=?";
        try{
        ps=cn.prepareStatement(sql);
        ps.setInt(1, cod);
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
          // System.out.println(""+zap.getCod());
        }
        }catch(SQLException e){
        System.out.print(e.toString());}
        return zap;
    }
    
    public ArrayList <Object[]> regresaTalla(int cod)
    {
        
         ArrayList <Object[]> datos= new ArrayList <Object[]>();
         String sql="SELECT t.talla FROM zapato z JOIN inventario i ON z.codigo = i.codigo JOIN talla t ON t.id_talla = i.id_talla where z.codigo=?";
        try{
        ps=cn.prepareStatement(sql);
        ps.setInt(1,cod);
        rs=ps.executeQuery();
        while(rs.next())
        {
            double talla=rs.getDouble("talla");
                Object [] campos = new Object[1];
                campos[0]= talla;
            datos.add(campos);
        }
        for(int i=0; i<datos.size();i++){
        System.out.println(datos.get(i).toString());
        }
        rs.close();
        rs.close();
        }catch(SQLException e){
        System.out.print("Hubo un eror al listar");}
        return datos;
        
    }
     public Zapato buscaExtras(int cod, double talla)
    {
        Zapato zap = new Zapato();
        String sql="SELECT * FROM zapato z JOIN inventario i ON z.codigo = i.codigo JOIN talla t ON t.id_talla = i.id_talla where z.codigo=? and t.talla=?";
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
            zap.setIDt(rs.getInt("id_talla"));
          // System.out.println(""+zap.getCod());
        }
        }catch(SQLException e){
        System.out.print(e.toString());}
        return zap;
    }
      public Zapato buscaExtra(int cod, double talla)
    {
        Zapato zap = new Zapato();
        //String sql="SELECT * FROM zapato z JOIN inventario i ON z.codigo = i.codigo JOIN talla t ON t.id_talla = i.id_talla where z.codigo=?";
         String sql="SELECT * FROM zapato z JOIN inventario i ON z.codigo = i.codigo JOIN talla t ON t.id_talla = i.id_talla JOIN proveedor p ON p.id_p =z.prov where z.codigo=? and t.talla=?";

        try{
        ps=cn.prepareStatement(sql);
        ps.setInt(1, cod);
        ps.setDouble(2, talla);
        rs=ps.executeQuery();
        if(rs.next())
        {
            zap.setCod(rs.getInt("codigo"));
            zap.setDesc(rs.getString("descripcion"));
            zap.setId_prov(rs.getInt("prov"));
            zap.setMaterial(rs.getString("material"));
            zap.setPrecioC(rs.getDouble("precio_C"));
            zap.setPrecioV(rs.getDouble("precio_V"));
            zap.setTipo(rs.getInt("tipo"));
            zap.setStock(rs.getInt("stock"));
            //zap.setTalla(rs.getDouble("talla"));
            zap.setIDt(rs.getInt("id_talla"));
            zap.setTalla(talla);
            zap.setNomProv(rs.getString("nombre"));
            zap.setColor(rs.getString("color"));
            zap.setId_inv(rs.getInt("id"));
            
          System.out.println(""+zap.getCod());
        }
        }catch(SQLException e){
        System.out.print(e.toString());}
        return zap;
    }
         public void consultaMaterial(JComboBox tipo)
    {
       
        String sql="SELECT material FROM danny_zapateria.zapato";
        try{
        ps=cn.prepareStatement(sql);
       // ps.setInt(1, t.getId());
        rs=ps.executeQuery();
        while(rs.next())
        {
            tipo.addItem(rs.getString("material"));
        }
        }catch(SQLException e){
        System.out.print(e.toString());}
    }
    public List listaZapatos()
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
            zap.setStock(rs.getInt("stock"));
            zap.setTalla(rs.getDouble("talla"));
            zap.setIDt(rs.getInt("id_talla"));
            zap.setNomProv(rs.getString("nombre"));
            zap.setNombreTipo(rs.getString("tipo"));
            zapito.add(zap);
        }
        }catch(SQLException e){
        System.out.print(e.toString());}
        return zapito;
        
    }
    public List listanuevaZapatos()
    {
        List<Zapato> zapito= new ArrayList();
       // String sql="SELECT * FROM zapato z JOIN inventario i ON z.codigo = i.codigo JOIN talla t ON t.id_talla = i.id_talla";
        //String sql="select * from inventario i JOIN zapato z ON i.codigo=z.codigo JOIN talla t ON t.id_talla=i.id_talla JOIN proveedor p ON p.id_p=z.prov";
      //String sql="select * from inventario i JOIN zapato z ON i.codigo=z.codigo JOIN talla t ON t.id_talla=i.id_talla JOIN proveedor p ON p.id_p=z.prov JOIN tipos tp ON tp.id_tipos=z.tipo";
      String sql="SELECT * FROM zapato z  JOIN proveedor p ON p.id_p =z.prov JOIN tipos tp ON tp.id_tipos=z.tipo";  
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
            zapito.add(zap);
        }
        }catch(SQLException e){
        System.out.print(e.toString());}
        return zapito;
        
    }
    public boolean eliminaProd(int id, int talla)
    {
       String query="delete FROM danny_zapateria.inventario where codigo=? and id_talla=?";
       try
       {
           ps=cn.prepareStatement(query);
           ps.setInt(1, id);
           ps.setInt(2, talla);
           ps.execute();
           System.out.print("elimindo");
           return true;
           
       }
       catch(SQLException e){System.out.println(e.toString());
       JOptionPane.showMessageDialog(null, "El producto no puede ser borrador por la existencia del codigo en las ventas");
       return false;}
      
        
    }
    public boolean actualizaZapato(Zapato z)
    {
        String query="UPDATE zapato SET descripcion=?,prov=?,material=?, precio_C=?, precio_V=?, tipo=? WHERE codigo=?";
        try
        {
            ps=cn.prepareStatement(query);
            ps.setString(1, z.getDesc());
            ps.setInt(2, z.getId_prov());
            ps.setString(3, z.getMaterial());
            ps.setDouble(4, z.getPrecioC());
            ps.setDouble(5, z.getPrecioV());
            ps.setInt(6, z.getTipo());
            ps.setInt(7, z.getCod());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Actuallización correcta");
            
            return true;
            
        }catch(SQLException e){System.out.print(e);
        
       JOptionPane.showMessageDialog(null, "El producto no puede ser borrador por la existencia del codigo en las ventas");return false;}
    }
    public boolean actualizaInventario(Zapato z)
    {
        String query="UPDATE inventario SET color=?,stock=? WHERE codigo=? and id_talla=?";
        try
        {
            ps=cn.prepareStatement(query);
            ps.setString(1, z.getColor());
            ps.setInt(2, z.getStock());
            ps.setInt(3, z.getCod());
            ps.setInt(4, z.getIDt());
            ps.execute();
            return true;
            
        }catch(SQLException e){System.out.print(e);return false;}
    }
    public boolean actualizaInventario1(Zapato z)
    {
        String query="UPDATE inventario SET stock=? WHERE codigo=? and id_talla=?";
        try
        {
            ps=cn.prepareStatement(query);
            ps.setInt(1, z.getStock());
            ps.setInt(2, z.getCod());
            ps.setInt(3, z.getIDt());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Agregado al inventario");
            return true;
            
        }catch(SQLException e){System.out.print(e);return false;}
    }
    public boolean eliminaZapato(int id, String desc)
    {
       String query="delete FROM danny_zapateria.zapato where codigo=? and descripcion=?";
       try
       {
           ps=cn.prepareStatement(query);
           ps.setInt(1, id);
           ps.setString(2, desc);
           ps.execute();
           JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
           return true;
           
       }
       catch(SQLException e){System.out.println(e.toString());
       
       JOptionPane.showMessageDialog(null, "El producto no puede ser elimnado por la existencia del codigo en las ventas");
       return false;}
      
        
    }
     public Zapato bucarZapatoEnBase(int cod)
    {
        Zapato zap = new Zapato();
        String sql="select z.descripcion,p.id_p,z.material,z.precio_C,z.precio_V,z.tipo,p.nombre,tp.tipo from zapato z  JOIN proveedor p ON p.id_p=z.prov JOIN tipos tp ON tp.id_tipos=z.tipo where z.codigo=?";
        try{
        ps=cn.prepareStatement(sql);
        ps.setInt(1, cod);
        rs=ps.executeQuery();
        if(rs.next())
        {
            zap.setCod(cod);
            zap.setDesc(rs.getString("z.descripcion"));
            zap.setId_prov(rs.getInt("p.id_p"));
            zap.setMaterial(rs.getString("z.material"));
            zap.setPrecioC(rs.getDouble("z.precio_C"));
            zap.setPrecioV(rs.getDouble("z.precio_V"));
            zap.setTipo(rs.getInt("z.tipo"));
            zap.setNomProv(rs.getString("p.nombre"));
           // zap.setTalla(rs.getDouble("t.talla"));
           zap.setNombreTipo(rs.getString("tp.tipo"));
          // System.out.println(""+zap.getCod());
        }
        }catch(SQLException e){
        System.out.print(e.toString());}
        return zap;
    }
}
