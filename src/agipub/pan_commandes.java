/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agipub;

import java.awt.HeadlessException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import object.Client;
import object.Commande;
import object.DetailCommand;
import object.FrenchNumberToWords;
import object.Multipage;
import object.Papier;

/**
 *
 * @author win_bmx
 */
public class pan_commandes extends javax.swing.JPanel {

    int indexCmd = -1, indexDet = -1;
    Statement state;
    ResultSet result;

    ArrayList<Client> dataClient = new ArrayList<>();
    ArrayList<Commande> dataCmd = new ArrayList<>();
    DetailCommand detailsCmd;
    String[] titlesCmd = new String[]{
        "Code Commande", "Code Client", "Date Commande", "Date Livraison", "Nbre Travaux", "Prix HT", "Prix TT", "Avance"
    };
    String[] titlesDetails = new String[]{
        "Document", "Papier", "Hauteur", "Largeur", "Nbre Couleurs", "Quantité", "Prix d'Impression", "Multipage"
    };

    /**
     * Creates new form pan_commandes
     */
    public pan_commandes() {
        initComponents();
        chargeCommandes();
        chargeComboClient();
        tabCmd_selectChanged();
        desactiverDetails();
        tabDet_selectChanged();
    }

    private void chargeClient() {
        try {
            state = Connexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            result = state.executeQuery("SELECT * FROM client");
            dataClient.removeAll(dataClient);
            Client clt;

            while (result.next()) {
                clt = new Client();
                clt.setId(result.getInt("id_client"));
                clt.setSte(result.getString("clt_ste"));
                clt.setNom(result.getString("clt_nom"));
                clt.setPrenom(result.getString("clt_prenom"));

                clt.setFix1(result.getString("clt_fix1"));
                clt.setFix2(result.getString("clt_fix2"));
                clt.setGsm1(result.getString("clt_gsm1"));
                clt.setGsm2(result.getString("clt_gsm2"));
                clt.setSiteweb(result.getString("clt_siteweb"));
                clt.setMail(result.getString("clt_mail"));
                clt.setAdresse(result.getString("clt_adresse"));
                dataClient.add(clt);
            }
            state.close();
            result.close();

        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ereur au chargement des clients\n" + ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void chargeComboClient() {
        cmb_num_client.removeAllItems();
        if (dataClient.size() < 1) {
            chargeClient();
        }
        for (int i = 0; i < dataClient.size(); i++) {
            cmb_num_client.addItem(dataClient.get(i).toString());
        }
    }

    private void tabCmd_selectChanged() {
        tbl_commandes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {

                    indexCmd = tbl_commandes.getSelectedRow();
                    //JOptionPane.showMessageDialog(null, ind);
                    if (indexCmd > -1) {

                        for (int i = 0; i < dataClient.size(); i++) {
                            if (dataClient.get(i).getId() == dataCmd.get(indexCmd).getIdClient()) {
                                cmb_num_client.setSelectedIndex(i);
                                break;
                            }
                        }
                        txt_code.setText(String.valueOf(dataCmd.get(indexCmd).getIdCmd()));
                        jDate_cmd.setDate(dataCmd.get(indexCmd).getDateCmd());
                        jDate_livraisan.setDate(dataCmd.get(indexCmd).getDateLivraison());
                        txt_avance.setText(String.valueOf(dataCmd.get(indexCmd).getAvance()));
                        chargeDetails();
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur au changment de selection : \n" + ex.getMessage());
                    Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void tabDet_selectChanged() {
        tbl_detail_cmd.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {

                    indexDet = tbl_detail_cmd.getSelectedRow();
                    //JOptionPane.showMessageDialog(null, ind);
                    if (indexDet > -1) {
                      //  JOptionPane.showMessageDialog(null, dataCmd.get(indexCmd).getDetails().get(indexDet).getNbre_feuille_consom());
                        if (dataCmd.get(indexCmd).getDetails().get(indexDet).getIsMultiple()) {
                            lbl_papier.setText(dataCmd.get(indexCmd).getDetails().get(indexDet).getMultip().getPapier().toString());
                            lbl_nbrePage.setText(String.valueOf(dataCmd.get(indexCmd).getDetails().get(indexDet).getMultip().getNbre_page()));
                            lbl_nbreClr.setText(String.valueOf(dataCmd.get(indexCmd).getDetails().get(indexDet).getMultip().getNbre_couleur()));
                            lbl_ep.setText(String.valueOf(dataCmd.get(indexCmd).getDetails().get(indexDet).getMultip().getEpaisseur_bande()));
                        } else {
                            lbl_papier.setText("");
                            lbl_nbrePage.setText("");
                            lbl_nbreClr.setText("");
                            lbl_ep.setText("");
                        }
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur au changment de selection : \n" + ex.getMessage());
                    Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void chargeCommandes() {
        try {
            indexDet = -1;
            indexCmd = -1;
            state = Connexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Object[][] data;
            result = state.executeQuery("SELECT * FROM commande");
            dataCmd.removeAll(dataCmd);
            Commande cmd;

            while (result.next()) {
                cmd = new Commande();
                cmd.setIdCmd(result.getInt("id_cmd"));
                cmd.setIdClient(result.getInt("id_client"));
                cmd.setDateCmd(result.getDate("date_cmd"));
                cmd.setDateLivraison(result.getDate("date_livraison"));
                cmd.setNbreTravaux(result.getInt("nbre_travaux"));
                cmd.setPrixHt(result.getDouble("prix_ht"));
                cmd.setPrixTt(result.getDouble("prix_tt"));
                cmd.setAvance(result.getDouble("avance"));

                dataCmd.add(cmd);
            }
            result.last();
            data = new Object[result.getRow()][titlesCmd.length];
            int j = 1;
            result.beforeFirst();

            while (result.next()) {
                for (int i = 1; i <= titlesCmd.length; i++) {
                    data[j - 1][i - 1] = result.getObject(i);
                }
                j++;

            }
            tbl_commandes.setModel(new DefaultTableModel(data, titlesCmd) {

                Class[] types = new Class[]{
                    java.lang.Integer.class, java.lang.Integer.class, java.util.Date.class, java.util.Date.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
                };

                @Override
                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }

            });
            // tbl_clients.setCellEditor(null);

            state.close();
            result.close();

        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ereur au chargement\n" + ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ereur au chargement\n" + ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void chargeDetails() {
        try {
            if (indexCmd > -1) {
                indexDet = -1;
                state = Connexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                Object[][] data;
                String query = "SELECT d.* FROM detailcmd d inner join commande c on c.id_cmd=d.id_cmd WHERE c.id_cmd=" + dataCmd.get(indexCmd).getIdCmd();
                //JOptionPane.showMessageDialog(null, query);
                result = state.executeQuery(query);
                dataCmd.get(indexCmd).getDetails().clear();
                DetailCommand cmd;
                ResultSet resultSet = null;
                ArrayList<Integer> p = new ArrayList<>();
                ArrayList<Integer> m = new ArrayList<>();
                while (result.next()) {
                    cmd = new DetailCommand();
                    cmd.setIdCmd(result.getInt("id_cmd"));
                    cmd.setDocument(result.getString("document"));
                    p.add(result.getInt("id_papier"));
                    cmd.setHauteur(result.getFloat("hauteur"));
                    cmd.setLargeur(result.getFloat("largeur"));
                    cmd.setNbreCouleur(result.getInt("nbre_couleur"));
                    cmd.setQte(result.getInt("qte"));
                    cmd.setPrixConception(result.getDouble("prix_conception"));
                    cmd.setPrixMatiere(result.getDouble("prix_matieres"));
                    cmd.setPrixPapier(result.getDouble("prix_papier"));
                    cmd.setPrixImpression(result.getDouble("prix_impression"));
                    cmd.setDesignation(result.getString("designation"));
                    cmd.setNbre_feuille_consom(result.getDouble("nbre_feuille_consom"));
                    
                    cmd.setIsMultiple(result.getBoolean("isMultipage"));
                    if (cmd.getIsMultiple()) {
                        m.add(result.getInt("id_multip"));
                    }
                    dataCmd.get(indexCmd).getDetails().add(cmd);
                    
                }

                for (int i = 0; i < dataCmd.get(indexCmd).getDetails().size(); i++) {
                    int id_pap = p.get(i);
                    resultSet = state.executeQuery("select * from papier where id_papier = " + id_pap);
                    resultSet.first();
                    Papier pap = new Papier();
                    pap.setId_papier(resultSet.getInt("id_papier"));
                    pap.setNom_papier(resultSet.getString("nom_papier"));
                    pap.setGrammage(resultSet.getInt("grammage"));
                    pap.setCouleur(resultSet.getString("couler"));
                    pap.setHauteur(resultSet.getFloat("hauteur"));
                    pap.setLargeur(resultSet.getFloat("largeur"));
                    pap.setQte(resultSet.getDouble("qte_stock"));
                    dataCmd.get(indexCmd).getDetails().get(i).setPapier(pap);
                }
                if (resultSet != null) {
                    resultSet.close();
                }
                p.clear();
                int y = 0;
                for (int i = 0; i < dataCmd.get(indexCmd).getDetails().size(); i++) {
                    if (dataCmd.get(indexCmd).getDetails().get(i).getIsMultiple()) {
                        resultSet = state.executeQuery("select * from multipage where id_multip = " + m.get(y++));
                        resultSet.first();
                        Multipage mult = new Multipage();
                        mult.setId_multip(resultSet.getInt("id_multip"));
                        mult.setNbre_couleur(resultSet.getInt("nbre_couleur"));
                        mult.setNbre_page(resultSet.getInt("nbre_page"));
                        mult.setNbre_feuille_consom(resultSet.getDouble("nbre_feuille_consom"));
//                        JOptionPane.showMessageDialog(null, resultSet.getDouble("nbre_feuille_consom"));
                        // mult.setPrixPapier(resultSet.getDouble("prix_papier"));
                        p.add(resultSet.getInt("id_papier"));
                        mult.setEpaisseur_bande(resultSet.getFloat("epaisseur_bande"));
                        dataCmd.get(indexCmd).getDetails().get(i).setMultip(mult);
                    }
                }
                if (resultSet != null) {
                    resultSet.close();
                }
                y = 0;
                for (int i = 0; i < dataCmd.get(indexCmd).getDetails().size(); i++) {
                    if (dataCmd.get(indexCmd).getDetails().get(i).getIsMultiple()) {
                        int id_pap = p.get(y++);
                        resultSet = state.executeQuery("select * from papier where id_papier = " + id_pap);
                        resultSet.first();
                        Papier pap = new Papier();
                        pap.setId_papier(resultSet.getInt("id_papier"));
                        pap.setNom_papier(resultSet.getString("nom_papier"));
                        pap.setGrammage(resultSet.getInt("grammage"));
                        pap.setCouleur(resultSet.getString("couler"));
                        pap.setHauteur(resultSet.getFloat("hauteur"));
                        pap.setLargeur(resultSet.getFloat("largeur"));
                        pap.setQte(resultSet.getDouble("qte_stock"));
                        dataCmd.get(indexCmd).getDetails().get(i).getMultip().setPapier(pap);
                    }
                }
                if (resultSet != null) {
                    resultSet.close();
                }
//                result.last();
                data = new Object[dataCmd.get(indexCmd).getDetails().size()][titlesDetails.length];
//                int j = 1;
//                result.beforeFirst();
                state.close();
                result.close();
                for (int i = 0; i < dataCmd.get(indexCmd).getDetails().size(); i++) {

                    data[i][0] = dataCmd.get(indexCmd).getDetails().get(i).getDocument();
                    data[i][1] = dataCmd.get(indexCmd).getDetails().get(i).getPapier().toString();
                    data[i][2] = dataCmd.get(indexCmd).getDetails().get(i).getHauteur();
                    data[i][3] = dataCmd.get(indexCmd).getDetails().get(i).getLargeur();
                    data[i][4] = dataCmd.get(indexCmd).getDetails().get(i).getNbreCouleur();
                    data[i][5] = dataCmd.get(indexCmd).getDetails().get(i).getQte();

                    data[i][6] = dataCmd.get(indexCmd).getDetails().get(i).getPrixImpression();
                    data[i][7] = dataCmd.get(indexCmd).getDetails().get(i).getIsMultiple();

                }

//                while (result.next()) {
//                    for (int i = 1; i <= titlesDetails.length; i++) {
//                        data[j - 1][i - 1] = result.getObject(i);
//                    }
//                    j++;
//
//                }
                tbl_detail_cmd.setModel(new DefaultTableModel(data, titlesDetails) {

                    Class[] types = new Class[]{
                        java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Boolean.class
                    };

                    @Override
                    public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                    }

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return false;
                    }

                });
                //tbl_detail_cmd.setDefaultRenderer(Color.class, new ColorCellRenderer());

            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ereur au chargement \n" + ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ereur au chargement\n" + ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void viderDetails() {
        cmb_num_client.setSelectedIndex(-1);
        jDate_cmd.setDate(null);
        jDate_livraisan.setDate(null);
        for (int i = tbl_detail_cmd.getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) tbl_detail_cmd.getModel()).removeRow(i);
        }

    }

    private void activerDetails() {
        cmb_num_client.setEnabled(true);
        jDate_cmd.setEnabled(true);
        jDate_livraisan.setEnabled(true);
        txt_avance.setEnabled(true);
//        for (int i = 0; i < tools_details.getComponentCount(); i++) {
//            if ("JButton".equals(tools_details.getComponent(i).getClass().getSimpleName())) {
//                tools_details.getComponent(i).setEnabled(true);
//            }
//        }
        btn_atteindre.setEnabled(false);
        btn_first.setEnabled(false);
        btn_suiv.setEnabled(false);
        btn_prec.setEnabled(false);
        btn_last.setEnabled(false);
        btn_delete.setEnabled(false);
        tbl_commandes.setEnabled(false);
    }

    private void desactiverDetails() {
        cmb_num_client.setEnabled(false);
        jDate_cmd.setEnabled(false);
        jDate_livraisan.setEnabled(false);
        txt_avance.setEnabled(false);
//        for (int i = 0; i < tools_details.getComponentCount(); i++) {
//            if ("JButton".equals(tools_details.getComponent(i).getClass().getSimpleName())) {
//                tools_details.getComponent(i).setEnabled(false);
//            }
//        }
        btn_atteindre.setEnabled(true);
        btn_first.setEnabled(true);
        btn_suiv.setEnabled(true);
        btn_prec.setEnabled(true);
        btn_last.setEnabled(true);
        btn_delete.setEnabled(true);
        tbl_commandes.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane_commande = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_commandes = new javax.swing.JTable();
        pnl_edition_commandes = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmb_num_client = new javax.swing.JComboBox();
        jDate_cmd = new com.toedter.calendar.JDateChooser();
        jDate_livraisan = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        txt_avance = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lbl_papier = new javax.swing.JLabel();
        lbl_nbrePage = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_nbreClr = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbl_ep = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_detail_cmd = new javax.swing.JTable();
        tools_details = new javax.swing.JToolBar();
        btn_refrech_alimentation = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btn_add_alimentation = new javax.swing.JButton();
        btn_edit_alimentation = new javax.swing.JButton();
        btn_delete_alimentation = new javax.swing.JButton();
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
        btn_facturer = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        splitPane_commande.setDividerLocation(500);
        splitPane_commande.setDividerSize(15);
        splitPane_commande.setLastDividerLocation(splitPane_commande.getWidth()/2);
        splitPane_commande.setOneTouchExpandable(true);

        jPanel3.setLayout(new java.awt.BorderLayout());

        tbl_commandes.setShowGrid(true);
        tbl_commandes.setRowHeight(32);
        tbl_commandes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Code Commande", "Code Client", "Date Commande", "Date Livraison", "Nbre Travaux", "Prix HT", "Prix TT", "Avance"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_commandes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(tbl_commandes);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        splitPane_commande.setLeftComponent(jPanel3);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Num Client : ");

        jLabel2.setText("Date Commande : ");

        jLabel3.setText("Date Livraison : ");

        cmb_num_client.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jDate_cmd.setDate(Calendar.getInstance().getTime());
        jDate_cmd.setMinSelectableDate(new java.util.Date(-62135765909000L));

        jLabel4.setText("Avance : ");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Document Multipage"));

        jLabel5.setText("Papier : ");

        jLabel6.setText("Nbre pages :");

        jLabel7.setText("Nbre Couleurs :");

        jLabel8.setText("Epaisseur : ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_ep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_nbreClr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_nbrePage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_papier, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lbl_papier, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbl_nbrePage, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lbl_nbreClr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lbl_ep, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmb_num_client, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDate_livraisan, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                            .addComponent(jDate_cmd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_avance))))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cmb_num_client, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jDate_cmd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jDate_livraisan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_avance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel5.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Détails Commande"));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setPreferredSize(new java.awt.Dimension(300, 402));

        tbl_detail_cmd.setRowHeight(32);
        tbl_detail_cmd.setShowGrid(true);
        tbl_detail_cmd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Document", "Papier", "Hauteur", "Largeur", "Quantité", "Nbre Couleurs", "Prix d'Impression", "Multipage"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tbl_detail_cmd);

        jPanel1.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        tools_details.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tools_details.setOrientation(javax.swing.SwingConstants.VERTICAL);
        tools_details.setRollover(true);

        btn_refrech_alimentation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/interact_16px.png"))); // NOI18N
        btn_refrech_alimentation.setToolTipText("Actualiser");
        btn_refrech_alimentation.setFocusable(false);
        btn_refrech_alimentation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_refrech_alimentation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_refrech_alimentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refrech_alimentationActionPerformed(evt);
            }
        });
        tools_details.add(btn_refrech_alimentation);
        tools_details.add(jSeparator4);

        btn_add_alimentation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/plus_16px.png"))); // NOI18N
        btn_add_alimentation.setToolTipText("Ajouter");
        btn_add_alimentation.setFocusable(false);
        btn_add_alimentation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_add_alimentation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_add_alimentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_alimentationActionPerformed(evt);
            }
        });
        tools_details.add(btn_add_alimentation);

