/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agipub;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooserCellEditor;
import java.awt.Color;
import java.awt.HeadlessException;
import java.util.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import object.Alimentation;
import object.ColorCellRenderer;
import object.Couleurs;
import object.Papier;
import object.Ramette;
import object.comboCellEditor;

/**
 *
 * @author win_bmx
 */
public class pan_papier extends javax.swing.JPanel {

    int papierCode = -1;
    int rowPapier = -1, rowAlimentation = -1;
    Statement state;
    ResultSet result;
    String titlePapier[] = new String[]{
        "id_papier", "Nom Papier", "Grammage", "Couleur", "Hauteur", "Largeur", "Quantité en Stock"
    };

    String titleAlimentation[] = new String[]{
        "Code Alimentation", "id_papier", "Date Achat", "Prix (Dh)", "Nbre ramette", "Nbre Feuilles par ramette"
    };
    static ArrayList<Papier> dataPapier = new ArrayList<>();
    static ArrayList<Ramette> dataRamette = new ArrayList<>();
    static ArrayList<Alimentation> dataAlimentation = new ArrayList<>();

    /**
     * Creates new form pan_papier
     */
    public pan_papier() {
        initComponents();
        chargerPapier();

        chargerAlimentation();

        //  ((DefaultTableModel)tbl_papier.getModel());
    }

