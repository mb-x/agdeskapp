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
public class Papier {
    protected int id_papier;
    protected String nom_papier;
    protected int grammage;
    protected Couleurs couleur;
    protected float hauteur;
    protected float largeur;
    protected Double qte;

    @Override
    public String toString(){
        return nom_papier + " " + couleur + " " + grammage + " g";
        
    }
    /**
     * @return the nom_papier
     */
    public String getNom_papier() {
        return nom_papier;
    }

    /**
     * @param nom_papier the nom_papier to set
     * @return 
     */
    public Boolean setNom_papier(String nom_papier) {
        if(!nom_papier.matches("^[a-zA-z-]+$")){
            return false;
        }
        this.nom_papier = nom_papier.substring(0, 1).toUpperCase() + ""+nom_papier.substring(1).toLowerCase();
        return true;
    }

    /**
     * @return the qte
     */
    public Double getQte() {
        return qte;
    }

    /**
     * @param qte the qte to set
     * @return 
     */
    public Boolean setQte(Double qte) {
        if(qte < 0){
            return false;
        }
        this.qte = qte;
        return true;
    }

    /**
     * @return the id_papier
     */
    public int getId_papier() {
        return id_papier;
    }

    /**
     * @param id_papier the id_papier to set
     */
    public void setId_papier(int id_papier) {
        this.id_papier = id_papier;
    }

    /**
     * @return the grammage
     */
    public int getGrammage() {
        return grammage;
    }

    /**
     * @param grammage the grammage to set
     */
    public Boolean setGrammage(int grammage) {
        if(grammage <= 0){
            return false;
        }
        this.grammage = grammage;
        return true;
    }

    /**
     * @return the couleur
     */
    public Couleurs getCouleur() {
        return couleur;
    }

    /**
     * @param couleur the couleur to set
     */
    public void setCouleur(String couleur) {
        Couleurs c = Couleurs.getCouleur(couleur);
        this.couleur = c;
    }

    /**
     * @return the hauteur
     */
    public float getHauteur() {
        return hauteur;
    }

    /**
     * @param hauteur the hauteur to set
     */
    public Boolean setHauteur(float hauteur) {
         if(hauteur <= 0){
            return false;
        }
        this.hauteur = hauteur;
        return true;
    }

    /**
     * @return the largeur
     */
    public float getLargeur() {
        return largeur;
    }

    /**
     * @param largeur the largeur to set
     */
    public Boolean setLargeur(float largeur) {
         if(largeur <= 0){
            return false;
        }
        this.largeur = largeur;
        return true;
    }
}
