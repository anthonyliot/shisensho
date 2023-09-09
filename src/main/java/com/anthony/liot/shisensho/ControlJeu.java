package com.anthony.liot.shisensho;
/*
 * ControlJeu.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

//le controlJeu herite des fonctionnalites d'un Jpanel et est implementes de actionListener

public class ControlJeu  extends JPanel implements ActionListener{    

    //on appel un shisensho
    protected ShisenSho shisensho;
    
    //on prepare les partie du controlleur de jeu: vie, niveau, score, temp.
    protected JLabel level;
    protected JLabel vie;
    protected JLabel score;
    protected JProgressBar tempBarre;
    protected Timer chrono;
    
    protected int boutonSelectionne ;
    protected int [] solution ;
    protected int nombreVie ;
    protected int niveau;
    protected boolean partieEnCours;
    protected int scoreCourant;
    Gravite [] graviter;
    
   
    protected final int NBNIVEAU;
    protected final int NBPIECE;

    public ControlJeu(ShisenSho shi, int nbNiv) {
        
        shisensho = shi;    
        partieEnCours = false;
        NBNIVEAU = nbNiv-1;
        NBPIECE = shisensho.plateau.HAUTEUR*shisensho.plateau.LARGEUR;
  
        //formation de la partie graphique inferieur
        setLayout(new GridLayout(4, 2));
        
        JLabel levelChar = new JLabel("Niveau:");
        JLabel vieChar = new JLabel("Vie:");
        JLabel scoreChar = new JLabel("Score:");
        JLabel tempChar = new JLabel("Temps:");
        
        level = new JLabel();
        vie = new JLabel();
        score = new JLabel();
        
        tempBarre = new JProgressBar(0, 200);
        tempBarre.setStringPainted(true);
        
        add(levelChar);
        add(level);
        
        add(vieChar);
        add(vie);
        
        add(scoreChar);
        add(score);
        
        add(tempChar);
        add(tempBarre);
        chrono = new Timer (6000,this);
        chrono.addActionListener(this);
        
        //Creation d'un tableau de gravite correspondant a chaque niveau
        graviter = new Gravite[5];
        graviter[0]=new GraviteNulle();
        graviter[1]=new GraviteBas();
        graviter[2]=new GraviteHaut();
        graviter[3]=new GraviteGauche();
        graviter[4]=new GraviteDroit();
          

    }
    /****
    * Methode de l'interface ActionListener : 
    *        necessaire pour l'object javax.swing.Timer
    *        methode appelle a intervalle de temps regulier par le timer
    *
    ****/
    public void actionPerformed (ActionEvent e){
        // Cas d'un evenement genere par le bouton
        int tempEcoul = tempBarre.getValue();
        int tempTotal = tempBarre.getMaximum();
        
        //Si la barre de temp est remplit on a perdu
        if(tempEcoul + 1 >= tempTotal){
            tempBarre.setValue(tempTotal);
            arreterLaPartie(1);            
            return;
        }
        //Sinon on augmente le niveau de la barre de temps
        else {
            tempBarre.setValue(tempEcoul + 1);
            return;
        }
    }
    
    public void demarrerPartie(){
        solution = new int[2];
        //Initialisation de la grille de jeu
        //ainsi que des boutons graphiques pause et aide 
        shisensho.plateau.initialiseGrille();
        shisensho.pouce.setEnabled(true);        
        shisensho.pause.setEnabled(true);
                
        // verification qu'il existe bien une solution.
        // il faudra forcer les deux premieres pieces...
        solution = new int[2];
        shisensho.plateau.existeUneSolution();
        // Initialisation de la marque de selection
        boutonSelectionne = -1;
        // initialisation du nombre de vie
        nombreVie = 2*(NBNIVEAU+1);
        niveau = 0 ;
        scoreCourant = 0;
        partieEnCours=true;
        
        level.setText(String.valueOf(niveau+1));      
        vie.setText(String.valueOf(nombreVie)); 
        score.setText(String.valueOf(scoreCourant)); 
        
        chrono.stop();
        tempBarre.setValue(tempBarre.getMinimum());
        chrono.start();
    
    }
    public boolean verifMemeFamille ( int pos){
        // les familles sont index�es dans le tableau de position
        // suffit de regarder si la "valeur" est la m�me.
        return shisensho.plateau.positionPiece[pos]== shisensho.plateau.positionPiece[boutonSelectionne]? true : false;
    }
    
    public int calculScore(){
        // on calcul le numerateur
        double n = shisensho.plateau.LARGEUR*shisensho.plateau.HAUTEUR;
        double num = Math.sqrt(n*n*n);
        
        // on calcul le denominateur
        double t = tempBarre.getValue()*3;
        double denom = (0.012831211946D * t);
        
        // celon le niveau, on renvoit num/denom ou (num/denom)*2
        return niveau==0 ? (int)(num/denom) : (int)((num/denom)*2) ;
    }

    public void boutonConnecte(int position){
        //recup�ration de la position du deuxiemme bouton.
        int pos = position;
        // Switch sur la valeur du premier bouton.
        switch(boutonSelectionne){
            // La pi�ce est une piece vide. Il n'y avait pas de bouton deja selection
            // la piece est donc la premiere.
            case -1 : 
                shisensho.plateau.selectionPiece(pos);
                boutonSelectionne = pos;
                break;
            default:
                // si les deux pieces sont de la m�me famille, qu'il existe une chemin entre elle et 
                // que ce n'est pas le m�me bouton qui a �t� selectionn�...
                if (verifMemeFamille(pos)&& shisensho.plateau.existeUnChemin(boutonSelectionne, pos)&& 
                    pos != boutonSelectionne){
                    
                    shisensho.plateau.supprimerPiece(boutonSelectionne);
                    shisensho.plateau.supprimerPiece(pos);
                    //actualisation des deux pieces.
                    graviter[niveau].Actualiser(boutonSelectionne,pos,shisensho);
                    // Plus de piece selectionn�e
                    boutonSelectionne = -1;
                    // il faut verifier qu'il existe une solution sur la grille.
                    boolean flag = shisensho.plateau.existeUneSolution();
                    // pas de solution est le tableau de jeu est vide ?
                    if (!flag && shisensho.plateau.estVide()){
                        // c'est le dernier niveau, alors la partie s'arr�te
                        if (niveau == NBNIVEAU){
                            arreterLaPartie(2);
                        }
                        else{
                            // sinon on passe au niveau suivant.
                             scoreCourant += calculScore();
                             niveauSuivant(); 
                             //flag = shisensho.plateau.existeUneSolution();
                           
                        }
                    }
                    // le plateau de jeu n'est pas vide. Le plateau est remelang�.
                    // Le nombre de vie est verifi� au passage et mis � jour.
                    
                    else if (!flag){
                         nombreVie--;
                         if (nombreVie==0){
                             arreterLaPartie(1);
                         } 
                         vie.setText(String.valueOf(nombreVie));
                         shisensho.plateau.melangerGrille();
                         //flag = shisensho.plateau.existeUneSolution();
                       
                    }
                    
                }
                // sinon on deselectionne la premiere piece et on reinitialise 
                // boutonSelectionne.
                else{
                    shisensho.plateau.deselectionnerPiece(boutonSelectionne);
                    boutonSelectionne = -1;
                }
        }
    }
    
    // On mets a jour le tableau de solution
    public void miseAJourSolution(int a, int b){
        solution[0]=a;
        solution[1]=b;
    }
    
    // on lance un niveau suivant
    public void niveauSuivant(){
        // augmentation de la vie et du niveau
        niveau++;
        nombreVie++;
        // mis a jour de la partie graphique
        
        level.setText(String.valueOf(niveau+1));  
        vie.setText(String.valueOf(nombreVie));
        
        score.setText(String.valueOf(scoreCourant));
        shisensho.plateau.initialiseGrille();
        shisensho.plateau.existeUneSolution();
        
        //chrono arreter puis relancer
        chrono.stop();
        tempBarre.setValue(tempBarre.getMinimum());
        chrono.start();      
    }   


    @SuppressWarnings("static-access")
	public void arreterLaPartie(int part){
        int reponse;
        
        switch (part){
            // Quoi qu'il en soit si tu a un scores correcte
            // tu sera enregistrer dans les scores 
            
            case 0:
                //tu abandonnes la partie
                reponse=JOptionPane.showConfirmDialog(null, "Etes vous s�r de vouloir abandonner?","ABANDON DE PARTIE",JOptionPane.YES_NO_OPTION);
                shisensho.hof.ajouterScore(shisensho,scoreCourant);
                if (reponse==0){
                    System.exit(0);
                }
                break;
                
            case 1:
                //tu as perdu la partie
                reponse=JOptionPane.showConfirmDialog(null, "voulez-vous rejouer?","PARTIE PERDUE",JOptionPane.YES_NO_OPTION);
                shisensho.hof.ajouterScore(shisensho,scoreCourant);
                if (reponse==1){
                    System.exit(0);
                }
                //Tu redemare une partie;
                shisensho.control.demarrerPartie();
                break;  
                
            case 2:
                //tu as terminer le jeu, tu as gagn�
                reponse=JOptionPane.showConfirmDialog(null, "FELICITATIONS !!!!! /n Voulez-vous rejouer?","PARTIE GAGNE",JOptionPane.YES_NO_OPTION);
                shisensho.hof.ajouterScore(shisensho,scoreCourant);
                if (reponse==1){
                    System.exit(0);
                }
                //Tu redemare une partie;
                shisensho.control.demarrerPartie();
                break;
                
            default:
                shisensho.hof.ajouterScore(shisensho,scoreCourant);
                break;
        }
    }    
}
        
