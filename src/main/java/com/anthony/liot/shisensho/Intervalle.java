package com.anthony.liot.shisensho;
/*
 * Intervalle.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */


public class Intervalle {
    
    // Valeurs max et min de l'intervalle. On ne veut pas que les valeurs soit 
    // modifi�s une fois que les valeurs sont d�termin�es.
    protected int MIN, MAX ;
    
    public Intervalle (){}
    
    public Intervalle(int min, int max) {
        // un intervalle est un espace entre deux coordonn�es.
        MIN = min;
        MAX = max;
    }
    public Intervalle intervalleHorizontal(boolean [][] grille,int lin, int col){
        // Au depart l'intervalle est de longueur 0, le min est le max sont egaux �
        // la colonne de la piece.
        int min,max;
        min=max=col;
        // On explore d'un cot�....
        for ( int j=col ; j >0 && grille[lin][j-1]; j--){
                min-=1 ;
        }
        // on explore de l'autre cot�
        for ( int j=col; j< grille[0].length-1 && grille[lin][j+1]; j++ ){
                max+=1;
        }
        // le min et le max de l'intervalle sont determin�s, on renvoie � 
        // l'appelant un nouvel intervalle...
        return new Intervalle(min,max);
    }
    public Intervalle intervalleVertical( boolean [][] grille, int lin, int col){
        // copie conforme que precedemment mais pour un intervale vertical 
        int min,max;
        min=max=lin;
        for ( int i=lin ; i >0 && grille[i-1][col]; i--){
                min-=1 ;
        }
        for ( int i=lin; i< grille.length-1 && grille[i+1][col]; i++ ){
                max+=1;
        }
        return new Intervalle(min,max);
        
    }
   
}
