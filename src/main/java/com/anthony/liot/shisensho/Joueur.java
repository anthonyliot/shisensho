package com.anthony.liot.shisensho;

public class Joueur implements Comparable<Object>{

    public final String NOM;
    public final int SCORE;
        
    public Joueur(String name, int point) {
        NOM=name;
        SCORE=point;                
    }    
    
    public int compareTo(Object a) {
        Joueur j = (Joueur)a ;
        int nom = Integer.parseInt(j.NOM);
        int nom2 = Integer.parseInt(this.NOM);
        

        if (j.SCORE>this.SCORE){
            return 1;
        }
        if (j.SCORE<this.SCORE){
            return -1;
        }
        if(j.SCORE==this.SCORE){
            if (nom>nom2){
                return 1;
            }
            if (nom<nom2){
                return -1;
            }
            if (nom>nom2){
                return 1;
            }
        }
      return 0;
    }
}
