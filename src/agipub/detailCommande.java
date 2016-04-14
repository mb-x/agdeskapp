/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agipub;

import com.sun.glass.ui.Screen;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import object.Couleurs;
import object.DetailCommand;
import object.Multipage;
import object.Papier;

/**
 *
 * @author win_bmx
 */
public class detailCommande extends javax.swing.JDialog {

    Multipage mult = new Multipage();
    Statement state;
    ResultSet result;
    int idCmd = 0;
    int ancienIdPapier = 0;
    int ancienIdPapierMult = 0;

    DetailCommand detailCmd = new DetailCommand();
    ArrayList<Papier> dataPapier = new ArrayList<>();
    private Boolean sendData;
    double h;
    double l;
    double m = 0.5;
    double p = 1.5;

    double prixImpression = 0;
    int nbrePos = 0;
    int nbreTirage = 0;
    Double nbreFeuillesConsomme = 0.0;
    double prixPapier = 0.0;

    int nbrePosM = 0;
    int nbreTirageM = 0;
    Double nbreFeuillesConsommeM = 0.0;
    double prixPapierM = 0.0;

    public detailCommande(int idcmd) {
        super();

        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Ajout pour la commande N° : " + idcmd);
        desactiverMultip();
        init();
        btn_edit.setEnabled(false);
        idCmd = idcmd;
        detailCmd.setIdCmd(idcmd);
    }

    public detailCommande(DetailCommand cmd) {
        super();
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Modification de la commande N° : " + cmd.getIdCmd());
        desactiverMultip();
        init();
        btn_add.setEnabled(false);
        cmb_document.setSelectedItem(cmd.getDocument());
        txt_hauteur.setText(String.valueOf(cmd.getHauteur()));
        txt_largeur.setText(String.valueOf(cmd.getLargeur()));
        spn_nbreCouleurs.setValue(cmd.getNbreCouleur());
        spn_qte.setValue(cmd.getQte());
        txt_prixConception.setText(String.valueOf(cmd.getPrixConception()));
        txt_prixMatiere.setText(String.valueOf(cmd.getPrixMatiere()));
        txt_prixImpression.setText(String.valueOf(cmd.getPrixImpression()));
        cmb_papier.setSelectedItem(cmd.getPapier().getNom_papier());
        ancienIdPapier = cmd.getPapier().getId_papier();
        cmb_couleur.setSelectedItem(cmd.getPapier().getCouleur());
        spn_grammage.setValue(cmd.getPapier().getGrammage());
        nbreFeuillesConsomme = cmd.getNbre_feuille_consom();
        //JOptionPane.showMessageDialog(null,nbreFeuillesConsomme);
        txt_nbreFueilles.setText(String.valueOf(nbreFeuillesConsomme));
        if (cmd.getIsMultiple()) {
            check_mult.setSelected(true);
            // activerMultip();
            nbreFeuillesConsommeM = cmd.getMultip().getNbre_feuille_consom();
            txt_nbreFueille_mult6.setText(String.valueOf(nbreFeuillesConsommeM));
            cmb_papier_mult.setSelectedItem(cmd.getMultip().getPapier().getNom_papier());
            ancienIdPapierMult = cmd.getMultip().getPapier().getId_papier();
            cmb_couleur_mult.setSelectedItem(cmd.getMultip().getPapier().getCouleur());
            spn_grammage_mult.setValue(cmd.getMultip().getPapier().getGrammage());
            spn_nbrePage_mult.setValue(cmd.getMultip().getNbre_page());
            spn_nbre_colors_mult.setValue(cmd.getMultip().getNbre_couleur());
            txt_epaisseur.setText(String.valueOf(cmd.getMultip().getEpaisseur_bande()));
        }

        detailCmd = cmd;
    }

