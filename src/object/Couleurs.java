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
public enum Couleurs {

    Blanc,
    Bleu,
    Jaune,
    Vert;

    static Couleurs getCouleur(String c) {
        switch (c) {
            case "Blanc":
                return Couleurs.Blanc;
            case "Bleu":
                return Couleurs.Bleu;
            case "Jaune":
                return Couleurs.Jaune;
            case "Vert":
                return Couleurs.Vert;
            default:
                return Couleurs.Blanc;
        }
    }

}
