package com.anthony.liot.shisensho;

/*
 * Gravite.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */


//on cree la classe abstract gravit�
abstract class Gravite {

    //cette classe contient deux methodes 
    //actualiser qui recup�re la position des deux pieces selectionner
    abstract void Actualiser(int pos,int pos2, ShisenSho shisensho);
    //actualiser recup�re la position d'une des deux pieces selectionner
    abstract void ActualiserBis(int pos , ShisenSho shisensho);
}
