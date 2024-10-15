/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inicio;

import Reportes.Excel;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Image;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Graphics;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

//import java.awt.Image;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import inicio.Zapato;
import inicio.ZapatoDao;
import inicio.Ventas;
import inicio.VentasDao;
import inicio.detallesVentadao;
import inicio.detallesVenta;
import inicio.Talla;
import inicio.TallaDao;
import inicio.Proveedor;
import inicio.ProveedorDao;
import inicio.Tipo;
import inicio.TipoDao;
import inicio.InventarioDao;
import controlador.AccesoDatos;
import controlador.Conexion;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

/**
 *
 * @author porra
 */
public class PuntoDeVenta extends javax.swing.JFrame {
    private AccesoDatos acceso;
    Conexion cone= new Conexion();
    Connection cn= cone.getConexion();
    ResultSet rs;
    
    Ventas ventas= new Ventas();
    VentasDao ventasdao= new VentasDao();
    detallesVentadao detao = new detallesVentadao();
    detallesVenta det = new detallesVenta();
    Zapato zapato= new Zapato();
    ZapatoDao zapdao = new ZapatoDao();
    Talla talla= new Talla();
    TallaDao talladao= new TallaDao();
    Proveedor prov= new Proveedor();
    ProveedorDao provdao = new ProveedorDao();
    Tipo tp= new Tipo();
    TipoDao tpdao= new TipoDao();
    InventarioDao inv= new InventarioDao();
    DefaultTableModel mod= new DefaultTableModel();
    DefaultTableModel modpro= new DefaultTableModel();
    DefaultTableModel modelo= new DefaultTableModel();
    DefaultTableModel pmodelo= new DefaultTableModel();
    DefaultTableModel pro= new DefaultTableModel();
    DefaultTableModel tmp= new DefaultTableModel();
    private ArrayList venta;
    double totalpago=0.00;
    double vuelto=0.00;
    int item;
    ArrayList<Object[]> pproducto;
    ArrayList<Object[]> pinventario;
    ArrayList<Object[]> pinventarioID;
    ArrayList<Object[]> ve;
    ArrayList<Object[]> aprove;
    ArrayList<Object[]> aprov;
    ArrayList<Object[]> nomtallas;
    ArrayList<Object[]> nomtallass= new ArrayList<Object[]>();
    Object[] campos;
    private TextAutoCompleter ac;
    public PuntoDeVenta() {
        initComponents();
        txtdesc.setEnabled(false);
        txtstock.setEnabled(false);
        txtprecio.setEnabled(false);
        lbl_aviso1.setVisible(false);
        lbl_aviso2.setVisible(false);
        lbl_aviso3.setVisible(false);
        lbl_aviso4.setVisible(false);
        lbl_aviso5.setVisible(false);
        lbl_aviso16.setVisible(false);
        lbl_aviso20.setVisible(false);
        lbl_aviso21.setVisible(false);
        lbl_aviso22.setVisible(false);
        lbl_aviso25.setVisible(false);
        this.setLocationRelativeTo(null);
         
         ac = new TextAutoCompleter(txtcod);
         autocompletaText();
         ac= new TextAutoCompleter(txtTalla);
         listaProveedores();
         
         tdesc.setEnabled(false);
         tprecioc.setEnabled(false);
         tpreciov.setEnabled(false);
         tmat.setEnabled(false);
         tprov.setEnabled(false);
         ttipo.setEnabled(false);
         
         italla.setEnabled(false);
         istock.setEnabled(false);
         mostrarInventarios();
         mostrarZapatos();
         
       
         
    }
     public void mostrarInventarios()
    {
        pinventario= new ArrayList<Object[]>();
         acceso = new AccesoDatos();
        String sql="select z.codigo, z.descripcion, t.talla,p.nombre, i.stock from inventario i JOIN zapato z ON i.codigo=z.codigo JOIN talla t ON t.id_talla=i.id_talla JOIN proveedor p ON p.id_p=z.prov JOIN tipos tp ON tp.id_tipos=z.tipo";
        pinventario= (ArrayList<Object[]>)acceso.getInventarios(sql);
        JTable table=tabProd2;
         mod = (DefaultTableModel)tabProd2.getModel();
        for(Object[] datos:pinventario)
        {
            mod.addRow(datos);
        }
        table.setModel(mod);
        mod.fireTableDataChanged();
    }
     public void mostrarZapatos()
    {
        pproducto= new ArrayList<Object[]>();
         acceso = new AccesoDatos();
        String sql="select z.codigo,z.descripcion,p.nombre,z.material,z.precio_C,z.precio_V,tp.tipo from zapato z  JOIN proveedor p ON p.id_p=z.prov JOIN tipos tp ON tp.id_tipos=z.tipo";
        pproducto= (ArrayList<Object[]>)acceso.getProductos(sql);
        JTable table=tabProd1;
         modpro = (DefaultTableModel)tabProd1.getModel();
        for(Object[] datos:pproducto)
        {
            modpro.addRow(datos);
        }
        table.setModel(modpro);
        modpro.fireTableDataChanged();
    }
    public void listaProveedores()
    {
        List<Proveedor> listap=provdao.listaProveedor();
        pmodelo=(DefaultTableModel) tabProveedor.getModel();
        Object[] ob= new Object[4];
        for(int i=0; i<listap.size();i++)
        {
            ob[0]=listap.get(i).getId();
            ob[1]=listap.get(i).getNombre();
            ob[2]=listap.get(i).getTelefono();
            ob[3]=listap.get(i).getE_mail();
            pmodelo.addRow(ob);
        }
        tabProveedor.setModel(pmodelo);
    }
  
