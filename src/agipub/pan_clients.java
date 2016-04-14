/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agipub;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import object.Client;

/**
 *
 * @author win_bmx
 */
public class pan_clients extends javax.swing.JPanel {

    int ind = -1;
    static ArrayList<Client> clients = new ArrayList<>();
    Statement state;
    ResultSet resultClient;
    String titleClient[] = new String[]{
        "Numero Client", "Société", "Nom", "Prénom", "Fix 1 ", "Fix 2", "GSM 1", "GSM 2", "E-mail", "Site web", "Adresse"
    };

    /**
     * Creates new form pan_clients
     */
    public pan_clients() {
        initComponents();
        chargeClient();
        tabClient_selectChanged();
    }

    private void tabClient_selectChanged() {
        tbl_clients.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {

                    ind = tbl_clients.getSelectedRow();
                    //JOptionPane.showMessageDialog(null, ind);
                    if (ind > -1) {
                        txt_code.setText(String.valueOf(clients.get(ind).getId()));
                        txt_ste_name.setText(clients.get(ind).getSte());
                        txt_clt_nom.setText(clients.get(ind).getNom());
                        txt_clt_prenom.setText(clients.get(ind).getPrenom());
                        txt_fix1.setText(clients.get(ind).getFix1());
                        txt_fix2.setText(clients.get(ind).getFix2());
                        txt_gsm1.setText(clients.get(ind).getGsm1());
                        txt_gsm2.setText(clients.get(ind).getGsm2());
                        txt_email.setText(clients.get(ind).getMail());
                        txt_site_web.setText(clients.get(ind).getSiteweb());
                        txt_adresse.setText(clients.get(ind).getAdresse());
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur au changment de selection : \n" + ex.getMessage());
                    Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void chargeClient() {
        try {
            state = Connexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Object[][] data;
            resultClient = state.executeQuery("SELECT * FROM client");
            clients.removeAll(clients);
            Client clt;

            while (resultClient.next()) {
                clt = new Client();
                clt.setId(resultClient.getInt("id_client"));
                clt.setSte(resultClient.getString("clt_ste"));
                clt.setNom(resultClient.getString("clt_nom"));
                clt.setPrenom(resultClient.getString("clt_prenom"));

                clt.setFix1(resultClient.getString("clt_fix1"));
                clt.setFix2(resultClient.getString("clt_fix2"));
                clt.setGsm1(resultClient.getString("clt_gsm1"));
                clt.setGsm2(resultClient.getString("clt_gsm2"));
                clt.setSiteweb(resultClient.getString("clt_siteweb"));
                clt.setMail(resultClient.getString("clt_mail"));
                clt.setAdresse(resultClient.getString("clt_adresse"));
                clients.add(clt);
            }
            resultClient.last();
            data = new Object[resultClient.getRow()][titleClient.length];
            int j = 1;
            resultClient.beforeFirst();

            while (resultClient.next()) {
                for (int i = 1; i <= titleClient.length; i++) {
                    data[j - 1][i - 1] = resultClient.getObject(i);
                }
                j++;

            }
            tbl_clients.setModel(new DefaultTableModel(data, titleClient) {
                
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }

            });
            // tbl_clients.setCellEditor(null);
//            txt_code.setText(String.valueOf(clients.get(0).getId()));
            state.close();
            resultClient.close();
            ind = -1;
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ereur au chargement des clients\n" + ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ereur au chargement des clients\n" + ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane_Clients = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_clients = new javax.swing.JTable();
        pnl_edition_client = new javax.swing.JScrollPane();
        pnl_clients = new javax.swing.JPanel();
        lbl_ste_name = new javax.swing.JLabel();
        txt_ste_name = new javax.swing.JTextField();
        txt_clt_nom = new javax.swing.JTextField();
        lbl_ste_name9 = new javax.swing.JLabel();
        txt_clt_prenom = new javax.swing.JTextField();
        lbl_ste_name10 = new javax.swing.JLabel();
        lbl_ste_name11 = new javax.swing.JLabel();
        lbl_ste_name12 = new javax.swing.JLabel();
        lbl_ste_name13 = new javax.swing.JLabel();
        lbl_ste_name14 = new javax.swing.JLabel();
        lbl_ste_name15 = new javax.swing.JLabel();
        lbl_ste_name16 = new javax.swing.JLabel();
        txt_site_web = new javax.swing.JTextField();
        txt_fix1 = new javax.swing.JFormattedTextField();
        txt_fix2 = new javax.swing.JFormattedTextField();
        txt_gsm1 = new javax.swing.JFormattedTextField();
        txt_gsm2 = new javax.swing.JFormattedTextField();
        txt_email = new javax.swing.JFormattedTextField();
        lbl_ste_name17 = new javax.swing.JLabel();
        txt_adresse = new javax.swing.JTextArea();
        toolBar_clients = new javax.swing.JToolBar();
        btn_atteindre = new javax.swing.JButton();
        btn_refrech = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btn_first = new javax.swing.JButton();
        btn_prec = new javax.swing.JButton();
        txt_code = new javax.swing.JTextField();
        btn_suiv = new javax.swing.JButton();
        btn_last = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btn_add = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_annuler = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        splitPane_Clients.setBackground(new java.awt.Color(255, 255, 255));
        splitPane_Clients.setDividerLocation(590);
        splitPane_Clients.setDividerSize(15);
        splitPane_Clients.setToolTipText("");
        splitPane_Clients.setAutoscrolls(true);
        splitPane_Clients.setOneTouchExpandable(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tbl_clients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"xx", "xx", "xx", "xx", null, "xx", null, "xx", "xx", null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Société", "Nom", "Prénom", "Fix 1 ", "Fix 2", "GSM 1", "GSM 2", "E-mail", "Site web", "Adresse"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_clients.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbl_clients.setGridColor(new java.awt.Color(204, 204, 204));
        tbl_clients.setRowHeight(32);
        tbl_clients.setShowHorizontalLines(true);
        tbl_clients.setShowVerticalLines(true);
        jScrollPane1.setViewportView(tbl_clients);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        splitPane_Clients.setLeftComponent(jPanel2);

        lbl_ste_name.setText("Nom Societé : ");

        txt_ste_name.setEditable(false);

        txt_clt_nom.setEditable(false);

        lbl_ste_name9.setText("Nom : ");

        txt_clt_prenom.setEditable(false);

        lbl_ste_name10.setText("Prénom");

        lbl_ste_name11.setText("Fix 1 :");

        lbl_ste_name12.setText("Fix 2 :");

        lbl_ste_name13.setText("GSM :");

        lbl_ste_name14.setText("GSM 2 :");

        lbl_ste_name15.setText("E-mail");

        lbl_ste_name16.setText("Site web : ");

        txt_site_web.setEditable(false);

        txt_fix1.setEditable(false);
        try {
            txt_fix1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("05-##-##-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_fix1.setText("");

        txt_fix2.setEditable(false);
        try {
            txt_fix2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("05-##-##-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_fix2.setText("");

        txt_gsm1.setEditable(false);
        try {
            txt_gsm1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("06-##-##-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_gsm1.setText("");

        txt_gsm2.setEditable(false);
        try {
            txt_gsm2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("06-##-##-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_gsm2.setText("");

        txt_email.setEditable(false);

        lbl_ste_name17.setText("Adresse :");

        txt_adresse.setEditable(false);
        txt_adresse.setColumns(20);
        txt_adresse.setRows(5);

        javax.swing.GroupLayout pnl_clientsLayout = new javax.swing.GroupLayout(pnl_clients);
        pnl_clients.setLayout(pnl_clientsLayout);
        pnl_clientsLayout.setHorizontalGroup(
            pnl_clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_clientsLayout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addGroup(pnl_clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_clientsLayout.createSequentialGroup()
                        .addComponent(lbl_ste_name, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(pnl_clientsLayout.createSequentialGroup()
                        .addGroup(pnl_clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_ste_name9)
                            .addComponent(lbl_ste_name10)
                            .addComponent(lbl_ste_name11)
                            .addComponent(lbl_ste_name12)
                            .addComponent(lbl_ste_name13)
                            .addComponent(lbl_ste_name14)
                            .addComponent(lbl_ste_name15)
                            .addComponent(lbl_ste_name16)
                            .addComponent(lbl_ste_name17))
                        .addGap(30, 30, 30)))
                .addGroup(pnl_clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_clt_nom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addComponent(txt_site_web, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addComponent(txt_ste_name, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_clt_prenom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addComponent(txt_fix1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_fix2)
                    .addComponent(txt_gsm1)
                    .addComponent(txt_gsm2)
                    .addComponent(txt_email)
                    .addComponent(txt_adresse))
                .addContainerGap())
        );
        pnl_clientsLayout.setVerticalGroup(
            pnl_clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_clientsLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_ste_name)
                    .addComponent(txt_ste_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnl_clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_ste_name9)
                    .addComponent(txt_clt_nom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnl_clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_ste_name10)
                    .addComponent(txt_clt_prenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnl_clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name11)
                    .addComponent(txt_fix1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnl_clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name12)
                    .addComponent(txt_fix2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnl_clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name13)
                    .addComponent(txt_gsm1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnl_clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name14)
                    .addComponent(txt_gsm2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnl_clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name15)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnl_clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_ste_name16)
                    .addComponent(txt_site_web, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(pnl_clientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_ste_name17)
                    .addComponent(txt_adresse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnl_edition_client.setViewportView(pnl_clients);

        splitPane_Clients.setRightComponent(pnl_edition_client);

        add(splitPane_Clients, java.awt.BorderLayout.CENTER);

        toolBar_clients.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        toolBar_clients.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btn_atteindre.setText("Atteindre");
        btn_atteindre.setFocusable(false);
        btn_atteindre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_atteindre.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_atteindre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atteindreActionPerformed(evt);
            }
        });
        toolBar_clients.add(btn_atteindre);

        btn_refrech.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/interact01.png"))); // NOI18N
        btn_refrech.setToolTipText("Actualiser");
        btn_refrech.setFocusable(false);
        btn_refrech.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_refrech.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_refrech.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refrechActionPerformed(evt);
            }
        });
        toolBar_clients.add(btn_refrech);
        toolBar_clients.add(jSeparator2);

        btn_first.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/navigation_first_frame.png"))); // NOI18N
        btn_first.setToolTipText("Aller au premier");
        btn_first.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_first.setFocusable(false);
        btn_first.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_first.setMargin(new java.awt.Insets(2, 7, 2, 7));
        btn_first.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_firstActionPerformed(evt);
            }
        });
        toolBar_clients.add(btn_first);

        btn_prec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/navigation_left_frame.png"))); // NOI18N
        btn_prec.setToolTipText("Aller au précédent");
        btn_prec.setFocusable(false);
        btn_prec.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_prec.setMargin(new java.awt.Insets(2, 7, 2, 7));
        btn_prec.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_prec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_precActionPerformed(evt);
            }
        });
        toolBar_clients.add(btn_prec);

        txt_code.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_code.setFocusable(false);
        txt_code.setMaximumSize(new java.awt.Dimension(800, 25));
        txt_code.setMinimumSize(new java.awt.Dimension(50, 20));
        txt_code.setPreferredSize(new java.awt.Dimension(60, 25));
        toolBar_clients.add(txt_code);

        btn_suiv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/navigation_right_frame.png"))); // NOI18N
        btn_suiv.setToolTipText("Aller au suivant");
        btn_suiv.setFocusable(false);
        btn_suiv.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_suiv.setMargin(new java.awt.Insets(2, 7, 2, 7));
        btn_suiv.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_suiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suivActionPerformed(evt);
            }
        });
        toolBar_clients.add(btn_suiv);

