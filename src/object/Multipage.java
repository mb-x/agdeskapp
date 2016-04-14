/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package object;

/**
 *
 * @author win_bmx
 */
public class Multipage {
    
    protected int id_multip;
    protected Papier papier;
    protected int nbre_page;
    protected int nbre_couleur;
    protected double epaisseur_bande;
    private double nbre_feuille_consom;

    @Override
    public String toString(){
        return nbre_page+" pages," + nbre_couleur + " couleur(s) sur papier "+ papier.toString() + "";
    }
    /**
     * @return the id_multip
     */
    public int getId_multip() {
        return id_multip;
    }

    /**
     * @param id_multip the id_multip to set
     */
    public void setId_multip(int id_multip) {
        this.id_multip = id_multip;
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
     * @return the nbre_page
     */
    public int getNbre_page() {
        return nbre_page;
    }

    /**
     * @param nbre_page the nbre_page to set
     */
    public Boolean setNbre_page(int nbre_page) {
        if(nbre_page <=0){
            return false;
        }
        this.nbre_page = nbre_page;
        return true;
    }

    /**
     * @return the nbre_couleur
     */
    public int getNbre_couleur() {
        return nbre_couleur;
    }

    /**
     * @param nbre_couleur the nbre_couleur to set
     */
    public Boolean setNbre_couleur(int nbre_couleur) {
        if(nbre_couleur< 1 || nbre_couleur > 4){
            return false;
        }
        this.nbre_couleur = nbre_couleur;
        return true;
    }
    
    /**
     * @return the epaisseur_bande
     */
    public double getEpaisseur_bande() {
        return epaisseur_bande;
    }

    /**
     * @param epaisseur_bande the epaisseur_bande to set
     */
    public Boolean setEpaisseur_bande(double epaisseur_bande) {
        if(epaisseur_bande<0){
            return false;
        }
        this.epaisseur_bande = epaisseur_bande;
        return true;
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
