
package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.ClassNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
//5sd-t6
/**
 *
 * @author porra
 */
public class Conexion {
private  Connection coneccion=null;
//private static Conexion conexion=null;
Statement st= null;
ResultSet rs=null;
public  Conexion()//
{   try{
     Class.forName("com.mysql.cj.jdbc.Driver");
     // para MySql : "com.mysql.jdbc.Driver"
     
     coneccion =  (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/danny_zapateria","root","Rycardo-Porr4s");

     } catch (ClassNotFoundException | SQLException ex) {
        JOptionPane.showMessageDialog(null, "conexion no exitosa"+ex, "Conexion", JOptionPane.ERROR_MESSAGE);
   
}
}

public  Connection getConexion()
{
    return coneccion;
}
public static void main(String[] args)
{
    Conexion mysql = new Conexion();
    
}
}