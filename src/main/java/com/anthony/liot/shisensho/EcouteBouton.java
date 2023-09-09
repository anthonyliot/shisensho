package com.anthony.liot.shisensho;
/*
 * EcoutBouton.java
 *
 * Auteurs : Lamoury jean fr�d�ric - Liot Anthony
 *
 */


import java.awt.event.* ;

//EcouteBouton est implementer de actionListener
//elle sert a ecouter les position des boutons lors d'un clic
public class EcouteBouton implements ActionListener{
    
    protected ControlJeu controleur ;
    protected int pos ;
   
    public EcouteBouton (int n , ControlJeu control){
        controleur = control;
        pos = n ;
    }
    
    //Si on clic, on renvoit la position du bouton a la methode boutonConnecte
    public void actionPerformed (ActionEvent ev){
        this.controleur.boutonConnecte(pos);
    }
}
        