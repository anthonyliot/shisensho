package com.anthony.liot.shisensho;
/*
 * OptionPause.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */

import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class OptionPause extends Action{
    
    protected boolean jeuEnPause = false;     

    public OptionPause(ShisenSho shisensho) {
        super("Pause",new ImageIcon(shisensho.getClass().getResource("/images/pause.png")),"mets le jeu en pause",80, KeyStroke.getKeyStroke(80, 2),shisensho);
    }

    //Lors d'une action sur le bouton, on mets le jeu en pause
    public void actionPerformed(ActionEvent e) {
        // si c'est le premier clic on cache le plateau de jeu
        // et on arrete le chrone
        if (!jeuEnPause){
            sujet.pouce.setEnabled(false);
            sujet.nouveau.setEnabled(false);
            sujet.quitter.setEnabled(false);
            cacherPiece();
            sujet.control.chrono.stop();
            jeuEnPause = true;
        
        }
        // sinon c'est que l'on est deja en pause
        // alors on reaffiche le plateau et on relance le chrono
        else{
            sujet.pouce.setEnabled(true);
            sujet.nouveau.setEnabled(true);
            sujet.quitter.setEnabled(true);
            afficherPiece();
            sujet.control.chrono.start();
            jeuEnPause = false;           
        }        
    }
    
    public void cacherPiece(){
        for (int i=0 ; i<144 ; i++){
            sujet.plateau.bouton[i].setVisible(false);
            
        }
        
    }
    
    public void afficherPiece(){
        for (int i=0 ; i<144 ; i++){
            sujet.plateau.bouton[i].setVisible(true);
            
        }        
    }
}