package com.anthony.liot.shisensho;
/*
 * ShisenSho.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */

import com.anthony.liot.shisensho.JeuCoupDePouce;
import com.anthony.liot.shisensho.OptionPause;
import com.anthony.liot.shisensho.AideAPropos;
import com.anthony.liot.shisensho.JeuQuitter;
import com.anthony.liot.shisensho.AideRegles;
import com.anthony.liot.shisensho.JeuNouveau;
import com.anthony.liot.shisensho.JeuMeilleursScores;
import com.anthony.liot.shisensho.GrilleJeu;
import com.anthony.liot.shisensho.ControlJeu;
import com.anthony.liot.shisensho.HighScore;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

// ShisenSho est un jFrame
public class ShisenSho extends JFrame {

    //on initialise une GrilleJeu, "plateau" (JPanel)
    GrilleJeu plateau;
    //on initialise un ControlJeu, "control" (JPanel)
    protected ControlJeu control;
    //on initialise un JeuCoupDePouce, "pouce" (Action)
    protected JeuCoupDePouce pouce;
    //on initialise un JeuNouveau, "nouveau" (Action)
    JeuNouveau nouveau;
    //on initialise un JeuQuitter, "quitter" (Action)
    protected JeuQuitter quitter;
    //on initialise un AideAPropos, "aideapropos" (Action)
    protected AideAPropos aideapropos;
    //on initialise un HighScore, "hof" (hall of famous)
    protected HighScore hof;
    //on initialise un OptionPause, "pause" (Action)
    protected OptionPause pause;

    /**
     *creation du menu cascade JEU avec les options
     *     - Nouveau
     *     - Meilleurs Scores
     *     - Coup De Pouce
     *     - Quitter
     **/
    public void initMenuJeu(JMenuBar menubar, JToolBar toolbar) {
        JMenu menu = new JMenu("Jeu");
        menubar.add(menu);
        menu.setMnemonic('J');

        nouveau = new JeuNouveau(this);
        menu.add(nouveau);
        toolbar.add(nouveau);
        menu.addSeparator();

        JeuMeilleursScores best = new JeuMeilleursScores(this);
        if (hof.scores.isEmpty()) {
            best.setEnabled(false);
        }
        menu.add(best);
        toolbar.add(best);

        pouce = new JeuCoupDePouce(this);
        pouce.setEnabled(false);
        menu.add(pouce);
        toolbar.add(pouce);
        menu.addSeparator();

        quitter = new JeuQuitter(this);
        menu.add(quitter);
        toolbar.add(quitter);
        toolbar.addSeparator();
    }

    /**
     *creation du menu cascade Options avec les options
     *         - Pause
     **/
    public void initMenuOption(JMenuBar menubar, JToolBar toolbar) {
        JMenu menu = new JMenu("Option");
        menubar.add(menu);
        menu.setMnemonic('O');

        pause = new OptionPause(this);
        pause.setEnabled(false);
        menu.add(pause);
        toolbar.add(pause);
        toolbar.addSeparator();


    }

    /**
     *creation du menu cascade Aide avec les options
     *     - Documentation
     *     - A propos
     **/
    public void initMenuAide(JMenuBar menubar, JToolBar toolbar) {
        JMenu menu = new JMenu("Aide");
        menubar.add(menu);
        menu.setMnemonic('A');

        AideRegles aideregles = new AideRegles(this);
        menu.add(aideregles);
        toolbar.add(aideregles);
        menu.addSeparator();

        aideapropos = new AideAPropos(this);
        menu.add(aideapropos);
        toolbar.add(aideapropos);
        toolbar.addSeparator();


    }

    // Cette methode affiche la partie superieur
    public void initToutLaPartieSuperieur() {
        JMenuBar menubar = new JMenuBar();
        JToolBar toolbar = new JToolBar();
        initMenuJeu(menubar, toolbar);
        initMenuOption(menubar, toolbar);
        initMenuAide(menubar, toolbar);
        setJMenuBar(menubar);
        getContentPane().add(toolbar, "North");

    }

    // Cette methode affiche la partie centrale
    public void initPartieCentrale() {
        plateau = new GrilleJeu(16, 9, 4, this);
        plateau.initFenGraphique();
        getContentPane().add(plateau, "Center");

    }

    // Cette methode affiche la partie inferieur
    public void initPartieInferieure() {
        control = new ControlJeu(this, 5);
        getContentPane().add(control, "South");

    }

    // Creation du constructeur
    public ShisenSho() {
        hof = new HighScore();
        hof.lire();
        
        URL url = getClass().getResource("/images/shisen-sho.png");
        ImageIcon ii = new ImageIcon(url);
        setIconImage(ii.getImage());
        
        setTitle("Shisen-Sho");
        initToutLaPartieSuperieur();
        initPartieCentrale();
        initPartieInferieure();

        //Si la fenetre est fermer le jeu est totalement arreter
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                control.arreterLaPartie(3);
                System.exit(0);
            }
        });
    }
}