    public void listanuevaZapatos()
    {
        
            List<Zapato> listap=zapdao.listanuevaZapatos();
        pro=(DefaultTableModel) tabProd1.getModel();
        Object[] ob= new Object[7];
        for(int i=0; i<listap.size();i++)
        {
            ob[0]=listap.get(i).getCod();
            ob[1]=listap.get(i).getNombreTipo();
            ob[2]=listap.get(i).getNomProv();
            ob[3]=listap.get(i).getMaterial();
            ob[4]=listap.get(i).getPrecioV();
            ob[5]=listap.get(i).getPrecioC();
            ob[6]=listap.get(i).getDesc();
            pro.addRow(ob);
        }
        tabProd1.setModel(pro);
    }
     public void listaInventario()
    {
            List<Zapato> listap=inv.listaInventario();
        pro=(DefaultTableModel) tabProd2.getModel();
        Object[] ob= new Object[5];
        for(int i=0; i<listap.size();i++)
        {
            ob[0]=listap.get(i).getCod();
            ob[1]=listap.get(i).getDesc();
            ob[2]=listap.get(i).getTalla();
            ob[3]=listap.get(i).getNomProv();
            ob[4]=listap.get(i).getStock();
            pro.addRow(ob);
        }
        tabProd2.setModel(pro);
    }
   public void autocompletaText()
    {
         nomtallas= new ArrayList<Object[]>();
         acceso = new AccesoDatos();
         String consulta="SELECT * FROM zapato";
         nomtallas= (ArrayList<Object[]>)acceso.getProductoscopy(consulta);
         for (int i=0;i<nomtallas.size();i++)
         {
             ac.addItems(nomtallas.get(i));
         }
    }
    public void autocompletaTalla(int cod)
    {
         ArrayList<Object[]> u;
         u = new ArrayList<>();
       int coda=cod;
         nomtallass=(ArrayList<Object[]>)zapdao.regresaTalla(coda);
         u.clear();
         u.addAll(nomtallass);
         ac.removeAllItems();
         for (int i=0;i<u.size();i++)
         {
             ac.addItems(u.get(i));
         }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtcod = new javax.swing.JTextField();
        txtprecio = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtstock = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtotal = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        pago = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtcambio = new javax.swing.JLabel();
        txtcant = new javax.swing.JTextField();
        txtdesc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        txtTalla = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabventa = new ColorCelda2();
        jLabel14 = new javax.swing.JLabel();
        txtmail = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtrazon = new javax.swing.JTextField();
        txtnomprov = new javax.swing.JTextField();
        txtciudad = new javax.swing.JTextField();
        txttel = new javax.swing.JTextField();
        txte = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabProveedor = new javax.swing.JTable();
        guardaprov = new javax.swing.JButton();
        lbl_aviso1 = new javax.swing.JLabel();
        lbl_aviso2 = new javax.swing.JLabel();
        lbl_aviso3 = new javax.swing.JLabel();
        lbl_aviso4 = new javax.swing.JLabel();
        lbl_aviso5 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lbl_aviso16 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        lbl_aviso20 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        lbl_aviso21 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        lbl_aviso22 = new javax.swing.JLabel();
        tcod = new javax.swing.JTextField();
        tdesc = new javax.swing.JTextField();
        tprov = new javax.swing.JComboBox<>();
        tmat = new javax.swing.JComboBox<>();
        ttipo = new javax.swing.JComboBox<>();
        tpreciov = new javax.swing.JTextField();
        tprecioc = new javax.swing.JTextField();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        icod = new javax.swing.JTextField();
        italla = new javax.swing.JComboBox<>();
        jLabel45 = new javax.swing.JLabel();
        lbl_aviso25 = new javax.swing.JLabel();
        istock = new javax.swing.JTextField();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabProd1 = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabProd2 = new ColorCelda();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 255, 204));

        jPanel2.setBackground(new java.awt.Color(0, 153, 255));
        jPanel2.setForeground(new java.awt.Color(255, 153, 102));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Punto de venta");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_proveedor/proveedor_1.png"))); // NOI18N
        jButton1.setText("Proveedor");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_proveedor/Nventa.png"))); // NOI18N
        jButton4.setText("Nueva venta");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_proveedor/producto.png"))); // NOI18N
        jButton8.setText("Productos");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel1)
                .addGap(78, 78, 78)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setBackground(new java.awt.Color(0, 255, 204));
        jTabbedPane1.setForeground(new java.awt.Color(102, 0, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Código del producto");

        jLabel3.setText("Cantidad");

        jLabel4.setText("Precio");

        jLabel5.setForeground(new java.awt.Color(51, 51, 255));
        jLabel5.setText("Stock disponilble");

        txtcod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcodKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcodKeyTyped(evt);
            }
        });

        jLabel6.setText("$");

        jPanel4.setBackground(new java.awt.Color(237, 255, 246));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 204));
        jLabel8.setText("Total a pagar");

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 204));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_proveedor/GuardarTodo.png"))); // NOI18N
        jButton3.setText("Realiza venta");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 153));
        jLabel11.setText("Paga con:");

        jLabel12.setText("$");

        pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagoActionPerformed(evt);
            }
        });
        pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pagoKeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 204));
        jLabel13.setText("Cambio");

        jLabel15.setText("$");

        jLabel16.setText("$");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcambio, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(34, 34, 34)
                                .addComponent(jButton3))
                            .addComponent(pago, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(19, 19, 19)
                        .addComponent(jLabel11)
                        .addGap(8, 8, 8)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addGap(22, 22, 22)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addComponent(txtcambio, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        txtcant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcantKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantKeyTyped(evt);
            }
        });

        jLabel10.setText("Descripción");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/eliminaC.png"))); // NOI18N
        jButton2.setText("Eliminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtTalla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTallaActionPerformed(evt);
            }
        });
        txtTalla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTallaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTallaKeyTyped(evt);
            }
        });

        jLabel7.setText("    Talla");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Productos en el carro");

        jPanel6.setBackground(new java.awt.Color(237, 255, 246));

        tabventa.setBackground(new java.awt.Color(102, 102, 0));
        tabventa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Talla", "Cantidad", "Precio", "Total", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabventa.setGridColor(new java.awt.Color(153, 0, 153));
        tabventa.setName(""); // NOI18N
        tabventa.setSelectionBackground(new java.awt.Color(255, 102, 102));
        jScrollPane1.setViewportView(tabventa);
        if (tabventa.getColumnModel().getColumnCount() > 0) {
            tabventa.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabventa.getColumnModel().getColumn(1).setPreferredWidth(40);
            tabventa.getColumnModel().getColumn(2).setPreferredWidth(50);
            tabventa.getColumnModel().getColumn(3).setPreferredWidth(40);
            tabventa.getColumnModel().getColumn(4).setPreferredWidth(50);
        }
        tabventa.getAccessibleContext().setAccessibleName("");
        tabventa.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 204, 102));
        jLabel14.setText("Cuenta total");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(378, 378, 378)
                                        .addComponent(jLabel3))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtcod, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(38, 38, 38)
                                        .addComponent(txtTalla, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtcant, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(22, 22, 22))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(40, 40, 40)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(41, 41, 41)
                                        .addComponent(jLabel10)
                                        .addGap(232, 232, 232)
                                        .addComponent(jLabel4))
                                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(212, 212, 212)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(txtstock, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(148, 148, 148)
                            .addComponent(jButton2)
                            .addGap(59, 59, 59))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(304, 304, 304)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(jLabel14)))
                        .addGap(72, 72, 72))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTalla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtstock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(86, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Nueva venta", jPanel3);

        txtmail.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setText("Nombre:");

        jLabel18.setText("Razon social:");

        jLabel19.setText("Ciudad:");

        jLabel20.setText("Teléfono:");

        jLabel21.setText("E_mail:");

        txtrazon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtrazonKeyTyped(evt);
            }
        });

        txtnomprov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnomprovKeyTyped(evt);
            }
        });

        txtciudad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtciudadKeyTyped(evt);
            }
        });

        txttel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txttelKeyTyped(evt);
            }
        });

        txte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txteKeyReleased(evt);
            }
        });

        tabProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Nombre", "Teléfono", "E_mail"
            }
        ));
        tabProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabProveedorMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabProveedor);
        if (tabProveedor.getColumnModel().getColumnCount() > 0) {
            tabProveedor.getColumnModel().getColumn(0).setPreferredWidth(8);
            tabProveedor.getColumnModel().getColumn(1).setPreferredWidth(25);
            tabProveedor.getColumnModel().getColumn(2).setPreferredWidth(10);
            tabProveedor.getColumnModel().getColumn(3).setPreferredWidth(20);
            tabProveedor.getColumnModel().getColumn(3).setHeaderValue("Precio Venta");
        }

        guardaprov.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        guardaprov.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_proveedor/GuardarTodo.png"))); // NOI18N
        guardaprov.setText("Registrar");
        guardaprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardaprovActionPerformed(evt);
            }
        });

        lbl_aviso1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_aviso1.setForeground(new java.awt.Color(153, 153, 255));
        lbl_aviso1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_aviso1.setText("Campo obligatorio(*)");

        lbl_aviso2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_aviso2.setForeground(new java.awt.Color(153, 153, 255));
        lbl_aviso2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_aviso2.setText("Campo obligatorio(*)");

        lbl_aviso3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_aviso3.setForeground(new java.awt.Color(153, 153, 255));
        lbl_aviso3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_aviso3.setText("Campo obligatorio(*)");

        lbl_aviso4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_aviso4.setForeground(new java.awt.Color(153, 153, 255));
        lbl_aviso4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_aviso4.setText("Campo obligatorio(*)");

        lbl_aviso5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_aviso5.setForeground(new java.awt.Color(153, 153, 255));
        lbl_aviso5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_aviso5.setText("Email invalido(*)");

        txtid.setEditable(false);
        txtid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/componentes/icons8-x-16.png"))); // NOI18N
        jButton5.setText("Eliminar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_proveedor/Actualizar (2).png"))); // NOI18N
        jButton6.setText("Actualizar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton7.setText("Limpiar campos");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 204));
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-grupo-de-usuario-64.png"))); // NOI18N
        jLabel34.setText("Proveedores activos");

        javax.swing.GroupLayout txtmailLayout = new javax.swing.GroupLayout(txtmail);
        txtmail.setLayout(txtmailLayout);
        txtmailLayout.setHorizontalGroup(
            txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtmailLayout.createSequentialGroup()
                .addGap(0, 81, Short.MAX_VALUE)
                .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(txtmailLayout.createSequentialGroup()
                        .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_aviso1)
                            .addComponent(lbl_aviso2)
                            .addComponent(lbl_aviso3)
                            .addComponent(lbl_aviso4))
                        .addGap(52, 52, 52)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(txtmailLayout.createSequentialGroup()
                        .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(txtmailLayout.createSequentialGroup()
                                .addComponent(guardaprov)
                                .addGap(108, 108, 108)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(txtmailLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28))
                    .addGroup(txtmailLayout.createSequentialGroup()
                        .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_aviso5)
                            .addGroup(txtmailLayout.createSequentialGroup()
                                .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel19)
                                        .addComponent(jLabel17))
                                    .addGroup(txtmailLayout.createSequentialGroup()
                                        .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel21)
                                            .addComponent(jLabel20))
                                        .addGap(4, 4, 4)))
                                .addGap(18, 18, 18)
                                .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtrazon, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtnomprov, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtciudad, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txte, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(105, 105, 105)))
                .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtmailLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtmailLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel34)
                        .addGap(205, 205, 205))))
        );
        txtmailLayout.setVerticalGroup(
            txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtmailLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lbl_aviso1)
                .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtrazon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(32, 32, 32)
                .addComponent(lbl_aviso2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnomprov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(29, 29, 29)
                .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_aviso3)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtciudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(lbl_aviso4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(lbl_aviso5)
                .addGap(18, 18, 18)
                .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtmailLayout.createSequentialGroup()
                        .addComponent(txte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtmailLayout.createSequentialGroup()
                                .addGroup(txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(guardaprov)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(61, 61, 61))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton6)
                                .addComponent(jButton7))))
                    .addComponent(jLabel21))
                .addGap(42, 42, 42))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtmailLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel34)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );

        jTabbedPane1.addTab("Proveedores", txtmail);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(240, 255, 184));

        jTabbedPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane2MouseClicked(evt);
            }
        });

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel35.setText("Nuevo producto");

        jLabel36.setText("Código");

        jLabel37.setText("Descripción");

        jLabel38.setText("Proveedor");

        lbl_aviso16.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lbl_aviso16.setForeground(new java.awt.Color(153, 153, 255));
        lbl_aviso16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_aviso16.setText("Campo obligatorio(*)");

        jLabel39.setText("Material");

        lbl_aviso20.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lbl_aviso20.setForeground(new java.awt.Color(153, 153, 255));
        lbl_aviso20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_aviso20.setText("Campo obligatorio(*)");

        jLabel40.setText("Precio de compra");

        lbl_aviso21.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lbl_aviso21.setForeground(new java.awt.Color(153, 153, 255));
        lbl_aviso21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_aviso21.setText("Campo obligatorio(*)");

        jLabel41.setText("Precio de venta");

        jLabel42.setText("Tipo de calzado");

        lbl_aviso22.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lbl_aviso22.setForeground(new java.awt.Color(153, 153, 255));
        lbl_aviso22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_aviso22.setText("Campo obligatorio(*)");

        tcod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tcodKeyPressed(evt);
            }
        });

        tmat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmatActionPerformed(evt);
            }
        });

        jButton14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_proveedor/GuardarTodo.png"))); // NOI18N
        jButton14.setText("Dar de alta");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/componentes/icons8-x-16.png"))); // NOI18N
        jButton15.setText("Eliminar");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_proveedor/Actualizar (2).png"))); // NOI18N
        jButton16.setText("Actualizar");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton17.setText("Limpiar campos");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton22.setBackground(new java.awt.Color(153, 255, 153));
        jButton22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Reportes/excel.png"))); // NOI18N
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel42)
                .addGap(93, 93, 93))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addGap(125, 125, 125))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_aviso16)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tdesc, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tmat, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ttipo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_aviso21)
                                .addComponent(tpreciov, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 80, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lbl_aviso20)
                                .addComponent(tprov, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tcod, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tprecioc, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel40)
                                .addGap(81, 81, 81)
                                .addComponent(jLabel41))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton16)
                                    .addComponent(jButton14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton15)
                                    .addComponent(jButton17)))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(lbl_aviso22))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(jLabel37)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton22)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(jButton22))
                .addGap(18, 18, 18)
                .addComponent(lbl_aviso16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tcod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ttipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tmat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tprov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(lbl_aviso20))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(lbl_aviso21)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tprecioc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tpreciov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_aviso22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Producto", jPanel10);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel43.setText("Código");

        jLabel44.setText("Talla");

        icod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                icodMousePressed(evt);
            }
        });
        icod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                icodActionPerformed(evt);
            }
        });
        icod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                icodKeyPressed(evt);
            }
        });

        jLabel45.setText("Stock");

        lbl_aviso25.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lbl_aviso25.setForeground(new java.awt.Color(153, 153, 255));
        lbl_aviso25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_aviso25.setText("Campo obligatorio(*)");

        jButton18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_proveedor/GuardarTodo.png"))); // NOI18N
        jButton18.setText("Agregar");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/componentes/icons8-x-16.png"))); // NOI18N
        jButton19.setText("Eliminar");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_proveedor/Actualizar (2).png"))); // NOI18N
        jButton20.setText("Actualizar Stock");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton21.setText("Limpiar campos");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton23.setBackground(new java.awt.Color(153, 255, 153));
        jButton23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Reportes/excel.png"))); // NOI18N
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(161, 161, 161)
                                .addComponent(jLabel43))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(128, 128, 128)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(italla, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(icod, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_aviso25)
                                    .addComponent(istock, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(163, 163, 163)
                                .addComponent(jLabel44))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(163, 163, 163)
                                .addComponent(jLabel45))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 47, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton23)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton23)
                .addGap(27, 27, 27)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(icod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(italla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(lbl_aviso25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton18)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton20)
                    .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Inventario", jPanel12);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        jPanel9.setBackground(new java.awt.Color(240, 255, 184));

        tabProd1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción", "Proveedor", "Material", "Precio Compra", "Precio Venta", "Tipo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabProd1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabProd1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabProd1);
        if (tabProd1.getColumnModel().getColumnCount() > 0) {
            tabProd1.getColumnModel().getColumn(0).setPreferredWidth(8);
            tabProd1.getColumnModel().getColumn(1).setPreferredWidth(20);
            tabProd1.getColumnModel().getColumn(2).setPreferredWidth(25);
            tabProd1.getColumnModel().getColumn(3).setPreferredWidth(15);
            tabProd1.getColumnModel().getColumn(4).setPreferredWidth(25);
            tabProd1.getColumnModel().getColumn(5).setPreferredWidth(20);
            tabProd1.getColumnModel().getColumn(6).setPreferredWidth(10);
        }

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("", jPanel11);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        tabProd2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción", "Talla", "Proveedor", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabProd2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabProd2MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabProd2);
        if (tabProd2.getColumnModel().getColumnCount() > 0) {
            tabProd2.getColumnModel().getColumn(0).setPreferredWidth(8);
            tabProd2.getColumnModel().getColumn(1).setPreferredWidth(20);
            tabProd2.getColumnModel().getColumn(2).setPreferredWidth(10);
            tabProd2.getColumnModel().getColumn(3).setPreferredWidth(25);
            tabProd2.getColumnModel().getColumn(4).setPreferredWidth(15);
        }

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("", jPanel13);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jTabbedPane3))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(65, 65, 65))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Administración de productos", jPanel7);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      // limpiarTable();
       //listaProveedores();
      jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void guardaprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardaprovActionPerformed
        if(txtnomprov.getText().trim().isEmpty())
        {
            lbl_aviso2.setVisible(true);
        }
        else {lbl_aviso2.setVisible(false);}
        
        if(txtciudad.getText().trim().isEmpty())
        {
            lbl_aviso3.setVisible(true);
        }
        else {lbl_aviso3.setVisible(false);}
        
        if(txtrazon.getText().trim().isEmpty())
        {
            lbl_aviso1.setVisible(true);
        }
        else {lbl_aviso1.setVisible(false);}
        
        if(txttel.getText().trim().isEmpty())
        {
            lbl_aviso4.setVisible(true);
        }
        else {lbl_aviso4.setVisible(false);}
        if(txte.getText().trim().isEmpty())
        {
            lbl_aviso5.setVisible(true);
        }
        else {lbl_aviso5.setVisible(false);}
       
        if(txtnomprov.getText().trim().isEmpty() || txtciudad.getText().trim().isEmpty() || txtrazon.getText().trim().isEmpty()
                || txttel.getText().trim().isEmpty() || txte.getText().trim().isEmpty())
        {
            JOptionPane.showMessageDialog(this,"Rellene los o el campo faltante", "Error",JOptionPane.WARNING_MESSAGE);
            
        }
        else{
            if(verificamail(txte.getText()))
            {
            aprov= new ArrayList<Object[]>();
             acceso = new AccesoDatos();
             String consulta="SELECT * FROM danny_zapateria.proveedor";
             aprov= (ArrayList<Object[]>)acceso.getProveedores(consulta);
             
            int id=(Integer)aprov.get(aprov.size()-1)[0]+1;
            prov.setId(id);
            prov.setCiudad(txtciudad.getText());
            prov.setTelefono(txttel.getText());
            prov.setRazon(txtrazon.getText());
            prov.setE_mail(txte.getText());
            prov.setNombre(txtnomprov.getText());
            provdao.regitrarProveedor(prov);
            limpiarTable();
            listaProveedores();
            limpiarProveedores();
            }
            else
            { JOptionPane.showMessageDialog(this,"Ingresé un correo valido", "Error",JOptionPane.WARNING_MESSAGE);
            lbl_aviso5.setVisible(true);
            }
        }
    }//GEN-LAST:event_guardaprovActionPerformed

    private void txteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txteKeyReleased
      if(verificamail(txte.getText()))
      {
          lbl_aviso5.setVisible(false);
      }
      else {
           lbl_aviso5.setVisible(true);
      }
    }//GEN-LAST:event_txteKeyReleased

    private void txtrazonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrazonKeyTyped
        // TODO add your handling code here:
      
    }//GEN-LAST:event_txtrazonKeyTyped

    private void txttelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelKeyTyped
        Character c=evt.getKeyChar();
        if(!Character.isDigit(c))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txttelKeyTyped

    private void txtnomprovKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnomprovKeyTyped
        Character c=evt.getKeyChar();
        if(!Character.isLetter(c) && c!=KeyEvent.VK_SPACE)
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtnomprovKeyTyped

    private void txtciudadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtciudadKeyTyped
        Character c=evt.getKeyChar();
        if(!Character.isLetter(c) && c!=KeyEvent.VK_SPACE)
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtciudadKeyTyped

    private void tabProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabProveedorMouseClicked
       int fila=tabProveedor.rowAtPoint(evt.getPoint());
       txtid.setText(tabProveedor.getValueAt(fila, 0).toString());
       int id=Integer.parseInt(txtid.getText());
       prov=provdao.bucarProv(id);
       txtrazon.setText(prov.getRazon());
       txtciudad.setText(prov.getCiudad());
       txte.setText(prov.getE_mail());
       txtnomprov.setText(prov.getNombre());
       txttel.setText(prov.getTelefono());
    }//GEN-LAST:event_tabProveedorMouseClicked

    private void txtidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if(!"".equals(txtid.getText()))
        {
           int pregunta= JOptionPane.showConfirmDialog(null,"Esta seguro de elimnar" );
           if( pregunta==0)
             {
                int id=Integer.parseInt(txtid.getText());
                provdao.eliminaProv(id);
                limpiarTable();
                
                listaProveedores();
                limpiarProveedores();
             }
        }
        else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
      if("".equals(txtid.getText()))
      {
          JOptionPane.showMessageDialog(null, "Seleccione una fila");
      }
      else{
        if(txtnomprov.getText().trim().isEmpty())
        {
            lbl_aviso2.setVisible(true);
        }
        else {lbl_aviso2.setVisible(false);}
        
        if(txtciudad.getText().trim().isEmpty())
        {
            lbl_aviso3.setVisible(true);
        }
        else {lbl_aviso3.setVisible(false);}
        
        if(txtrazon.getText().trim().isEmpty())
        {
            lbl_aviso1.setVisible(true);
        }
        else {lbl_aviso1.setVisible(false);}
        
        if(txttel.getText().trim().isEmpty())
        {
            lbl_aviso4.setVisible(true);
        }
        else {lbl_aviso4.setVisible(false);}
        if(txte.getText().trim().isEmpty())
        {
            lbl_aviso5.setVisible(true);
        }
        else {lbl_aviso5.setVisible(false);}
       
        if(txtnomprov.getText().trim().isEmpty() || txtciudad.getText().trim().isEmpty() || txtrazon.getText().trim().isEmpty()
                || txttel.getText().trim().isEmpty() || txte.getText().trim().isEmpty())
        {
            JOptionPane.showMessageDialog(this,"Rellene los o el campo faltante", "Error",JOptionPane.WARNING_MESSAGE);
            
        }
        else
        {
           prov.setId(Integer.parseInt(txtid.getText()));
           prov.setCiudad(txtciudad.getText());
           prov.setE_mail(txte.getText());
           prov.setNombre(txtnomprov.getText());
           prov.setRazon(txtrazon.getText());
           prov.setTelefono(txttel.getText());
           provdao.actualizarProveedor(prov);
           limpiarTable();
           listaProveedores();
           limpiarProveedores();
        }
      }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        limpiarProveedores();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
         jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void txtTallaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTallaKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER)
        {
            if(!"".equals(txtTalla.getText()))
            {
                int codpro=Integer.parseInt(txtcod.getText());
                double talla=Double.parseDouble(txtTalla.getText());
                //zapato=zapdao.bucarZapato(codpro);
                zapato=zapdao.buscaExtras(codpro, talla);

                if(zapato.getCod() != 0)
                {
                    txtdesc.setText(""+zapato.getDesc());
                    txtprecio.setText(""+zapato.getPrecioV());
                    txtstock.setText(""+zapato.getStock());
                    txtcant.requestFocus();

                }
                else
                {
                    JOptionPane.showMessageDialog(null, "No contamos con la talla, por favor elija una disponible");
                    // limpiarventa();
                    txtTalla.setText("");
                    txtTalla.requestFocus();
                }
            }
            else JOptionPane.showMessageDialog(null,"Ingrese la talla del producto");

        }
    }//GEN-LAST:event_txtTallaKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      
        modelo=(DefaultTableModel) tabventa.getModel();
        modelo.removeRow(tabventa.getSelectedRow());
        totalpagar();
        cambio();
        txtcod.requestFocus();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtcantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantKeyTyped
        Character c=evt.getKeyChar();
        if(!Character.isDigit(c) || c<'1')
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtcantKeyTyped

    private void txtcantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
            if(!"".equals(txtcant.getText()))
            {
                int cod=Integer.parseInt(txtcod.getText());
                String desc=txtdesc.getText();
                int cant=Integer.parseInt(txtcant.getText());
                double precio=Double.parseDouble(txtprecio.getText());
                double total=cant*precio;
                int stock=Integer.parseInt(txtstock.getText());
                String talla=txtTalla.getText();
                if(stock>=cant)
                {
                    item=item+1;
                    modelo=(DefaultTableModel)tabventa.getModel();
                    for(int i=0;i<tabventa.getRowCount();i++)
                    {
                        if(tabventa.getValueAt(i, 1).equals(txtTalla.getText()) && tabventa.getValueAt(i, 5).equals(txtdesc.getText()))
                        {
                            JOptionPane.showMessageDialog(null, "El calzado en la talla "+txtTalla.getText()+ " ya se encuentra almacenado en al carrito");
                            return;
                        }
                    }
                    venta= new ArrayList();
                    venta.add(item);
                    venta.add(cod);
                    venta.add(talla);
                    venta.add(cant);
                    venta.add(precio);
                    venta.add(total);
                    venta.add(desc);
                    campos = new Object[6];
                    campos[0]=venta.get(1);
                    campos[1]=venta.get(2);
                    campos[2]=venta.get(3);
                    campos[3]=venta.get(4);
                    campos[4]=venta.get(5);
                    campos[5]=venta.get(6);
                    modelo.addRow(campos);
                    tabventa.setModel(modelo);
                    totalpagar();
                    limpiarventa();
                    txtcod.requestFocus();
                }
                else {JOptionPane.showMessageDialog(null, "Stock no disponible");}
            }
            else {JOptionPane.showMessageDialog(null, "Ingrese una cantidad");}
        }
      
    }//GEN-LAST:event_txtcantKeyPressed

    private void pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pagoKeyReleased
        try{
            cambio();
        }catch(Exception e){}
    }//GEN-LAST:event_pagoKeyReleased

    private void pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pagoActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       if(tabventa.getRowCount()==0) 
       {
          JOptionPane.showMessageDialog(null, "No tienes productos en el carrito de ventas");
        }
        else{
           if(pago.getText().trim().isEmpty()){
               JOptionPane.showMessageDialog(null, "Ingresa el monto del pago a recibir");
        }
           else{
         registrarVenta();
        actualizarStock();
        pdf();
        limpiarVenta();
//        listaProductos();
//        limpiarProductos();
        limpiarTablaZapatos();
        mostrarZapatos();
        limpiameLosZapatos();
        limpiarTablaInventarios();
        mostrarInventarios();
        limpiarInventario();
           }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtcodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodKeyTyped
        Character c=evt.getKeyChar();
        if(!Character.isDigit(c))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtcodKeyTyped

    private void txtcodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()== KeyEvent.VK_ENTER)
        {
            if(!"".equals(txtcod.getText()))
            {
                int codpro=Integer.parseInt(txtcod.getText());
                zapato=zapdao.bucarZapato(codpro);

                if(zapato.getCod() != 0)
                {
                    autocompletaTalla(codpro);
                    txtTalla.requestFocus();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "El producto no existe");
                    limpiarventa();
                    txtcod.requestFocus();
                }
            }
            else JOptionPane.showMessageDialog(null,"Ingrese el codigo del producto");

        }
      
    }//GEN-LAST:event_txtcodKeyPressed

    private void txtTallaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTallaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTallaKeyTyped

    private void tabProd1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabProd1MouseClicked
        int fila=tabProd1.rowAtPoint(evt.getPoint());
      
     tcod.setText(tabProd1.getValueAt(fila, 0).toString());
     
      tdesc.setText(tabProd1.getValueAt(fila, 6).toString());
     
       int id=Integer.parseInt(tcod.getText());
      
       zapato=zapdao.bucarZapato(id);
         tdesc.setEnabled(true);
         tprecioc.setEnabled(true);
         tpreciov.setEnabled(true);
         tmat.setEnabled(true);
         tprov.setEnabled(true);
         ttipo.setEnabled(true);
       
       tdesc.setText(zapato.getDesc());
       zapdao.consultaMaterial(tmat);
       tmat.setSelectedItem(zapato.getMaterial());
       tpdao.consultaTipo(ttipo);
       ttipo.setSelectedItem(zapato.getMaterial());
       provdao.consultaProveedor(tprov);
       tprov.setSelectedItem(tabProd1.getValueAt(fila, 2).toString());
       //tprov.setSelectedItem(zapa);
       tprecioc.setText(String.valueOf(zapato.getPrecioC()));
       tpreciov.setText(String.valueOf(zapato.getPrecioV()));
    }//GEN-LAST:event_tabProd1MouseClicked

    private void tmatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tmatActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
    if(!"".equals(tcod.getText()))
            {
                if(tcod.getText().trim().isEmpty())
                      {
                          lbl_aviso16.setVisible(true);
                       }
                      else {lbl_aviso16.setVisible(false);}
        
                      if(tprecioc.getText().trim().isEmpty())
                      {
                       lbl_aviso20.setVisible(true);
                      }
                      else {lbl_aviso20.setVisible(false);}
                      if(tpreciov.getText().trim().isEmpty())
                      {
                       lbl_aviso21.setVisible(true);
                      }
                      else {lbl_aviso21.setVisible(false);}
                      if(tdesc.getText().trim().isEmpty())
                      {
                       lbl_aviso22.setVisible(true);
                      }
                      else {lbl_aviso22.setVisible(false);}
              if(tcod.getText().trim().isEmpty() || tdesc.getText().trim().isEmpty() ||  tprecioc.getText().trim().isEmpty()
                        || tpreciov.getText().trim().isEmpty())
                     {
                          JOptionPane.showMessageDialog(this,"Rellene los o el campo faltante", "Error",JOptionPane.WARNING_MESSAGE);
            
                       }
                    else{
                
                //si es q exite saca los datos
                int codpro=Integer.parseInt(tcod.getText());
                String tip=ttipo.getSelectedItem().toString();
                int ttip=tpdao.regresaTipo(tip);
                String nprov=tprov.getSelectedItem().toString();
                int tnprov=provdao.regresaProveedor(nprov);
                String nmat=tmat.getSelectedItem().toString();
                double preciov=Double.parseDouble(tpreciov.getText());
                double precioc=Double.parseDouble(tprecioc.getText());
                String des=tdesc.getText();
                
                zapato=zapdao.bucarZapatoEnBase(codpro);
                boolean exis=false;
                if(zapato.getCod() != 0)
                {
                    int pregunta= JOptionPane.showConfirmDialog(null,"El producto ya se encuentra registrado \n\n¿Desea que se actualizen los campos ingresados?","Advertencia",JOptionPane.YES_NO_OPTION);
                    if( pregunta==0)
                    { 
                        try{
        
                          PreparedStatement pps = cn.prepareStatement("UPDATE zapato SET descripcion=?,prov=?,material=?, precio_C=?, precio_V=?, tipo=? WHERE codigo=?");
            
                          pps.setString(1, tdesc.getText());
                          pps.setInt(2, tnprov);
                          pps.setString(3, nmat);
                          pps.setDouble(4, precioc);
                          pps.setDouble(5, preciov);
                          pps.setInt(6, ttip);
                          pps.setInt(7, codpro);
                          int res=pps.executeUpdate();
                          if (res>0){
                          limpiarTablaZapatos();
                          mostrarZapatos();
                          limpiameLosZapatos();
                          limpiarTablaInventarios();
                          mostrarInventarios();
                          limpiarInventario();
                         
                         JOptionPane.showMessageDialog(null, "Se ha actualizado el Producto");
   
           
                         }
                         else JOptionPane.showMessageDialog(null, "Los datos no han sido actualizados");
          
                            
                            } catch (SQLException ex) {
                           // JOptionPane.showMessageDialog(null, "Error al actualizar");
                             }
                        
            
                    }
                    else
                    {
                     icod.requestFocus();//si no quiere actualizar el stock
                    }
                    
                }
                else
                {
                     //int id_in=zapato.getId_inv();
                    // JOptionPane.showMessageDialog(null,"inserta");
                     try{
                          PreparedStatement pps = cn.prepareStatement("INSERT INTO zapato(codigo,descripcion,prov,material,precio_C,precio_V,tipo) VALUES(?,?,?,?,?,?,?)");
                          pps.setInt(1, codpro);
                          pps.setString(2, tdesc.getText());
                          pps.setInt(3, tnprov);
                          pps.setString(4, nmat);
                          pps.setDouble(5, precioc);
                          pps.setDouble(6, preciov);
                          pps.setInt(7, ttip);
                          pps.executeUpdate();
                          modpro.fireTableDataChanged();
                          limpiarTablaZapatos();
                          mostrarZapatos();
                          limpiameLosZapatos();
                          limpiarTablaInventarios();
                          mostrarInventarios();
                          limpiarInventario();
                          JOptionPane.showMessageDialog(null, "Producto dado de alta");
                         } catch (SQLException ex) {
                             //JOptionPane.showMessageDialog(null, "Producto no dado de alta ");
                         }
                     icod.requestFocus();
                }
                }//fin empty
           }
            else JOptionPane.showMessageDialog(null,"Ingrese el codigo del producto"); 
        
        
    }//GEN-LAST:event_jButton14ActionPerformed

    private void tcodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tcodKeyPressed
     if(evt.getKeyCode()== KeyEvent.VK_ENTER)
        {
            if(!"".equals(tcod.getText()))
            {
                int codpro=Integer.parseInt(tcod.getText());
               zapato=zapdao.bucarZapato(codpro);
                 
                  if(zapato.getCod() != 0)
                  {
                     JOptionPane.showMessageDialog(null, "El producto con este código ya está dado de alta");
                      tcod.setText("");
                      tcod.requestFocus();
                    
                  }
                  else
                  {
                       tmat.setEnabled(true);
                      AutoCompleteDecorator.decorate(tmat);
                      zapdao.consultaMaterial(tmat);
                      //tmat.requestFocus();
                      ttipo.setEnabled(true);
                      AutoCompleteDecorator.decorate(ttipo);
                      tpdao.consultaTipo(ttipo);
                      tdesc.setEnabled(true);
                      tprecioc.setEnabled(true);
                      tpreciov.setEnabled(true);
                      tprov.setEnabled(true);
                      AutoCompleteDecorator.decorate(tprov);
                      provdao.consultaProveedor(tprov);
                   }
            }
            else JOptionPane.showMessageDialog(null,"Ingrese el codigo del producto");
          
        }   
      
    }//GEN-LAST:event_tcodKeyPressed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
       if(!"".equals(tcod.getText()))
        {
            if(!"".equals(tdesc.getText()))
            {
           int pregunta= JOptionPane.showConfirmDialog(null,"Esta seguro de elimnar del productos" );
           if( pregunta==0)
             {
                int id=Integer.parseInt(tcod.getText());
                String des=tdesc.getText();
                zapato=zapdao.bucarZapato(id);
                zapdao.eliminaZapato(zapato.getCod(), zapato.getDesc());
               
                         limpiarTablaZapatos();
                          mostrarZapatos();
                          limpiameLosZapatos();
                          limpiarTablaInventarios();
                          mostrarInventarios();
                          limpiarInventario();
               tdesc.setEnabled(false);
         tprecioc.setEnabled(false);
         tpreciov.setEnabled(false);
         tmat.setEnabled(false);
         tprov.setEnabled(false);
         ttipo.setEnabled(false);
         tcod.requestFocus();
             }
            }
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
        else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
       if("".equals(tcod.getText()))
      {
          JOptionPane.showMessageDialog(null, "Seleccione una fila");
      }
      else{
     if(!"".equals(tcod.getText()))
            {
              //  int codpro=Integer.parseInt(tcod.getText());
               //   zapato=zapdao.bucarZapato(codpro);
                      if(tcod.getText().trim().isEmpty())
                      {
                          lbl_aviso16.setVisible(true);
                       }
                      else {lbl_aviso16.setVisible(false);}
        
                      if(tprecioc.getText().trim().isEmpty())
                      {
                       lbl_aviso20.setVisible(true);
                      }
                      else {lbl_aviso20.setVisible(false);}
                      if(tpreciov.getText().trim().isEmpty())
                      {
                       lbl_aviso21.setVisible(true);
                      }
                      else {lbl_aviso21.setVisible(false);}
                      if(tdesc.getText().trim().isEmpty())
                      {
                       lbl_aviso22.setVisible(true);
                      }
                      else {lbl_aviso22.setVisible(false);}
                     if(tcod.getText().trim().isEmpty() || tdesc.getText().trim().isEmpty() ||  tprecioc.getText().trim().isEmpty()
                        || tpreciov.getText().trim().isEmpty())
                     {
                          JOptionPane.showMessageDialog(this,"Rellene los o el campo faltante", "Error",JOptionPane.WARNING_MESSAGE);
            
                       }
                     else{
            
            int idzap=Integer.parseInt(tcod.getText());
            String des=tdesc.getText();
            String nprov=(String)tprov.getSelectedItem();
            String mate=(String)tmat.getSelectedItem();
            double pC=Double.parseDouble(tprecioc.getText());
            double pV=Double.parseDouble(tpreciov.getText());
            String tipZap= (String) ttipo.getSelectedItem();
            zapato.setCod(idzap);
            zapato.setDesc(des);
            zapato.setId_prov(provdao.regresaProveedor(nprov));
            zapato.setMaterial(mate);
            zapato.setPrecioC(pC);
            zapato.setPrecioV(pV);
            zapato.setTipo(tpdao.regresaTipo(tipZap));
            //aca lo demas
            
            zapdao.actualizaZapato(zapato);
                          limpiarTablaZapatos();
                          mostrarZapatos();
                          limpiameLosZapatos();
                          limpiarTablaInventarios();
                          mostrarInventarios();
                          limpiarInventario();
            tcod.requestFocus();
            
           // System.out.print(provdao.regresaProveedor(nprov));
            
            
        }
                      
                  
            }
            else JOptionPane.showMessageDialog(null,"Ingrese el codigo del producto");
      }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
  
                          limpiameLosZapatos();
                          tcod.requestFocus();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void icodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_icodActionPerformed
         
    }//GEN-LAST:event_icodActionPerformed

    private void jTabbedPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane2MouseClicked
        int sindex=jTabbedPane2.getSelectedIndex();
        if(sindex==0)
        {
         jTabbedPane3.setSelectedIndex(0);   
        }
        else if(sindex==1)
        {
            jTabbedPane3.setSelectedIndex(1);  
        }
    }//GEN-LAST:event_jTabbedPane2MouseClicked

    private void tabProd2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabProd2MouseClicked
     int fila=tabProd2.rowAtPoint(evt.getPoint());
      
     icod.setText(tabProd2.getValueAt(fila, 0).toString());
     AutoCompleteDecorator.decorate(italla);
     talladao.consulatTallas(italla);
     
       italla.setSelectedItem(tabProd2.getValueAt(fila, 2).toString());
       int id=Integer.parseInt(icod.getText());
       double talla=Double.parseDouble(italla.getSelectedItem().toString());
      
       zapato=inv.bucarZapatoinv(id,talla);
         icod.setEnabled(true);
         istock.setEnabled(true);
         italla.setEnabled(true);
       
       //talladao.consulatTallas(italla);
       italla.setSelectedItem(zapato.getTalla());
       istock.setText(String.valueOf(zapato.getStock()));
    }//GEN-LAST:event_tabProd2MouseClicked

    private void icodMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icodMousePressed
       
       
    }//GEN-LAST:event_icodMousePressed

    private void icodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_icodKeyPressed
      if(evt.getKeyCode()== KeyEvent.VK_ENTER)
        {
            if(!"".equals(icod.getText()))
            {
                int codpro=Integer.parseInt(icod.getText());
               
                   

                      italla.setEnabled(true);
                      istock.setEnabled(true);
                      italla.requestFocus();
                      AutoCompleteDecorator.decorate(italla);
                      talladao.consulatTallas(italla);
                  
            }
            else JOptionPane.showMessageDialog(null,"Ingrese el codigo del producto");
          
        }
    }//GEN-LAST:event_icodKeyPressed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
         if(!"".equals(icod.getText()))
            {
                if(istock.getText().trim().isEmpty())
                      {
                       lbl_aviso25.setVisible(true);
                      }
                 else {lbl_aviso25.setVisible(false);}
                if(icod.getText().trim().isEmpty() || istock.getText().trim().isEmpty() )
                     {
                          JOptionPane.showMessageDialog(this,"Rellene los o el campo faltante", "Error",JOptionPane.WARNING_MESSAGE);
            
                     }
                else{
                
                //si es q exite saca los datos
                int codpro=Integer.parseInt(icod.getText());
                double talla=Double.parseDouble(italla.getSelectedItem().toString());
                zapato=inv.bucarZapatoinv(codpro, talla);
                boolean exis=false;
                if(zapato.getId_inv() != 0)
                {
                    int pregunta= JOptionPane.showConfirmDialog(null,"El producto ya se encuentra inventariado \n\n¿Desea solo actualizar el STOCK?","Advertencia",JOptionPane.YES_NO_OPTION);
                    if( pregunta==0)
                    { 
                        try{
                            codpro=Integer.parseInt(icod.getText());
                            talla=Double.parseDouble(italla.getSelectedItem().toString());
                            int stock=Integer.parseInt(istock.getText());
                            int id_in=zapato.getId_inv();
                          PreparedStatement pps = cn.prepareStatement("update danny_zapateria.inventario set stock=? where id=?");
            
                          pps.setInt(1, stock);
                          pps.setInt(2, id_in);
                          int res=pps.executeUpdate();
                          if (res>0){
                          limpiarTablaZapatos();
                          mostrarZapatos();
                          limpiameLosZapatos();
                          limpiarTablaInventarios();
                          mostrarInventarios();
                          limpiarInventario();
                          limpiaCamposdeVenta();
                         JOptionPane.showMessageDialog(null, "Se ha actualizado el stock");
   
           
                         }
                         else JOptionPane.showMessageDialog(null, "Los datos no han sido actualizados");
          
                            
                            } catch (SQLException ex) {
                           }
                        
            
                    }
                    else
                    {
                     icod.requestFocus();//si no quiere actualizar el stock
                    }
                    
                }
                else
                {
                    pinventarioID= new ArrayList<Object[]>();
                    acceso = new AccesoDatos();
                    String sql="select i.id,z.codigo,i.id_talla,i.stock from inventario i JOIN zapato z ON i.codigo=z.codigo JOIN talla t ON t.id_talla=i.id_talla JOIN proveedor p ON p.id_p=z.prov JOIN tipos tp ON tp.id_tipos=z.tipo";
                    pinventarioID= (ArrayList<Object[]>)acceso.getInvenParaID(sql);
                     int id=(Integer)pinventarioID.get(pinventarioID.size()-1)[0]+1;
                     codpro=Integer.parseInt(icod.getText());
                     talla=Double.parseDouble(italla.getSelectedItem().toString());
                     int id_talla=talladao.regresaTalla(talla);
                     int stock=Integer.parseInt(istock.getText());
                     //int id_in=zapato.getId_inv();
                     JOptionPane.showMessageDialog(null,"inserta");
                     try{
                          PreparedStatement pps = cn.prepareStatement("INSERT INTO inventario(id,codigo,id_talla,stock) VALUES(?,?,?,?)");
                          pps.setInt(1, id);
                          pps.setInt(2, codpro); 
                          pps.setInt(3, id_talla);
                          pps.setInt(4, stock);
                          pps.executeUpdate();
                          mod.fireTableDataChanged();
                          limpiarTablaZapatos();
                          mostrarZapatos();
                          limpiameLosZapatos();
                          limpiarTablaInventarios();
                          mostrarInventarios();
                          limpiarInventario();
                          limpiaCamposdeVenta();
                          JOptionPane.showMessageDialog(null, "Producto dado de alta exitosamente");
                         } catch (SQLException ex) {
                             //JOptionPane.showMessageDialog(null, "Producto no dado de alta ");
                        }
                     icod.requestFocus();
                }
                }//fin empty
           }
            else JOptionPane.showMessageDialog(null,"Ingrese el codigo del producto");
        
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
       if("".equals(icod.getText()))
      {
          JOptionPane.showMessageDialog(null, "Seleccione una fila");
      }
      else{
           if(!"".equals(icod.getText()))
            {
                int codpro=Integer.parseInt(icod.getText());
                zapato=zapdao.bucarZapato(codpro);
                 
                  if(zapato.getCod() != 0)
                  {
                      if(istock.getText().trim().isEmpty())
                      {
                       lbl_aviso25.setVisible(true);
                      }
                       else {lbl_aviso25.setVisible(false);}
                       //empieaza las condiciones
                       if(icod.getText().trim().isEmpty() || istock.getText().trim().isEmpty() )
                        {
                          JOptionPane.showMessageDialog(this,"Rellene los o el campo faltante", "Error",JOptionPane.WARNING_MESSAGE);
            
                         }
                         else{
                           //empieza a actualizar
                           //si es q exite saca los datos
                     codpro=Integer.parseInt(icod.getText());
                     double talla=Double.parseDouble(italla.getSelectedItem().toString());
                     zapato=inv.bucarZapatoinv(codpro, talla);
                     boolean exis=false;
                if(zapato.getId_inv() != 0)
                {
//                    int pregunta= JOptionPane.showConfirmDialog(null,"El producto ya se encuntra inventariado \n\n¿Desea solo actualizar el STOCK?","Advertencia",JOptionPane.YES_NO_OPTION);
//                    if( pregunta==0)
//                    { 
                        try{
                            codpro=Integer.parseInt(icod.getText());
                            talla=Double.parseDouble(italla.getSelectedItem().toString());
                            int stock=Integer.parseInt(istock.getText());
                            int id_in=zapato.getId_inv();
                          PreparedStatement pps = cn.prepareStatement("update danny_zapateria.inventario set stock=? where id=?");
            
                          pps.setInt(1, stock);
                          pps.setInt(2, id_in);
                          int res=pps.executeUpdate();
                          if (res>0){
                          limpiarTablaInventarios();
                          mostrarInventarios();
                          limpiarInventario();
                          limpiaCamposdeVenta();
                         JOptionPane.showMessageDialog(null, "Se ha actualizado el stock");
   
           
                         }
                         else JOptionPane.showMessageDialog(null, "Los datos no han sido actualizados");
          
                            
                            } catch (SQLException ex) {
                           JOptionPane.showMessageDialog(null, "Error al actualizar");
                             }
                        
            
//                    }
//                    else
//                    {
//                     icod.requestFocus();//si no quiere actualizar el stock
//                    }
                    
                }
                else
                {
                   JOptionPane.showConfirmDialog(null,"El producto no se encuentra inventariado \n\n","Advertencia",JOptionPane.CANCEL_OPTION);
                    
                    
                }
                          }//empieza actualizar
                  
                     
                    
                  }
                  else
                  {
                       JOptionPane.showMessageDialog(null, "El producto con este código no está dado de alta");
                       italla.setEnabled(false);
                       istock.setEnabled(false);
                       icod.setText("");
                       icod.requestFocus();
                   }
           }
            else JOptionPane.showMessageDialog(null,"Ingrese el codigo del producto");
      }
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        if("".equals(icod.getText()))
      {
          JOptionPane.showMessageDialog(null, "Seleccione una fila");
      }
      else{
           if(!"".equals(icod.getText()))
            {
                int codpro=Integer.parseInt(icod.getText());
                zapato=zapdao.bucarZapato(codpro);
                 
                  if(zapato.getCod() != 0)
                  {
                      if(istock.getText().trim().isEmpty())
                      {
                       lbl_aviso25.setVisible(true);
                      }
                       else {lbl_aviso25.setVisible(false);}
                       //empieaza las condiciones
                       if(icod.getText().trim().isEmpty() || istock.getText().trim().isEmpty() )
                        {
                          JOptionPane.showMessageDialog(this,"Rellene los o el campo faltante", "Error",JOptionPane.WARNING_MESSAGE);
            
                         }
                         else{
                           //empieza a actualizar
                           //si es q exite saca los datos
                     codpro=Integer.parseInt(icod.getText());
                     double talla=Double.parseDouble(italla.getSelectedItem().toString());
                     zapato=inv.bucarZapatoinv(codpro, talla);
                     boolean exis=false;
                if(zapato.getId_inv() != 0)
                {
                    //elimina
                    if(!"".equals(zapato.getId_inv()))
        {
           int pregunta= JOptionPane.showConfirmDialog(null,"Esta seguro de elimnar" );
           if( pregunta==0)
             {//inicio elimna
                 zapato=inv.bucarZapatoinv(codpro, talla);
                 try{
                     int idinv=zapato.getId_inv();
           PreparedStatement pps = cn.prepareStatement("delete from inventario where id=? ");
             pps.setInt(1, idinv);
          int res=pps.executeUpdate();
          if (res>0){
          limpiarTablaInventarios();
          mostrarInventarios();
          limpiarInventario();
          limpiaCamposdeVenta();
            JOptionPane.showMessageDialog(null, "Los datos han sido borrados exitosamente");
   
           
          }
          else JOptionPane.showMessageDialog(null, "Los datos no han sido borrados");
          } catch (SQLException ex) {
        }
                 
             }//fin elimina
           else{icod.requestFocus();}
        }
        else {
            
              }
                    
                }
                else
                {
                   JOptionPane.showConfirmDialog(null,"El producto no se encuentra inventariado \n\n","Advertencia",JOptionPane.CANCEL_OPTION);
                    
                    
                }
                          }//empieza actualizar
                  
                                        
                  }
                  else
                  {
                       JOptionPane.showMessageDialog(null, "El producto con este código no está dado de alta");
                       italla.setEnabled(false);
                       istock.setEnabled(false);
                       icod.setText("");
                       icod.requestFocus();
                   }
              
           }
            else JOptionPane.showMessageDialog(null,"Ingrese el codigo del producto");
      }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void txtTallaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTallaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTallaActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
 
                          limpiarInventario();
                          icod.requestFocus();
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
       Excel.reporte();
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        Excel.reporteInventario();
    }//GEN-LAST:event_jButton23ActionPerformed
    public void limpiarTablaInventarios()
    {
        for(int i=0;i<mod.getRowCount();i++)
        {
            mod.removeRow(i);
            i=i-1;
        }
    }
    public void limpiarTablaZapatos()
    {
        for(int i=0;i<modpro.getRowCount();i++)
        {
            modpro.removeRow(i);
            i=i-1;
        }
    }
    public void limpiarTable1()
    {
        for(int i=0;i<pro.getRowCount();i++)
        {
            pro.removeRow(i);
            i=i-1;
        }
    }
    public void limpiarTable()
    {
        for(int i=0;i<pmodelo.getRowCount();i++)
        {
            pmodelo.removeRow(i);
            i=i-1;
        }
    }
    public boolean verificamail(String correo)
    {
        Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mat=patron.matcher(correo);
        return mat.find();
   }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PuntoDeVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PuntoDeVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PuntoDeVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PuntoDeVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PuntoDeVenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton guardaprov;
    private javax.swing.JTextField icod;
    private javax.swing.JTextField istock;
    private javax.swing.JComboBox<String> italla;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel lbl_aviso1;
    private javax.swing.JLabel lbl_aviso16;
    private javax.swing.JLabel lbl_aviso2;
    private javax.swing.JLabel lbl_aviso20;
    private javax.swing.JLabel lbl_aviso21;
    private javax.swing.JLabel lbl_aviso22;
    private javax.swing.JLabel lbl_aviso25;
    private javax.swing.JLabel lbl_aviso3;
    private javax.swing.JLabel lbl_aviso4;
    private javax.swing.JLabel lbl_aviso5;
    private javax.swing.JTextField pago;
    private javax.swing.JTable tabProd1;
    private javax.swing.JTable tabProd2;
    private javax.swing.JTable tabProveedor;
    private javax.swing.JTable tabventa;
    private javax.swing.JTextField tcod;
    private javax.swing.JTextField tdesc;
    private javax.swing.JComboBox<String> tmat;
    private javax.swing.JTextField tprecioc;
    private javax.swing.JTextField tpreciov;
    private javax.swing.JComboBox<String> tprov;
    private javax.swing.JComboBox<String> ttipo;
    private javax.swing.JTextField txtTalla;
    private javax.swing.JLabel txtcambio;
    private javax.swing.JTextField txtcant;
    private javax.swing.JTextField txtciudad;
    private javax.swing.JTextField txtcod;
    private javax.swing.JTextField txtdesc;
    private javax.swing.JTextField txte;
    private javax.swing.JTextField txtid;
    private javax.swing.JPanel txtmail;
    private javax.swing.JTextField txtnomprov;
    private javax.swing.JLabel txtotal;
    private javax.swing.JTextField txtprecio;
    private javax.swing.JTextField txtrazon;
    private javax.swing.JTextField txtstock;
    private javax.swing.JTextField txttel;
    // End of variables declaration//GEN-END:variables
