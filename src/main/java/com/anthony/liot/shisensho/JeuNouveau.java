package com.anthony.liot.shisensho;
/*
 * JeuNouveau.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */

import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;


class JeuNouveau extends Action {

    public JeuNouveau(ShisenSho shisensho){       
        super("Nouveau",new ImageIcon(shisensho.getClass().getResource("/images/nouveau.png")),"Arrete la partie pr�c�dente et debute une nouvelle",78, KeyStroke.getKeyStroke(78, 2),shisensho);
    }

    //Lors d'une action sur le bouton, on lance une nouvelle partie
    public void actionPerformed(ActionEvent e) {
        //On demarre une partie
        sujet.control.demarrerPartie();    
    }   
}