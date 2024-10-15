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
import javax.swing.JComboBox;
import controlador.Conexion;
/**
 *
 * @author porra
 */
public class TallaDao {
       Conexion cone= new Conexion();
    Connection cn= cone.getConexion();
    PreparedStatement ps;
    ResultSet rs;
    public TallaDao()
    {
        
    }
    
    public void consulatTallas(JComboBox tallas)
    {
        String sql="SELECT talla FROM danny_zapateria.talla";
        try{
        ps=cn.prepareStatement(sql);
       // ps.setInt(1, t.getId());
        rs=ps.executeQuery();
        while(rs.next())
        {
            tallas.addItem(rs.getDouble("talla"));
        }
        }catch(SQLException e){
        System.out.print(e.toString());}
    }
   public int regresaTalla(double talla)
    {
        int id=0;
         String sql="select id_talla from talla where talla=?";
        try{
        ps=cn.prepareStatement(sql);
        ps.setDouble(1,talla);
        rs=ps.executeQuery();
        while(rs.next())
        {
             id=rs.getInt("id_talla");
        }
        }catch(SQLException e){
        System.out.print("Hubo un eror al listar");}
        return id;
    }
        
    }
