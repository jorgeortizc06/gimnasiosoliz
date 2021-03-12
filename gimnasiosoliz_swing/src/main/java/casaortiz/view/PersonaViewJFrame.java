/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casaortiz.view;

import casaortiz.buss.PersonaBuss;
import casaortiz.buss.TipoPersonaBuss;
import casaortiz.model.Persona;
import casaortiz.model.TipoPersona;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.Color;
import java.awt.Image;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author jorge
 */
public class PersonaViewJFrame extends javax.swing.JFrame {

    private Persona persona;
    private List<Persona> personas;
    private PersonaBuss perBuss;
    private TipoPersonaBuss tpBuss;
    
    private Executor executor = Executors.newSingleThreadExecutor();
    private AtomicBoolean initialized = new AtomicBoolean(false);
    private Webcam webcam = null;
    private WebcamPanel panel = null;
    public PersonaViewJFrame() {
        initComponents();
        perBuss = new PersonaBuss();
        tpBuss = new TipoPersonaBuss();
        loadPersonas();
        loadTipoPersonas();
    }
    
    public void encenderCamara(){
        if(webcam == null){
            webcam = Webcam.getDefault();
            webcam.setViewSize(webcam.getViewSizes()[0]);
            panel = new WebcamPanel(webcam, false);
            panel.setPreferredSize(webcam.getViewSize());
            panel.setOpaque(true);
            panel.setBackground(Color.BLACK);
            panel.setBounds(0, 0, 400, 300);
            jPCamera.add(panel);
            if (initialized.compareAndSet(false, true)) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        panel.start();
                    }
                });
            }
        }else{
            webcam.open();
            panel.start();
        }
    }
    
    private void loadImageGuardada(String name) {
        System.out.println(name);
        try {
            String string = System.getProperty("user.dir") + "/media/persona/" +name;
            
            Image img = new ImageIcon(string).getImage();
            
            //Me permite redimensionar la imagen para que se adapte al jLabel
            ImageIcon ii = new ImageIcon(img.getScaledInstance(400, 300, Image.SCALE_SMOOTH));

            jLFoto.setIcon(ii);
            jLFoto.validate();
            jLFoto.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void apagarCamara(){
        webcam.close();
    }

    public void guardar(){
        persona = new Persona();
        persona.setNombre(jTFNombre.getText());
        persona.setApellido(jTFApellido.getText());
        persona.setCedula(jTFCedula.getText());
        persona.setDireccion(JTADirecc.getText());
        persona.setEmail(jTFEmail.getText());
        persona.setFechaNacimiento(rSDCFechaNacimiento.getDatoFecha());
        persona.setTelefono(jTFTele.getText());
        persona.setActivo("A");
        persona.setFoto(jTFCedula.getText()+".png");
        TipoPersona item = (TipoPersona) jCBTipoPersona.getSelectedItem();
        persona.setIdTipoPersona(item.getId());
        boolean estadoGuardado = perBuss.guardar(persona);
        if(estadoGuardado){
            JOptionPane.showMessageDialog(this, "Persona guardado");
            loadPersonas();
            vaciarFormulario();
        }else{
            JOptionPane.showMessageDialog(this, "Error al guardar la Persona");
        }
    }
    
    public void actualizar(){
        persona = new Persona();
        persona.setId(Integer.parseInt(jLID.getText()));
        persona.setNombre(jTFNombre.getText());
        persona.setApellido(jTFApellido.getText());
        persona.setCedula(jTFCedula.getText());
        persona.setDireccion(JTADirecc.getText());
        persona.setEmail(jTFEmail.getText());
        System.out.println(rSDCFechaNacimiento.getFormatoFecha());
        persona.setFechaNacimiento(rSDCFechaNacimiento.getDatoFecha());
        persona.setTelefono(jTFTele.getText());
        persona.setActivo("A");
        persona.setFoto(jTFCedula.getText()+".png");
        TipoPersona item = (TipoPersona) jCBTipoPersona.getSelectedItem();
        persona.setIdTipoPersona(item.getId());
        boolean estadoGuardado = perBuss.actualizar(persona);
        if(estadoGuardado){
            JOptionPane.showMessageDialog(this, "Persona actualizada");
            loadPersonas();
            vaciarFormulario();
        }else{
            JOptionPane.showMessageDialog(this, "Error al actualizar la Persona");
        }
    }
    
    public void eliminar(){
        int fila = jTListaPersonas.getSelectedRow();
        if(fila == -1){
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fila");
        }else{
            int id = Integer.parseInt((String)jTListaPersonas.getValueAt(fila, 0).toString());
            String nombre = (String)jTListaPersonas.getValueAt(fila, 1);        
            int estadoEliminacionDialog = JOptionPane.showConfirmDialog(this, "Seguro que desea eliminar "+nombre+"?","Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(estadoEliminacionDialog == 0){
                boolean estadoEliminacion = perBuss.eliminar(id);
                if(estadoEliminacion){
                    JOptionPane.showMessageDialog(this, "Persona eliminado");
                    loadPersonas();
                }else{
                    JOptionPane.showMessageDialog(this, "Error al eliminar la Persona");
                }
            }
        }
    }
    
    public void seleccionarItemTabla(){
        int fila = jTListaPersonas.getSelectedRow();
        if(fila == -1){
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fila");
        }else{
            int id = Integer.parseInt((String)jTListaPersonas.getValueAt(fila, 0).toString());
            Persona item = perBuss.getPersona(id);
            jLID.setText(""+item.getId());
            jTFNombre.setText(item.getNombre());
            jTFApellido.setText(item.getApellido());
            jTFCedula.setText(item.getCedula());
            JTADirecc.setText(item.getDireccion());
            jTFEmail.setText(item.getEmail());
            rSDCFechaNacimiento.setDatoFecha(item.getFechaNacimiento());
            jTFTele.setText(item.getTelefono());
            TipoPersona itemTipoPersona = tpBuss.getTipoPersona(item.getIdTipoPersona());
            jCBTipoPersona.getModel().setSelectedItem(itemTipoPersona);
            loadImageGuardada(item.getFoto());
            
        }
    }
    
    public void vaciarFormulario(){
        jLID.setText("");
        jTFNombre.setText("");
        jTFApellido.setText("");
        jTFCedula.setText("");
        JTADirecc.setText("");
        jTFEmail.setText("");
        rSDCFechaNacimiento.setDatoFecha(null);
        jTFTele.setText("");
        jLFoto.setIcon(null);
    }
    
    public void vaciarTabla(){
        DefaultTableModel modelo = (DefaultTableModel) jTListaPersonas.getModel();
        modelo.setRowCount(0);
        jTListaPersonas.setModel(modelo);
    }
    
    public void loadTipoPersonas(){
        List<TipoPersona> items = tpBuss.getTipoPersonas();
        for(TipoPersona tp: items){
           jCBTipoPersona.addItem(tp);
        }
    }
    
    public void loadPersonas(){
        vaciarTabla();
        DefaultTableModel modelo = (DefaultTableModel) jTListaPersonas.getModel();
        List<Persona> personas = perBuss.getPersonas();
        Object rowData[] = new Object[6];
        for(Persona p: personas){
            rowData[0] = p.getId();
            rowData[1] = p.getNombre();
            rowData[2] = p.getApellido();
            rowData[3] = p.getEmail();
            rowData[4] = p.getTelefono();
            rowData[5] = p.getActivo();
            modelo.addRow(rowData);
        }
        jTListaPersonas.setModel(modelo);
    }
    
    public void loadPersonasBusqueda(List<Persona> items){
        vaciarTabla();
        DefaultTableModel modelo = (DefaultTableModel) jTListaPersonas.getModel();
        List<Persona> personas = items;
        Object rowData[] = new Object[6];
        for(Persona p: personas){
            rowData[0] = p.getId();
            rowData[1] = p.getNombre();
            rowData[2] = p.getApellido();
            rowData[3] = p.getEmail();
            rowData[4] = p.getTelefono();
            rowData[5] = p.getActivo();
            modelo.addRow(rowData);
        }
        jTListaPersonas.setModel(modelo);
    }
    
    public void vaciarCamposBusqueda(){
        jTFBusNombre.setText("");
        jTFBusApell.setText("");
        jTFBusCedula.setText("");
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPDatos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLID = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTFCedula = new javax.swing.JTextField();
        jBGuardar = new javax.swing.JButton();
        JBListar = new javax.swing.JButton();
        JBEditar = new javax.swing.JButton();
        JBOk = new javax.swing.JButton();
        JBEliminar = new javax.swing.JButton();
        JBVaciarFormulario = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jCBTipoPersona = new javax.swing.JComboBox<TipoPersona>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTFTele = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        JTADirecc = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jTFNombre = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTFApellido = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTFEmail = new javax.swing.JTextField();
        jLFoto = new javax.swing.JLabel();
        jBTomarFoto = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        rSDCFechaNacimiento = new rojeru_san.componentes.RSDateChooser();
        jBEncenderCam = new javax.swing.JButton();
        jBApagarCam = new javax.swing.JButton();
        jPCamera = new javax.swing.JPanel();
        JPListaPersonas = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTListaPersonas = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jBLimpiar = new javax.swing.JButton();
        jTFBusCedula = new javax.swing.JTextField();
        jTFBusNombre = new javax.swing.JTextField();
        jTFBusApell = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N
        jPDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("ID:");
        jPDatos.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jLID.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jPDatos.add(jLID, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 130, 17));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Cédula:");
        jPDatos.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        jTFCedula.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTFCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFCedulaActionPerformed(evt);
            }
        });
        jPDatos.add(jTFCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 270, -1));

        jBGuardar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jBGuardar.setText("Guardar");
        jBGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuardarActionPerformed(evt);
            }
        });
        jPDatos.add(jBGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 110, -1));

        JBListar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JBListar.setText("Listar");
        JBListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBListarActionPerformed(evt);
            }
        });
        jPDatos.add(JBListar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 100, -1));

        JBEditar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JBEditar.setText("Editar");
        JBEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBEditarActionPerformed(evt);
            }
        });
        jPDatos.add(JBEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 410, 70, -1));

        JBOk.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JBOk.setText("Ok");
        JBOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBOkActionPerformed(evt);
            }
        });
        jPDatos.add(JBOk, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 410, 90, -1));

        JBEliminar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JBEliminar.setText("Eliminar");
        JBEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBEliminarActionPerformed(evt);
            }
        });
        jPDatos.add(JBEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 410, 120, -1));

        JBVaciarFormulario.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JBVaciarFormulario.setText("Vaciar Formulario");
        JBVaciarFormulario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBVaciarFormularioActionPerformed(evt);
            }
        });
        jPDatos.add(JBVaciarFormulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 410, -1, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Tipo Persona:");
        jPDatos.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jCBTipoPersona.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jPDatos.add(jCBTipoPersona, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, 270, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Teléfono:");
        jPDatos.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Dirección:");
        jPDatos.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 80, -1));

        jTFTele.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jPDatos.add(jTFTele, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 270, -1));

        JTADirecc.setColumns(20);
        JTADirecc.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        JTADirecc.setLineWrap(true);
        JTADirecc.setRows(5);
        jScrollPane3.setViewportView(JTADirecc);

        jPDatos.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, 270, 100));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("Nombre:");
        jPDatos.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 20));

        jTFNombre.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTFNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFNombreActionPerformed(evt);
            }
        });
        jPDatos.add(jTFNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 270, -1));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("Apellido:");
        jPDatos.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, 20));

        jTFApellido.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTFApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFApellidoActionPerformed(evt);
            }
        });
        jPDatos.add(jTFApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 270, -1));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setText("Email:");
        jPDatos.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, 20));

        jTFEmail.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTFEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFEmailActionPerformed(evt);
            }
        });
        jPDatos.add(jTFEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 270, -1));

        jLFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPDatos.add(jLFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 30, 400, 300));

        jBTomarFoto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jBTomarFoto.setText("Tomar Foto");
        jBTomarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTomarFotoActionPerformed(evt);
            }
        });
        jPDatos.add(jBTomarFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 340, -1, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Fecha");
        jPDatos.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 50, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Nacimiento");
        jPDatos.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 80, -1));

        rSDCFechaNacimiento.setBackground(java.awt.SystemColor.control);
        rSDCFechaNacimiento.setColorBackground(new java.awt.Color(0, 0, 0));
        rSDCFechaNacimiento.setColorForeground(new java.awt.Color(0, 0, 0));
        rSDCFechaNacimiento.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rSDCFechaNacimiento.setFormatoFecha("dd/MM/yyyy");
        rSDCFechaNacimiento.setFuente(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rSDCFechaNacimiento.setPlaceholder("");
        jPDatos.add(rSDCFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 270, -1));

        jBEncenderCam.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jBEncenderCam.setText("Encender Cámara");
        jBEncenderCam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEncenderCamActionPerformed(evt);
            }
        });
        jPDatos.add(jBEncenderCam, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 340, -1, -1));

        jBApagarCam.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jBApagarCam.setText("Apagar Cámara");
        jBApagarCam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBApagarCamActionPerformed(evt);
            }
        });
        jPDatos.add(jBApagarCam, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 340, -1, -1));

        jPCamera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPCamera.setLayout(new java.awt.GridLayout());
        jPDatos.add(jPCamera, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 400, 300));

        JPListaPersonas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N
        JPListaPersonas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTListaPersonas.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTListaPersonas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Apellido", "Email", "Telefono", "Activo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTListaPersonas);

        JPListaPersonas.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1220, 340));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setText("Nombre:");
        JPListaPersonas.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("Apellido:");
        JPListaPersonas.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, -1, 20));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setText("Cedula:");
        JPListaPersonas.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, -1, 20));

        jBLimpiar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jBLimpiar.setText("Limpiar");
        jBLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimpiarActionPerformed(evt);
            }
        });
        JPListaPersonas.add(jBLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 30, -1, -1));

        jTFBusCedula.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTFBusCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFBusCedulaKeyReleased(evt);
            }
        });
        JPListaPersonas.add(jTFBusCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 30, 140, -1));

        jTFBusNombre.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTFBusNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFBusNombreKeyReleased(evt);
            }
        });
        JPListaPersonas.add(jTFBusNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 140, -1));

        jTFBusApell.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTFBusApell.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFBusApellKeyReleased(evt);
            }
        });
        JPListaPersonas.add(jTFBusApell, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 140, -1));

        jPDatos.add(JPListaPersonas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 1240, 410));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1261, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 1261, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTFCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFCedulaActionPerformed

    private void jBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuardarActionPerformed
        //guardarCanton();
        guardar();
        if (webcam != null){
            webcam.close();
        }
    }//GEN-LAST:event_jBGuardarActionPerformed

    private void JBListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBListarActionPerformed
        // TODO add your handling code here:
        loadPersonas();
    }//GEN-LAST:event_JBListarActionPerformed

    private void JBEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBEditarActionPerformed
        jBGuardar.setVisible(false);
        seleccionarItemTabla();
    }//GEN-LAST:event_JBEditarActionPerformed

    private void JBOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBOkActionPerformed
        actualizar();
        jBGuardar.setVisible(true);
        if (webcam != null){
            webcam.close();
        }
    }//GEN-LAST:event_JBOkActionPerformed

    private void JBEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_JBEliminarActionPerformed

    private void JBVaciarFormularioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBVaciarFormularioActionPerformed
        vaciarFormulario();
        jBGuardar.setVisible(true);
    }//GEN-LAST:event_JBVaciarFormularioActionPerformed

    private void jTFNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFNombreActionPerformed

    private void jTFApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFApellidoActionPerformed

    private void jTFEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFEmailActionPerformed

    private void jBTomarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTomarFotoActionPerformed
        // TODO add your handling code here:
        try {
            if(jTFCedula.getText().equals("")){
                JOptionPane.showMessageDialog(jTFCedula, "Debe ingresar primero la cédula");
            }else{
                BufferedImage image = webcam.getImage();
                //nombre y formato de la imagen de salida
                ImageIO.write(image, "PNG", new File(System.getProperty("user.dir") + "/media/persona/" +jTFCedula.getText()+".png"));
                loadImageGuardada(jTFCedula.getText()+".png");
                webcam.close();
            }

        } catch (IOException ex) {

        }
    }//GEN-LAST:event_jBTomarFotoActionPerformed

    private void jBEncenderCamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEncenderCamActionPerformed
        // TODO add your handling code here:
        encenderCamara();
    }//GEN-LAST:event_jBEncenderCamActionPerformed

    private void jBApagarCamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBApagarCamActionPerformed
        // TODO add your handling code here:
        apagarCamara();
    }//GEN-LAST:event_jBApagarCamActionPerformed

    private void jBLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimpiarActionPerformed
        // TODO add your handling code here:
        loadPersonas();
        vaciarCamposBusqueda();
    }//GEN-LAST:event_jBLimpiarActionPerformed

    private void jTFBusCedulaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFBusCedulaKeyReleased
        // TODO add your handling code here:
        loadPersonasBusqueda(perBuss.buscarPersonasPorCedula(jTFBusCedula.getText()));
    }//GEN-LAST:event_jTFBusCedulaKeyReleased

    private void jTFBusNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFBusNombreKeyReleased
        loadPersonasBusqueda(perBuss.buscarPersonasPorNombre(jTFBusNombre.getText()));
    }//GEN-LAST:event_jTFBusNombreKeyReleased

    private void jTFBusApellKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFBusApellKeyReleased
        // TODO add your handling code here:
        loadPersonasBusqueda(perBuss.buscarPersonasPorApellido(jTFBusApell.getText()));
    }//GEN-LAST:event_jTFBusApellKeyReleased

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
            java.util.logging.Logger.getLogger(PersonaViewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PersonaViewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PersonaViewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PersonaViewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PersonaViewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JBEditar;
    private javax.swing.JButton JBEliminar;
    private javax.swing.JButton JBListar;
    private javax.swing.JButton JBOk;
    private javax.swing.JButton JBVaciarFormulario;
    private javax.swing.JPanel JPListaPersonas;
    private javax.swing.JTextArea JTADirecc;
    private javax.swing.JButton jBApagarCam;
    private javax.swing.JButton jBEncenderCam;
    private javax.swing.JButton jBGuardar;
    private javax.swing.JButton jBLimpiar;
    private javax.swing.JButton jBTomarFoto;
    private javax.swing.JComboBox<TipoPersona> jCBTipoPersona;
    private javax.swing.JLabel jLFoto;
    private javax.swing.JLabel jLID;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPCamera;
    private javax.swing.JPanel jPDatos;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTFApellido;
    private javax.swing.JTextField jTFBusApell;
    private javax.swing.JTextField jTFBusCedula;
    private javax.swing.JTextField jTFBusNombre;
    private javax.swing.JTextField jTFCedula;
    private javax.swing.JTextField jTFEmail;
    private javax.swing.JTextField jTFNombre;
    private javax.swing.JTextField jTFTele;
    private javax.swing.JTable jTListaPersonas;
    private rojeru_san.componentes.RSDateChooser rSDCFechaNacimiento;
    // End of variables declaration//GEN-END:variables
}