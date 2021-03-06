/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agipub;

import java.awt.Color;
import java.awt.Component;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author win_bmx
 */
public class Accueil extends javax.swing.JFrame {
    
    Boolean connecte = false;
    pan_papier papier;
    pan_clients client;
    pan_commandes command;
    pan_facture facture;

    /**
     * Creates new form Accueil
     */
    public Accueil() {
        
        initComponents();
        this.tlb_cnx.setVisible(false);
        this.setExtendedState(Accueil.MAXIMIZED_BOTH);
        btn_cnx.setBackground(Color.red);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        container = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        tlb_cnx = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        txt_login = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_pass = new javax.swing.JPasswordField();
        btn_cnx = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuItem_clients = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuItem_papier = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menuItem_commande = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        menuItem_facture = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        menuItem_fermer = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        menuItem_quitter = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menu_connect = new javax.swing.JMenuItem();
        menu_deconnect = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Agipub 1");
        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(java.awt.Color.white);
        setLocationByPlatform(true);

        container.setBackground(new java.awt.Color(0, 0, 27));
        container.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        container.setForeground(new java.awt.Color(255, 255, 255));
        container.setLayout(new java.awt.BorderLayout());
        container.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(container, java.awt.BorderLayout.CENTER);

        tlb_cnx.setFloatable(false);
        tlb_cnx.setRollover(true);

        jLabel1.setText("Utilisateur : ");
        tlb_cnx.add(jLabel1);

        txt_login.setBackground(new java.awt.Color(0, 102, 102));
        txt_login.setForeground(new java.awt.Color(255, 255, 255));
        txt_login.setPreferredSize(new java.awt.Dimension(250, 30));
        tlb_cnx.add(txt_login);

        jLabel2.setText("Mot de passe : ");
        tlb_cnx.add(jLabel2);

        txt_pass.setBackground(new java.awt.Color(0, 102, 102));
        txt_pass.setForeground(new java.awt.Color(255, 255, 255));
        txt_pass.setPreferredSize(new java.awt.Dimension(250, 30));
        tlb_cnx.add(txt_pass);

        btn_cnx.setBackground(new java.awt.Color(204, 204, 255));
        btn_cnx.setForeground(new java.awt.Color(0, 102, 102));
        btn_cnx.setText("Connexion");
        btn_cnx.setFocusable(false);
        btn_cnx.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_cnx.setPreferredSize(new java.awt.Dimension(80, 30));
        btn_cnx.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_cnx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cnxActionPerformed(evt);
            }
        });
        tlb_cnx.add(btn_cnx);

        getContentPane().add(tlb_cnx, java.awt.BorderLayout.PAGE_START);

        jMenu1.setText("Menu");
        jMenu1.setPreferredSize(new java.awt.Dimension(120, 19));

        menuItem_clients.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        menuItem_clients.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_menu_agipub/client.png"))); // NOI18N
        menuItem_clients.setText("Clients");
        menuItem_clients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_clientsActionPerformed(evt);
            }
        });
        jMenu1.add(menuItem_clients);
        jMenu1.add(jSeparator3);

        menuItem_papier.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        menuItem_papier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_menu_agipub/documents.png"))); // NOI18N
        menuItem_papier.setText("Papier");
        menuItem_papier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_papierActionPerformed(evt);
            }
        });
        jMenu1.add(menuItem_papier);
        jMenu1.add(jSeparator4);

        menuItem_commande.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuItem_commande.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_menu_agipub/cmd.png"))); // NOI18N
        menuItem_commande.setText("Commandes");
        menuItem_commande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_commandeActionPerformed(evt);
            }
        });
        jMenu1.add(menuItem_commande);
        jMenu1.add(jSeparator5);

        menuItem_facture.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK));
        menuItem_facture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_menu_agipub/facture.png"))); // NOI18N
        menuItem_facture.setText("Factures");
        menuItem_facture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_factureActionPerformed(evt);
            }
        });
        jMenu1.add(menuItem_facture);
        jMenu1.add(jSeparator6);

        menuItem_fermer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        menuItem_fermer.setText("Fermer");
        menuItem_fermer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_fermerActionPerformed(evt);
            }
        });
        jMenu1.add(menuItem_fermer);
        jMenu1.add(jSeparator7);

        menuItem_quitter.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        menuItem_quitter.setText("Quitter");
        menuItem_quitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_quitterActionPerformed(evt);
            }
        });
        jMenu1.add(menuItem_quitter);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Connexion");

        menu_connect.setText("Se connecter");
        menu_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_connectActionPerformed(evt);
            }
        });
        jMenu2.add(menu_connect);

        menu_deconnect.setText("Se déconnecter");
        menu_deconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_deconnectActionPerformed(evt);
            }
        });
        jMenu2.add(menu_deconnect);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuItem_clientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_clientsActionPerformed
        // TODO add your handling code here:
        try {
            if (connecte) {
                if (client == null) {
                    client = new pan_clients();
                    
                    jTabbedPane1.addTab("Clients", new javax.swing.ImageIcon(getClass().getResource("/imgs/img_menu_agipub/client.png")), client);
                }
                jTabbedPane1.setSelectedComponent(client);
                //jTabbedPane1.add(clt);
            } else {
                JOptionPane.showMessageDialog(null, "Vous n'ête pas connecté !");
            }
        } catch (Exception exp) {
            JOptionPane.showMessageDialog(null, "Erreur à l'ouverture : \n" + exp.getMessage() + "\n" + exp.getStackTrace());
        }

    }//GEN-LAST:event_menuItem_clientsActionPerformed

    private void menuItem_fermerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_fermerActionPerformed
        // TODO add your handling code here:
        try {
            if (jTabbedPane1.getComponentCount() > 0) {
                // JOptionPane.showMessageDialog(null, jTabbedPane1.getSelectedComponent());
                int c = jTabbedPane1.getSelectedIndex();
                Component co = jTabbedPane1.getSelectedComponent();
                
                jTabbedPane1.remove(c);
                if (co == client) {
                    client = null;
                } else if (co == command) {
                    command = null;
                } else if (co == papier) {
                    papier = null;
                } else if (co == facture) {
                    facture = null;
                }
                
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }//GEN-LAST:event_menuItem_fermerActionPerformed

    private void menuItem_quitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_quitterActionPerformed
        try {
            
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuItem_quitterActionPerformed

    private void menuItem_papierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_papierActionPerformed
        // TODO add your handling code here:
        try {
            if (connecte) {
                if (papier == null) {
                    papier = new pan_papier();
                    jTabbedPane1.addTab("Papier", new javax.swing.ImageIcon(getClass().getResource("/imgs/img_menu_agipub/documents.png")), papier);
                }
                jTabbedPane1.setSelectedComponent(papier);
            } else {
                JOptionPane.showMessageDialog(null, "Vous n'ête pas connecté !");
            }
        } catch (Exception exp) {
            JOptionPane.showMessageDialog(null, "Erreur à l'ouverture : \n" + exp.getMessage() + "\n" + exp.getStackTrace());
        }
    }//GEN-LAST:event_menuItem_papierActionPerformed

    private void menuItem_commandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_commandeActionPerformed
        // TODO add your handling code here:
        try {
            if (connecte) {
                if (command == null) {
                    command = new pan_commandes();
                    jTabbedPane1.addTab("Commandes", new javax.swing.ImageIcon(getClass().getResource("/imgs/img_menu_agipub/documents.png")), command);
                }
                jTabbedPane1.setSelectedComponent(command);
                
            } else {
                JOptionPane.showMessageDialog(null, "Vous n'ête pas connecté !");
            }
        } catch (Exception exp) {
            JOptionPane.showMessageDialog(null, "Erreur à l'ouverture : \n" + exp.getMessage() + "\n" + exp.getStackTrace());
        }
    }//GEN-LAST:event_menuItem_commandeActionPerformed

    private void menuItem_factureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_factureActionPerformed
        // TODO add your handling code here:
        try {
            if (connecte) {
                if (facture == null) {
                    facture = new pan_facture();
                    jTabbedPane1.addTab("Facture", new javax.swing.ImageIcon(getClass().getResource("/imgs/img_menu_agipub/facture.png")), facture);
                    //JOptionPane.showMessageDialog(null, getClass().getResource("/imgs/img_menu_agipub/facture.png").getFile());
                }
                jTabbedPane1.setSelectedComponent(facture);
            } else {
                JOptionPane.showMessageDialog(null, "Vous n'ête pas connecté !");
            }
        } catch (Exception exp) {
            JOptionPane.showMessageDialog(null, "Erreur à l'ouverture : \n" + exp.getMessage() + "\n" + Arrays.toString(exp.getStackTrace()));
        }
    }//GEN-LAST:event_menuItem_factureActionPerformed

    private void menu_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_connectActionPerformed
        // TODO add your handling code here:
        if (!connecte) {
            tlb_cnx.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Vous êtes déjà connecté !");
        }
    }//GEN-LAST:event_menu_connectActionPerformed

    private void menu_deconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_deconnectActionPerformed
        // TODO add your handling code here:
        try {
            if (jTabbedPane1.getComponentCount() > 0) {
                // JOptionPane.showMessageDialog(null, jTabbedPane1.getSelectedComponent());
                
                if (client != null) {
                    jTabbedPane1.remove(client);
                    client = null;
                }
                if (command != null) {
                    jTabbedPane1.remove(command);
                    command = null;
                }
                if (papier != null) {
                    jTabbedPane1.remove(papier);
                    papier = null;
                }
                if (facture != null) {
                    jTabbedPane1.remove(facture);
                    facture = null;
                }
                
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        connecte = false;
        JOptionPane.showMessageDialog(null, "Vous êtes déconnecté !");
    }//GEN-LAST:event_menu_deconnectActionPerformed

    private void btn_cnxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cnxActionPerformed
        // TODO add your handling code here:
        if (txt_login.getText().isEmpty() || txt_pass.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "S'il vous plaît, Saisir votre login et mot de passe !");
        } else {
            String user = txt_login.getText();
            String pass = "";
            for (int i = 0; i < txt_pass.getPassword().length; i++) {
                pass += txt_pass.getPassword()[i];
            }
            //JOptionPane.showMessageDialog(null, pass);
            Connexion.getInstance(user, pass);
            connecte = true;
            txt_login.setText("");
            txt_pass.setText("");
            tlb_cnx.setVisible(false);
        }
    }//GEN-LAST:event_btn_cnxActionPerformed

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
            java.util.logging.Logger.getLogger(Accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Accueil().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cnx;
    private javax.swing.JPanel container;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuItem menuItem_clients;
    private javax.swing.JMenuItem menuItem_commande;
    private javax.swing.JMenuItem menuItem_facture;
    private javax.swing.JMenuItem menuItem_fermer;
    private javax.swing.JMenuItem menuItem_papier;
    private javax.swing.JMenuItem menuItem_quitter;
    private javax.swing.JMenuItem menu_connect;
    private javax.swing.JMenuItem menu_deconnect;
    private javax.swing.JToolBar tlb_cnx;
    private javax.swing.JTextField txt_login;
    private javax.swing.JPasswordField txt_pass;
    // End of variables declaration//GEN-END:variables
}
