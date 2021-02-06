/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casaortiz.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author jorge
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form VentanaPrincipal
     */
    private ProductoView pv = new ProductoView();
    private CantonView cv = new CantonView();
    private EmpresaView ev = new EmpresaView();
    private FormaPagoView fpv = new FormaPagoView();
    private TipoComprobanteView tcv = new TipoComprobanteView();
    private TipoPersonaView tpv = new TipoPersonaView();
    private PersonaView perv = new PersonaView();
    public VentanaPrincipal() {
        initComponents();
        this.setLayout(new FlowLayout());;
        pv = new ProductoView();
        this.add(pv, BorderLayout.CENTER);
        this.pack();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        JMIProductos = new javax.swing.JMenuItem();
        jMICantones = new javax.swing.JMenuItem();
        jMIEmpresa = new javax.swing.JMenuItem();
        jMIFormaPago = new javax.swing.JMenuItem();
        jMITipoComprobantes = new javax.swing.JMenuItem();
        jMITipoPersona = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMIPersonas = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gimnasio Soliz");
        setBackground(java.awt.Color.white);
        setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        setLocation(new java.awt.Point(0, 0));
        setMinimumSize(new java.awt.Dimension(1280, 760));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jMenu1.setText("Archivo");

        JMIProductos.setText("Productos");
        JMIProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMIProductosActionPerformed(evt);
            }
        });
        jMenu1.add(JMIProductos);

        jMICantones.setText("Cantones");
        jMICantones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMICantonesActionPerformed(evt);
            }
        });
        jMenu1.add(jMICantones);

        jMIEmpresa.setText("Empresa");
        jMIEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIEmpresaActionPerformed(evt);
            }
        });
        jMenu1.add(jMIEmpresa);

        jMIFormaPago.setText("Formas de Pago");
        jMIFormaPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIFormaPagoActionPerformed(evt);
            }
        });
        jMenu1.add(jMIFormaPago);

        jMITipoComprobantes.setText("Tipo de Comprobantes");
        jMITipoComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMITipoComprobantesActionPerformed(evt);
            }
        });
        jMenu1.add(jMITipoComprobantes);

        jMITipoPersona.setText("Tipos de Personas");
        jMITipoPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMITipoPersonaActionPerformed(evt);
            }
        });
        jMenu1.add(jMITipoPersona);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Persona");

        jMIPersonas.setText("Personas");
        jMIPersonas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIPersonasActionPerformed(evt);
            }
        });
        jMenu2.add(jMIPersonas);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JMIProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMIProductosActionPerformed
        // TODO add your handling code here:
        vaciarVentana();
        pv = new ProductoView();
        this.add(pv, BorderLayout.CENTER);
        this.pack();
    }//GEN-LAST:event_JMIProductosActionPerformed

    private void jMICantonesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMICantonesActionPerformed
        // TODO add your handling code here:
        vaciarVentana();
        cv = new CantonView();
        this.add(cv, BorderLayout.CENTER);
        this.pack();
    }//GEN-LAST:event_jMICantonesActionPerformed

    private void jMIEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIEmpresaActionPerformed
        // TODO add your handling code here:
        vaciarVentana();
        ev = new EmpresaView();
        this.add(ev, BorderLayout.CENTER);
        this.pack();
    }//GEN-LAST:event_jMIEmpresaActionPerformed

    private void jMIFormaPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIFormaPagoActionPerformed
        // TODO add your handling code here:
        vaciarVentana();
        fpv = new FormaPagoView();
        this.add(fpv, BorderLayout.CENTER);
        this.pack();
    }//GEN-LAST:event_jMIFormaPagoActionPerformed

    private void jMITipoComprobantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMITipoComprobantesActionPerformed
        // TODO add your handling code here:
        vaciarVentana();
        tcv = new TipoComprobanteView();
        this.add(tcv, BorderLayout.CENTER);
        this.pack();
    }//GEN-LAST:event_jMITipoComprobantesActionPerformed

    private void jMITipoPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMITipoPersonaActionPerformed
        // TODO add your handling code here:
        vaciarVentana();
        tpv = new TipoPersonaView();
        this.add(tpv, BorderLayout.CENTER);
        this.pack();
    }//GEN-LAST:event_jMITipoPersonaActionPerformed

    private void jMIPersonasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIPersonasActionPerformed
        // TODO add your handling code here:
        vaciarVentana();
        perv = new PersonaView();
        this.add(perv, BorderLayout.CENTER);
        this.pack();
    }//GEN-LAST:event_jMIPersonasActionPerformed

    public void vaciarVentana(){
        
        this.remove(pv);
        this.remove(cv);
        this.remove(ev);
        this.remove(fpv);
        this.remove(tcv);
        this.remove(tpv);
        this.remove(perv);
        
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
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                VentanaPrincipal v = new VentanaPrincipal();
                v.setLocationRelativeTo(null); //para que aparezca la ventana en el centro                
                v.setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem JMIProductos;
    private javax.swing.JMenuItem jMICantones;
    private javax.swing.JMenuItem jMIEmpresa;
    private javax.swing.JMenuItem jMIFormaPago;
    private javax.swing.JMenuItem jMIPersonas;
    private javax.swing.JMenuItem jMITipoComprobantes;
    private javax.swing.JMenuItem jMITipoPersona;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}