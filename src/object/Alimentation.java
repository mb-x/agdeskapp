/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package object;

import java.util.Date;

/**
 *
 * @author win_bmx
 */
public class Alimentation {

    protected int id_alimentation;
    protected int id_papier;
    protected Date dateAchat;
    protected Double prixAchat;
    protected int nbreRamette;
    protected int nbreFueille;


    /**
     * @return the dateAchat
     */
    public Date getDateAchat() {
        return dateAchat;
    }

    /**
     * @param dateAchat the dateAchat to set
     */
    public void setDateAchat(Date dateAchat) {
        this.dateAchat = dateAchat;
    }

    /**
     * @return the prixAchat
     */
    public Double getPrixAchat() {
        return prixAchat;
    }

    /**
     * @param prixAchat the prixAchat to set
     * @return 
     */
    public Boolean setPrixAchat(Double prixAchat) {
        if(prixAchat <= 0){
            return false;
        }
        this.prixAchat = prixAchat;
        return true;
    }



    /**
     * @return the nbreRamette
     */
    public int getNbreRamette() {
        return nbreRamette;
    }

    /**
     * @param nbreRamette the nbreRamette to set
     * @return 
     */
    public Boolean setNbreRamette(int nbreRamette) {
        if(nbreRamette < 1){
            return false;
        }
        this.nbreRamette = nbreRamette;
        return true;
    }

    /**
     * @return the id_alimentation
     */
    public int getId_alimentation() {
        return id_alimentation;
    }

    /**
     * @param id_alimentation the id_alimentation to set
     */
    public void setId_alimentation(int id_alimentation) {
        this.id_alimentation = id_alimentation;
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
     * @return the nbreFueille
     */
    public int getNbreFueille() {
        return nbreFueille;
    }

    /**
     * @param nbreFueille the nbreFueille to set
     * @return 
     */
    public Boolean setNbreFueille(int nbreFueille) {
        if(nbreFueille <= 0){
            return false;
        }
        this.nbreFueille = nbreFueille;
        return true;
    }
}