        btn_last.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/navigation_last_frame.png"))); // NOI18N
        btn_last.setToolTipText("Aller au dernier");
        btn_last.setFocusable(false);
        btn_last.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_last.setMargin(new java.awt.Insets(2, 7, 2, 7));
        btn_last.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lastActionPerformed(evt);
            }
        });
        toolBar_clients.add(btn_last);
        toolBar_clients.add(jSeparator1);

        btn_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/plus.png"))); // NOI18N
        btn_add.setToolTipText("Ajouter");
        btn_add.setFocusable(false);
        btn_add.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_add.setMargin(new java.awt.Insets(2, 7, 2, 7));
        btn_add.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });
        toolBar_clients.add(btn_add);

        btn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/graphic_design.png"))); // NOI18N
        btn_edit.setToolTipText("Modifier");
        btn_edit.setFocusable(false);
        btn_edit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_edit.setMargin(new java.awt.Insets(2, 7, 2, 7));
        btn_edit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        toolBar_clients.add(btn_edit);

        btn_annuler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/undo.png"))); // NOI18N
        btn_annuler.setToolTipText("Annuler");
        btn_annuler.setFocusable(false);
        btn_annuler.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_annuler.setMargin(new java.awt.Insets(2, 7, 2, 7));
        btn_annuler.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_annulerActionPerformed(evt);
            }
        });
        toolBar_clients.add(btn_annuler);

        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/sub_blue_delete.png"))); // NOI18N
        btn_delete.setToolTipText("Supprimer");
        btn_delete.setFocusable(false);
        btn_delete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_delete.setMargin(new java.awt.Insets(2, 7, 2, 7));
        btn_delete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        toolBar_clients.add(btn_delete);

        add(toolBar_clients, java.awt.BorderLayout.WEST);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_firstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_firstActionPerformed
        ind = 0;
        tbl_clients.changeSelection(ind, 1, false, false);

    }//GEN-LAST:event_btn_firstActionPerformed

    private void btn_precActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_precActionPerformed
        ind--;
        if (ind < 0) {
            ind = 0;
        }
        tbl_clients.changeSelection(ind, 0, false, false);


    }//GEN-LAST:event_btn_precActionPerformed

    private void btn_suivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suivActionPerformed
        ind++;
        if (ind >= clients.size() - 1) {
            // JOptionPane.showMessageDialog(null,"entre"+ ind);
            ind = clients.size() - 1;
        }
        tbl_clients.changeSelection(ind, 1, false, false);

    }//GEN-LAST:event_btn_suivActionPerformed

    private void btn_lastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lastActionPerformed
        ind = clients.size() - 1;
        tbl_clients.changeSelection(ind, 1, false, false);

    }//GEN-LAST:event_btn_lastActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:

        try {
            if ("Ajouter".equals(btn_add.getToolTipText())) {
                actualiser();
                txt_ste_name.setEditable(true);
                txt_ste_name.setFocusable(true);
                txt_clt_nom.setEditable(true);
                txt_clt_prenom.setEditable(true);
                txt_fix1.setEditable(true);
                txt_fix2.setEditable(true);
                txt_gsm1.setEditable(true);
                txt_gsm2.setEditable(true);
                txt_email.setEditable(true);
                txt_site_web.setEditable(true);
                txt_adresse.setEditable(true);
                btn_add.setToolTipText("Enregistrer");

                btn_edit.setEnabled(false);
                btn_atteindre.setEnabled(false);
                btn_delete.setEnabled(false);
                tbl_clients.setEnabled(false);
                btn_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/save.png")));
            } else {
                String query;
                Client clt = new Client();
                Boolean oki = clt.setNom(txt_clt_nom.getText());
                if (oki) {
                    clt.setSte(txt_ste_name.getText());

                    clt.setPrenom(txt_clt_prenom.getText());
                    oki = oki && clt.setFix1(txt_fix1.getText());
                    oki = oki && clt.setFix2(txt_fix2.getText());
                    oki = oki && clt.setGsm1(txt_gsm1.getText());
                    oki = oki && clt.setGsm2(txt_gsm2.getText());
                    oki = oki && clt.setSiteweb(txt_site_web.getText());
                    oki = oki && clt.setMail(txt_email.getText());
                    clt.setAdresse(txt_adresse.getText());
                    String str = (!oki ? "Un certain nombre de données ne sont pas correctes et elles ne seront pas ajoutées !\n" : "");
                    if (JOptionPane.showConfirmDialog(null, str + "Voulez-vous vraiment ajouter ce client ?", "Confirmation d'ajout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        query = "INSERT INTO agipub.client (clt_ste, clt_nom, clt_prenom, clt_fix1, clt_fix2, clt_gsm1, clt_gsm2, clt_mail, clt_siteweb, clt_adresse) "
                                + " VALUES ('" + clt.getSte()
                                + "','" + clt.getNom() + "','"
                                + clt.getPrenom() + "','"
                                + clt.getFix1() + "','" + clt.getFix2() + "','"
                                + clt.getGsm1() + "','" + clt.getGsm2() + "','"
                                + clt.getMail() + "','" + clt.getSiteweb() + "','"
                                + clt.getAdresse() + "')";
                        state = Connexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        state.executeUpdate(query);
                        btn_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/plus.png")));
                        btn_add.setToolTipText("Ajouter");
                        state.close();
                        JOptionPane.showMessageDialog(null, "Client ajouté avec succès");
                        btn_edit.setEnabled(true);
                        btn_atteindre.setEnabled(true);
                        btn_delete.setEnabled(true);
                        
                  tbl_clients.setEnabled(true);
                        actualiser();
                        chargeClient();
                        //   ((DefaultTableModel) tbl_clients.getModel()).fireTableDataChanged();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Le nom du client est Obligatoire !");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        // TODO add your handling code here:
        try {
            if ("Modifier".equals(btn_edit.getToolTipText())) {
                //actualiser();
                ind = tbl_clients.getSelectedRow();
                if (ind < 0) {
                    JOptionPane.showMessageDialog(null, "Vous devez séléctionner le client à modifier !");
                } else {
                    txt_ste_name.setEditable(true);
                    txt_ste_name.setFocusable(true);
                    txt_clt_nom.setEditable(true);
                    txt_clt_prenom.setEditable(true);
                    txt_fix1.setEditable(true);
                    txt_fix2.setEditable(true);
                    txt_gsm1.setEditable(true);
                    txt_gsm2.setEditable(true);
                    txt_email.setEditable(true);
                    txt_site_web.setEditable(true);
                    txt_adresse.setEditable(true);
                    btn_edit.setToolTipText("Enregistrer");
                    btn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/save.png")));
                    btn_add.setEnabled(false);
                    btn_atteindre.setEnabled(false);
                    btn_delete.setEnabled(false);
                tbl_clients.setEnabled(false);
                }
            } else {
                String query;
                //Client clt = new Client();
                Boolean oki = clients.get(ind).setNom(txt_clt_nom.getText());
                if (oki) {
                    clients.get(ind).setSte(txt_ste_name.getText());

                    clients.get(ind).setPrenom(txt_clt_prenom.getText());
                    oki = oki && clients.get(ind).setFix1(txt_fix1.getText());
                    oki = oki && clients.get(ind).setFix2(txt_fix2.getText());
                    oki = oki && clients.get(ind).setGsm1(txt_gsm1.getText());
                    oki = oki && clients.get(ind).setGsm2(txt_gsm2.getText());
                    oki = oki && clients.get(ind).setSiteweb(txt_site_web.getText());
                    oki = oki && clients.get(ind).setMail(txt_email.getText());
                    clients.get(ind).setAdresse(txt_adresse.getText());
                    String str = (!oki ? "Un certain nombre de données ne sont pas correctes et elles ne seront pas ajoutées !\n" : "");
                    if (JOptionPane.showConfirmDialog(null, str + "Voulez-vous vraiment modifier ce client ?", "Confirmation d'ajout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        query = "UPDATE agipub.client set clt_ste =?, clt_nom=?, clt_prenom=?, clt_fix1=?, clt_fix2=?, clt_gsm1=?, clt_gsm2=?, clt_mail=?, clt_siteweb=?, clt_adresse=? WHERE id_client=? ";
                        try (java.sql.PreparedStatement prepare = Connexion.getInstance().prepareStatement(query)) {
                            prepare.setString(1, clients.get(ind).getSte());
                            prepare.setString(2, clients.get(ind).getNom());
                            prepare.setString(3, clients.get(ind).getPrenom());
                            prepare.setString(4, clients.get(ind).getFix1());
                            prepare.setString(5, clients.get(ind).getFix2());
                            prepare.setString(6, clients.get(ind).getGsm1());
                            prepare.setString(7, clients.get(ind).getGsm2());
                            prepare.setString(8, clients.get(ind).getMail());
                            prepare.setString(9, clients.get(ind).getSiteweb());
                            prepare.setString(10, clients.get(ind).getAdresse());
                            prepare.setInt(11, clients.get(ind).getId());
                            prepare.executeUpdate();

                            btn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/graphic_design.png")));
                            btn_edit.setToolTipText("Modifier");

                            btn_add.setEnabled(true);
                            btn_atteindre.setEnabled(true);
                            btn_delete.setEnabled(true);
                        }
                        JOptionPane.showMessageDialog(null, "Client N° : " + clients.get(ind).getId() + " Modifié avec succès");
                     tbl_clients.setEnabled(true);
                        actualiser();
                        chargeClient();
                        //   ((DefaultTableModel) tbl_clients.getModel()).fireTableDataChanged();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Le nom du client est Obligatoire !");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_annulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_annulerActionPerformed
        // TODO add your handling code here:

        btn_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/plus.png")));
        btn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/graphic_design.png")));
        btn_edit.setToolTipText("Modifier");
        btn_add.setToolTipText("Ajouter");
               tbl_clients.setEnabled(true);
        actualiser();

        btn_add.setEnabled(true);
        btn_edit.setEnabled(true);
        btn_atteindre.setEnabled(true);
        btn_delete.setEnabled(true);

        txt_code.setText("");
    }//GEN-LAST:event_btn_annulerActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        if (ind < 0) {
            JOptionPane.showMessageDialog(null, "Aucun Client séléctionné !");
        } else {
            if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce client ?\nCette opération est irréversible !!!\nToutes les commandes de ce client seront supprimées aussi !", "Confirmation d'ajout", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                try {
                    java.sql.PreparedStatement prepare = Connexion.getInstance().prepareStatement("DELETE FROM agipub.client WHERE id_client=?");
                    prepare.setInt(1, clients.get(ind).getId());
                    prepare.executeUpdate();
                    prepare.close();
                    actualiser();
                    chargeClient();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur en suppression :\n" + ex.getMessage());
                    Logger.getLogger(pan_clients.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_atteindreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atteindreActionPerformed
        int code;
        String rep = JOptionPane.showInputDialog("Saisir le code client à atteindre");
        Boolean existe = false;
        if (rep != null) {
            code = Integer.parseInt(rep);
            for (int i = 0; i < clients.size() && !existe; i++) {
                if (clients.get(i).getId() == code) {
                    tbl_clients.changeSelection(i, 0, false, false);
                    existe = true;
                }
            }
            if (!existe) {
                JOptionPane.showMessageDialog(null, "Ce code client n'existe pas !");
            }
        }


    }//GEN-LAST:event_btn_atteindreActionPerformed

    private void btn_refrechActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refrechActionPerformed
        // TODO add your handling code here:
        actualiser();
        chargeClient();
    }//GEN-LAST:event_btn_refrechActionPerformed
    private void actualiser() {
        Object objs[];

        objs = pnl_clients.getComponents();
        for (Object obj : objs) {
            //JOptionPane.showMessageDialog(null, obj.getClass().getSimpleName());
            switch (obj.getClass().getSimpleName()) {
                case "JTextField":
                    ((JTextField) obj).setText("");
                    ((JTextField) obj).setEditable(false);
                    break;
                case "JTextArea":
                    ((JTextArea) obj).setText("");
                    ((JTextArea) obj).setEditable(false);
                    break;
                case "JFormattedTextField":
                    ((JFormattedTextField) obj).setText("");
                    ((JFormattedTextField) obj).setEditable(false);
                    break;
            }
        }
        tbl_clients.clearSelection();
        ind = -1;
        //chargeClient();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_annuler;
    private javax.swing.JButton btn_atteindre;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_first;
    private javax.swing.JButton btn_last;
    private javax.swing.JButton btn_prec;
    private javax.swing.JButton btn_refrech;
    private javax.swing.JButton btn_suiv;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JLabel lbl_ste_name;
    private javax.swing.JLabel lbl_ste_name10;
    private javax.swing.JLabel lbl_ste_name11;
    private javax.swing.JLabel lbl_ste_name12;
    private javax.swing.JLabel lbl_ste_name13;
    private javax.swing.JLabel lbl_ste_name14;
    private javax.swing.JLabel lbl_ste_name15;
    private javax.swing.JLabel lbl_ste_name16;
    private javax.swing.JLabel lbl_ste_name17;
    private javax.swing.JLabel lbl_ste_name9;
    private javax.swing.JPanel pnl_clients;
    private javax.swing.JScrollPane pnl_edition_client;
    private javax.swing.JSplitPane splitPane_Clients;
    private javax.swing.JTable tbl_clients;
    private javax.swing.JToolBar toolBar_clients;
    private javax.swing.JTextArea txt_adresse;
    private javax.swing.JTextField txt_clt_nom;
    private javax.swing.JTextField txt_clt_prenom;
    private javax.swing.JTextField txt_code;
    private javax.swing.JFormattedTextField txt_email;
    private javax.swing.JFormattedTextField txt_fix1;
    private javax.swing.JFormattedTextField txt_fix2;
    private javax.swing.JFormattedTextField txt_gsm1;
    private javax.swing.JFormattedTextField txt_gsm2;
    private javax.swing.JTextField txt_site_web;
    private javax.swing.JTextField txt_ste_name;
    // End of variables declaration//GEN-END:variables
}
