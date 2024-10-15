/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import inventarios.Conexion;
//import java.awt.List;
import java.util.List;
import java.sql.Statement;
import java.util.ArrayList;
import java.lang.Object;
import java.sql.ResultSet;
//import java.awt.List;
/**
 *
 * @author porra
 */
public class AccesoDatos {
    private Connection coneccion;
    private Conexion conexion;
    public AccesoDatos()
    {
        conexion =new Conexion();
    }
    
   public boolean actualizaDatos(String sql)
    {
        
        //lo que le comunique como cadena sql podra insertar "INSERT ...", para actualizar "UPDATE" y para eliminar DELTE
        boolean estado=false;
        try{
            java.sql.Statement orden= conexion.getConexion().createStatement();
            orden.executeUpdate(sql);
            estado=true;
            System.out.println("Se inserto/ se actualizo / se elimino ");
        }
        catch (Exception e)
        {
            System.out.println("Error al insertar/actualizar/eliminar");
        }
        return estado;
    }
    
  public List <Object[]> getTrabajadores1(String consulta)
    {
       // Object[] campos= new Object[5];
        List <Object[]> datos= new ArrayList <Object[]>();
        
        coneccion=conexion.getConexion();
        try
        {
            Statement orden= coneccion.createStatement();
            ResultSet rs=orden.executeQuery(consulta);
            //rs.first();
            while(rs.next()) 
      { 
          int id=rs.getInt(1);
          String nom=rs.getString(2);
          String ape=rs.getString(3);
          String ing=rs.getString(4);
          String nac=rs.getString(5);
          String tur= rs.getString(6);
          String puesto= rs.getString(7);
          Object [] campos= new Object[7];
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
        campos[0]=  id; 
        campos[1]=  nom;
        campos[2]=  ape;
        campos[3]= ing;
        campos[4]=  nac;
        campos[5]=  tur;
        campos[6]= puesto;
        
        datos.add(campos);
           }

        }
        catch(Exception e){
            System.out.println("Hubo un error al listar  "+e);
            
        }
        return datos;
        
    }
     public List <Object[]> getProveedores(String consulta)
    {
       // Object[] campos= new Object[5];
        List <Object[]> datos= new ArrayList <Object[]>();
        
        coneccion=conexion.getConexion();
        try
        {
            Statement orden= coneccion.createStatement();
            ResultSet rs=orden.executeQuery(consulta);
            //rs.first();
            while(rs.next()) 
      { 
          int id=rs.getInt(1);
          String nom=rs.getString(2);
          String raz=rs.getString(3);
          String ciudad=rs.getString(4);
          String tel=rs.getString(5);
          String mail=rs.getString(6);
          Object [] campos= new Object[3];
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
        campos[0]=nom;
        campos[1]=  tel;
        campos[2]= mail;
        
        datos.add(campos);
           }

        }
        catch(Exception e){
            System.out.println("Hubo un error al listar  "+e);
            
        }
        return datos;
        
    }
     public List <Object[]> getVenta(String consulta)
    {
       // Object[] campos= new Object[5];
        List <Object[]> datos= new ArrayList <Object[]>();
        
        coneccion=conexion.getConexion();
        try
        {
            Statement orden= coneccion.createStatement();
            ResultSet rs=orden.executeQuery(consulta);
            //rs.first();
            while(rs.next()) 
                
      { 
          int folio=rs.getInt(1);
          int cod=rs.getInt(2);
          int cantidad=rs.getInt(3);
         String tipp= rs.getString(4);
          int total=rs.getInt(5);
//          int fol=ts.getInt(1);
//          String fecha=ts.getString(2);
//          int idC=ts.getInt(3);
          
          Object [] campos= new Object[5];
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
        campos[0]=  folio; 
        campos[1]=  cod;
        campos[2]= tipp;
        campos[3]= cantidad;
        campos[4]=total;
        
        datos.add(campos);
      }
         
           

        }
        catch(Exception e){
            System.out.println("Hubo un error al listar  "+e);
            
        }
        return datos;
        
    }
     public List <Object[]> getCompras(String con1, String con2, String con3)    {
       // Object[] campos= new Object[5];
        List <Object[]> datos= new ArrayList <Object[]>();
        Object [] campos= new Object[4];
        try
        {
            coneccion=conexion.getConexion();
            // Consulta 1
            Statement stmt1 = coneccion.createStatement();
            ResultSet rs1 = stmt1.executeQuery(con1);

            // Almacenar resultados de la consulta 1 en la estructura de datos
            while (rs1.next()) {
                // Obtener datos de las columnas y agregarlos a la estructura de datos
                Object dato1 = rs1.getObject(1);
                Object dato2 = rs1.getObject(2);
                // Agregar los datos a la estructura de datos
                 
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
                 campos[0]=  dato1; 
                 campos[1]=  dato2;
            }

            // Cerrar el Statement y ResultSet de la consulta 1
            rs1.close();
            stmt1.close();
            // Consulta 2
            Statement stmt2 = coneccion.createStatement();
            ResultSet rs2 = stmt2.executeQuery(con2);

            // Almacenar resultados de la consulta 2 en la estructura de datos
            while (rs2.next()) {
                // Obtener datos de las columnas y agregarlos a la estructura de datos
                Object dato3 = rs2.getObject(2);
                // Agregar los datos a la estructura de datos
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
                 campos[2]=  dato3; 
            }

            // Cerrar el Statement y ResultSet de la consulta 2
            rs2.close();
            stmt2.close();
            Statement stmt3 = coneccion.createStatement();
            ResultSet rs3 = stmt3.executeQuery(con3);

            // Almacenar resultados de la consulta 2 en la estructura de datos
            while (rs3.next()) {
                // Obtener datos de las columnas y agregarlos a la estructura de datos
                Object dato4 = rs3.getObject(2);
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
                 campos[3]=  dato4; 
                datos.add(campos);
            }

            // Cerrar el Statement y ResultSet de la consulta 2
            rs3.close();
            stmt3.close();
             coneccion.close();
         
           

        }
        catch(Exception e){
            System.out.println("Hubo un error al listar  "+e);
            
        }
        return datos;
        
    }
      public List <Object[]> getUsuarios(String consulta)
    {
       // Object[] campos= new Object[5];
        List <Object[]> datos= new ArrayList <Object[]>();
        
        coneccion=conexion.getConexion();
        try
        {
            Statement orden= coneccion.createStatement();
            ResultSet rs=orden.executeQuery(consulta);
            //rs.first();
            while(rs.next()) 
                
      { 
          int idU=rs.getInt(1);
          int id_e=rs.getInt(2);
          String correo=rs.getString(3);
          String nom= rs.getString(4);
          String contra=rs.getString(5);
          String priv=rs.getString(6);
          
//          int fol=ts.getInt(1);
//          String fecha=ts.getString(2);
//          int idC=ts.getInt(3);
          
          Object [] campos= new Object[6];
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
        campos[0]=  idU; 
        campos[1]=  id_e;
        campos[2]=  correo;
        campos[3]=  nom;
        campos[4]=  priv;
        campos[5]= contra;
        
        datos.add(campos);
      }
         
           

        }
        catch(Exception e){
            System.out.println("Hubo un error al listar  "+e);
            
        }
        return datos;
        
    }
       public List <Object[]> getProductoscopy(String consulta)
    {
        List <Object[]> datos= new ArrayList <Object[]>();
        
        coneccion=conexion.getConexion();
        try
        {
            Statement orden= coneccion.createStatement();
            ResultSet rs=orden.executeQuery(consulta);
            //rs.first();
            while(rs.next()) 
                
      { 
          int cod=rs.getInt(1);
          String desc=rs.getString(2);
          String prov=rs.getString(3);
          String met=rs.getString(4);
          double pc=rs.getDouble(5);
          double pv=rs.getDouble(6);
          String tipo=rs.getString(7);
          
          Object [] campos= new Object[1];
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
        campos[0]=  cod; 
        
        datos.add(campos);
      }
         
           

        }
        catch(Exception e){
            System.out.println("Hubo un error al listar  "+e);
            
        }
        return datos;
        
    }
          public List <Object[]> getProductos(String consulta)
    {
        List <Object[]> datos= new ArrayList <Object[]>();
        
        coneccion=conexion.getConexion();
        try
        {
            Statement orden= coneccion.createStatement();
            ResultSet rs=orden.executeQuery(consulta);
            //rs.first();
            while(rs.next()) 
                
      { 
          int cod=rs.getInt(1);
          String desc=rs.getString(2);
          String prov=rs.getString(3);
          String met=rs.getString(4);
          double pc=rs.getDouble(5);
          double pv=rs.getDouble(6);
          String tipo=rs.getString(7);
          
          Object [] campos= new Object[7];
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
        campos[0]=  cod; 
        campos[1]= desc;
        campos[2]= prov;
        campos[3]= met;
        campos[4]=pc;
        campos[5]=pv;
        campos[6]=tipo;
        
        datos.add(campos);
      }
         
           

        }
        catch(Exception e){
            System.out.println("Hubo un error al listar  "+e);
            
        }
        return datos;
        
    }
              public List <Object[]> getCaja(String consulta)
    {
       // Object[] campos= new Object[5];
        List <Object[]> datos= new ArrayList <Object[]>();
        
        coneccion=conexion.getConexion();
        try
        {
            Statement orden= coneccion.createStatement();
            ResultSet rs=orden.executeQuery(consulta);
            //rs.first();
            while(rs.next()) 
                
      { 
          int id=rs.getInt(1);
          int ide=rs.getInt(2);
          int sm=rs.getInt(3);
          int smn=rs.getInt(4);
          
          Object [] campos= new Object[4];
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
        campos[0]=  id; 
        campos[1]=  ide;
        campos[2]= sm;
        campos[3]= smn;
        
        datos.add(campos);
      }
         
           

        }
        catch(Exception e){
            System.out.println("Hubo un error al listar  "+e);
            
        }
        return datos;
        
    }
               public List <Object[]> getComprasB(String con)
    {
       // Object[] campos= new Object[5];
        List <Object[]> datos= new ArrayList <Object[]>();
        
        coneccion=conexion.getConexion();
        try
        {
            Statement orden= coneccion.createStatement();
            ResultSet rs=orden.executeQuery(con);
            //rs.first();
            while(rs.next()) 
                
      { 
          int id=rs.getInt(1);
          String fecha=rs.getString(2);
          String nomp=rs.getString(3);
          String nome=rs.getString(4);
          
          
          Object [] campos= new Object[4];
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
        campos[0]=  id; 
        campos[1]=  fecha;
        campos[2]= nomp;
        campos[3]= nome;
        
        datos.add(campos);
      }
         
           

        }
        catch(Exception e){
            System.out.println("Hubo un error al listar  "+e);
            
        }
        return datos;
        
    }
   public List <Object[]> getVentas(String con)
    {
       // Object[] campos= new Object[5];
        List <Object[]> datos= new ArrayList <Object[]>();
        
        coneccion=conexion.getConexion();
        try
        {
            Statement orden= coneccion.createStatement();
            ResultSet rs=orden.executeQuery(con);
            //rs.first();
            while(rs.next()) 
                
      { 
          int id=rs.getInt(1);
          String fecha=rs.getString(2);
          String id_=rs.getString(3);
          
          
          Object [] campos= new Object[3];
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
        campos[0]=  id; 
        campos[1]=  fecha;
        campos[2]= id_;
        datos.add(campos);
      }
         
           

        }
        catch(Exception e){
            System.out.println("Hubo un error al listar  "+e);
            
        }
        return datos;
        
    }
    public List <Object[]> getTalla(String consulta)
    {
       // Object[] campos= new Object[5];
        List <Object[]> datos= new ArrayList <Object[]>();
        
        coneccion=conexion.getConexion();
        try
        {
            Statement orden= coneccion.createStatement();
            ResultSet rs=orden.executeQuery(consulta);
            //rs.first();
            while(rs.next()) 
                
      { 
          int id_talla=rs.getInt(1);
          double talla=rs.getDouble(2);
          
          Object [] campos= new Object[1];
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
        campos[0]=  talla; 
        
        datos.add(campos);
      }
         
           

        }
        catch(Exception e){
            System.out.println("Hubo un error al listar  "+e);
            
        }
        return datos;
        
    }
    
