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
public class Client {

    protected int id;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param aId the id to set
     */
    public void setId(int aId) {
        id = aId;
    }
    protected String ste;
    protected String nom;
    protected String prenom;
    protected String fix1;
    protected String fix2;
    protected String gsm1;
    protected String gsm2;
    protected String mail;
    protected String siteweb;
    protected String adresse;

    public Client() {
        this.ste = "";
        this.nom = "";
        this.prenom = "";
        this.fix1 = "";
        this.fix2 = "";
        this.gsm1 = "";
        this.gsm2 = "";
        this.mail = "";
        this.siteweb = "";
        this.adresse = "";
    }
@Override
public String toString(){
    return "#"+id+"  :  " + nom + "   " + prenom + "   ( " + ste + " )";
}
    /**
     * @return the ste
     */
    public String getSte() {
        return ste;
    }

    /**
     * @param ste the ste to set
     */
    public void setSte(String ste) {
        if(ste.isEmpty()){
            ste = "individu";
        }
        this.ste = ste;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     * @return
     */
    public Boolean setNom(String nom) {
        if (!nom.matches("^[a-zA-Z]+$")) {
            // JOptionPane.showMessageDialog(null, "Le nom est obligatoire");
            return false;
        } else {
            nom = nom.toUpperCase();
            this.nom = nom;
            return true;
        }
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public Boolean setPrenom(String prenom) {
         if (!prenom.matches("^[a-zA-Z]+$")) {
           //  JOptionPane.showMessageDialog(null, "Le nom est obligatoire");
            return false;
        } else {
            prenom = prenom.substring(0, 1).toUpperCase() + ""+prenom.substring(1).toLowerCase();
            this.prenom = prenom;
            return true;
        }
    }

    /**
     * @return the fix1
     */
    public String getFix1() {
        return fix1;
    }

    /**
     * @param fix1 the fix1 to set
     * @return 
     */
    public Boolean setFix1(String fix1) {
        if (fix1.matches("^05(-[\\d]{2}){4}$") || "".equals(fix1)) {
            this.fix1 = fix1;
            return true;
        } else {
            // JOptionPane.showMessageDialog(null, "Numéro de Téléphone Fix 1 est incorrecte !");
            return false;
        }
    }

    /**
     * @return the fix2
     */
    public String getFix2() {
        return fix2;
    }

    /**
     * @param fix2 the fix2 to set
     * @return
     */
    public Boolean setFix2(String fix2) {
        if (fix2.matches("^05(-[\\d]{2}){4}$") || fix2.isEmpty()) {
            this.fix2 = fix2;
            return true;
        } else {
            //JOptionPane.showMessageDialog(null, "Numéro de Téléphone Fix 2 est incorrecte !");
            return false;
        }
    }

    /**
     * @return the gsm1
     */
    public String getGsm1() {
        return gsm1;
    }

    /**
     * @param gsm1 the gsm1 to set
     * @return
     */
    public Boolean setGsm1(String gsm1) {
        if (gsm1.matches("^06(-[\\d]{2}){4}$") || gsm1.isEmpty()) {
            this.gsm1 = gsm1;
            return true;
        } else {
            // JOptionPane.showMessageDialog(null, "Numéro de Téléphone GSM 1 est incorrecte !");
            return false;
        }
    }

    /**
     * @return the gsm2
     */
    public String getGsm2() {
        return gsm2;
    }

    /**
     * @param gsm2 the gsm2 to set
     * @return
     */
    public Boolean setGsm2(String gsm2) {
        if (gsm2.matches("^06(-[\\d]{2}){4}$") || gsm2.isEmpty()) {
            this.gsm2 = gsm2;
            return true;
        } else {
            return false;
            //JOptionPane.showMessageDialog(null, "Numéro de Téléphone GSM 2 est incorrecte !");
        }
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     * @return
     */
    public Boolean setMail(String mail) {
        if (mail.matches("^[a-z0-9._-]+@[a-z0-9._-]+\\.[a-z]{2,6}$") || mail.isEmpty()) {
            this.mail = mail;
            return true;
        } else {
            // JOptionPane.showMessageDialog(null, "E-mail incorrecte !");
            return false;
        }
    }

    /**
     * @return the siteweb
     */
    public String getSiteweb() {
        return siteweb;
    }

    /**
     * @param siteweb the siteweb to set
     * @return
     */
    public Boolean setSiteweb(String siteweb) {
        if (siteweb.matches("^www\\.[a-z0-9_-]+\\.[a-z]{2,6}$") || siteweb.isEmpty()) {
            this.siteweb = siteweb;
            return true;
        } else {
            // JOptionPane.showMessageDialog(null, "site web incorrecte !");
            return false;
        }
    }

    /**
     * @return the adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @param adresse the adresse to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
