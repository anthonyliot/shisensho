package com.anthony.liot.shisensho;

/*
 * GraviteHaut.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */



class GraviteHaut extends Gravite{
    
    public void Actualiser (int posPlacement,int posPlacement2,ShisenSho shisensho){
        // cette gravite va du bas vers le haut
        
        // on decalera dabord le plus bas
        ActualiserBis(Math.max(posPlacement,posPlacement2),shisensho);
        // ensuite le plus haut
        ActualiserBis(Math.min(posPlacement,posPlacement2),shisensho);
    }
        
    public void ActualiserBis (int posPlacement,ShisenSho shisensho){
        
        int largeur = shisensho.plateau.LARGEUR;
        int posDeplacement = posPlacement;        
        
        for (int i = posDeplacement; i<=143; i+=largeur){
            
            int familleAuDessus = shisensho.plateau.positionPiece[i];    

            // la position du dessus n'est pas vide
            // on decale et on change la position de placement
            if (familleAuDessus != -1){                
                //on place a la position de depart la piece du dessus
                shisensho.plateau.placerPiece(posPlacement,familleAuDessus);
                // mise � jour de la partie graphique
                String nom = "/images/"+familleAuDessus+".png";
                shisensho.plateau.dessinerPiece(posPlacement,familleAuDessus,nom);
                //on change la position de placement au dessus
                posPlacement+=largeur;
                //on supprime la piece du dessus
                shisensho.plateau.supprimerPiece(posPlacement);
            }
        }                     
    }    
}
