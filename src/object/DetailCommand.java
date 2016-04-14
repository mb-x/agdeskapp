/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package object;

import javax.swing.JOptionPane;

/**
 *
 * @author win_bmx
 */
public class DetailCommand {
    protected int idCmd;
    protected String document;
    protected Papier papier = new Papier();
    protected Float hauteur;
    protected Float largeur;
    protected int nbreCouleur;
    protected int qte;
    protected Double prixConception;
    protected Double prixMatiere;
    private double prixPapier;
    protected double prixImpression;
    protected Boolean isMultiple;
    protected Multipage multip = new Multipage();
    private String designation;
    private double prix_unitaire;
private double nbre_feuille_consom;
    public DetailCommand(){
        
    }
    public DetailCommand(DetailCommand c){
        setDocument(c.getDocument());
        setIdCmd(c.getIdCmd());
        setPapier(c.getPapier());
        setHauteur(c.getHauteur());
        setLargeur(c.getLargeur());
        setNbreCouleur(c.getNbreCouleur());
        setQte(c.getQte());
        setPrixConception(c.getPrixConception());
        setPrixMatiere(c.getPrixMatiere());
        setPrixPapier(c.getPrixPapier());
        setPrixImpression(c.getPrixImpression());
        setIsMultiple(c.getIsMultiple());
        setMultip(c.getMultip());
        setDesignation(c.getDesignation());
        setPrix_unitaire(c.getPrix_unitaire());
        setNbre_feuille_consom(c.getNbre_feuille_consom());
    }
    
      @Override
    public String toString(){
        return   document + " " + hauteur +"/"+largeur +", " + nbreCouleur + " couleur(s) sur papier " + papier.toString() + (isMultiple ? " de " + multip.toString():"" );
        
    }
    
    /**
     * @return the idCmd
     */
    public int getIdCmd() {
        return idCmd;
    }

    /**
     * @param idCmd the idCmd to set
     */
    public void setIdCmd(int idCmd) {
        this.idCmd = idCmd;
    }

    /**
     * @return the document
     */
    public String getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * @return the id_papier
     */
  
    /**
     * @return the hauteur
     */
    public Float getHauteur() {
        return hauteur;
    }

    /**
     * @param hauteur the hauteur to set
     * @return 
     */
    public Boolean setHauteur(Float hauteur) {
        
        if(hauteur < 1){
           
            return false;
        }
        
        this.hauteur = hauteur;
        return true;
    }

    /**
     * @return the largeur
     */
    public Float getLargeur() {
        return largeur;
    }

    /**
     * @param largeur the largeur to set
     * @return 
     */
    public Boolean setLargeur(Float largeur) {
        if(largeur < 1){
            return false;
        }
        this.largeur = largeur;
        return true;
    }

    /**
     * @return the nbreCouleur
     */
    public int getNbreCouleur() {
        return nbreCouleur;
    }

    /**
     * @param nbreCouleur the nbreCouleur to set
     * @return 
     */
    public Boolean setNbreCouleur(int nbreCouleur) {
        if(nbreCouleur < 1 || nbreCouleur > 4){
            return false;
        }
        this.nbreCouleur = nbreCouleur;
        return true;
    }

    /**
     * @return the qte
     */
    public int getQte() {
        return qte;
    }

    /**
     * @param qte the qte to set
     */
    public Boolean setQte(int qte) {
        if(qte < 1){
            return false;
        }
        this.qte = qte;
        return true;
    }

    /**
     * @return the prixConception
     */
    public Double getPrixConception() {
        return prixConception;
    }

    /**
     * @param prixConception the prixConception to set
     * @return 
     */
    public Boolean setPrixConception(Double prixConception) {
        if(prixConception< 0){
            return false;
        }
        this.prixConception = prixConception;
        return true;
    }

    /**
     * @return the prixMatiere
     */
    public Double getPrixMatiere() {
        return prixMatiere;
    }

    /**
     * @param prixMatiere the prixMatiere to set
     * @return 
     */
    public Boolean setPrixMatiere(Double prixMatiere) {
        if(prixMatiere < 0){
            return false;
        }
        this.prixMatiere = prixMatiere;
        return true;
    }

    /**
     * @return the prixImpression
     */
    public double getPrixImpression() {
        return prixImpression;
    }

    /**
     * @param prixImpression the prixImpression to set
     * @return 
     */
    public Boolean setPrixImpression(double prixImpression) {
        if(prixImpression < 0){
            return false;
        }
        this.prixImpression = prixImpression;
        setPrix_unitaire(prixImpression/getQte());
        return true;
    }
 /**
     * @return the prixPapier
     */
    public double getPrixPapier() {
        return prixPapier;
    }

    /**
     * @param prixPapier the prixPapier to set
     */
    public void setPrixPapier(double prixPapier) {
        this.prixPapier = prixPapier;
    }
    /**
     * @return the isMultiple
     */
    public Boolean getIsMultiple() {
        return isMultiple;
    }

    /**
     * @param isMultiple the isMultiple to set
     */
    public void setIsMultiple(Boolean isMultiple) {
        this.isMultiple = isMultiple;
    }


    /**
     * @return the papier
     */
    public Papier getPapier() {
        return papier;
    }

    /**
     * @param papier the papier to set
     */
    public void setPapier(Papier papier) {
        this.papier = papier;
    }

    /**
     * @return the multip
     */
    public Multipage getMultip() {
        return multip;
    }

    /**
     * @param multip the multip to set
     */
    public void setMultip(Multipage multip) {
        this.multip = multip;
    }

    /**
     * @return the designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * @param designation the designation to set
     */
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    /**
     * @return the prix_unitaire
     */
    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    /**
     * @param prix_unitaire the prix_unitaire to set
     */
    public void setPrix_unitaire(double prix_unitaire) {
        
        this.prix_unitaire = prix_unitaire;
    }

    /**
     * @return the nbre_feuille_consom
     */
    public double getNbre_feuille_consom() {
        return nbre_feuille_consom;
    }

    /**
     * @param nbre_feuille_consom the nbre_feuille_consom to set
     */
    public void setNbre_feuille_consom(double nbre_feuille_consom) {
        this.nbre_feuille_consom = nbre_feuille_consom;
    }

   

    

}