    private void chargerPapier() {
        try {
            state = Connexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Object[][] data;
            result = state.executeQuery("SELECT * FROM papier");
            dataPapier.removeAll(dataPapier);
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
            }
            result.last();
            data = new Object[result.getRow()][titlePapier.length];
            int j = 1;
            result.beforeFirst();

            while (result.next()) {
                for (int i = 1; i <= titlePapier.length; i++) {
                    data[j - 1][i - 1] = result.getObject(i);
                }
                j++;

            }
            tbl_papier.setModel(new DefaultTableModel(data, titlePapier) {

                Class[] types = new Class[]{
                    java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, Color.class, java.lang.Float.class, java.lang.Float.class, java.lang.Integer.class
                };

                @Override
                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    if (rowPapier == rowIndex) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            );
            tbl_papier.setCellSelectionEnabled(false);
            tbl_papier.setRowSelectionAllowed(true);
            tbl_papier.setDefaultRenderer(Color.class, new ColorCellRenderer());
            tbl_papier.setDefaultEditor(Color.class, new comboCellEditor(Couleurs.values()));

            state.close();

            result.close();

        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ereur au chargement des clients\n" + ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ereur au chargement des clients\n" + ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void chargerAlimentation() {
        try {
            state = Connexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Object[][] data;
            int indexPapier = tbl_papier.getSelectedRow();
            // int indexRamette = tbl_ramette.getSelectedRow();
            String query;
            if (papierCode != -1) {
                
                query = "SELECT a.* FROM alimentation a inner join papier p on p.id_papier=a.id_papier WHERE a.id_papier =" + papierCode;
            } else {
                query = "SELECT * FROM alimentation";
            }
            result = state.executeQuery(query);
            dataAlimentation.removeAll(dataAlimentation);
            Alimentation alim;

            while (result.next()) {
                alim = new Alimentation();
                alim.setId_alimentation(result.getInt("id_alimentation"));
                alim.setId_papier(result.getInt("id_papier"));
                alim.setDateAchat(result.getDate("date_achat"));
                alim.setPrixAchat(result.getDouble("prix_achat"));
                alim.setNbreRamette(result.getInt("nbre_ramette"));
                alim.setNbreFueille(result.getInt("nbre_fueilles"));
                dataAlimentation.add(alim);
            }
            result.last();
            data = new Object[result.getRow()][titleAlimentation.length];
            int j = 1;
            result.beforeFirst();

            while (result.next()) {
                for (int i = 1; i <= titleAlimentation.length; i++) {
                    data[j - 1][i - 1] = result.getObject(i);
                }
                j++;

            }
            tbl_alimentation.setModel(new DefaultTableModel(data, titleAlimentation) {

                Class[] types = new Class[]{
                    java.lang.Integer.class, java.lang.Integer.class, java.util.Date.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class
                };

                @Override
                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    if (rowIndex == rowAlimentation) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });

            Integer[] paps = new Integer[dataPapier.size()];
            for (int i = 0; i < dataPapier.size(); i++) {
                paps[i] = dataPapier.get(i).getId_papier();
            }
            tbl_alimentation.setDefaultEditor(java.util.Date.class, new JDateChooserCellEditor());

            tbl_alimentation.getColumn("id_papier").setCellEditor(new comboCellEditor(paps));

            state.close();
            result.close();

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

        pop_papier = new javax.swing.JPopupMenu();
        menu_actualiser_papier = new javax.swing.JMenuItem();
        menu_atteindre_papier = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menu_add_papier = new javax.swing.JMenuItem();
        menu_edit_papier = new javax.swing.JMenuItem();
        menu_delete_papier = new javax.swing.JMenuItem();
        menu_annuler_papier = new javax.swing.JMenuItem();
        pop_alimentation = new javax.swing.JPopupMenu();
        menu_actualiser_alimentation = new javax.swing.JMenuItem();
        menu_atteindre_alimentation = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        menu_add_alimentation = new javax.swing.JMenuItem();
        menu_edit_alimentation = new javax.swing.JMenuItem();
        menu_delete_alimentation = new javax.swing.JMenuItem();
        menu_annuler_alimentation = new javax.swing.JMenuItem();
        splitPane_papier = new javax.swing.JSplitPane();
        jPanel8 = new javax.swing.JPanel();
        pnl_papier = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        btn_atteindre_papier = new javax.swing.JButton();
        btn_refrech_papier = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btn_add_papier = new javax.swing.JButton();
        btn_edit_papier = new javax.swing.JButton();
        btn_delete_papier = new javax.swing.JButton();
        btn_annuler_papier = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        btn_editable_alimentation1 = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        btn_aff = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_papier = new javax.swing.JTable();
        pnl_alimentation = new javax.swing.JScrollPane();
        pnl_alimentation_papier = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_alimentation = new javax.swing.JTable();
        jToolBar4 = new javax.swing.JToolBar();
        btn_atteindre_alimentation = new javax.swing.JButton();
        btn_refrech_alimentation = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btn_add_alimentation = new javax.swing.JButton();
        btn_edit_alimentation = new javax.swing.JButton();
        btn_delete_alimentation = new javax.swing.JButton();
        btn_annuler_alimentation = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btn_editable_alimentation = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btn_papier_concerne = new javax.swing.JButton();

        pop_papier.setPreferredSize(new java.awt.Dimension(200, 148));

        menu_actualiser_papier.setText("Actualiser");
        menu_actualiser_papier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_actualiser_papierActionPerformed(evt);
            }
        });
        pop_papier.add(menu_actualiser_papier);

        menu_atteindre_papier.setText("Ateindre");
        menu_atteindre_papier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_atteindre_papierActionPerformed(evt);
            }
        });
        pop_papier.add(menu_atteindre_papier);
        pop_papier.add(jSeparator1);

        menu_add_papier.setText("Ajouter");
        menu_add_papier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_add_papierActionPerformed(evt);
            }
        });
        pop_papier.add(menu_add_papier);

        menu_edit_papier.setText("Modifier");
        menu_edit_papier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_edit_papierActionPerformed(evt);
            }
        });
        pop_papier.add(menu_edit_papier);

        menu_delete_papier.setText("Supprimer");
        menu_delete_papier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_delete_papierActionPerformed(evt);
            }
        });
        pop_papier.add(menu_delete_papier);

        menu_annuler_papier.setText("Annuler");
        menu_annuler_papier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_annuler_papierActionPerformed(evt);
            }
        });
        pop_papier.add(menu_annuler_papier);

        pop_alimentation.setPreferredSize(new java.awt.Dimension(200, 148));

        menu_actualiser_alimentation.setText("Actualiser");
        menu_actualiser_alimentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_actualiser_alimentationActionPerformed(evt);
            }
        });
        pop_alimentation.add(menu_actualiser_alimentation);

        menu_atteindre_alimentation.setText("Ateindre");
        menu_atteindre_alimentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_atteindre_alimentationActionPerformed(evt);
            }
        });
        pop_alimentation.add(menu_atteindre_alimentation);
        pop_alimentation.add(jSeparator6);

        menu_add_alimentation.setText("Ajouter");
        menu_add_alimentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_add_alimentationActionPerformed(evt);
            }
        });
        pop_alimentation.add(menu_add_alimentation);

        menu_edit_alimentation.setText("Modifier");
        menu_edit_alimentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_edit_alimentationActionPerformed(evt);
            }
        });
        pop_alimentation.add(menu_edit_alimentation);

        menu_delete_alimentation.setText("Supprimer");
        menu_delete_alimentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_delete_alimentationActionPerformed(evt);
            }
        });
        pop_alimentation.add(menu_delete_alimentation);

        menu_annuler_alimentation.setText("Annuler");
        menu_annuler_alimentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_annuler_alimentationActionPerformed(evt);
            }
        });
        pop_alimentation.add(menu_annuler_alimentation);

        setLayout(new java.awt.BorderLayout());

        splitPane_papier.setDividerLocation(280);
        splitPane_papier.setDividerSize(15);
        splitPane_papier.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        splitPane_papier.setLastDividerLocation(splitPane_papier.getHeight()/2);
        splitPane_papier.setOneTouchExpandable(true);

        jPanel8.setLayout(new java.awt.BorderLayout());

        pnl_papier.setBorder(javax.swing.BorderFactory.createTitledBorder("Papier"));
        pnl_papier.setLayout(new java.awt.BorderLayout());

        jToolBar2.setBackground(new java.awt.Color(204, 204, 204));
        jToolBar2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar2.setForeground(new java.awt.Color(255, 255, 255));
        jToolBar2.setRollover(true);

        btn_atteindre_papier.setText("Atteindre");
        btn_atteindre_papier.setFocusable(false);
        btn_atteindre_papier.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_atteindre_papier.setOpaque(false);
        btn_atteindre_papier.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_atteindre_papier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atteindre_papierActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_atteindre_papier);

        btn_refrech_papier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/interact_16px.png"))); // NOI18N
        btn_refrech_papier.setToolTipText("Actualiser");
        btn_refrech_papier.setFocusable(false);
        btn_refrech_papier.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_refrech_papier.setOpaque(false);
        btn_refrech_papier.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_refrech_papier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refrech_papierActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_refrech_papier);
        jToolBar2.add(jSeparator3);

        btn_add_papier.setForeground(new java.awt.Color(255, 255, 255));
        btn_add_papier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/plus_16px.png"))); // NOI18N
        btn_add_papier.setToolTipText("Ajouter");
        btn_add_papier.setFocusable(false);
        btn_add_papier.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_add_papier.setOpaque(false);
        btn_add_papier.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_add_papier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_papierActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_add_papier);

        btn_edit_papier.setForeground(new java.awt.Color(255, 255, 255));
        btn_edit_papier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/graphic_design_16px.png"))); // NOI18N
        btn_edit_papier.setToolTipText("Modifier");
        btn_edit_papier.setFocusable(false);
        btn_edit_papier.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_edit_papier.setOpaque(false);
        btn_edit_papier.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_edit_papier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_edit_papierActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_edit_papier);

        btn_delete_papier.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete_papier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/sub_blue_delete_16px.png"))); // NOI18N
        btn_delete_papier.setToolTipText("Supprimer");
        btn_delete_papier.setFocusable(false);
        btn_delete_papier.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_delete_papier.setOpaque(false);
        btn_delete_papier.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_delete_papier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_papierActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_delete_papier);

        btn_annuler_papier.setForeground(new java.awt.Color(255, 255, 255));
        btn_annuler_papier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/undo_16px.png"))); // NOI18N
        btn_annuler_papier.setToolTipText("Annuler");
        btn_annuler_papier.setFocusable(false);
        btn_annuler_papier.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_annuler_papier.setMargin(new java.awt.Insets(2, 7, 2, 7));
        btn_annuler_papier.setOpaque(false);
        btn_annuler_papier.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_annuler_papier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_annuler_papierActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_annuler_papier);
        jToolBar2.add(jSeparator7);

        btn_editable_alimentation1.setText(">");
        btn_editable_alimentation1.setToolTipText("Atteindre la ligne en cours d'édition");
        btn_editable_alimentation1.setFocusable(false);
        btn_editable_alimentation1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_editable_alimentation1.setOpaque(false);
        btn_editable_alimentation1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_editable_alimentation1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editable_alimentation1ActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_editable_alimentation1);
        jToolBar2.add(jSeparator8);

        btn_aff.setText("Afficher");
        btn_aff.setToolTipText("Afficher uniquement les alimentation de ce papier");
        btn_aff.setFocusable(false);
        btn_aff.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_aff.setOpaque(false);
        btn_aff.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_aff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_affActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_aff);

        pnl_papier.add(jToolBar2, java.awt.BorderLayout.PAGE_START);

        tbl_papier.setShowGrid(true);
        tbl_papier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "id_papier", "Nom Papier", "Grammage", "Couleur", "Hauteur", "Largeur", "Quantité en Stock"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Float.class, java.lang.Float.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_papier.setComponentPopupMenu(pop_papier);
        tbl_papier.setRowHeight(32);
        tbl_papier.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tbl_papier);
        tbl_papier.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        pnl_papier.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel8.add(pnl_papier, java.awt.BorderLayout.CENTER);

        splitPane_papier.setLeftComponent(jPanel8);

        pnl_alimentation_papier.setBorder(javax.swing.BorderFactory.createTitledBorder("Alimentation Papier"));
        pnl_alimentation_papier.setLayout(new java.awt.BorderLayout());

        tbl_alimentation.setShowGrid(true);
        tbl_alimentation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Code Alimentation", "id_papier", "Date Achat", "Prix", "Nbre ramette", "Nbre Feuilles par ramette"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_alimentation.setComponentPopupMenu(pop_alimentation);
        tbl_alimentation.setRowHeight(32);
        tbl_alimentation.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(tbl_alimentation);

        pnl_alimentation_papier.add(jScrollPane6, java.awt.BorderLayout.CENTER);

        jToolBar4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar4.setRollover(true);

        btn_atteindre_alimentation.setText("Atteindre");
        btn_atteindre_alimentation.setFocusable(false);
        btn_atteindre_alimentation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_atteindre_alimentation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_atteindre_alimentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atteindre_alimentationActionPerformed(evt);
            }
        });
        jToolBar4.add(btn_atteindre_alimentation);

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
        jToolBar4.add(btn_refrech_alimentation);
        jToolBar4.add(jSeparator4);

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
        jToolBar4.add(btn_add_alimentation);

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
        jToolBar4.add(btn_edit_alimentation);

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
        jToolBar4.add(btn_delete_alimentation);

        btn_annuler_alimentation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/undo_16px.png"))); // NOI18N
        btn_annuler_alimentation.setToolTipText("Annuler");
        btn_annuler_alimentation.setFocusable(false);
        btn_annuler_alimentation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_annuler_alimentation.setMargin(new java.awt.Insets(2, 7, 2, 7));
        btn_annuler_alimentation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_annuler_alimentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_annuler_alimentationActionPerformed(evt);
            }
        });
        jToolBar4.add(btn_annuler_alimentation);
        jToolBar4.add(jSeparator2);

        btn_editable_alimentation.setText(">");
        btn_editable_alimentation.setToolTipText("Atteindre la ligne en cours d'édition");
        btn_editable_alimentation.setFocusable(false);
        btn_editable_alimentation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_editable_alimentation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_editable_alimentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editable_alimentationActionPerformed(evt);
            }
        });
        jToolBar4.add(btn_editable_alimentation);
        jToolBar4.add(jSeparator5);

        btn_papier_concerne.setText("Papier");
        btn_papier_concerne.setFocusable(false);
        btn_papier_concerne.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_papier_concerne.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_papier_concerne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_papier_concerneActionPerformed(evt);
            }
        });
        jToolBar4.add(btn_papier_concerne);

        pnl_alimentation_papier.add(jToolBar4, java.awt.BorderLayout.WEST);

        pnl_alimentation.setViewportView(pnl_alimentation_papier);

        splitPane_papier.setRightComponent(pnl_alimentation);

        add(splitPane_papier, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void menu_atteindre_papierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_atteindre_papierActionPerformed
        // TODO add your handling code here:
        btn_atteindre_papierActionPerformed(evt);
    }//GEN-LAST:event_menu_atteindre_papierActionPerformed

    private void menu_actualiser_papierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_actualiser_papierActionPerformed
        // TODO add your handling code here:
        btn_refrech_papierActionPerformed(evt);
    }//GEN-LAST:event_menu_actualiser_papierActionPerformed

    private void menu_add_papierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_add_papierActionPerformed
        // TODO add your handling code here:
        btn_add_papierActionPerformed(evt);
    }//GEN-LAST:event_menu_add_papierActionPerformed

    private void menu_edit_papierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_edit_papierActionPerformed
        // TODO add your handling code here:
        btn_edit_papierActionPerformed(evt);
    }//GEN-LAST:event_menu_edit_papierActionPerformed

    private void menu_delete_papierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_delete_papierActionPerformed
        // TODO add your handling code here:
        btn_delete_papierActionPerformed(evt);
    }//GEN-LAST:event_menu_delete_papierActionPerformed

    private void menu_annuler_papierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_annuler_papierActionPerformed
        // TODO add your handling code here:
        btn_annuler_papierActionPerformed(evt);
    }//GEN-LAST:event_menu_annuler_papierActionPerformed

    private void btn_atteindre_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atteindre_alimentationActionPerformed
        // TODO add your handling code here:
        String rep = JOptionPane.showInputDialog("Saisir le code ramette alimentation : ");
        if (rep != null) {
            Boolean existe = false;
            int code = Integer.parseInt(rep);
            for (int i = 0; i < dataAlimentation.size() && !existe; i++) {
                if (dataAlimentation.get(i).getId_alimentation() == code) {
                    tbl_alimentation.changeSelection(i, 0, false, false);
                    existe = true;
                }
            }
            if (!existe) {
                JOptionPane.showMessageDialog(null, "Ce code alimentation n'existe pas !");
            }

        }
    }//GEN-LAST:event_btn_atteindre_alimentationActionPerformed

    private void btn_refrech_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refrech_alimentationActionPerformed
        // TODO add your handling code here:
        btn_annuler_alimentationActionPerformed(evt);
        chargerAlimentation();
    }//GEN-LAST:event_btn_refrech_alimentationActionPerformed

    private void btn_add_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_alimentationActionPerformed
        // TODO add your handling code here:
        try {

            if ("Ajouter".equals(btn_add_alimentation.getToolTipText())) {
                JCalendar cal = new JCalendar();
//                int id = dataAlimentation.get(dataAlimentation.size() - 1).getId_alimentation();
//                id++;
                ((DefaultTableModel) tbl_alimentation.getModel()).addRow(new Object[]{0, 0, cal.getDate(), 0, 0, 0});
                rowAlimentation = tbl_alimentation.getRowCount() - 1;
                tbl_alimentation.changeSelection(rowAlimentation, 0, false, false);
                tbl_alimentation.setForeground(Color.red);
                btn_edit_alimentation.setEnabled(false);
                btn_delete_alimentation.setEnabled(false);
                btn_atteindre_alimentation.setEnabled(false);

                btn_add_alimentation.setToolTipText("Enregistrer");
                btn_add_alimentation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/save_16px.png")));
                tbl_alimentation.editCellAt(rowPapier, 0);

                menu_edit_alimentation.setEnabled(false);
                menu_delete_alimentation.setEnabled(false);
                menu_atteindre_alimentation.setEnabled(false);
                menu_add_alimentation.setText("Enregistrer");

            } else {

               
                Alimentation alim = new Alimentation();
                
                alim.setId_alimentation((int) tbl_alimentation.getValueAt(rowAlimentation, 0));
                alim.setId_papier((int) tbl_alimentation.getValueAt(rowAlimentation, 1));
                alim.setDateAchat((Date) tbl_alimentation.getValueAt(rowAlimentation, 2));
                 Boolean oki = alim.setPrixAchat(Double.valueOf(String.valueOf(tbl_alimentation.getValueAt(rowAlimentation, 3))));
                
                oki = oki && alim.setNbreRamette((int) tbl_alimentation.getValueAt(rowAlimentation, 4));
                 oki = oki && alim.setNbreFueille((int) tbl_alimentation.getValueAt(rowAlimentation, 5));

                if (!oki) {
                    JOptionPane.showMessageDialog(null, "Certains données ne sont pas correctes !!");
                } else {
                    if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment ajouter cette alimentation ?", "Confirmation d'ajout !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(alim.getDateAchat());
                        String d = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONDAY) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
                        String query = "INSERT INTO alimentation (id_papier, date_achat, prix_achat, nbre_ramette, nbre_fueilles)"
                                + "VALUES (" + alim.getId_papier() + ", '" + d + "', " + alim.getPrixAchat() + ", " + alim.getNbreRamette() + ", " + alim.getNbreFueille() + ")";
                        state = Connexion.getInstance().createStatement();
                        state.executeUpdate(query);
                        state.close();
                        dataAlimentation.add(alim);
                        ((DefaultTableModel) tbl_alimentation.getModel()).fireTableRowsInserted(rowAlimentation, rowAlimentation);
                        tbl_alimentation.setForeground(Color.black);
                        rowAlimentation = -1;
                        JOptionPane.showMessageDialog(null, "Alimentation effectuée avec succès");

                        btn_edit_alimentation.setEnabled(true);
                        btn_delete_alimentation.setEnabled(true);
                        btn_atteindre_alimentation.setEnabled(true);
                        menu_edit_alimentation.setEnabled(true);
                        menu_delete_alimentation.setEnabled(true);
                        menu_atteindre_alimentation.setEnabled(true);
                        btn_add_alimentation.setToolTipText("Ajouter");
                        btn_add_alimentation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/plus_16px.png")));
                        menu_add_alimentation.setText("Ajouter");
                        chargerAlimentation();
                    }

                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_add_alimentationActionPerformed

    private void btn_edit_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_edit_alimentationActionPerformed
        // TODO add your handling code here:
        try {

            if ("Modifier".equals(btn_edit_alimentation.getToolTipText())) {
                JCalendar cal = new JCalendar();

                rowAlimentation = tbl_alimentation.getSelectedRow();
                if (rowAlimentation < 0) {
                    JOptionPane.showMessageDialog(null, "Vous devez séléctionner l'alimentation à modifier !");
                } else {
                    tbl_alimentation.changeSelection(rowAlimentation, 0, false, false);
                    tbl_alimentation.setForeground(Color.red);

                    btn_add_alimentation.setEnabled(false);
                    btn_delete_alimentation.setEnabled(false);
                    btn_atteindre_alimentation.setEnabled(false);

                    btn_edit_alimentation.setToolTipText("Enregistrer");
                    btn_edit_alimentation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/save_16px.png")));
                    tbl_alimentation.editCellAt(rowPapier, 0);

                    menu_add_alimentation.setEnabled(false);
                    menu_delete_alimentation.setEnabled(false);
                    menu_atteindre_alimentation.setEnabled(false);
                    menu_edit_alimentation.setText("Enregistrer");
                }
            } else {

                Boolean oki = true;
                Alimentation al = new Alimentation();
                int id = 0;
                if (tbl_alimentation.getValueAt(rowAlimentation, 1).getClass().getSimpleName().equals("Integer")) {
                    id = (int) tbl_alimentation.getValueAt(rowAlimentation, 1);
                } else {
                    id = Long.valueOf((long) tbl_alimentation.getValueAt(rowAlimentation, 1)).intValue();
                }
                int nbreRamette = 0;
                if (tbl_alimentation.getValueAt(rowAlimentation, 4).getClass().getSimpleName().equals("Integer")) {
                    nbreRamette = (int) tbl_alimentation.getValueAt(rowAlimentation, 4);
                } else {
                    nbreRamette = Long.valueOf((long) tbl_alimentation.getValueAt(rowAlimentation, 4)).intValue();
                }
                int nbreF = 0;
                if (tbl_alimentation.getValueAt(rowAlimentation, 5).getClass().getSimpleName().equals("Integer")) {
                    nbreF = (int) tbl_alimentation.getValueAt(rowAlimentation, 5);
                } else {
                    nbreF = Long.valueOf((long) tbl_alimentation.getValueAt(rowAlimentation, 5)).intValue();
                }
//                al.setId_alimentation((int) tbl_alimentation.getValueAt(rowAlimentation, 0));
                al.setId_papier(id);
                al.setDateAchat((Date) tbl_alimentation.getValueAt(rowAlimentation, 2));
                oki = oki && al.setPrixAchat(Double.valueOf(String.valueOf(tbl_alimentation.getValueAt(rowAlimentation, 3))));
                oki = oki &&  al.setNbreRamette(nbreRamette);
                 oki = oki && al.setNbreFueille(nbreF);
                if (!oki) {
                    JOptionPane.showMessageDialog(null, "Certains données ne sont pas correctes !!");
                } else {
                    if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment modifier cette alimentation ?", "Confirmation d'ajout !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(al.getDateAchat());
                        String d = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONDAY) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
                        Calendar cal2 = Calendar.getInstance();
                        cal2.setTime(dataAlimentation.get(rowAlimentation).getDateAchat());
                        String d2 = cal2.get(Calendar.YEAR) + "-" + (cal2.get(Calendar.MONDAY) + 1) + "-" + cal2.get(Calendar.DAY_OF_MONTH);
                        String query = "UPDATE  alimentation SET id_papier=" + al.getId_papier()
                                + ", date_achat='" + d + "', prix_achat=" + al.getPrixAchat() + ", nbre_ramette=" + al.getNbreRamette() + ", nbre_fueilles=" + al.getNbreFueille()
                                + " WHERE id_alimentation = " + dataAlimentation.get(rowAlimentation).getId_alimentation();

                        //JOptionPane.showMessageDialog(null, query);
                        state = Connexion.getInstance().createStatement();
                        state.executeUpdate(query);
                        state.close();
                        dataAlimentation.add(rowAlimentation, al);
                        ((DefaultTableModel) tbl_alimentation.getModel()).fireTableRowsUpdated(rowAlimentation, rowAlimentation);
                        tbl_alimentation.setForeground(Color.black);
                        rowAlimentation = -1;
                        JOptionPane.showMessageDialog(null, "Alimentation modifié avec succès");
                        btn_add_alimentation.setEnabled(true);
                        btn_delete_alimentation.setEnabled(true);
                        btn_atteindre_alimentation.setEnabled(true);
                        menu_add_alimentation.setEnabled(true);
                        menu_delete_alimentation.setEnabled(true);
                        menu_atteindre_alimentation.setEnabled(true);
                        btn_edit_alimentation.setToolTipText("Modifier");
                        btn_edit_alimentation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/graphic_design_16px.png")));
                        menu_edit_alimentation.setText("Modifier");
                        chargerAlimentation();
                    }

                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_edit_alimentationActionPerformed

    private void btn_delete_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_alimentationActionPerformed
        // TODO add your handling code here:
        rowAlimentation = tbl_alimentation.getSelectedRow();
        if (rowAlimentation < 0) {
            JOptionPane.showMessageDialog(null, "Vous devez séléctionner une alimentation à supprimer !");
        } else {
            if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette alimentation ?", "Confirmation de suppression !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                try {
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(dataAlimentation.get(rowAlimentation).getDateAchat());
                    String d2 = cal2.get(Calendar.YEAR) + "-" + (cal2.get(Calendar.MONDAY) + 1) + "-" + cal2.get(Calendar.DAY_OF_MONTH);
                    state = Connexion.getInstance().createStatement();
                    String query = "DELETE FROM alimentation WHERE id_alimentation=" + dataAlimentation.get(rowAlimentation).getId_alimentation();
                    state.executeUpdate(query);
                    state.close();
                    dataAlimentation.remove(rowAlimentation);
                    ((DefaultTableModel) tbl_alimentation.getModel()).removeRow(rowAlimentation);
                    ((DefaultTableModel) tbl_alimentation.getModel()).fireTableRowsDeleted(rowAlimentation, rowAlimentation);
                    JOptionPane.showMessageDialog(null, "Suppression efféctuée !");
                    rowAlimentation = -1;
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur en Suppression :\n" + ex.getMessage());
                    Logger.getLogger(pan_papier.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }//GEN-LAST:event_btn_delete_alimentationActionPerformed

    private void btn_annuler_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_annuler_alimentationActionPerformed
        // TODO add your handling code here:
        if ("Enregistrer".equals(btn_add_alimentation.getToolTipText())) {
            ((DefaultTableModel) tbl_alimentation.getModel()).removeRow(rowAlimentation);
        }
        if ("Enregistrer".equals(btn_edit_alimentation.getToolTipText())) {
            tbl_alimentation.setValueAt(dataAlimentation.get(rowAlimentation).getId_alimentation(), rowAlimentation, 0);
            tbl_alimentation.setValueAt(dataAlimentation.get(rowAlimentation).getId_papier(), rowAlimentation, 1);
            tbl_alimentation.setValueAt(dataAlimentation.get(rowAlimentation).getDateAchat(), rowAlimentation, 2);
            tbl_alimentation.setValueAt(dataAlimentation.get(rowAlimentation).getPrixAchat(), rowAlimentation, 3);
            tbl_alimentation.setValueAt(dataAlimentation.get(rowAlimentation).getNbreRamette(), rowAlimentation, 4);
            tbl_alimentation.setValueAt(dataAlimentation.get(rowAlimentation).getNbreFueille(), rowAlimentation, 5);
        }
        tbl_alimentation.editCellAt(-1, 0);
        tbl_alimentation.clearSelection();
        btn_edit_alimentation.setEnabled(true);
        btn_delete_alimentation.setEnabled(true);
        btn_atteindre_alimentation.setEnabled(true);
        btn_add_alimentation.setToolTipText("Ajouter");
        btn_add_alimentation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/plus_16px.png")));

        btn_add_alimentation.setEnabled(true);
        btn_edit_alimentation.setToolTipText("Modifier");
        btn_edit_alimentation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/graphic_design_16px.png")));

        menu_add_alimentation.setText("Ajouter");
        menu_edit_alimentation.setText("Modifier");

        menu_atteindre_alimentation.setEnabled(true);
        menu_add_alimentation.setEnabled(true);
        menu_edit_alimentation.setEnabled(true);
        menu_delete_alimentation.setEnabled(true);
        tbl_alimentation.setForeground(Color.black);
        rowAlimentation = -1;
    }//GEN-LAST:event_btn_annuler_alimentationActionPerformed

    private void menu_actualiser_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_actualiser_alimentationActionPerformed
        // TODO add your handling code here:
        btn_refrech_alimentationActionPerformed(evt);
    }//GEN-LAST:event_menu_actualiser_alimentationActionPerformed

    private void menu_atteindre_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_atteindre_alimentationActionPerformed
        // TODO add your handling code here:
        btn_atteindre_alimentationActionPerformed(evt);
    }//GEN-LAST:event_menu_atteindre_alimentationActionPerformed

    private void menu_add_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_add_alimentationActionPerformed
        // TODO add your handling code here:
        btn_add_alimentationActionPerformed(evt);
    }//GEN-LAST:event_menu_add_alimentationActionPerformed

    private void menu_edit_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_edit_alimentationActionPerformed
        // TODO add your handling code here:
        btn_edit_alimentationActionPerformed(evt);
    }//GEN-LAST:event_menu_edit_alimentationActionPerformed

    private void menu_delete_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_delete_alimentationActionPerformed
        // TODO add your handling code here:
        btn_delete_alimentationActionPerformed(evt);
    }//GEN-LAST:event_menu_delete_alimentationActionPerformed

    private void menu_annuler_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_annuler_alimentationActionPerformed
        // TODO add your handling code here:
        btn_annuler_alimentationActionPerformed(evt);
    }//GEN-LAST:event_menu_annuler_alimentationActionPerformed

    private void btn_atteindre_papierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atteindre_papierActionPerformed
        // TODO add your handling code here:
        String rep = JOptionPane.showInputDialog("Saisir le code papier : ");
        if (rep != null) {
            Boolean existe = false;
            int code = Integer.parseInt(rep);
            for (int i = 0; i < dataPapier.size() && !existe; i++) {
                if (dataPapier.get(i).getId_papier() == code) {
                    tbl_papier.changeSelection(i, 0, false, false);
                    existe = true;
                }
            }
            if (!existe) {
                JOptionPane.showMessageDialog(null, "Ce code papier n'existe pas !");
            }

        }
    }//GEN-LAST:event_btn_atteindre_papierActionPerformed

    private void btn_refrech_papierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refrech_papierActionPerformed
        // TODO add your handling code here:
        btn_annuler_papierActionPerformed(evt);
        chargerPapier();
    }//GEN-LAST:event_btn_refrech_papierActionPerformed

    private void btn_editable_alimentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editable_alimentationActionPerformed
        // TODO add your handling code here:
        if (rowAlimentation > -1) {
            tbl_alimentation.changeSelection(rowAlimentation, 0, false, false);
        }
    }//GEN-LAST:event_btn_editable_alimentationActionPerformed

    private void btn_add_papierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_papierActionPerformed
        // TODO add your handling code here:
        try {

            if ("Ajouter".equals(btn_add_papier.getToolTipText())) {

                ((DefaultTableModel) tbl_papier.getModel()).addRow(new Object[]{0, "", 0, Couleurs.Blanc});
                rowPapier = tbl_papier.getRowCount() - 1;
                tbl_papier.changeSelection(rowPapier, 0, false, false);
                tbl_papier.setForeground(Color.red);
                btn_edit_papier.setEnabled(false);
                btn_delete_papier.setEnabled(false);
                btn_atteindre_papier.setEnabled(false);

                btn_add_papier.setToolTipText("Enregistrer");
                btn_add_papier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/save_16px.png")));
                // tbl_papier.editCellAt(rowPapier, 0);

                menu_edit_papier.setEnabled(false);
                menu_delete_papier.setEnabled(false);
                menu_atteindre_papier.setEnabled(false);
                menu_add_papier.setText("Enregistrer");

            } else {

                Papier pap = new Papier();
                Boolean oki =  pap.setNom_papier((String) tbl_papier.getValueAt(rowPapier, 1));
                // pap.setId_papier(result.getInt("id_papier"));
               
               oki = oki && pap.setGrammage((int) tbl_papier.getValueAt(rowPapier, 2));
                pap.setCouleur(String.valueOf((Couleurs) tbl_papier.getValueAt(rowPapier, 3)));
                oki = oki && pap.setHauteur((Float) tbl_papier.getValueAt(rowPapier, 4));
                oki = oki && pap.setLargeur((Float) tbl_papier.getValueAt(rowPapier, 5));
                oki = oki && pap.setQte(Double.valueOf(String.valueOf(tbl_papier.getValueAt(rowPapier, 6))));
                //JOptionPane.showMessageDialog(null, pap.getNom_papier() + " | " + pap.getQte());
                // JOptionPane.showMessageDialog(null, tbl_papier.getValueAt(rowPapier, 0).getClass() + " | " + tbl_papier.getValueAt(rowPapier, 1).getClass());
                if (!oki) {
                    JOptionPane.showMessageDialog(null, "Certains données ne sont pas correctes !!");
                } else {
                    if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment ajouter ce papier ?", "Confirmation d'ajout de papier !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        String query = "INSERT INTO agipub.papier (nom_papier, grammage, couler, hauteur, largeur, qte_stock) "
                                + " VALUES ('" + pap.getNom_papier() + "', " + pap.getGrammage() + ", '" + pap.getCouleur() + "', " + pap.getHauteur() + ", " + pap.getLargeur() + "," + pap.getQte() + ")";
                        state = Connexion.getInstance().createStatement();
                        state.executeUpdate(query);
                        state.close();
                        dataPapier.add(pap);
                        ((DefaultTableModel) tbl_papier.getModel()).fireTableRowsInserted(rowPapier, rowPapier);
                        tbl_papier.setForeground(Color.black);
                        rowPapier = -1;
                        JOptionPane.showMessageDialog(null, "Papier ajouté avec succès");

                        btn_edit_papier.setEnabled(true);
                        btn_delete_papier.setEnabled(true);
                        btn_atteindre_papier.setEnabled(true);
                        menu_edit_papier.setEnabled(true);
                        menu_delete_papier.setEnabled(true);
                        menu_atteindre_papier.setEnabled(true);
                        btn_add_papier.setToolTipText("Ajouter");
                        btn_add_papier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/plus_16px.png")));
                        menu_add_papier.setText("Ajouter");
                        chargerPapier();

                    }

                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_add_papierActionPerformed

    private void btn_edit_papierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_edit_papierActionPerformed
        // TODO add your handling code here:
        try {

            if ("Modifier".equals(btn_edit_papier.getToolTipText())) {

                rowPapier = tbl_papier.getSelectedRow();
                if (rowPapier < 0) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner le papier à modifier !");
                } else {
                    tbl_papier.setForeground(Color.red);
                    tbl_papier.editCellAt(rowPapier, 0);

                    btn_add_papier.setEnabled(false);
                    btn_delete_papier.setEnabled(false);
                    btn_atteindre_papier.setEnabled(false);
                    btn_edit_papier.setToolTipText("Enregistrer");
                    btn_edit_papier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/save_16px.png")));
                    menu_edit_papier.setText("Enregistrer");

                    menu_add_papier.setEnabled(false);
                    menu_delete_papier.setEnabled(false);
                    menu_atteindre_papier.setEnabled(false);
                }
            } else {

                Papier pap = new Papier();

               // JOptionPane.showMessageDialog(null, tbl_papier.getValueAt(rowPapier, 0).getClass() + " | " + tbl_papier.getValueAt(rowPapier, 1).getClass());
                int gr = 0;
                if (tbl_papier.getValueAt(rowPapier, 2).getClass().getSimpleName().equals("Integer")) {
                    gr = (int) tbl_papier.getValueAt(rowPapier, 2);
                } else {
                    gr = Long.valueOf((long) tbl_papier.getValueAt(rowPapier, 2)).intValue();
                }
                String c = "";
                if (tbl_papier.getValueAt(rowPapier, 3).getClass().getSimpleName().equals("String")) {
                    c = (String) tbl_papier.getValueAt(rowPapier, 3);
                } else {
                    c = String.valueOf((Couleurs) tbl_papier.getValueAt(rowPapier, 3));
                }
                Boolean oki = pap.setNom_papier((String) tbl_papier.getValueAt(rowPapier, 1));
                
                oki = oki && pap.setGrammage(gr);
                pap.setCouleur(c);
                oki = oki && pap.setHauteur((Float) tbl_papier.getValueAt(rowPapier, 4));
                oki = oki && pap.setLargeur((Float) tbl_papier.getValueAt(rowPapier, 5));
                oki = oki && pap.setQte(Double.valueOf(String.valueOf(tbl_papier.getValueAt(rowPapier, 6))));

                if (!oki) {
                    JOptionPane.showMessageDialog(null, "Certains données ne sont pas correctes !!");
                } else {
                    if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment modifier ce papier ?", "Confirmation de modification de papier !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        String query = "UPDATE  papier SET nom_papier='" + pap.getNom_papier() + "', grammage=" + pap.getGrammage() + ", couler='" + pap.getCouleur() + "' , hauteur=" + pap.getHauteur() + ", largeur=" + pap.getLargeur() + " ,qte_stock=" + pap.getQte() + " WHERE id_papier=" + dataPapier.get(rowPapier).getId_papier();
                        state = Connexion.getInstance().createStatement();
                        state.executeUpdate(query);
                        state.close();
                        ((DefaultTableModel) tbl_papier.getModel()).fireTableRowsUpdated(rowPapier, rowPapier);
                        tbl_papier.setForeground(Color.black);
                        rowPapier = -1;
                        JOptionPane.showMessageDialog(null, "Papier modifié avec succès");

                        btn_add_papier.setEnabled(true);
                        btn_delete_papier.setEnabled(true);
                        btn_atteindre_papier.setEnabled(true);
                        btn_edit_papier.setToolTipText("Modifier");
                        btn_edit_papier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/graphic_design_16px.png")));
                        menu_edit_papier.setText("Modifier");
                        menu_add_papier.setEnabled(true);
                        menu_delete_papier.setEnabled(true);
                        menu_atteindre_papier.setEnabled(true);
                        chargerPapier();
                    }

                }
            }

        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_edit_papierActionPerformed

    private void btn_delete_papierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_papierActionPerformed
        // TODO add your handling code here:
        rowPapier = tbl_papier.getSelectedRow();
        if (rowPapier < 0) {
            JOptionPane.showMessageDialog(null, "Vous devez séléctionner un papier à supprimer !");
        } else {
            if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce papier ?", "Confirmation de suppression de papier !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                try {
                    state = Connexion.getInstance().createStatement();
                    String query = "DELETE FROM papier WHERE id_papier=" + dataPapier.get(rowPapier).getId_papier();
                    state.executeUpdate(query);
                    state.close();
                    dataPapier.remove(rowPapier);
                    ((DefaultTableModel) tbl_papier.getModel()).removeRow(rowPapier);
                    ((DefaultTableModel) tbl_papier.getModel()).fireTableRowsDeleted(rowPapier, rowPapier);
                    JOptionPane.showMessageDialog(null, "Suppression efféctuée !");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur en Suppression :\n" + ex.getMessage());
                    Logger.getLogger(pan_papier.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }//GEN-LAST:event_btn_delete_papierActionPerformed

    private void btn_annuler_papierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_annuler_papierActionPerformed
        // TODO add your handling code here:
        if ("Enregistrer".equals(btn_add_papier.getToolTipText())) {
            ((DefaultTableModel) tbl_papier.getModel()).removeRow(rowPapier);
        }
        if ("Enregistrer".equals(btn_edit_papier.getToolTipText())) {
            tbl_papier.setValueAt(dataPapier.get(rowPapier).getId_papier(), rowPapier, 0);
            tbl_papier.setValueAt(dataPapier.get(rowPapier).getNom_papier(), rowPapier, 1);
            tbl_papier.setValueAt(dataPapier.get(rowPapier).getGrammage(), rowPapier, 2);
            tbl_papier.setValueAt(dataPapier.get(rowPapier).getCouleur(), rowPapier, 3);
            tbl_papier.setValueAt(dataPapier.get(rowPapier).getHauteur(), rowPapier, 4);
            tbl_papier.setValueAt(dataPapier.get(rowPapier).getLargeur(), rowPapier, 5);
            tbl_papier.setValueAt(dataPapier.get(rowPapier).getQte(), rowPapier, 6);
        }

        tbl_papier.clearSelection();
        btn_edit_papier.setEnabled(true);
        btn_delete_papier.setEnabled(true);
        btn_atteindre_papier.setEnabled(true);
        btn_add_papier.setToolTipText("Ajouter");
        btn_add_papier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/plus_16px.png")));

        btn_add_papier.setEnabled(true);
        btn_edit_papier.setToolTipText("Modifier");
        btn_edit_papier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img_app_agipub/graphic_design_16px.png")));

        menu_add_papier.setText("Ajouter");
        menu_edit_papier.setText("Modifier");
        menu_add_papier.setEnabled(true);
        menu_edit_papier.setEnabled(true);
        menu_delete_papier.setEnabled(true);
        menu_atteindre_papier.setEnabled(true);
        tbl_papier.setForeground(Color.black);
        rowPapier = -1;
    }//GEN-LAST:event_btn_annuler_papierActionPerformed

    private void btn_editable_alimentation1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editable_alimentation1ActionPerformed
        // TODO add your handling code here:
        if (rowPapier > -1) {

            tbl_papier.changeSelection(rowPapier, 0, false, false);
        }
    }//GEN-LAST:event_btn_editable_alimentation1ActionPerformed

    private void btn_papier_concerneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_papier_concerneActionPerformed
        // TODO add your handling code here:
        int row = tbl_alimentation.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Aucune alimentation séléctionnée !");
        } else {

            for (int i = 0; i < dataPapier.size(); i++) {
                if (dataPapier.get(i).getId_papier() == dataAlimentation.get(row).getId_papier()) {
                    tbl_papier.changeSelection(i, 0, false, false);
                    break;
                }
            }

        }
    }//GEN-LAST:event_btn_papier_concerneActionPerformed

    private void btn_affActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_affActionPerformed
        // TODO add your handling code here:
        int row = tbl_papier.getSelectedRow();
        if (row > -1) {
            papierCode = dataPapier.get(row).getId_papier();
            chargerAlimentation();
            papierCode = -1;
        }
    }//GEN-LAST:event_btn_affActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_alimentation;
    private javax.swing.JButton btn_add_papier;
    private javax.swing.JButton btn_aff;
    private javax.swing.JButton btn_annuler_alimentation;
    private javax.swing.JButton btn_annuler_papier;
    private javax.swing.JButton btn_atteindre_alimentation;
    private javax.swing.JButton btn_atteindre_papier;
    private javax.swing.JButton btn_delete_alimentation;
    private javax.swing.JButton btn_delete_papier;
    private javax.swing.JButton btn_edit_alimentation;
    private javax.swing.JButton btn_edit_papier;
    private javax.swing.JButton btn_editable_alimentation;
    private javax.swing.JButton btn_editable_alimentation1;
    private javax.swing.JButton btn_papier_concerne;
    private javax.swing.JButton btn_refrech_alimentation;
    private javax.swing.JButton btn_refrech_papier;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JMenuItem menu_actualiser_alimentation;
    private javax.swing.JMenuItem menu_actualiser_papier;
    private javax.swing.JMenuItem menu_add_alimentation;
    private javax.swing.JMenuItem menu_add_papier;
    private javax.swing.JMenuItem menu_annuler_alimentation;
    private javax.swing.JMenuItem menu_annuler_papier;
    private javax.swing.JMenuItem menu_atteindre_alimentation;
    private javax.swing.JMenuItem menu_atteindre_papier;
    private javax.swing.JMenuItem menu_delete_alimentation;
    private javax.swing.JMenuItem menu_delete_papier;
    private javax.swing.JMenuItem menu_edit_alimentation;
    private javax.swing.JMenuItem menu_edit_papier;
    private javax.swing.JScrollPane pnl_alimentation;
    private javax.swing.JPanel pnl_alimentation_papier;
    private javax.swing.JPanel pnl_papier;
    private javax.swing.JPopupMenu pop_alimentation;
    private javax.swing.JPopupMenu pop_papier;
    private javax.swing.JSplitPane splitPane_papier;
    private javax.swing.JTable tbl_alimentation;
    private javax.swing.JTable tbl_papier;
    // End of variables declaration//GEN-END:variables

}
