package com.anthony.liot.shisensho;
/*
 * AideAPropos.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */


import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import javax.swing.JOptionPane;

//AideAPropos herite de la classe Action

public class AideAPropos extends Action{    
    
    public AideAPropos(ShisenSho shisensho) {
        super("A-propos",new ImageIcon(shisensho.getClass().getResource("/images/a-propos.png")),"Information sur les auteurs",65, KeyStroke.getKeyStroke(65, 2),shisensho);
    }

    //Lors d'une action sur le bouton, on declenche un message
    public void actionPerformed(ActionEvent e) {
        
        //On cree un string, jeu, auteur, date, mail...
        String s = "Shisen-Sho 1.0\nUn jeu similaire au Mahjongg\n\nAnthony LIOT et Jean-Frederic Lamoury\n" +
                    "Projet 2006/2007 Licence 3 Informatique.\nUniversit� de Caen\n\n" +
                    "anthony.liot@gmail.com\njf.lamoury@laposte.fr";
        
        //Que l'on insert ensuite dans une boite de dialogue
        JOptionPane.showMessageDialog(sujet, s, "A-propos", 1, new ImageIcon("ressources/images/a-propos.png"));
    
    }
    
}