    public List <Object[]> getInventarios(String consulta)
    {
       // Object[] campos= new Object[5];
        List <Object[]> datos= new ArrayList <Object[]>();
        
        coneccion=conexion.getConexion();
        try
        {
            Statement orden= coneccion.createStatement();
            ResultSet rs=orden.executeQuery(consulta);
            //rs.first();
            while(rs.next()) 
                
      { 
          
   
          int codigo=rs.getInt(1);
          String desc=rs.getString(2);
          double talla=rs.getDouble(3);
          String nom=rs.getString(4);
          int stock=rs.getInt(5);
          
          Object [] campos= new Object[5];
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
        campos[0]=  codigo; 
         campos[1]=  desc; 
          campos[2]=  talla; 
          campos[3]=  nom;
          campos[4]=stock;
        datos.add(campos);
      }
         
           

        }
        catch(Exception e){
            System.out.println("Hubo un error al listar  "+e);
            
        }
        return datos;
        
    }
      public List <Object[]> getInvenParaID(String consulta)
    {
       // Object[] campos= new Object[5];
        List <Object[]> datos= new ArrayList <Object[]>();
        
        coneccion=conexion.getConexion();
        try
        {
            Statement orden= coneccion.createStatement();
            ResultSet rs=orden.executeQuery(consulta);
            //rs.first();
            while(rs.next()) 
                
      { 
          
   
          int codigo=rs.getInt(1);
          int codz=rs.getInt(2);
          int idt=rs.getInt(3);
          int stock=rs.getInt(4);
          
          Object [] campos= new Object[4];
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
        campos[0]=  codigo; 
        campos[1]=  codz; 
          campos[2]=  idt; 
          campos[3]=stock;
        datos.add(campos);
      }
         
           

        }
        catch(Exception e){
            System.out.println("Hubo un error al listar  "+e);
            
        }
        return datos;
        
    }
      public List <Object[]> getVentaConta(String consulta)
    {
       // Object[] campos= new Object[5];
        List <Object[]> datos= new ArrayList <Object[]>();
        
        coneccion=conexion.getConexion();
        try
        {
            Statement orden= coneccion.createStatement();
            ResultSet rs=orden.executeQuery(consulta);
            //rs.first();
            while(rs.next()) 
                
      { 
          int folio=rs.getInt(1);
          int cod=rs.getInt(2);
          int cantidad=rs.getInt(3);
          int total=rs.getInt(4);
//          int fol=ts.getInt(1);
//          String fecha=ts.getString(2);
//          int idC=ts.getInt(3);
          
          Object [] campos= new Object[4];
      //Estructura del registro de los datos pasados de acuerdo al tipo de cada uno
        campos[0]=  folio; 
        campos[1]=  cod;
        campos[2]= cantidad;
        campos[3]=total;
        
        datos.add(campos);
      }
         
           

        }
        catch(Exception e){
            System.out.println("Hubo un error al listar  "+e);
            
        }
        return datos;
        
    }
}
