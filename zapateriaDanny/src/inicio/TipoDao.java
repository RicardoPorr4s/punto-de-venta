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
import javax.swing.JComboBox;
import controlador.Conexion;
/**
 *
 * @author porra
 */
public class TipoDao {
     Conexion cone= new Conexion();
     Connection cn= cone.getConexion();
     PreparedStatement ps;
     ResultSet rs;
    public void consultaTipo(JComboBox tipo)
    {
       
        String sql="SELECT tipo FROM danny_zapateria.tipos";
        try{
        ps=cn.prepareStatement(sql);
       // ps.setInt(1, t.getId());
        rs=ps.executeQuery();
        while(rs.next())
        {
            tipo.addItem(rs.getString("tipo"));
        }
        }catch(SQLException e){
        System.out.print(e.toString());}
    }
     public int regresaTipo(String tipo)
    {
        int id=0;
         String sql="select id_tipos from tipos where tipo=?";
         
        try{
        ps=cn.prepareStatement(sql);
        ps.setString(1,tipo);
        rs=ps.executeQuery();
        while(rs.next())
        {
             id=rs.getInt("id_tipos");
        }
        }catch(SQLException e){
        System.out.print("Hubo un eror al listar");}
        return id;
    }
}
