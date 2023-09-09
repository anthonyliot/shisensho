package com.anthony.liot.shisensho;
/*
 * Doc.java
 *
 * Auteurs : Lamoury jean fr�d�ric - Liot Anthony
 *
 */

import javax.swing.ImageIcon;
import javax.swing.JFrame;

//Creation d'un nouveau JFrame

class Doc extends JFrame {

    public Doc(String s, ShisenSho shisensho){        
        //Le Jframe aura un titre
        setTitle(s);
        //Une image  
        setIconImage((new ImageIcon(shisensho.getClass().getResource("/images/regles.png"))).getImage());
        //Et a l'interieur du JFrame on va inserer une page HTML au centre
        getContentPane().add(new AfficheurHTML(shisensho), "Center");
    }
}
