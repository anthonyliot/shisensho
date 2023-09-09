package com.anthony.liot.shisensho;
/*
 * JeuMeilleursScores.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */

import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class JeuMeilleursScores extends Action{
    
    public JeuMeilleursScores(ShisenSho shisensho) {
        super("Meilleurs Scores",new ImageIcon(shisensho.getClass().getResource("/images/meilleurs-scores.png")),"les dix meilleurs joueurs",77, KeyStroke.getKeyStroke(77, 2),shisensho);
    }
    
    //Lors d'une action sur le bouton, on affiche le top ten
    @SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {
        // affiche les scores
        sujet.hof.afficherScore(sujet);
        
    }
}