        btn_edit_alimentation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/graphic_design_16px.png"))); // NOI18N
        btn_edit_alimentation.setToolTipText("Modifier");
        btn_edit_alimentation.setFocusable(false);
        btn_edit_alimentation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_edit_alimentation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_edit_alimentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_edit_alimentationActionPerformed(evt);
            }
        });
        tools_details.add(btn_edit_alimentation);

        btn_delete_alimentation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/sub_blue_delete_16px.png"))); // NOI18N
        btn_delete_alimentation.setToolTipText("Supprimer");
        btn_delete_alimentation.setFocusable(false);
        btn_delete_alimentation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_delete_alimentation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_delete_alimentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_alimentationActionPerformed(evt);
            }
        });
        tools_details.add(btn_delete_alimentation);

        jPanel1.add(tools_details, java.awt.BorderLayout.EAST);

        jPanel5.add(jPanel1, java.awt.BorderLayout.CENTER);

        pnl_edition_commandes.setViewportView(jPanel5);

        splitPane_commande.setRightComponent(pnl_edition_commandes);

        add(splitPane_commande, java.awt.BorderLayout.CENTER);

        toolBar_clients.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        btn_facturer.setText("Facturer");
        btn_facturer.setFocusable(false);
        btn_facturer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_facturer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_facturer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_facturerActionPerformed(evt);
            }
        });
        toolBar_clients.add(btn_facturer);

        add(toolBar_clients, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_atteindreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atteindreActionPerformed
        // TODO add your handling code here:
        int code;
        String rep = JOptionPane.showInputDialog("Saisir le code commande à atteindre");
        Boolean existe = false;
        if (rep != null) {
            code = Integer.parseInt(rep);
            for (int i = 0; i < dataCmd.size() && !existe; i++) {
                if (dataCmd.get(i).getIdCmd() == code) {
                    tbl_commandes.changeSelection(i, 0, false, false);
                    existe = true;
                }
            }
            if (!existe) {
                JOptionPane.showMessageDialog(null, "Ce code commande n'existe pas !");
            }
        }

    }//GEN-LAST:event_btn_atteindreActionPerformed

    private void btn_refrechActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refrechActionPerformed
        // TODO add your handling code here:
        viderDetails();
        btn_annulerActionPerformed(evt);
        chargeCommandes();
        chargeComboClient();
    }//GEN-LAST:event_btn_refrechActionPerformed

    private void btn_firstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_firstActionPerformed
        // TODO add your handling code here:
        indexCmd = 0;
        tbl_commandes.changeSelection(indexCmd, 1, false, false);
    }//GEN-LAST:event_btn_firstActionPerformed

    private void btn_precActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_precActionPerformed
        // TODO add your handling code here:
        indexCmd--;
        if (indexCmd < 0) {
            indexCmd = 0;
        }
        tbl_commandes.changeSelection(indexCmd, 1, false, false);
    }//GEN-LAST:event_btn_precActionPerformed

    private void btn_suivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suivActionPerformed
        // TODO add your handling code here:
        indexCmd++;
        if (indexCmd >= dataCmd.size() - 1) {
            indexCmd = dataCmd.size() - 1;
        }
        tbl_commandes.changeSelection(indexCmd, 1, false, false);
    }//GEN-LAST:event_btn_suivActionPerformed

    private void btn_lastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lastActionPerformed
        // TODO add your handling code here:
        indexCmd = dataCmd.size() - 1;
        tbl_commandes.changeSelection(indexCmd, 1, false, false);
    }//GEN-LAST:event_btn_lastActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:
        try {
            if ("Ajouter".equals(btn_add.getToolTipText())) {
                tbl_commandes.clearSelection();
                viderDetails();
                activerDetails();
                btn_add.setToolTipText("Enregistrer");
                btn_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/save.png")));
                btn_edit.setEnabled(false);
                jDate_cmd.setDate(Calendar.getInstance().getTime());

            } else {
                Boolean oki = true;
                String query;
                Commande cmd = new Commande();
                cmd.setIdClient(dataClient.get(cmb_num_client.getSelectedIndex()).getId());
                cmd.setDateCmd(jDate_cmd.getDate());
                cmd.setDateLivraison(jDate_livraisan.getDate());
                double av = txt_avance.getText().equals("") ? 0 : Double.valueOf(txt_avance.getText());
                cmd.setAvance(av);

                if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment ajouter cette commande ?", "Confirmation d'ajout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    Calendar cal = Calendar.getInstance();
                    if (jDate_cmd.getDate() == null) {
                        jDate_cmd.setDate(Calendar.getInstance().getTime());
                    }
                    cal.setTime(cmd.getDateCmd());
                    String dCmd = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONDAY) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
                    String dLiv = null;
                    query = "INSERT INTO agipub.commande (id_client, date_cmd, nbre_travaux, prix_ht, prix_tt, avance) "
                            + " VALUES (" + cmd.getIdClient() + ", '" + dCmd + "', " + cmd.getNbreTravaux() + ", " + cmd.getPrixHt() + ", " + cmd.getPrixTt() + ", " + cmd.getAvance() + ")";
                    if (jDate_livraisan.getDate() != null) {
                        cal.setTime(cmd.getDateLivraison());
                        dLiv = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONDAY) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
                        query = "INSERT INTO agipub.commande (id_client, date_cmd, date_livraison, nbre_travaux, prix_ht, prix_tt, avance) "
                                + " VALUES (" + cmd.getIdClient() + ", '" + dCmd + "', '" + dLiv + "', " + cmd.getNbreTravaux() + ", " + cmd.getPrixHt() + ", " + cmd.getPrixTt() + ", " + cmd.getAvance() + ")";
                    }

                    //  JOptionPane.showMessageDialog(null, query);
                    state = Connexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    int key = 0;
                    state.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
                    result = state.getGeneratedKeys();
                    if (result != null && result.next()) {
                        key = result.getInt(1);
                    }
                    cmd.setIdCmd(key);
                    dataCmd.add(cmd);
                    // JOptionPane.showMessageDialog(null, query);

                    state.close();
                    btn_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/plus.png")));
                    btn_add.setToolTipText("Ajouter");

                    JOptionPane.showMessageDialog(null, "Commande ajouté avec succès");
                    ((DefaultTableModel) tbl_commandes.getModel()).addRow(new Object[]{cmd.getIdCmd(), cmd.getIdClient(), cmd.getDateCmd(), cmd.getDateLivraison(), cmd.getNbreTravaux(), cmd.getPrixHt(), cmd.getPrixTt(), cmd.getAvance()});
                    btn_add.setToolTipText("Ajouter");
                    btn_edit.setEnabled(true);
                    desactiverDetails();
                }

                JOptionPane.showMessageDialog(null, "Cette commande est vide !!", "Commande vide !", JOptionPane.WARNING_MESSAGE);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_refrech_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refrech_alimentationActionPerformed
        // TODO add your handling code here:
        chargeDetails();
    }//GEN-LAST:event_btn_refrech_alimentationActionPerformed

    private void btn_annulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_annulerActionPerformed
        // TODO add your handling code here:

        btn_add.setToolTipText("Ajouter");
        btn_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/plus.png")));
        btn_add.setEnabled(true);
        btn_edit.setToolTipText("Modifier");
        btn_edit.setEnabled(true);
        btn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/graphic_design.png")));
        indexCmd = -1;
        desactiverDetails();


    }//GEN-LAST:event_btn_annulerActionPerformed

    private void btn_add_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_alimentationActionPerformed
        // TODO add your handling code here:
        if (indexCmd > -1) {
            detailCommande d = new detailCommande(dataCmd.get(indexCmd).getIdCmd());
            DetailCommand dCmd = d.showDialog();
            if (dCmd != null) {
                try {
                    detailsCmd = dCmd;
                    ((DefaultTableModel) tbl_detail_cmd.getModel()).addRow(new Object[]{detailsCmd.getDocument(), detailsCmd.getPapier().toString(), detailsCmd.getHauteur(), detailsCmd.getLargeur(), detailsCmd.getNbreCouleur(), detailsCmd.getQte(), detailsCmd.getPrixImpression(), detailsCmd.getIsMultiple()});
                    DetailCommand c = detailsCmd;
                    c.setDesignation(c.toString());
                    String query = "";
                    if (c.getIsMultiple()) {
                        query = "INSERT INTO agipub.detailcmd (id_cmd, document, id_papier, hauteur, largeur, nbre_couleur, qte, prix_conception, prix_matieres , prix_papier, prix_impression, isMultipage, id_multip , designation, prix_unitaire , nbre_feuille_consom) "
                                + " VALUES (" + c.getIdCmd() + ", '" + c.getDocument() + "', " + c.getPapier().getId_papier() + ", " + c.getHauteur() + "," + c.getLargeur() + " , " + c.getNbreCouleur() + ", " + c.getQte() + ", " + c.getPrixConception() + ", " + c.getPrixMatiere() + ", " + c.getPrixPapier() + " , " + c.getPrixImpression() + ", " + c.getIsMultiple() + ", " + c.getMultip().getId_multip() + " , '" + c.getDesignation() + "' , " + c.getPrix_unitaire() +" , "+c.getNbre_feuille_consom()+ ");";
                        lbl_papier.setText(c.getMultip().getPapier().toString());
                        lbl_nbrePage.setText(String.valueOf(c.getMultip().getNbre_page()));
                        lbl_nbreClr.setText(String.valueOf(c.getMultip().getNbre_couleur()));
                        lbl_ep.setText(String.valueOf(c.getMultip().getEpaisseur_bande()));
                    } else {
                        query = "INSERT INTO agipub.detailcmd (id_cmd, document, id_papier, hauteur, largeur, nbre_couleur, qte, prix_conception, prix_matieres, prix_papier, prix_impression, isMultipage, designation, prix_unitaire , nbre_feuille_consom) "
                                + " VALUES (" + c.getIdCmd() + ", '" + c.getDocument() + "', " + c.getPapier().getId_papier() + ", " + c.getHauteur() + "," + c.getLargeur() + " , " + c.getNbreCouleur() + ", " + c.getQte() + ", " + c.getPrixConception() + ", " + c.getPrixMatiere() + ", " + c.getPrixPapier() + " , " + c.getPrixImpression() + ", " + c.getIsMultiple() + " , '" + c.getDesignation() + "' , " + c.getPrix_unitaire() +" , "+c.getNbre_feuille_consom()+ ");";

                    }
                    // JOptionPane.showMessageDialog(null, query);
                    state = Connexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    state.executeUpdate(query);

                    dataCmd.get(indexCmd).getDetails().add(c);
                    dataCmd.get(indexCmd).setNbreTravaux(dataCmd.get(indexCmd).getDetails().size());
                    double prixHt = dataCmd.get(indexCmd).getPrixHt();
                    prixHt += c.getPrixImpression();
                    dataCmd.get(indexCmd).setPrixHt(prixHt);
                    dataCmd.get(indexCmd).setPrixTt(0);
                    query = "UPDATE commande SET nbre_travaux=" + dataCmd.get(indexCmd).getNbreTravaux() + ", prix_ht=" + dataCmd.get(indexCmd).getPrixHt() + " , prix_tt=" + dataCmd.get(indexCmd).getPrixTt() + " where id_cmd=" + dataCmd.get(indexCmd).getIdCmd();
                    state.executeUpdate(query);
                    tbl_commandes.setValueAt(dataCmd.get(indexCmd).getNbreTravaux(), indexCmd, 4);
                    tbl_commandes.setValueAt(dataCmd.get(indexCmd).getPrixHt(), indexCmd, 5);
                    tbl_commandes.setValueAt(dataCmd.get(indexCmd).getPrixTt(), indexCmd, 6);
                    JOptionPane.showMessageDialog(null, "Ajout réalisé avec succès !");
                    state.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    chargeDetails();
                    Logger.getLogger(pan_commandes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Aucune commande séléctionnée !");
        }
    }//GEN-LAST:event_btn_add_alimentationActionPerformed

    private void btn_edit_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_edit_alimentationActionPerformed
        // TODO add your handling code here:
        if (indexCmd > -1 && indexDet > -1) {
            DetailCommand dc = new DetailCommand(dataCmd.get(indexCmd).getDetails().get(indexDet));
            
            //JOptionPane.showMessageDialog(null, dataCmd.get(indexCmd).getDetails().get(indexDet).getNbre_feuille_consom());
            detailCommande d = new detailCommande(dc);
            DetailCommand dCmd = d.showDialog();
            if (dCmd != null) {
                try {
                    detailsCmd = dCmd;
                    DetailCommand c = detailsCmd;
                    c.setDesignation(c.toString());
                    String query = "";
                    if (c.getIsMultiple()) {
                        query = "UPDATE  agipub.detailcmd  SET id_cmd=" + c.getIdCmd()
                                + ", document='" + c.getDocument()
                                + "', id_papier=" + c.getPapier().getId_papier()
                                + ", hauteur=" + c.getHauteur()
                                + ", largeur=" + c.getLargeur()
                                + ", nbre_couleur=" + c.getNbreCouleur()
                                + ", qte= " + c.getQte()
                                + ", prix_conception=" + c.getPrixConception()
                                + ", prix_matieres=" + c.getPrixMatiere()
                                + " , prix_papier =  " + c.getPrixPapier()
                                + ", prix_impression=" + c.getPrixImpression()
                                + ", isMultipage=" + c.getIsMultiple()
                                + ", id_multip=" + c.getMultip().getId_multip()
                                + ", designation='" + c.getDesignation()
                                + "', prix_unitaire=" + c.getPrix_unitaire()
                                +", nbre_feuille_consom=" + c.getNbre_feuille_consom()
                                + " WHERE id_cmd=" + dataCmd.get(indexCmd).getDetails().get(indexDet).getIdCmd()
                                + " and document='" + dataCmd.get(indexCmd).getDetails().get(indexDet).getDocument()
                                + "' and id_papier=" + dataCmd.get(indexCmd).getDetails().get(indexDet).getPapier().getId_papier()
                                + " and hauteur=" + dataCmd.get(indexCmd).getDetails().get(indexDet).getHauteur()
                                + " and largeur=" + dataCmd.get(indexCmd).getDetails().get(indexDet).getLargeur();

                        lbl_papier.setText(c.getMultip().getPapier().toString());
                        lbl_nbrePage.setText(String.valueOf(c.getMultip().getNbre_page()));
                        lbl_nbreClr.setText(String.valueOf(c.getMultip().getNbre_couleur()));
                        lbl_ep.setText(String.valueOf(c.getMultip().getEpaisseur_bande()));
                    } else {
                        query = "UPDATE  agipub.detailcmd  SET id_cmd=" + c.getIdCmd()
                                + ", document='" + c.getDocument()
                                + "', id_papier=" + c.getPapier().getId_papier()
                                + ", hauteur=" + c.getHauteur()
                                + ", largeur=" + c.getLargeur()
                                + ", nbre_couleur=" + c.getNbreCouleur()
                                + ", qte= " + c.getQte()
                                + ", prix_conception=" + c.getPrixConception()
                                + ", prix_matieres=" + c.getPrixMatiere()
                                + " , prix_papier =  " + c.getPrixPapier()
                                + ", prix_impression=" + c.getPrixImpression()
                                + ", isMultipage=" + c.getIsMultiple()
                                + ", designation='" + c.getDesignation()
                                + "', prix_unitaire=" + c.getPrix_unitaire()
                                + ", nbre_feuille_consom=" + c.getNbre_feuille_consom()
                                + " WHERE id_cmd=" + dataCmd.get(indexCmd).getDetails().get(indexDet).getIdCmd()
                                + " and document='" + dataCmd.get(indexCmd).getDetails().get(indexDet).getDocument()
                                + "' and id_papier=" + dataCmd.get(indexCmd).getDetails().get(indexDet).getPapier().getId_papier()
                                + " and hauteur=" + dataCmd.get(indexCmd).getDetails().get(indexDet).getHauteur()
                                + " and largeur=" + dataCmd.get(indexCmd).getDetails().get(indexDet).getLargeur();
                    }

                    //JOptionPane.showMessageDialog(null, query);
                    state = Connexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    state.executeUpdate(query);

                    /**
                     * *************Modification de l'objet
                     * cmd******************************
                     */
                    //  dataCmd.get(indexCmd).setNbreTravaux(dataCmd.get(indexCmd).getDetails().size());
                    double prixHt = dataCmd.get(indexCmd).getPrixHt();

                    prixHt -= dataCmd.get(indexCmd).getDetails().get(indexDet).getPrixImpression();
                    //  JOptionPane.showMessageDialog(null, " prix ht - "+dataCmd.get(indexCmd).getDetails().get(indexDet).getPrixImpression()+" = "+prixHt);
                    prixHt += c.getPrixImpression();
                    //   JOptionPane.showMessageDialog(null, "new prix ht"+prixHt);
                    dataCmd.get(indexCmd).setPrixHt(prixHt);
                    dataCmd.get(indexCmd).setPrixTt(0);
                    query = "UPDATE commande SET prix_ht=" + dataCmd.get(indexCmd).getPrixHt() + " , prix_tt=" + dataCmd.get(indexCmd).getPrixTt() + " where id_cmd=" + dataCmd.get(indexCmd).getIdCmd();
                    state.executeUpdate(query);
                    //tbl_commandes.setValueAt(dataCmd.get(indexCmd).getNbreTravaux(), indexCmd, 4);
                    tbl_commandes.setValueAt(dataCmd.get(indexCmd).getPrixHt(), indexCmd, 5);
                    tbl_commandes.setValueAt(dataCmd.get(indexCmd).getPrixTt(), indexCmd, 6);
                    //----- Modification dans l'objet details
                    dataCmd.get(indexCmd).getDetails().get(indexDet).setIdCmd(c.getIdCmd());
                    dataCmd.get(indexCmd).getDetails().get(indexDet).setDocument(c.getDocument());
                    dataCmd.get(indexCmd).getDetails().get(indexDet).setPapier(c.getPapier());
                    dataCmd.get(indexCmd).getDetails().get(indexDet).setHauteur(c.getHauteur());
                    dataCmd.get(indexCmd).getDetails().get(indexDet).setLargeur(c.getLargeur());
                    dataCmd.get(indexCmd).getDetails().get(indexDet).setNbreCouleur(c.getNbreCouleur());
                    dataCmd.get(indexCmd).getDetails().get(indexDet).setQte(c.getQte());
                    dataCmd.get(indexCmd).getDetails().get(indexDet).setPrixConception(c.getPrixConception());
                    dataCmd.get(indexCmd).getDetails().get(indexDet).setPrixMatiere(c.getPrixMatiere());
                    dataCmd.get(indexCmd).getDetails().get(indexDet).setPrixPapier(c.getPrixPapier());
                    dataCmd.get(indexCmd).getDetails().get(indexDet).setPrixImpression(c.getPrixImpression());
                    dataCmd.get(indexCmd).getDetails().get(indexDet).setIsMultiple(c.getIsMultiple());
                    if (dataCmd.get(indexCmd).getDetails().get(indexDet).getIsMultiple()) {
                        dataCmd.get(indexCmd).getDetails().get(indexDet).setMultip(c.getMultip());
                    }
                    tbl_detail_cmd.setValueAt(c.getDocument(), indexDet, 0);
                    tbl_detail_cmd.setValueAt(c.getPapier().toString(), indexDet, 1);
                    tbl_detail_cmd.setValueAt(c.getHauteur(), indexDet, 2);
                    tbl_detail_cmd.setValueAt(c.getLargeur(), indexDet, 3);
                    tbl_detail_cmd.setValueAt(c.getNbreCouleur(), indexDet, 4);
                    tbl_detail_cmd.setValueAt(c.getQte(), indexDet, 5);
                    tbl_detail_cmd.setValueAt(c.getPrixImpression(), indexDet, 6);
                    tbl_detail_cmd.setValueAt(c.getIsMultiple(), indexDet, 7);
                    if (c.getIsMultiple()) {
                        lbl_papier.setText(dataCmd.get(indexCmd).getDetails().get(indexDet).getMultip().getPapier().toString());
                        lbl_nbrePage.setText(String.valueOf(dataCmd.get(indexCmd).getDetails().get(indexDet).getMultip().getNbre_page()));
                        lbl_nbreClr.setText(String.valueOf(dataCmd.get(indexCmd).getDetails().get(indexDet).getMultip().getNbre_couleur()));
                        lbl_ep.setText(String.valueOf(dataCmd.get(indexCmd).getDetails().get(indexDet).getMultip().getEpaisseur_bande()));
                    }
                    JOptionPane.showMessageDialog(null, "Modification terminée avec succès !");
                    state.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    chargeDetails();
                    Logger.getLogger(pan_commandes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Aucune commande séléctionnée !");
        }
    }//GEN-LAST:event_btn_edit_alimentationActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        // TODO add your handling code here:
        try {
            if (indexCmd > -1) {
                if ("Modifier".equals(btn_edit.getToolTipText())) {

                    activerDetails();
                    btn_edit.setToolTipText("Enregistrer");
                    btn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/save.png")));
                    btn_add.setEnabled(false);
                    // jDate_cmd.setDate(Calendar.getInstance().getTime());

                } else {
                    Boolean oki = true;
                    String query;
                    Commande cmd = new Commande();
                    cmd.setIdClient(dataClient.get(cmb_num_client.getSelectedIndex()).getId());
                    cmd.setDateCmd(jDate_cmd.getDate());
                    cmd.setDateLivraison(jDate_livraisan.getDate());
                    Double av = txt_avance.getText().equals("") ? 0 : Double.valueOf(txt_avance.getText());
                    cmd.setAvance(av);

                    if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment modifier cette commande ?", "Confirmation d'ajout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        Calendar cal = Calendar.getInstance();
                        if (jDate_cmd.getDate() == null) {
                            jDate_cmd.setDate(Calendar.getInstance().getTime());
                        }
                        cal.setTime(cmd.getDateCmd());
                        String dCmd = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONDAY) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
                        String dLiv = null;
                        query = "UPDATE agipub.commande SET id_client=" + cmd.getIdClient() + " , date_cmd = '" + dCmd + "', avance=" + cmd.getAvance() + " where id_cmd=" + dataCmd.get(indexCmd).getIdCmd();

                        if (jDate_livraisan.getDate() != null) {
                            cal.setTime(cmd.getDateLivraison());
                            dLiv = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONDAY) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
                            query = "UPDATE agipub.commande SET id_client=" + cmd.getIdClient() + " , date_cmd = '" + dCmd + "', date_livraison='" + dLiv + "' , avance=" + cmd.getAvance() + " where id_cmd=" + dataCmd.get(indexCmd).getIdCmd();
                        }

                        //  JOptionPane.showMessageDialog(null, query);
                        state = Connexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//                    int key = 0;
                        state.executeUpdate(query);
//                    result = state.getGeneratedKeys();
//                    if (result != null && result.next()) {
//                        key = result.getInt(1);
//                    }
                        //cmd.setIdCmd(key);
                        dataCmd.get(indexCmd).setIdClient(cmd.getIdClient());
                        dataCmd.get(indexCmd).setDateCmd(cmd.getDateCmd());
                        dataCmd.get(indexCmd).setDateLivraison(cmd.getDateLivraison());
                        dataCmd.get(indexCmd).setAvance(cmd.getAvance());
                        // JOptionPane.showMessageDialog(null, query);

                        state.close();
                        btn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/graphic_design.png")));
                        btn_edit.setToolTipText("Modifier");

                        JOptionPane.showMessageDialog(null, "Commande modifié avec succès");
                        tbl_commandes.setValueAt(cmd.getIdClient(), indexCmd, 1);
                        tbl_commandes.setValueAt(cmd.getDateCmd(), indexCmd, 2);
                        tbl_commandes.setValueAt(cmd.getDateLivraison(), indexCmd, 3);
                        tbl_commandes.setValueAt(cmd.getAvance(), indexCmd, 7);

                        btn_add.setEnabled(true);
                        desactiverDetails();
                    }

                }
            } else {
                JOptionPane.showMessageDialog(null, "Aucune commande séléctionnée !!", "Commande vide !", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        if (indexCmd > -1) {
            if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette commande ?", "Confirmation de suppression !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                try {
                    state = Connexion.getInstance().createStatement();
                    String query = "DELETE FROM commande WHERE id_cmd=" + dataCmd.get(indexCmd).getIdCmd();
                    state.executeUpdate(query);
                    state.close();
                    dataCmd.remove(indexCmd);
                    ((DefaultTableModel) tbl_commandes.getModel()).removeRow(indexCmd);
                    ((DefaultTableModel) tbl_commandes.getModel()).fireTableRowsDeleted(indexCmd, indexCmd);
                    JOptionPane.showMessageDialog(null, "Suppression efféctuée !");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur en Suppression :\n" + ex.getMessage());
                    Logger.getLogger(pan_papier.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "Aucune commande séléctionnée !");
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_delete_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_alimentationActionPerformed
        // TODO add your handling code here:
        if (indexDet > -1 && indexCmd > -1) {
            if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette ligne de commande ?", "Confirmation de suppression !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                try {
                    state = Connexion.getInstance().createStatement();
                    String query = "DELETE FROM detailcmd WHERE id_cmd=" + dataCmd.get(indexCmd).getDetails().get(indexDet).getIdCmd()
                            + " and document='" + dataCmd.get(indexCmd).getDetails().get(indexDet).getDocument()
                            + "'  and id_papier=" + dataCmd.get(indexCmd).getDetails().get(indexDet).getPapier().getId_papier()
                            + "  and hauteur=" + dataCmd.get(indexCmd).getDetails().get(indexDet).getHauteur()
                            + "  and largeur=" + dataCmd.get(indexCmd).getDetails().get(indexDet).getLargeur()
                            + " and nbre_couleur=" + dataCmd.get(indexCmd).getDetails().get(indexDet).getNbreCouleur();
                    state.executeUpdate(query);

                    double prixHt = dataCmd.get(indexCmd).getPrixHt();
                    prixHt -= dataCmd.get(indexCmd).getDetails().get(indexDet).getPrixImpression();
                    dataCmd.get(indexCmd).getDetails().remove(indexDet);
                    dataCmd.get(indexCmd).setNbreTravaux(dataCmd.get(indexCmd).getDetails().size());
                    dataCmd.get(indexCmd).setPrixHt(prixHt);
                    dataCmd.get(indexCmd).setPrixTt(0);
                    query = "UPDATE commande SET nbre_travaux=" + dataCmd.get(indexCmd).getNbreTravaux() + ", prix_ht=" + dataCmd.get(indexCmd).getPrixHt() + " , prix_tt=" + dataCmd.get(indexCmd).getPrixTt() + " where id_cmd=" + dataCmd.get(indexCmd).getIdCmd();
                    state.executeUpdate(query);
                    state.close();
                    tbl_commandes.setValueAt(dataCmd.get(indexCmd).getNbreTravaux(), indexCmd, 4);
                    tbl_commandes.setValueAt(dataCmd.get(indexCmd).getPrixHt(), indexCmd, 5);
                    tbl_commandes.setValueAt(dataCmd.get(indexCmd).getPrixTt(), indexCmd, 6);

                    ((DefaultTableModel) tbl_detail_cmd.getModel()).removeRow(indexDet);
                    ((DefaultTableModel) tbl_detail_cmd.getModel()).fireTableRowsDeleted(indexDet, indexDet);
                    JOptionPane.showMessageDialog(null, "Suppression efféctuée !");

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur en Suppression :\n" + ex.getMessage());
                    Logger.getLogger(pan_papier.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Aucune ligne séléctionnée !");
        }
    }//GEN-LAST:event_btn_delete_alimentationActionPerformed

    private void btn_facturerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_facturerActionPerformed
        // TODO add your handling code here:
        try {
            state = Connexion.getInstance().createStatement();
             double ttc = Double.valueOf(String.valueOf(dataCmd.get(indexCmd).getPrixTt()));
           // JOptionPane.showMessageDialog(null, ttc);
            String m = FrenchNumberToWords.convert(Double.valueOf(ttc).longValue());
            m = m.substring(0, 1).toUpperCase() + m.substring(1)+ "   Dh";
            String query = "INSERT INTO agipub.facture (id_cmd, date_facture) "
                    + " VALUES (" + dataCmd.get(indexCmd).getIdCmd() + ", CURRENT_DATE)";
            state.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            result = state.getGeneratedKeys();
            if (result != null && result.first()) {
                int id_fac = result.getInt(1);
                
                Map<String, Object> param = new HashMap<>();
                 param.put("NoFact", id_fac);
                  param.put("m", m);
               // JasperDesign jasperdesign = JRXmlLoader.load(getClass().getResource("/reports/facture_02.jrxml").getFile());
               String file = "/reports/facture_02.jasper";
                InputStream report = getClass().getResourceAsStream(file);
                  //Compilation du rapport
                //JasperReport report = JasperCompileManager.compileReport(jasperdesign);
                //Remplissage du rapport
                JasperPrint print = JasperFillManager.fillReport(report, param, Connexion.getInstance());
                //Visualisation du rapport à l'ecran
                JasperViewer.viewReport(print, false);
            }
            state.close();

        } catch (SQLException | JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btn_facturerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_add_alimentation;
    private javax.swing.JButton btn_annuler;
    private javax.swing.JButton btn_atteindre;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_delete_alimentation;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_edit_alimentation;
    private javax.swing.JButton btn_facturer;
    private javax.swing.JButton btn_first;
    private javax.swing.JButton btn_last;
    private javax.swing.JButton btn_prec;
    private javax.swing.JButton btn_refrech;
    private javax.swing.JButton btn_refrech_alimentation;
    private javax.swing.JButton btn_suiv;
    private javax.swing.JComboBox cmb_num_client;
    private com.toedter.calendar.JDateChooser jDate_cmd;
    private com.toedter.calendar.JDateChooser jDate_livraisan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JLabel lbl_ep;
    private javax.swing.JLabel lbl_nbreClr;
    private javax.swing.JLabel lbl_nbrePage;
    private javax.swing.JLabel lbl_papier;
    private javax.swing.JScrollPane pnl_edition_commandes;
    private javax.swing.JSplitPane splitPane_commande;
    private javax.swing.JTable tbl_commandes;
    private javax.swing.JTable tbl_detail_cmd;
    private javax.swing.JToolBar toolBar_clients;
    private javax.swing.JToolBar tools_details;
    private javax.swing.JTextField txt_avance;
    private javax.swing.JTextField txt_code;
    // End of variables declaration//GEN-END:variables
}