    private void init() {
        try {

            cmb_document.removeAllItems();
            cmb_papier.removeAllItems();
            cmb_papier_mult.removeAllItems();
            state = Connexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            result = state.executeQuery("select * from document");
            while (result.next()) {
                cmb_document.addItem(result.getString("document"));
            }
            cmb_couleur.setModel(new DefaultComboBoxModel(Couleurs.values()));
            cmb_couleur_mult.setModel(new DefaultComboBoxModel(Couleurs.values()));
            result = state.executeQuery("select * from papier");
            Papier pap;
            while (result.next()) {
                pap = new Papier();
                pap.setId_papier(result.getInt("id_papier"));
                pap.setNom_papier(result.getString("nom_papier"));
                pap.setGrammage(result.getInt("grammage"));
                pap.setCouleur(result.getString("couler"));
                pap.setHauteur(result.getFloat("hauteur"));
                pap.setLargeur(result.getFloat("largeur"));
                pap.setQte(result.getDouble("qte_stock"));
                dataPapier.add(pap);
                cmb_papier.addItem(result.getString("nom_papier"));
                cmb_papier_mult.addItem(result.getString("nom_papier"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(detailCommande.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void activerMultip() {
        cmb_papier_mult.setEnabled(true);
        cmb_couleur_mult.setEnabled(true);
        spn_grammage_mult.setEnabled(true);
        spn_nbrePage_mult.setEnabled(true);
        spn_nbre_colors_mult.setEnabled(true);
        txt_epaisseur.setEnabled(true);
    }

    private void desactiverMultip() {
        cmb_papier_mult.setEnabled(false);
        cmb_couleur_mult.setEnabled(false);
        spn_grammage_mult.setEnabled(false);
        spn_nbrePage_mult.setEnabled(false);
        spn_nbre_colors_mult.setEnabled(false);
        txt_epaisseur.setEnabled(false);
    }

    public DetailCommand showDialog() {
        this.sendData = false;
        this.setVisible(true);
        return detailCmd;
    }

    private detailCommande(JFrame jFrame, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        pnl_papier = new javax.swing.JPanel();
        lbl_ste_name11 = new javax.swing.JLabel();
        cmb_papier = new javax.swing.JComboBox();
        cmb_couleur = new javax.swing.JComboBox();
        lbl_ste_name17 = new javax.swing.JLabel();
        spn_grammage = new javax.swing.JSpinner();
        lbl_ste_name18 = new javax.swing.JLabel();
        pnl_format = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_h = new javax.swing.JFormattedTextField();
        txt_l = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        pnl_papier_mult = new javax.swing.JPanel();
        pnl_pap_mult = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmb_papier_mult = new javax.swing.JComboBox();
        cmb_couleur_mult = new javax.swing.JComboBox();
        lbl_ste_name27 = new javax.swing.JLabel();
        lbl_ste_name28 = new javax.swing.JLabel();
        spn_grammage_mult = new javax.swing.JSpinner();
        spn_nbrePage_mult = new javax.swing.JSpinner();
        spn_nbre_colors_mult = new javax.swing.JSpinner();
        txt_epaisseur = new javax.swing.JTextField();
        check_mult = new javax.swing.JCheckBox();
        pnl_marge = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txt_p = new javax.swing.JFormattedTextField();
        txt_m = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        lbl_img_info_mult6 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txt_nbrePose_mult6 = new javax.swing.JTextField();
        txt_nbreTirage_mult6 = new javax.swing.JTextField();
        txt_prix_papier_cons_mult6 = new javax.swing.JTextField();
        txt_nbreFueille_mult6 = new javax.swing.JTextField();
        btn_afficher_infoMilt6 = new javax.swing.JButton();
        pnl_document = new javax.swing.JPanel();
        lbl_ste_name9 = new javax.swing.JLabel();
        cmb_document = new javax.swing.JComboBox();
        lbl_ste_name19 = new javax.swing.JLabel();
        lbl_ste_name20 = new javax.swing.JLabel();
        spn_nbreCouleurs = new javax.swing.JSpinner();
        spn_qte = new javax.swing.JSpinner();
        lbl_ste_name21 = new javax.swing.JLabel();
        lbl_ste_name22 = new javax.swing.JLabel();
        lbl_ste_name23 = new javax.swing.JLabel();
        lbl_ste_name24 = new javax.swing.JLabel();
        lbl_ste_name26 = new javax.swing.JLabel();
        lbl_ste_name29 = new javax.swing.JLabel();
        txt_prixImpression = new javax.swing.JFormattedTextField();
        txt_prixConception = new javax.swing.JFormattedTextField();
        txt_prixMatiere = new javax.swing.JFormattedTextField();
        txt_hauteur = new javax.swing.JFormattedTextField();
        txt_largeur = new javax.swing.JFormattedTextField();
        lbl_ste_name25 = new javax.swing.JLabel();
        btn_calculer = new javax.swing.JButton();
        btn_add_doc = new javax.swing.JButton();
        btn_delet_doc = new javax.swing.JButton();
        btn_edit_doc = new javax.swing.JButton();
        btn_add = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_close = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        lbl_img_info = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_nbePose = new javax.swing.JTextField();
        txt_nbreTirage = new javax.swing.JTextField();
        txt_prix_papier_consomme = new javax.swing.JTextField();
        txt_nbreFueilles = new javax.swing.JTextField();
        btn_afficher_info = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);

        pnl_papier.setBorder(javax.swing.BorderFactory.createTitledBorder("Papier"));

        lbl_ste_name11.setText("Papier : ");

        cmb_papier.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmb_couleur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbl_ste_name17.setText("Couleur papier :");

        lbl_ste_name18.setText("Grammage :");

        javax.swing.GroupLayout pnl_papierLayout = new javax.swing.GroupLayout(pnl_papier);
        pnl_papier.setLayout(pnl_papierLayout);
        pnl_papierLayout.setHorizontalGroup(
            pnl_papierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_papierLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_papierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_ste_name18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_ste_name17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_ste_name11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(pnl_papierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_papierLayout.createSequentialGroup()
                        .addGroup(pnl_papierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmb_papier, 0, 224, Short.MAX_VALUE)
                            .addComponent(cmb_couleur, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnl_papierLayout.createSequentialGroup()
                        .addComponent(spn_grammage)
                        .addGap(22, 22, 22))))
        );
        pnl_papierLayout.setVerticalGroup(
            pnl_papierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_papierLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_papierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name11)
                    .addComponent(cmb_papier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_papierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name17)
                    .addComponent(cmb_couleur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_papierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name18)
                    .addComponent(spn_grammage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pnl_format.setBorder(javax.swing.BorderFactory.createTitledBorder("Format de montage"));

        jLabel1.setText("H :");

        jLabel2.setText("L :");

        javax.swing.GroupLayout pnl_formatLayout = new javax.swing.GroupLayout(pnl_format);
        pnl_format.setLayout(pnl_formatLayout);
        pnl_formatLayout.setHorizontalGroup(
            pnl_formatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_formatLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_h, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_l, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnl_formatLayout.setVerticalGroup(
            pnl_formatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_formatLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(pnl_formatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_h, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_l, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_papier_mult.setBorder(javax.swing.BorderFactory.createTitledBorder("Document Multipage"));

        pnl_pap_mult.setBorder(javax.swing.BorderFactory.createTitledBorder("Document Multipage"));

        jLabel5.setText("Papier : ");

        jLabel6.setText("Nbre pages :");

        jLabel7.setText("Nbre Couleurs :");

        jLabel8.setText("Epaisseur : ");

        cmb_papier_mult.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmb_couleur_mult.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbl_ste_name27.setText("Couleur papier :");

        lbl_ste_name28.setText("Grammage :");

        txt_epaisseur.setText("0");

        javax.swing.GroupLayout pnl_pap_multLayout = new javax.swing.GroupLayout(pnl_pap_mult);
        pnl_pap_mult.setLayout(pnl_pap_multLayout);
        pnl_pap_multLayout.setHorizontalGroup(
            pnl_pap_multLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_pap_multLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_pap_multLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_ste_name27)
                    .addComponent(jLabel5)
                    .addComponent(lbl_ste_name28)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(pnl_pap_multLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_pap_multLayout.createSequentialGroup()
                        .addComponent(cmb_papier_mult, 0, 210, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addComponent(cmb_couleur_mult, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spn_grammage_mult)
                    .addComponent(spn_nbrePage_mult)
                    .addComponent(spn_nbre_colors_mult)
                    .addComponent(txt_epaisseur)))
        );
        pnl_pap_multLayout.setVerticalGroup(
            pnl_pap_multLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_pap_multLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnl_pap_multLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmb_papier_mult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_pap_multLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name27)
                    .addComponent(cmb_couleur_mult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_pap_multLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name28)
                    .addComponent(spn_grammage_mult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_pap_multLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(spn_nbrePage_mult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_pap_multLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(spn_nbre_colors_mult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_pap_multLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_epaisseur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        check_mult.setText("Multipage");
        check_mult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_multActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_papier_multLayout = new javax.swing.GroupLayout(pnl_papier_mult);
        pnl_papier_mult.setLayout(pnl_papier_multLayout);
        pnl_papier_multLayout.setHorizontalGroup(
            pnl_papier_multLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_papier_multLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(check_mult)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_papier_multLayout.createSequentialGroup()
                .addComponent(pnl_pap_mult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_papier_multLayout.setVerticalGroup(
            pnl_papier_multLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_papier_multLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(check_mult)
                .addGap(10, 10, 10)
                .addComponent(pnl_pap_mult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_marge.setBorder(javax.swing.BorderFactory.createTitledBorder("Marges"));

        jLabel3.setText("Prise de pince :");

        txt_p.setText("1.5");

        txt_m.setText("0.5");

        jLabel4.setText("Marges :");

        javax.swing.GroupLayout pnl_margeLayout = new javax.swing.GroupLayout(pnl_marge);
        pnl_marge.setLayout(pnl_margeLayout);
        pnl_margeLayout.setHorizontalGroup(
            pnl_margeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_margeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_margeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_p)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(pnl_margeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txt_m, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );
        pnl_margeLayout.setVerticalGroup(
            pnl_margeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_margeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_margeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_margeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_p, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(104, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Calcul sur le contenu de document multipage"));

        lbl_img_info_mult6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbl_img_info_mult6.setPreferredSize(new java.awt.Dimension(100, 120));

        jLabel38.setText("Nbre de poses :");

        jLabel39.setText("Nbre de Tirages :");

        jLabel40.setText("Nbre de Feuilles consommées :");

        jLabel41.setText("Prix du papier consommé :");

        txt_nbrePose_mult6.setEditable(false);

        txt_nbreTirage_mult6.setEditable(false);

        txt_prix_papier_cons_mult6.setEditable(false);

        txt_nbreFueille_mult6.setEditable(false);

        btn_afficher_infoMilt6.setText("Calculer");
        btn_afficher_infoMilt6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_afficher_infoMilt6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(txt_prix_papier_cons_mult6, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_afficher_infoMilt6))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38)
                            .addComponent(jLabel41)
                            .addComponent(jLabel39)
                            .addComponent(jLabel40)
                            .addComponent(txt_nbrePose_mult6, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_nbreTirage_mult6, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_nbreFueille_mult6, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_img_info_mult6, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nbrePose_mult6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nbreTirage_mult6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel40)
                        .addGap(4, 4, 4)
                        .addComponent(txt_nbreFueille_mult6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel41)
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addComponent(lbl_img_info_mult6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_afficher_infoMilt6)
                    .addComponent(txt_prix_papier_cons_mult6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnl_document.setBorder(javax.swing.BorderFactory.createTitledBorder("Document"));

        lbl_ste_name9.setText("Document :");

        cmb_document.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbl_ste_name19.setText("Hauteur :");

        lbl_ste_name20.setText("Largeur : ");

        lbl_ste_name21.setText("Nbre Couleur : ");

        lbl_ste_name22.setText("Prix Conception : ");

        lbl_ste_name23.setText("Prix Matieres :");

        lbl_ste_name24.setText("Prix Impression : ");

        lbl_ste_name26.setText("Quantité : ");

        lbl_ste_name29.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        lbl_ste_name29.setText("Si le prix d'impression n'est pas saisie, il sera calculé \nautomatiquement");

        lbl_ste_name25.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lbl_ste_name25.setText("Si le Document est multipage, les caractéristique ci-dessous concerne la couverture");

        btn_calculer.setText("Calculer");
        btn_calculer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_calculerActionPerformed(evt);
            }
        });

        btn_add_doc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/plus_16px.png"))); // NOI18N
        btn_add_doc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_docActionPerformed(evt);
            }
        });

        btn_delet_doc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/sub_blue_delete_16px.png"))); // NOI18N
        btn_delet_doc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delet_docActionPerformed(evt);
            }
        });

        btn_edit_doc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/graphic_design_16px.png"))); // NOI18N
        btn_edit_doc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_edit_docActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_documentLayout = new javax.swing.GroupLayout(pnl_document);
        pnl_document.setLayout(pnl_documentLayout);
        pnl_documentLayout.setHorizontalGroup(
            pnl_documentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_documentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_documentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_documentLayout.createSequentialGroup()
                        .addGroup(pnl_documentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_ste_name19)
                            .addComponent(lbl_ste_name20)
                            .addComponent(lbl_ste_name9)
                            .addComponent(lbl_ste_name21)
                            .addComponent(lbl_ste_name26)
                            .addComponent(lbl_ste_name22)
                            .addComponent(lbl_ste_name24)
                            .addComponent(lbl_ste_name23))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_documentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmb_document, 0, 184, Short.MAX_VALUE)
                            .addComponent(spn_nbreCouleurs)
                            .addComponent(spn_qte)
                            .addComponent(txt_prixImpression)
                            .addComponent(txt_prixConception)
                            .addComponent(txt_prixMatiere)
                            .addComponent(txt_hauteur)
                            .addComponent(txt_largeur))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_documentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_calculer)
                            .addGroup(pnl_documentLayout.createSequentialGroup()
                                .addComponent(btn_add_doc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_delet_doc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_edit_doc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lbl_ste_name29)
                    .addComponent(lbl_ste_name25))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_documentLayout.setVerticalGroup(
            pnl_documentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_documentLayout.createSequentialGroup()
                .addComponent(lbl_ste_name25, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_documentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name9)
                    .addComponent(cmb_document, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_add_doc)
                    .addComponent(btn_delet_doc)
                    .addComponent(btn_edit_doc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_documentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name19)
                    .addComponent(txt_hauteur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_documentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name20)
                    .addComponent(txt_largeur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_documentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name21)
                    .addComponent(spn_nbreCouleurs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_documentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name26)
                    .addComponent(spn_qte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_documentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name22)
                    .addComponent(txt_prixConception, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_documentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name23)
                    .addComponent(txt_prixMatiere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_documentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ste_name24)
                    .addComponent(txt_prixImpression, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_calculer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_ste_name29, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btn_add.setText("Ajouter");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_edit.setText("Modifier");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        btn_close.setText("Fermer");
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Calcul sur le papier de document (ou la couverture)"));

        lbl_img_info.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbl_img_info.setPreferredSize(new java.awt.Dimension(100, 120));

        jLabel10.setText("Nbre de poses :");

        jLabel11.setText("Nbre de Tirages :");

        jLabel12.setText("Nbre de Feuilles consommées :");

        jLabel13.setText("Prix du papier consommé :");

        txt_nbePose.setEditable(false);

        txt_nbreTirage.setEditable(false);

        txt_prix_papier_consomme.setEditable(false);

        txt_nbreFueilles.setEditable(false);

        btn_afficher_info.setText("Calculer");
        btn_afficher_info.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_afficher_infoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txt_prix_papier_consomme, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_afficher_info))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel13)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(txt_nbePose, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_nbreTirage, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_nbreFueilles, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_img_info, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(5, 5, 5)
                        .addComponent(txt_nbePose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addGap(7, 7, 7)
                        .addComponent(txt_nbreTirage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addGap(8, 8, 8)
                        .addComponent(txt_nbreFueilles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addComponent(lbl_img_info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_afficher_info)
                    .addComponent(txt_prix_papier_consomme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(pnl_format, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 301, Short.MAX_VALUE))
                            .addComponent(btn_edit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_add, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_close, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnl_papier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnl_papier_mult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnl_document, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnl_marge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnl_document, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnl_marge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnl_format, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_add))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(pnl_papier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnl_papier_mult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_close, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_edit, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void check_multActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_multActionPerformed
        // TODO add your handling code here:
        if (check_mult.isSelected()) {
            activerMultip();
        } else {
            desactiverMultip();
        }
    }//GEN-LAST:event_check_multActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            if (verifier()) {
                if (!txt_prixImpression.getText().equals("") && Double.valueOf(txt_prixImpression.getText()) > 0) {

                    String q = "update papier set qte_stock=qte_stock-" + nbreFeuillesConsomme + " where id_papier=" + detailCmd.getPapier().getId_papier();
                    state.executeUpdate(q);
                    detailCmd.setNbre_feuille_consom(nbreFeuillesConsomme);
                    if (detailCmd.getIsMultiple()) {
                        q = "update papier set qte_stock=qte_stock-" + nbreFeuillesConsommeM + " where id_papier=" + detailCmd.getMultip().getPapier().getId_papier();
                        state.executeUpdate(q);
                        detailCmd.getMultip().setNbre_feuille_consom(nbreFeuillesConsommeM);
                    }
                    detailCmd.setPrixImpression(Double.valueOf(txt_prixImpression.getText()));
                    state.close();
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Vueillez calculer le prix ou bien saisir le prix pour cette ligne de commande !!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Commande incomplet !!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(detailCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            if (verifier()) {
                if (!txt_prixImpression.getText().equals("") && Double.valueOf(txt_prixImpression.getText()) > 0) {

                    String q = "update papier set qte_stock=qte_stock+" + detailCmd.getNbre_feuille_consom() + " where id_papier=" + ancienIdPapier;
                    state.executeUpdate(q);
                    detailCmd.setNbre_feuille_consom(nbreFeuillesConsomme);
                    q = "update papier set qte_stock=qte_stock-" + nbreFeuillesConsomme + " where id_papier=" + detailCmd.getPapier().getId_papier();
                    state.executeUpdate(q);
                    if (detailCmd.getIsMultiple()) {
                        q = "update papier set qte_stock=qte_stock+" + detailCmd.getMultip().getNbre_feuille_consom() + " where id_papier=" + ancienIdPapierMult;
                        state.executeUpdate(q);
                        detailCmd.getMultip().setNbre_feuille_consom(nbreFeuillesConsommeM);
                        q = "update papier set qte_stock=qte_stock-" + nbreFeuillesConsommeM + " where id_papier=" + detailCmd.getMultip().getPapier().getId_papier();
                        state.executeUpdate(q);

                    }
                    detailCmd.setPrixImpression(Double.valueOf(txt_prixImpression.getText()));
                    state.close();
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Vueillez calculer le prix ou bien saisir le prix pour cette ligne de commande !!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Commande incomplet !!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(detailCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
        try {
            // TODO add your handling code here:
            state.close();
            detailCmd = null;
            this.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(detailCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_closeActionPerformed

    private void btn_afficher_infoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_afficher_infoActionPerformed
        // TODO add your handling code here:
        if (verifier()) {
            String pap = (String) cmb_papier.getSelectedItem();
            String c = ((Couleurs) cmb_couleur.getSelectedItem()).name();
            int g = (int) spn_grammage.getValue();
            Boolean oki = checkPapier(pap, c, g);
            if (oki) {
                txt_nbePose.setText(String.valueOf(nbrePos));
                txt_nbreTirage.setText(String.valueOf(nbreTirage));
                txt_nbreFueilles.setText(String.valueOf(nbreFeuillesConsomme));
                txt_prix_papier_consomme.setText(String.valueOf(prixPapier));
            }
        }
    }//GEN-LAST:event_btn_afficher_infoActionPerformed

    private void btn_afficher_infoMilt6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_afficher_infoMilt6ActionPerformed
        // TODO add your handling code here:
        if (check_mult.isSelected()) {
            if (verifier()) {
                String pap = (String) cmb_papier_mult.getSelectedItem();
                String c = ((Couleurs) cmb_couleur_mult.getSelectedItem()).name();
                int g = (int) spn_grammage_mult.getValue();
                Boolean oki = checkPapierMult(pap, c, g);
                if (oki) {
                    txt_nbrePose_mult6.setText(String.valueOf(nbrePosM));
                    txt_nbreTirage_mult6.setText(String.valueOf(nbreTirageM));
                    txt_nbreFueille_mult6.setText(String.valueOf(nbreFeuillesConsommeM));
                    txt_prix_papier_cons_mult6.setText(String.valueOf(prixPapierM));
                    //btn_afficher_infoActionPerformed(evt);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ce n'est pas un document multipage !");
        }
    }//GEN-LAST:event_btn_afficher_infoMilt6ActionPerformed

    private void btn_calculerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_calculerActionPerformed
        // TODO add your handling code here:
        if (prixPapier != 0.0) {
            prixImpression = detailCmd.getPrixConception() + detailCmd.getPrixMatiere() + detailCmd.getPrixPapier();
            if (prixPapierM == 0.0 && check_mult.isSelected()) {
                JOptionPane.showMessageDialog(null, "Le calcul du prix d'impression ne peut pas être réalisé si le calcul sur le papier du contenu de document n'est pas effectué !");
                return;
            } else {
                // prixImpression += detailCmd.getMultip().getPrixPapier();
            }
            JOptionPane.showMessageDialog(null, prixImpression + " = " + detailCmd.getPrixConception() + "+" + detailCmd.getPrixMatiere() + "+" + detailCmd.getPrixPapier());
            if (prixImpression < 1000.0) {
                prixImpression = prixImpression * 2;
            } else {
                prixImpression = prixImpression + (Double) (0.5 * prixImpression);
            }
            txt_prixImpression.setText(String.valueOf(prixImpression));
            detailCmd.setPrixImpression(prixImpression);
        } else {
            JOptionPane.showMessageDialog(null, "Le calcul du prix d'impression ne peut pas être réalisé si le calcul sur le papier du document n'est pas effectué !");
        }
    }//GEN-LAST:event_btn_calculerActionPerformed

    private void btn_add_docActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_docActionPerformed
        // TODO add your handling code here:

        String rep = JOptionPane.showInputDialog("Saisir le nom de document à ajouter");
        if (rep != null) {
            try {
                String query = "INSERT INTO document(document) values ('" + rep + "')";
                state.executeUpdate(query);
                cmb_document.addItem(rep);
                JOptionPane.showMessageDialog(null, "Document : " + rep + " ajouté avec succès !");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_add_docActionPerformed

    private void btn_delet_docActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delet_docActionPerformed
        // TODO add your handling code here:
        int ind = cmb_document.getSelectedIndex();
        if (ind > -1) {
            if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce document ?", "Confirmation de suppression !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                try {
                    state = Connexion.getInstance().createStatement();
                    String doc = (String) cmb_document.getSelectedItem();
                    String query = "DELETE FROM agipub.document WHERE document ='" + doc + "'";
                    state.executeUpdate(query);
                    cmb_document.removeItemAt(ind);
                    JOptionPane.showMessageDialog(null, "Suppression efféctuée !");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur en Suppression :\n" + ex.getMessage());
                    Logger.getLogger(pan_papier.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "Aucun document séléctionnée !");
        }
    }//GEN-LAST:event_btn_delet_docActionPerformed

    private void btn_edit_docActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_edit_docActionPerformed
        // TODO add your handling code here:
        int ind = cmb_document.getSelectedIndex();
        if (ind > -1) {

            String doc = (String) cmb_document.getSelectedItem();
            if (JOptionPane.showConfirmDialog(null, "le document sera aussi modifié dans toutes les lignes de commandes ayant ce document.\nVoulez-vous vraiment modifier ce document "
                    + doc + "  ?\n", "Confirmation de modification !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                String rep = JOptionPane.showInputDialog("Saisir le nouveau nom du document qui va remplacer : " + doc);
                if (rep != null) {
                    try {
                        state = Connexion.getInstance().createStatement();

                        String query = "UPDATE agipub.document SET document='" + rep + "'  WHERE document ='" + doc + "'";
                        state.executeUpdate(query);

                        JOptionPane.showMessageDialog(null, "Modification efféctuée !");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erreur en Modification :\n" + ex.getMessage());
                        Logger.getLogger(pan_papier.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Aucun document séléctionnée !");
        }
    }//GEN-LAST:event_btn_edit_docActionPerformed

    private Boolean checkPapier(String nom_papier, String couleur, int grammage) {
        Boolean oki = true;
        Papier pap = new Papier();

        try {
            result = state.executeQuery("select * from papier where nom_papier='" + nom_papier
                    + "' and couler='" + couleur + "' and grammage=" + grammage);
            if (result.next()) {
                pap.setId_papier(result.getInt("id_papier"));
                pap.setNom_papier(result.getString("nom_papier"));
                pap.setGrammage(result.getInt("grammage"));
                pap.setCouleur(result.getString("couler"));
                pap.setHauteur(result.getFloat("hauteur"));
                pap.setLargeur(result.getFloat("largeur"));
                pap.setQte(result.getDouble("qte_stock"));
                result.close();
                m = Double.valueOf(txt_m.getText());
                p = Double.valueOf(txt_p.getText());
                h = Double.valueOf(txt_h.getText());
                l = Double.valueOf(txt_l.getText());
                Float larg = detailCmd.getLargeur();
                if (check_mult.isSelected()) {
                    float ep = Float.valueOf(txt_epaisseur.getText());
                    larg = larg * 2 + ep;

                }
                //JOptionPane.showMessageDialog(null, larg);
                Double lMontage = l;//- (m + p);
                Double hMontage = h;// - m * 2;
                if (((lMontage >= larg && hMontage >= detailCmd.getHauteur()) || (hMontage >= larg && lMontage >= detailCmd.getHauteur()))) {
                    nbrePos = 0;
                    nbreTirage = 0;
                    int larAvecLar;
                    int nbreH = (int) (hMontage / detailCmd.getHauteur());
                    int nbreL = (int) (lMontage / larg);
                    larAvecLar = nbreH * nbreL;
                    int larAvecHaut;
                    int nbreH2 = (int) (lMontage / detailCmd.getHauteur());
                    int nbreL2 = (int) (hMontage / larg);
                    larAvecHaut = nbreH2 * nbreL2;
                    if (larAvecLar > larAvecHaut) {
                        nbrePos = larAvecLar;
                        lbl_img_info.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/larg_avec_larg.jpg")));
                    } else {
                        nbrePos = larAvecHaut;
                        lbl_img_info.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/larg_avec_haut.jpg")));
                    }
                    //papier ------------------------------
                    int qte = detailCmd.getQte();//+ ((20 / 100) * detailCmd.getQte());
                    nbreTirage = qte / nbrePos;
                    h = h + m * 2;
                    l = l + m + p;
                    nbreH = (int) (pap.getHauteur() / h);
                    nbreL = (int) (pap.getLargeur() / l);
                    larAvecLar = nbreH * nbreL;
                    nbreH2 = (int) (pap.getHauteur() / l);
                    nbreL2 = (int) (pap.getLargeur() / h);
                    larAvecHaut = nbreH2 * nbreL2;
                    if (larAvecLar > larAvecHaut) {
                        nbreFeuillesConsomme = Double.valueOf(nbreTirage / larAvecLar);
                    } else {
                        nbreFeuillesConsomme = Double.valueOf(nbreTirage / larAvecHaut);
                    }
                    double percent = 0.2;
                    //  JOptionPane.showMessageDialog(null, percent);
                    percent = percent * nbreFeuillesConsomme;
                    //  JOptionPane.showMessageDialog(null, percent);
                    nbreFeuillesConsomme += percent;
                    // JOptionPane.showMessageDialog(null, nbreFeuillesConsomme);
                    if (nbreFeuillesConsomme <= pap.getQte()) {
                        JOptionPane.showMessageDialog(null, "Commande oki !!", "", JOptionPane.INFORMATION_MESSAGE);
                        result = state.executeQuery("select prix_achat/nbre_fueilles as 'prixF' from alimentation where id_papier=" + pap.getId_papier() + " order by date_achat desc LIMIT 1");
                        if (result.next()) {
                            prixPapier = nbreFeuillesConsomme * result.getDouble("prixF");
                        }
                        result.close();
                        detailCmd.setPrixPapier(prixPapier);
                        detailCmd.setPapier(pap);
                        oki = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Le papier n'est pas suffisant !!");
                        oki = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Le format de montage n'est pas suffisant !");
                    oki = false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Le papier avec les caracteristiques demandées n'existe pas dans la base de donnée\nIl faut l'ajouter en allant dans la gestion du papier.");
                oki = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(detailCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oki;
    }

    private Boolean checkPapierMult(String nom_papier, String couleur, int grammage) {
        Boolean oki = true;
        Papier pap = new Papier();

        try {
            result = state.executeQuery("select * from papier where nom_papier='" + nom_papier
                    + "' and couler='" + couleur + "' and grammage=" + grammage);
            if (result.next()) {
                pap.setId_papier(result.getInt("id_papier"));
                pap.setNom_papier(result.getString("nom_papier"));
                pap.setGrammage(result.getInt("grammage"));
                pap.setCouleur(result.getString("couler"));
                pap.setHauteur(result.getFloat("hauteur"));
                pap.setLargeur(result.getFloat("largeur"));
                pap.setQte(result.getDouble("qte_stock"));
                result.close();
                int idPap = pap.getId_papier(),
                        nbrePage = (int) spn_nbrePage_mult.getValue(),
                        nbreClr = (int) spn_nbre_colors_mult.getValue();
                double ep = Double.valueOf(txt_epaisseur.getText());

                mult.setPapier(pap);
                mult.setNbre_page(nbrePage);
                mult.setNbre_couleur(nbreClr);
                mult.setEpaisseur_bande(ep);
                detailCmd.setMultip(mult);
                m = Double.valueOf(txt_m.getText());
                p = Double.valueOf(txt_p.getText());
                h = Double.valueOf(txt_h.getText());
                l = Double.valueOf(txt_l.getText());
                Float larg = detailCmd.getLargeur();

                // JOptionPane.showMessageDialog(null, larg);
                Double lMontage = l;// - (m + p);
                Double hMontage = h;//- m * 2;
                if (!((lMontage < larg || hMontage < detailCmd.getHauteur()) && (hMontage < larg || lMontage < detailCmd.getHauteur()))) {
                    nbrePosM = 0;
                    nbreTirageM = 0;
                    int larAvecLar;
                    int nbreH = (int) (hMontage / detailCmd.getHauteur());
                    int nbreL = (int) (lMontage / larg);
                    larAvecLar = nbreH * nbreL;
                    int larAvecHaut;
                    int nbreH2 = (int) (lMontage / detailCmd.getHauteur());
                    int nbreL2 = (int) (hMontage / larg);
                    larAvecHaut = nbreH2 * nbreL2;
                    if (larAvecLar > larAvecHaut) {
                        nbrePosM = larAvecLar;
                        lbl_img_info.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/larg_avec_larg.jpg")));
                    } else {
                        nbrePosM = larAvecHaut;
                        lbl_img_info.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/larg_avec_haut.jpg")));
                    }
                    //Papier -------------------------
                    int qte = detailCmd.getQte() * mult.getNbre_page();//+ ((20 / 100) * detailCmd.getQte());
                    nbreTirageM = qte / nbrePosM;
                    h = h + m * 2;
                    l = l + m + p;
                    nbreH = (int) (pap.getHauteur() / h);
                    nbreL = (int) (pap.getLargeur() / l);
                    larAvecLar = nbreH * nbreL;
                    nbreH2 = (int) (pap.getHauteur() / l);
                    nbreL2 = (int) (pap.getLargeur() / h);
                    larAvecHaut = nbreH2 * nbreL2;
                    if (larAvecLar > larAvecHaut) {
                        nbreFeuillesConsommeM = Double.valueOf(nbreTirageM / larAvecLar);
                    } else {
                        nbreFeuillesConsommeM = Double.valueOf(nbreTirageM / larAvecHaut);
                    }
                    double percent = 0.2;
                    //  JOptionPane.showMessageDialog(null, percent);
                    percent = percent * nbreFeuillesConsommeM;
                    // JOptionPane.showMessageDialog(null, percent);
                    nbreFeuillesConsommeM += percent;
                    // JOptionPane.showMessageDialog(null, nbreFeuillesConsommeM);
                    if (nbreFeuillesConsommeM <= pap.getQte()) {
                        JOptionPane.showMessageDialog(null, "Commande oki !!", "", JOptionPane.INFORMATION_MESSAGE);
                        result = state.executeQuery("select prix_achat/nbre_fueilles as 'prixF' from alimentation where id_papier=" + pap.getId_papier() + " order by date_achat desc LIMIT 1");
                        if (result.next()) {
                            prixPapierM = nbreFeuillesConsommeM * result.getDouble("prixF");
                        }
                        result.close();
                        detailCmd.setPrixPapier(detailCmd.getPrixPapier() + prixPapierM);
                        // detailCmd.getMultip().setPapier(pap);
                        oki = true;
                        //JOptionPane.showMessageDialog(null, "debut pr mult  : " + mult.toString());
                        result = state.executeQuery("select id_multip from multipage where id_papier =" + idPap + "  and  nbre_page=" + nbrePage + " and nbre_couleur=" + nbreClr + " and epaisseur_bande=" + ep);
                        //  JOptionPane.showMessageDialog(null, result + " : " + result.next());
                        if (!result.next()) {
                            // JOptionPane.showMessageDialog(null, "entre dans else  : " + mult.toString());
                            result.close();
                            String q = "INSERT INTO multipage SET id_papier =" + idPap + "  , nbre_page=" + nbrePage + " , nbre_couleur=" + nbreClr + " , epaisseur_bande=" + ep;
                            state.executeUpdate(q, Statement.RETURN_GENERATED_KEYS);
                            result = state.getGeneratedKeys();
                            if (result != null && result.next()) {
                                detailCmd.getMultip().setId_multip(result.getInt(1));
                            }
                            //    JOptionPane.showMessageDialog(null, "N'existait pas  : " + mult.toString());
                        } else {
                            // JOptionPane.showMessageDialog(null, "entre dans si  : " + mult.toString());
                            detailCmd.getMultip().setId_multip(result.getInt("id_multip"));
                            // JOptionPane.showMessageDialog(null, "mult existait deja  : " + mult.toString());
                        }
                        result.close();
                    } else {
                        JOptionPane.showMessageDialog(null, "Le papier n'est pas suffisant !!");
                        oki = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Le format de montage n'est pas suffisant !");
                    oki = false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Le papier avec les caracteristiques demandées n'existe pas dans la base de donnée\nIl faut l'ajouter en allant dans la gestion du papier.");
                oki = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(detailCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oki;
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
            java.util.logging.Logger.getLogger(detailCommande.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(detailCommande.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(detailCommande.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(detailCommande.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                detailCommande dialog = new detailCommande(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {

                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private Boolean isAllEntred() {
        Component[] e = pnl_document.getComponents();
        Boolean oki = true;
        for (int i = 0; i < e.length && oki; i++) {
            Component c = e[i];
            if (c.getClass().getSimpleName().equals("JComboBox")) {
                oki = ((JComboBox) c).getSelectedIndex() >= 0;
            } else if (c.getClass().getSimpleName().equals("JTextField")) {

                oki = !"".equals(((JTextField) c).getText());
            } else if (c.getClass().getSimpleName().equals("JFormattedTextField")) {
                oki = !"".equals(((JFormattedTextField) c).getText());
            } else if (c.getClass().getSimpleName().equals("JSpinner")) {
                oki = ((JSpinner) c).getValue() != "";
            }
        }
        if (!oki) {
            JOptionPane.showMessageDialog(null, "Vueillez saisir tous les infos concernant le document !");
            return oki;
        }
        oki = !"".equals(txt_h.getText()) && !"".equals(txt_l.getText());
        if (!oki) {
            JOptionPane.showMessageDialog(null, "Vueillez saisir tous les infos le format de montage !");
            return oki;
        }
        oki = !"".equals(txt_m.getText()) && !"".equals(txt_p.getText());
        if (!oki) {
            JOptionPane.showMessageDialog(null, "Vueillez saisir tous les marges et la prise de pince !");
            return oki;
        }
        oki = cmb_couleur.getSelectedIndex() >= 0 && cmb_papier.getSelectedIndex() >= 0 && spn_grammage.getValue() != "";
        if (!oki) {
            JOptionPane.showMessageDialog(null, "Vueillez saisir tous infos du papier !");
            return oki;
        }
        if (check_mult.isSelected()) {
            e = pnl_pap_mult.getComponents();

            for (int i = 0; i < e.length && oki; i++) {
                Component c = e[i];
                if (c.getClass().getSimpleName().equals("JComboBox")) {
                    oki = ((JComboBox) c).getSelectedIndex() >= 0;
                } else if (c.getClass().getSimpleName().equals("JTextField")) {

                    oki = !"".equals(((JTextField) c).getText());
                } else if (c.getClass().getSimpleName().equals("JFormattedTextField")) {
                    oki = !"".equals(((JFormattedTextField) c).getText());
                } else if (c.getClass().getSimpleName().equals("JSpinner")) {
                    oki = ((JSpinner) c).getValue() != "";
                }
            }
            if (!oki) {
                JOptionPane.showMessageDialog(null, "Vueillez saisir tous infos concernant le contenu du document multipage !");
                return oki;
            }
        }

        return oki;
    }

    private Boolean verifier() {

        if (isAllEntred()) {
            Boolean oki = true;

            if (cmb_document.getSelectedIndex() < 0) {
                JOptionPane.showMessageDialog(null, "Vueillez choisir un document");
                return false;
            } else {
                detailCmd.setDocument((String) cmb_document.getSelectedItem());
            }
            if (!detailCmd.setHauteur(Float.valueOf(txt_hauteur.getText()))) {
                JOptionPane.showMessageDialog(null, "Vueillez saisir une hauteur valide !");
                return false;
            } else if (!detailCmd.setLargeur(Float.valueOf(txt_largeur.getText()))) {
                JOptionPane.showMessageDialog(null, "Vueillez saisir une largeur valide !");
                return false;
            } else if (!detailCmd.setNbreCouleur((int) spn_nbreCouleurs.getValue())) {
                JOptionPane.showMessageDialog(null, "Vueillez saisir un nbre de couleur entre 1 et 4 !");
                return false;
            } else if (!detailCmd.setQte((int) spn_qte.getValue())) {
                JOptionPane.showMessageDialog(null, "Vueillez saisir une quantité supérieur à 0 !");
                return false;
            } else if (!detailCmd.setPrixConception(Double.valueOf(txt_prixConception.getText()))) {
                JOptionPane.showMessageDialog(null, "emmm !! le prix de conception est négatif !");
                return false;
            } else if (!detailCmd.setPrixMatiere(Double.valueOf(txt_prixMatiere.getText()))) {
                JOptionPane.showMessageDialog(null, "emmm !! le prix des matieres est négatif !");
                return false;
            }
            if (txt_h.getText().equals("") || txt_l.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vueillez saisir une taille du format de montage !");
                return false;
            }
            if (txt_m.getText().equals("") || txt_p.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vueillez saisir les marges à laisser dans le papier !");
                return false;
            }
            if (cmb_papier.getSelectedIndex() < 0 || cmb_couleur.getSelectedIndex() < 0 || (int) spn_grammage.getValue() <= 0) {
                JOptionPane.showMessageDialog(null, "Vueillez saisir des infos valide pour le papier !");
                return false;
            }
            if (check_mult.isSelected()) {
                if (cmb_papier_mult.getSelectedIndex() < 0 || cmb_couleur_mult.getSelectedIndex() < 0 || (int) spn_grammage_mult.getValue() <= 0) {
                    JOptionPane.showMessageDialog(null, "Vueillez saisir des infos valide pour le papier de contenu de document multipage !");
                    return false;
                }
                if (!detailCmd.getMultip().setNbre_couleur((int) spn_nbre_colors_mult.getValue())) {
                    JOptionPane.showMessageDialog(null, "Vueillez saisir un nbre de couleur entre 1 et 4 pour le contenu de document multipage!");
                    return false;
                }
                if (!detailCmd.getMultip().setNbre_page((int) spn_nbrePage_mult.getValue())) {
                    JOptionPane.showMessageDialog(null, "Vueillez saisir un nbre page valide pour le contenu de document multipage!");
                    return false;
                }
                if (!detailCmd.getMultip().setEpaisseur_bande(Float.valueOf(txt_epaisseur.getText()))) {
                    JOptionPane.showMessageDialog(null, "Vueillez saisir une epaisseur superieur ou égale à 0 pour le document multipage!");
                    return false;
                }
            }
            detailCmd.setIsMultiple(check_mult.isSelected());
            return true;
        } else {
            return false;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_add_doc;
    private javax.swing.JButton btn_afficher_info;
    private javax.swing.JButton btn_afficher_infoMilt6;
    private javax.swing.JButton btn_calculer;
    private javax.swing.JButton btn_close;
    private javax.swing.JButton btn_delet_doc;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_edit_doc;
    private javax.swing.JCheckBox check_mult;
    private javax.swing.JComboBox cmb_couleur;
    private javax.swing.JComboBox cmb_couleur_mult;
    private javax.swing.JComboBox cmb_document;
    private javax.swing.JComboBox cmb_papier;
    private javax.swing.JComboBox cmb_papier_mult;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_img_info;
    private javax.swing.JLabel lbl_img_info_mult6;
    private javax.swing.JLabel lbl_ste_name11;
    private javax.swing.JLabel lbl_ste_name17;
    private javax.swing.JLabel lbl_ste_name18;
    private javax.swing.JLabel lbl_ste_name19;
    private javax.swing.JLabel lbl_ste_name20;
    private javax.swing.JLabel lbl_ste_name21;
    private javax.swing.JLabel lbl_ste_name22;
    private javax.swing.JLabel lbl_ste_name23;
    private javax.swing.JLabel lbl_ste_name24;
    private javax.swing.JLabel lbl_ste_name25;
    private javax.swing.JLabel lbl_ste_name26;
    private javax.swing.JLabel lbl_ste_name27;
    private javax.swing.JLabel lbl_ste_name28;
    private javax.swing.JLabel lbl_ste_name29;
    private javax.swing.JLabel lbl_ste_name9;
    private javax.swing.JPanel pnl_document;
    private javax.swing.JPanel pnl_format;
    private javax.swing.JPanel pnl_marge;
    private javax.swing.JPanel pnl_pap_mult;
    private javax.swing.JPanel pnl_papier;
    private javax.swing.JPanel pnl_papier_mult;
    private javax.swing.JSpinner spn_grammage;
    private javax.swing.JSpinner spn_grammage_mult;
    private javax.swing.JSpinner spn_nbreCouleurs;
    private javax.swing.JSpinner spn_nbrePage_mult;
    private javax.swing.JSpinner spn_nbre_colors_mult;
    private javax.swing.JSpinner spn_qte;
    private javax.swing.JTextField txt_epaisseur;
    private javax.swing.JFormattedTextField txt_h;
    private javax.swing.JFormattedTextField txt_hauteur;
    private javax.swing.JFormattedTextField txt_l;
    private javax.swing.JFormattedTextField txt_largeur;
    private javax.swing.JFormattedTextField txt_m;
    private javax.swing.JTextField txt_nbePose;
    private javax.swing.JTextField txt_nbreFueille_mult6;
    private javax.swing.JTextField txt_nbreFueilles;
    private javax.swing.JTextField txt_nbrePose_mult6;
    private javax.swing.JTextField txt_nbreTirage;
    private javax.swing.JTextField txt_nbreTirage_mult6;
    private javax.swing.JFormattedTextField txt_p;
    private javax.swing.JFormattedTextField txt_prixConception;
    private javax.swing.JFormattedTextField txt_prixImpression;
    private javax.swing.JFormattedTextField txt_prixMatiere;
    private javax.swing.JTextField txt_prix_papier_cons_mult6;
    private javax.swing.JTextField txt_prix_papier_consomme;
    // End of variables declaration//GEN-END:variables
}
