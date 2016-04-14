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
public class Ramette {
    protected int nbreFeuille;
    protected int grammage;
    protected Couleurs color;
    protected Float hauteur;
    protected Float largeur;
    protected int id_ramette;

    /**
     * @return the nbreFeuille
     */
    public int getNbreFeuille() {
        return nbreFeuille;
    }

    /**
     * @param nbreFeuille the nbreFeuille to set
     * @return 
     */
    public Boolean setNbreFeuille(int nbreFeuille) {
        if(nbreFeuille <= 0){
            return false;
        }
        this.nbreFeuille = nbreFeuille;
        return true;
    }

    /**
     * @return the grammage
     */
    public int getGrammage() {
        return grammage;
    }

    /**
     * @param grammage the grammage to set
     * @return 
     */
    public Boolean setGrammage(int grammage) {
        if(grammage <=0){
            return false;
        }
        this.grammage = grammage;
        return true;
    }

    /**
     * @return the color
     */
    public Couleurs getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
       Couleurs c = Couleurs.getCouleur(color);
        //JOptionPane.showMessageDialog(null, c);
        this.color =c;
        
    }

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
        if(hauteur <= 0){
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
     */
    public Boolean setLargeur(Float largeur) {
          if(largeur <= 0){
            return false;
        }
        this.largeur = largeur;
        return true;
    }

    /**
     * @return the id_ramette
     */
    public int getId_ramette() {
        return id_ramette;
    }

    /**
     * @param id_ramette the id_ramette to set
     */
    public void setId_ramette(int id_ramette) {
        this.id_ramette = id_ramette;
    }
}