class fondo1 extends JPanel
{
    private Image imagen;
    public void paint (Graphics g)
    {
       // imagen = new ImageIcon(getClass().getResource("fondo(1.1).jpg")).getImage();
        //g.drawImage(imagen, 0 , 0, getWidth(), getHeight(), this);
        //setOpaque(false);
        //super.paint(g);
    }
    
}
private double totalpagar()
{
    totalpago=0.00;
    int  fila=tabventa.getRowCount();
    for(int i=0; i<fila;i++)
    {
        double cal=Double.parseDouble(String.valueOf(tabventa.getModel().getValueAt(i, 4)));
        totalpago=totalpago+cal;
    }
    txtotal.setText(String.format("%.2f", totalpago));
    return totalpago;
}
private void cambio()
{
    vuelto=0.00;
    double pag=Double.parseDouble(pago.getText());
    vuelto=pag-totalpago;
    txtcambio.setText(String.format("%.2f", vuelto));
}
private void limpiarventa()
{
    txtcod.setText("");
    txtTalla.setText("");
    txtprecio.setText("");
    txtstock.setText("");
    txtdesc.setText("");
    txtcant.setText("");
    
}
private void registrarVenta()
{
    ve= new ArrayList<Object[]>();
    acceso = new AccesoDatos();
    String consulta="SELECT * FROM danny_zapateria.venta";
    ve= (ArrayList<Object[]>)acceso.getVentas(consulta);
    int cod=(Integer)ve.get(ve.size()-1)[0]+1;
    int id_empleado=1001;
  
    ventas.setFolio(cod);
    //ventas.setFecha(fecha);
    ventas.setId_e(id_empleado);
     //System.out.println(ventas.getFecha());
    ventasdao.registrarVenta(ventas);
    
    int  fila=tabventa.getRowCount();
    for(int i=0; i<fila;i++)
    {
        double cal=Double.parseDouble(String.valueOf(tabventa.getModel().getValueAt(i, 4)));
        int codigocalzado=Integer.parseInt(String.valueOf(tabventa.getModel().getValueAt(i, 0)));
        int cantidadcompra=Integer.parseInt(String.valueOf(tabventa.getModel().getValueAt(i, 2)));
        int cal2=(int)cal;
        det.setFolio(cod);
        det.setCod(codigocalzado);
        det.setCant(cantidadcompra);
        det.setTotal(cal2);
        detao.registrarVenta(det);
    }
    
}
private void actualizarStock()
{
    int  fila=tabventa.getRowCount();
    for(int i=0; i<fila;i++)
    {
        int codigocalzado=Integer.parseInt(String.valueOf(tabventa.getModel().getValueAt(i, 0)));
        double tal=Double.parseDouble(String.valueOf(tabventa.getModel().getValueAt(i, 1)));
        int cantidadcompra=Integer.parseInt(String.valueOf(tabventa.getModel().getValueAt(i, 2)));
       // double talla=Double.parseDouble(txtTalla.getText());
        zapato=zapdao.buscaExtras(codigocalzado, tal);
     //   zapato=zapdao.bucarZapato(codigocalzado);
        int stockactual=zapato.getStock()-cantidadcompra;
        ventasdao.actualizarStock(stockactual, codigocalzado,zapato.getIDt());
 
    }
    }
    public void limpiarVenta()
    {
        tmp=(DefaultTableModel)tabventa.getModel();
        int  fila=tabventa.getRowCount();
    for(int i=0; i<fila;i++)
    {
        tmp.removeRow(0);
        
    }
    }
    public void limpiarProveedores()
    {
        txtid.setText("");
        txtciudad.setText("");
        txtrazon.setText("");
        txttel.setText("");
        txte.setText("");
        txtnomprov.setText("");
    }
    public void limpiaCamposdeVenta()
    {
        txtcod.setText("");
        txtTalla.setText("");
        txtdesc.setText("");
        txtcant.setText("");
        txtprecio.setText("");
        txtstock.setText("");
        
    }
  
    public void limpiameLosZapatos()
    {
        tcod.setText("");
        tdesc.setText("");
        tprecioc.setText("");
        tpreciov.setText("");
        //desabilita
         tdesc.setEnabled(false);
         tprecioc.setEnabled(false);
         tpreciov.setEnabled(false);
         tmat.setEnabled(false);
         tprov.setEnabled(false);
         ttipo.setEnabled(false);
    }
    public void limpiarZapatos()
    {
        tcod.setText("");
        tdesc.setText("");
        tprecioc.setText("");
        tpreciov.setText("");
        //desabilita
         tdesc.setEnabled(false);
         tprecioc.setEnabled(false);
         tpreciov.setEnabled(false);
         tmat.setEnabled(false);
         tprov.setEnabled(false);
         ttipo.setEnabled(false);
    }
    public void limpiarInventario()
    {
        icod.setText("");
        istock.setText("");
        //desabilita
         istock.setEnabled(false);
         italla.setEnabled(false);
    }
    private void pdf()
    {
        try
        {
            FileOutputStream archivo;
            File file= new File("src/pdf/venta.pdf");
            archivo = new FileOutputStream(file);
            Document doc= new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            Image img= Image.getInstance("src/icons/Logo_DanyV3.png");
            Paragraph fecha = new Paragraph();
            Font negrita= new Font(Font.FontFamily.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
            Date date= new Date();
            fecha.add("Fecha: "+new SimpleDateFormat("dd-mm-yyyy").format(date)+"\n\n");
            PdfPTable encabezado = new PdfPTable(4);
            encabezado.setWidthPercentage(100);
            encabezado.getDefaultCell().setBorder(0);
            float[] columnaEncabezado= new float[]{20f, 30f, 70f, 40f};
            encabezado.setWidths(columnaEncabezado);
            encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
            encabezado.addCell(img);
            String codi="codigo";
            String tal="33";
            String canti="3";
            String prec="500";
            String totsl="1500";
            encabezado.addCell("");
            encabezado.addCell("Codigo:  "+codi+"\nTalla:  "+tal+"\nCantidad: "+canti+"\nPrecio:  "+prec+"\nTotal: "+totsl);
            encabezado.addCell(fecha);
            doc.add(encabezado);
            
            PdfPTable tablapro= new PdfPTable(4);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            float[] columnapro= new float[]{20f, 20f, 30f, 30f};
            tablapro.setWidths(columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell pro1= new PdfPCell(new Phrase("Talla",negrita));
            PdfPCell pro2= new PdfPCell(new Phrase("Cantidad",negrita));
            PdfPCell pro3= new PdfPCell(new Phrase("Precio U",negrita));
            PdfPCell pro4= new PdfPCell(new Phrase("Precio T",negrita));
            pro1.setBorder(0);
            pro2.setBorder(0);
            pro3.setBorder(0);
            pro4.setBorder(0);
            pro1.setBackgroundColor(BaseColor.DARK_GRAY);
            pro2.setBackgroundColor(BaseColor.DARK_GRAY);
            pro3.setBackgroundColor(BaseColor.DARK_GRAY);
            pro4.setBackgroundColor(BaseColor.DARK_GRAY);
            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            for(int i=0;i<tabventa.getRowCount();i++)
            {
                String talla=tabventa.getValueAt(i, 1).toString();
               String cantidad=tabventa.getValueAt(i, 2).toString();
               String precio=tabventa.getValueAt(i, 3).toString();
               String total=tabventa.getValueAt(i, 4).toString();
               tablapro.addCell(talla);
               tablapro.addCell(cantidad);
               tablapro.addCell(precio);
               tablapro.addCell(total);
            }
            doc.add(tablapro);
            doc.close();
            archivo.close();
        }
        catch(Exception e){}
    }
}
