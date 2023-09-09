package com.anthony.liot.shisensho;

/*
 * GraviteGauche.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */




class GraviteGauche extends Gravite{
    
    public void Actualiser (int posPlacement,int posPlacement2,ShisenSho shisensho){
        // cette gravite va de la droite vers la gauche
        
        // on decalera dabord a partir du plus a droite
        ActualiserBis(Math.max(shisensho.plateau.colonne(posPlacement),shisensho.plateau.colonne(posPlacement2)),shisensho);
        // ensuite le plus a gauche
        ActualiserBis(Math.min(shisensho.plateau.colonne(posPlacement),shisensho.plateau.colonne(posPlacement2)),shisensho);
    }
        
    public void ActualiserBis (int posPlacement,ShisenSho shisensho){
        
        int largeur = shisensho.plateau.LARGEUR;
        int colonne = shisensho.plateau.colonne(posPlacement);
        int posDeplacement = posPlacement;        
        
        for (int i = colonne; i<=largeur; i+=1){            
        
            int familleAuDessus = shisensho.plateau.positionPiece[posDeplacement];    
            posDeplacement+=1;
            // la position du dessus n'est pas vide
            // on decale et on change la position de placement
            if (familleAuDessus != -1){                
                //on place a la position de depart la piece du dessus
                shisensho.plateau.placerPiece(posPlacement,familleAuDessus);
                // mise � jour de la partie graphique
                String nom = "/images/"+familleAuDessus+".png";
                shisensho.plateau.dessinerPiece(posPlacement,familleAuDessus,nom);
                //on change la position de placement au dessus
                posPlacement+=1;
                //on supprime la piece du dessus
                shisensho.plateau.supprimerPiece(posPlacement);
            }
        }                     
    }    
}
