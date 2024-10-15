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
import controlador.Conexion;

/**
 *
 * @author porra
 */
public class ProveedorDao {
    Conexion cone= new Conexion();
    Connection cn= cone.getConexion();
    PreparedStatement ps;
    ResultSet rs;
    public boolean regitrarProveedor(Proveedor p)
    {
        String consulta="INSERT INTO proveedor(id_p,nombre,razon_social,ciudad,telefono,e_mail) VALUES(?,?,?,?,?,?)";
            try
            {
                
            ps=cn.prepareStatement(consulta);
            ps.setInt(1, p.getId());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getRazon());
            ps.setString(4, p.getCiudad());
            ps.setString(5, p.getTelefono());
            ps.setString(6, p.getE_mail());
            ps.execute();
            return true;
            }
            catch(SQLException e){
            System.out.println(e);return false;}
           
    }
    public List listaProveedor()
    {
        List<Proveedor> lista= new ArrayList();
        String query="SELECT * FROM danny_zapateria.proveedor";
        try{
            ps=cn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                Proveedor p = new Proveedor();
                p.setId(rs.getInt("id_p"));
                p.setNombre(rs.getString("nombre"));
                p.setRazon(rs.getString("razon_social"));
                p.setCiudad(rs.getString("ciudad"));
                p.setTelefono(rs.getString("telefono"));
                p.setE_mail(rs.getString("e_mail"));
                lista.add(p);
            }
        }catch(SQLException e){System.out.print(e);}
        return lista;
    }
    public Proveedor bucarProv(int cod)
    {
        Proveedor p = new Proveedor();
        String sql="SELECT * FROM danny_zapateria.proveedor where id_p=?";
        try{
        ps=cn.prepareStatement(sql);
        ps.setInt(1, cod);
        rs=ps.executeQuery();
        if(rs.next())
        {
            p.setId(cod);
            p.setNombre(rs.getString("nombre"));
            p.setRazon(rs.getString("razon_social"));
            p.setCiudad(rs.getString("ciudad"));
            p.setTelefono(rs.getString("telefono"));
            p.setE_mail(rs.getString("e_mail"));
          // System.out.println(""+zap.getCod());
        }
        }catch(SQLException e){
        System.out.print(e.toString());}
        return p;
    }
    public boolean eliminaProv(int id)
    {
       String query="delete FROM danny_zapateria.proveedor where id_p=?";
       try
       {
           ps=cn.prepareStatement(query);
           ps.setInt(1, id);
           ps.execute();
           return true;
       }
       catch(SQLException e){System.out.println(e.toString());return false;}
      
        
    }
    public boolean actualizarProveedor(Proveedor p)
    {
        String query="UPDATE proveedor SET nombre=?,razon_social=?,ciudad=?, telefono=?, e_mail=? WHERE id_p=?";
        try
        {
            ps=cn.prepareStatement(query);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getRazon());
            ps.setString(3, p.getCiudad());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getE_mail());
            ps.setInt(6, p.getId());
            ps.execute();
            return true;
            
        }catch(SQLException e){System.out.print(e);return false;}
    }
        public void consultaProveedor(JComboBox prov)
    {
       
        String sql="SELECT nombre FROM danny_zapateria.proveedor";
        try{
        ps=cn.prepareStatement(sql);
       // ps.setInt(1, t.getId());
        rs=ps.executeQuery();
        while(rs.next())
        {
            prov.addItem(rs.getString("nombre"));
        }
        }catch(SQLException e){
        System.out.print(e.toString());}
    }
         public int regresaProveedor(String nombre)
    {
        int id=0;
         String sql="select id_p from proveedor where nombre=?";
        try{
        ps=cn.prepareStatement(sql);
        ps.setString(1,nombre);
        rs=ps.executeQuery();
        while(rs.next())
        {
             id=rs.getInt("id_p");
        }
        }catch(SQLException e){
        System.out.print("Hubo un eror al listar");}
        return id;
    }
    
}
