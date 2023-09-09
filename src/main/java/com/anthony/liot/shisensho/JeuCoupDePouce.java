package com.anthony.liot.shisensho;
/*
 * JeuCoupDePouce.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */

import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

//JeuCoupDePouce herite de la classe Action

public class JeuCoupDePouce extends Action{
    
    //permet de savoir si on a demander un coup de pouce ou pas
    protected boolean aideActive;

    public JeuCoupDePouce(ShisenSho shisensho) {
        super("Conseil",new ImageIcon(shisensho.getClass().getResource("/images/conseil.png")),"Te donne une solution, mais te retire une vie",67, KeyStroke.getKeyStroke(67, 2),shisensho);
    }

    //Lors d'une action sur le bouton, on affiche une solution
    public void actionPerformed(ActionEvent e) {
        //On selectionne le couple de solution
        for (int piece : sujet.control.solution){
            sujet.plateau.selectionPiece(piece);            
        } 
        // le coup de pouce a �t� utilis�
        aideActive = true;
        // si il n'y a plus de vie, la partie s'arr�te.
        if (sujet.control.nombreVie==0){
            sujet.control.arreterLaPartie(1);
        }
        // le nombre de vie diminue
        sujet.control.nombreVie--;
        // on affiche le nouveau nombre de vie
        sujet.control.vie.setText(String.valueOf(sujet.control.nombreVie)); 
        
       

    }
  
}
