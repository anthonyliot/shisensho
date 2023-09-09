package com.anthony.liot.shisensho;
/*
 * FamillePiece.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */

import java.util.*;

class FamillePiece {
    
    // Nbre de pi�ce dans la famille de piece
    protected final int NBPIECE; 
    // le tableau de positions de chaque pi�ce de la famille. 
    // choix du treeSet car operation standard en log(n)
    protected TreeSet<Integer> position ; 
    protected ShisenSho shisensho;
   
    
    public FamillePiece(int nbpiece,ShisenSho shi) {
       //constructeur de FamillePiece
       shisensho = shi;
       NBPIECE = nbpiece;
       position =new TreeSet<Integer>();   
    }
    public void initialiser(){
        // Reinitialisation de la famille
        position.clear();   
    }    
    public int [] lireLesPositions(){
        // Permet de mettre dans un tableau les positions de chaque piece
        // de la famille.
        // cela permettra de comparer les entiers deux � deux plus facilement.
        Object [] objet = position.toArray();
        // Cr�ation du "receptacle"
        int [] pos = new int[position.size()];
        for (int i=0; i <pos.length; i++){
            // on cast l'objet en Integer pour recuperer la valeur
            pos[i]=(Integer)objet[i];
        }
        return pos;
    }
    public void ajouterPiece( int i){
        // ajouter la position d'un pi�ce � la famille
        position.add(i);
    }
    public void supprimerPiece (int i){
        // supprimer une piece � la famille de piece
        position.remove(i);
    }
    public boolean existeChemin(FamillePiece piece,GrilleJeu grille){
        // Il existe un chemin entre les differentes pieces de la famille ?
        // on commence par recuperer un tableau des valeurs de position.
        int[] posPiece = piece.lireLesPositions();
        // pour chaque position... 
        for (int i=0; i<posPiece.length; i++)
            for( int j=i+1; j<posPiece.length; j++){
                int a = posPiece[i];
                int b = posPiece[j];
                // .. on regarde s'il s'existe un chemin avec les autres positions
                // de la famille.
                if (grille.existeUnChemin(a,b)){
                    // mise � jour de la solution du coup de pouce.
                    shisensho.control.miseAJourSolution(a,b);
                    return true;
                
                }
               
            } 
        return false;
        }
}

