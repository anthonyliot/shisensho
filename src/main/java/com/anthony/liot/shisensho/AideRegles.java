package com.anthony.liot.shisensho;
/*
 * AideRegles.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */


import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

        
//AideRegle herite de la classe Action

public class AideRegles extends Action{    
   
    public AideRegles(ShisenSho shisensho) {
        super("Documentation",new ImageIcon(shisensho.getClass().getResource("/images/regles.png")),"Documentation et explication du jeu",68, KeyStroke.getKeyStroke(68, 2),shisensho);
    }

    //Lors d'une action sur le bouton, on lance la visualisation des pages HTML
    public void actionPerformed(ActionEvent e) {
        // Un nouveau doc que l'on va creer        
        Doc doc = new Doc("Documentation", sujet);
        doc.pack();
        // On le rend visible
        doc.setVisible(true);

    }
}
