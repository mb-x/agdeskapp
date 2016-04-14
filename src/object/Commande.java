/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author win_bmx
 */
public class Commande {

    protected int idCmd;
    protected int idClient;
    protected Date dateCmd;
    protected Date dateLivraison;
    protected int nbreTravaux = 0;
    protected double prixHt;
    protected double prixTt;
    protected double avance;
    protected ArrayList<DetailCommand> details = new ArrayList<>();
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
     * @return the idClient
     */
    public int getIdClient() {
        return idClient;
    }

    /**
     * @param idClient the idClient to set
     */
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    /**
     * @return the dateCmd
     */
    public Date getDateCmd() {
        return dateCmd;
    }

    /**
     * @param dateCmd the dateCmd to set
     */
    public void setDateCmd(Date dateCmd) {
        this.dateCmd = dateCmd;
    }

    /**
     * @return the dateLivraison
     */
    public Date getDateLivraison() {
        return dateLivraison;
    }

    /**
     * @param dateLivraison the dateLivraison to set
     */
    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    /**
     * @return the nbreTravaux
     */
    public int getNbreTravaux() {
        return nbreTravaux;
    }

    /**
     * @param nbreTravaux the nbreTravaux to set
     * @return 
     */
    public Boolean setNbreTravaux(int nbreTravaux) {
        if(nbreTravaux<=0){
            return false;
        }
        this.nbreTravaux = nbreTravaux;
        return true;
    }

    /**
     * @return the prixHt
     */
    public double getPrixHt() {
        return prixHt;
    }

    /**
     * @param prixHt the prixHt to set 
     */
    public void setPrixHt(double prixHt) {
       int p = Integer.valueOf(String.valueOf(Math.round(prixHt)));
        this.prixHt =p;
        
    }

    /**
     * @return the prixTt
     */
    public double getPrixTt() {
        return prixTt;
    }

    /**
     * @param prixTt the prixTt to set
     */
    public void setPrixTt(double prixTt) {
        if(prixTt<=0){
            double percent = 0.2 * this.prixHt;
           prixTt =  this.prixHt + percent;
        }
        int p = Integer.valueOf(String.valueOf(Math.round(prixTt)));
        this.prixTt = p;
    }

    /**
     * @return the avance
     */
    public double getAvance() {
        return avance;
    }

    /**
     * @param avance the avance to set
     */
    public void setAvance(double avance) {
        this.avance = avance;
    }

    /**
     * @return the details
     */
    public ArrayList<DetailCommand> getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(ArrayList<DetailCommand> details) {
        this.details = details;
    }

}
