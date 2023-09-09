package com.anthony.liot.shisensho;
/*
 * GrilleJeu.java
 * 
 * Auteurs : Lamoury jean fr�d�ric - Liot Anthony
 *
 */
import java.util.*;
import javax.swing.*;
import java.awt.*;

 

class GrilleJeu extends JPanel{
    
    /*
     * Grille du jeu
     * Param�tres : largeur du tableau de piece Int
     *              hauteur du tableau de piece Int
     *              Nombre de pieces par famille Int
     *
     * contrainte : largeur x hauteur doit etre divisible par nbTypePiece
     *
     * Role : Tout ce qui concerne la grille de jeu graphique, ainsi que le 
     * "Moteur" de cette grille.  
     *
     */
    // nombre de ligne
    protected final int HAUTEUR;
    // nombre de colonne
    protected final int LARGEUR;
    // nbre de famille de piece
    protected final int NBFAMILLE;
    // nbre de type de piece par famille
    protected final int NBTYPEPIECE;
    // Tableau de masque pour la recherche de chemin.
    protected boolean [][] grille; 
    // Tableau des positions possibles sur la grille. Chaques pi�ces est 
    // represent�e par un int qui est son index dans le Tableau des 
    // famille de piece
    protected int [] positionPiece;
    // Tableau representant chaque famille de piece. C'est l'index qui 
    // determine le type de piece.
    protected FamillePiece [] typePiece;
    
    /*Partie Graphique*/
    // On creer un tableau de JButton
    protected JButton [] bouton;
    // on utilisera le Jframe shisensho    
    protected ShisenSho shisensho;
    // on utilisera la fonction GridBagConstraints
    protected GridBagConstraints grid;
    // des images
    protected ImageIcon fond, ImPiece;
    // les dimensions des images 
    protected int hautIcon, largIcon , hautIm , largIm;
    
    // Constructeur de GrilleJeu 
    public GrilleJeu(int largeur, int hauteur, int nbTypePiece, ShisenSho fen){
        
        shisensho = fen;
        LARGEUR = largeur;
        HAUTEUR = hauteur;
        NBTYPEPIECE = nbTypePiece;
        NBFAMILLE = (HAUTEUR*LARGEUR)/NBTYPEPIECE;
        // initialisation du tableau des positions
        positionPiece = new int[LARGEUR*HAUTEUR];
        // initialisation du tableau des familles
        typePiece = new FamillePiece [NBFAMILLE];
        // la grille de jeu est l�g�rement plus grande que la grille 
        // des pi�ces.
        grille = new boolean [HAUTEUR+2][LARGEUR+2];
        // bouton est un tableau de 144 boutons
        bouton = new JButton [HAUTEUR*LARGEUR];
        
        // on ajoute un gestionnaire de GridBagLayout a this (JPanel)
        this.setLayout(new GridBagLayout());
        grid = new GridBagConstraints();

       
    }
    /**
     * Nous creons le fond d'ecran
     **/    
    public void initFenGraphique(){
        this.FondEcran();
    }
    public void FondEcran() {
        fond = new ImageIcon(shisensho.getClass().getResource("/images/fond.png"));
        // on recup�re les dimensions du fond d'ecran 
        hautIcon = fond.getIconWidth();
        largIcon = fond.getIconHeight();
    }
        
    public void paintComponent(Graphics fon){
        super.paintComponent(fon);
        Dimension taille = getSize();
        int x=0,y=0;
        // tant que l'ecran n'est pas totalement recouvert sur x et y
        while (y<taille.height){
            while (x<taille.width){
                // on le dessine 
                fon.drawImage(fond.getImage(),x,y,null);
                x+=largIcon;
            }
            x=0;
            y+=hautIcon;
        }
          
    }
    
    // selectionner une piece
    public void selectionPiece (int pos){
        // si le coup de pouce a �t� utilis� on deselectionne
        // les deux piece selectionner
        if (shisensho.pouce.aideActive){
            for (int piece : shisensho.control.solution){
                shisensho.plateau.deselectionnerPiece(piece);   
            } 
            // l'aide n'est plus utilis�
            shisensho.pouce.aideActive=false;
        }        
        // on selectionne la piece
        String nom = "/images/"+positionPiece[pos]+"selec.png";
        dessinerPiece(pos,positionPiece[pos],nom);    
    }
    
    // deselectionner une piece
    public void deselectionnerPiece(int pos){
        String nom = "/images/"+positionPiece[pos]+".png";
        dessinerPiece(pos,positionPiece[pos],nom);
    }
    
    public void initialiseGrille (){
        
        /* 
         * Initialisation des differents composants de la grille de jeu
         * Version 1.0 --> pas d'initialisation d'une grille de bouton.
         */
        
        // initialisation du tableau de famille
        for ( int i = 0; i< typePiece.length; i++){
            typePiece[i] = new FamillePiece(NBTYPEPIECE,shisensho);
        }
        // Initialisation de la grille binaire
        // on rempli la grille avec la valeur true qui indique un deplacement
        // possible
        for ( boolean [] i : grille){
            Arrays.fill(i, true);
        }
        // initilisation des deux tableaux pour placer les pieces 
        // sur le jeu. A defaut de quelquechose de plus efficace par la suite.
        //Position possible sur le plateau de jeu
        int [] listePosition = new int [HAUTEUR*LARGEUR];
        int [] listePiece = new int [HAUTEUR*LARGEUR];
        
        for (int i=0; i< (HAUTEUR* LARGEUR); i++){
            listePosition[i]=i;
        }
        int type = 0;
        for ( int i=0 ; i< HAUTEUR*LARGEUR ; i++){ 
                listePiece[i]=type;
                if (type == NBFAMILLE-1){
                    type = 0;
                }
                else{
                    type+=1;
                }
        }
        // Permutations des deux tableaux pour le random. 
        permutTab(listePosition);
        permutTab(listePiece);
        
        // on place les pieces sauf les vides...
        for ( int i=0 ; i<HAUTEUR*LARGEUR; i++){
            if ( listePiece[i] != -1){
                placerPiece( listePosition[i], listePiece[i]);
            }
        }
        // une solution n'est forc�e que si n�c�ssaire.
        if (!existeUneSolution()){
           forcerSolution();
        }
        // il existe un solution ou bien elle a �t� forc�e, on dessine
        // le tableau de jeu.
        for ( int i=0 ; i<HAUTEUR*LARGEUR; i++){
            if ( positionPiece[i] != -1){
                 String nom = "/images/"+listePiece[i]+".png";
                 dessinerPiece(listePosition[i],listePiece[i],nom);
                
            }
        }
        // le validate et le repaint sont optionel mais il permettre un affichage
        // de la grille � la cr�ation plus "fluide" car tout est redessin� en m�me 
        // temps...
        this.validate();
        this.repaint();
         
 }      
    
    public void permutTab(int[] tableau){
        // Apr�s essai le nombre minimal de permutation qui semble correct est de 
        // l'ordre de 2*N
        int tabsize = tableau.length;
        // 1440 permutations...
        int nbrPermut = 10*tabsize;
         
        Random random = new Random();
        for (int i=0; i< nbrPermut; i++){
            // Tirage grace � random de 2 entiers compris entre 0 et tabsize  
            int indice1 = Math.abs(random.nextInt(tabsize));
            int indice2 = Math.abs(random.nextInt(tabsize));
            if (tableau[indice1] != -1 && tableau[indice2] != -1){
                    // on permute les valeurs si elles ne sont pas egale � -1.
                    int temp = tableau[indice1];
                    tableau[indice1] = tableau[indice2];
                    tableau[indice2] = temp;
            }
        }
    }
    
    public void forcerSolution(){
        /* Fonction qui garantie qu'il existe au moins une solution. N'est pas 
         * cens�e �tre utilis�e un jour 
         */
        
        // initialisation des param�tres pour le swap des pieces
        int indicePiece1 =0;
        int famillePiece1 = 0;
        int indicePiece2 = 0;
        int famillePiece2 = 0;
      
        
        // La premiere boucle parcourt le tableau de position
        for ( int i =0 ; i< positionPiece.length ; i++){
            // Si la position est non vide. 
            if (positionPiece[i] != -1){
                // La position et la famille de la piece sont sauvegard�es
                indicePiece1 = i;
                famillePiece1 = positionPiece[i];
                // La deuxiemme boucle du tableau reprends le parcourt � partir 
                // de la premiere piece non vide.
                for ( int j = indicePiece1+1; j <positionPiece.length ; j++) {
                    // s'il existe un chemin entre les deux positions
                    // on sauvegarde la position et la famille de la deuxiemme 
                    // piece.
                     if (positionPiece[j]!=-1 && existeUnChemin (indicePiece1,j)){
                        indicePiece2 = j; 
                        famillePiece2 = positionPiece[j];
                        // on mets � jour la solution "coup de pouce"
                        shisensho.control.miseAJourSolution(indicePiece1,indicePiece2);
                    }
                break;
                }
                // une piece non vide est trouv�e
                // Pas la peine de continuer la boucle.
                break;
            }
        }
        
        // Maintenant on va pouvoir swapper les deux pieces..
        // mise � jour des positions dans FamillePiece
        // prise de la premiere piece dans la premiere famille dont la position
        // n'est pas indicePiece1
        for (int position : typePiece[famillePiece1].position){
            if (position != indicePiece1){
                // La position de cette piece est elimin�e de la famille 1
                typePiece[famillePiece1].supprimerPiece(position);
                // on ajoute la nouvelle position
                typePiece[famillePiece1].ajouterPiece(indicePiece2);
                // de m�me pour le deuxiemme piece.
                typePiece[famillePiece2].supprimerPiece(indicePiece2);
                typePiece[famillePiece2].ajouterPiece(position);
                // Mise � jour du tableau des positions
                positionPiece[position] = famillePiece2;
                positionPiece[indicePiece2] = famillePiece1;
                break;
            }
            
        }
        
    }
    
    public void placerPiece(int position, int piece){

        // on calcule les coordonn�es par rapport � sa position
        int i = ligne(position);
        int j = colonne(position);
        // Mise � jour du tableau des positions.
        positionPiece[position] = piece ;

        // mise � jour du flag sur la grille de position
        // on affecte false car c'est une case qui n'est plus vide.
        grille [i][j] = false;
        //mise � jour du tableau de bouton et des statuts associ�s
        
        //mise � jour de TreeSet contenant les positions de chaque piece 
        // de la famille
        typePiece[piece].ajouterPiece(position);
    }
    
    // Dessin d'une piece
    public void dessinerPiece( int position, int piece, String nom){    
        
        //on r�cupere la position x et y de la piece
        grid.gridx=colonne(position);
        grid.gridy=ligne(position);
        
        //on recupere l'image est sa dimension
        ImPiece = new ImageIcon(shisensho.getClass().getResource(nom));
        largIm = ImPiece.getIconWidth();
        hautIm = ImPiece.getIconHeight();
        
        //si on affiche un bouton sur une position qui est null alors on 
        //creer le bouton est on l'affiche'
        if (bouton[position]==null){
            bouton[position] = new JButton();
            bouton[position].setIcon(ImPiece);
            bouton[position].setVisible(true);
        
        }
        //si il y a deja un bouton dessine, on le rend invisible en le mettant a setVisible(false)
        //et redessine une piece
        else{
            bouton[position].setVisible(false);
            bouton[position] = new JButton();
            bouton[position].setIcon(ImPiece);
            bouton[position].setVisible(true);
        }
        
        //la dimension de chaque bouton sera toujours la meme 
        Dimension dimi = new Dimension(largIm,hautIm);  
        bouton[position].setPreferredSize(dimi);
       
        //on ecoute si le bouton et cliqu� ou non
        EcouteBouton ecout = new EcouteBouton(position,shisensho.control) ;
        bouton[position].addActionListener(ecout);
        
        //on ajoute le bouton sur le grid sur le JPanel
        this.add(bouton[position] , grid);
    }
    
    public void supprimerPiece (int position){
        // Suppression d'une pi�ce de la grille
        // on recupere le type de la famille de piece
        int piece = positionPiece[position];
        
        // mise � jour de la position dans FamillePiece
        typePiece[piece].supprimerPiece(position);
        
        // on mets � jour la position
        positionPiece[position]= -1;
        // mise � jour de la grille de boolean
        grille[ligne(position)][colonne(position)] = true;

        
        // mise � jour de la partie graphique
        // en placant une piece "vide" invisible...        
        grid.gridx=colonne(position);
        grid.gridy=ligne(position);

        ImPiece = new ImageIcon(shisensho.getClass().getResource("/images/pieceVide.gif"));
        largIm = ImPiece.getIconWidth();
        hautIm = ImPiece.getIconHeight();
        bouton[position].setIcon(ImPiece);
        bouton[position].setContentAreaFilled(false);
        bouton[position].setBorderPainted(false);
        bouton[position].setEnabled(false);
       
        //la dimension de chaque bouton sera toujours la meme 
        Dimension dimi = new Dimension(largIm,hautIm); 
        bouton[position].setPreferredSize(dimi);
      
        this.add(bouton[position] , grid);
    }
    
    public void melangerGrille(){
        /* 
         * melange de la grille lorsqu'il n'y pas de solution
         * Essai sur une session de 10.000 jeu fini. Le taux de
         * melange est de 2.80. Soit 13 vie necessaire sur 5 niveau. 
         * Taux de grille sans solution g�n�r�e 4/10.000
         * La solution n'est forc�e que si necessaire ( jamais pour ainsi dire)
         * 
         */
            // Commen�ons par reconstruire les deux tableaux qui nous servirons �
            // placer les pieces
             int [] listePosition = new int [HAUTEUR*LARGEUR];
             int [] listePiece = new int [HAUTEUR*LARGEUR];
        
             for (int i=0; i < positionPiece.length; i++){
                 if (positionPiece[i] == -1 ){
                      listePosition[i] = -1;
                      listePiece[i] = -1;
                }
                 else{
                     listePosition[i] = i;
                     listePiece[i] = positionPiece[i];
                 }
            }
            // on melange les deux Tableaux. Sachant que la methode permutTab ne 
            // touche pas au cases avec la valeur -1. Les cases sans pieces ont leur
            // position pr�serv�e.
            permutTab(listePosition);
            permutTab(listePiece); 
            //Avant de replacer les pieces dans leur nouvelles positions on 
            // reinitialise le tableau des familles de pieces.
                for (FamillePiece piece : typePiece){
                piece.initialiser();
                }
                // on reinitialise la grille de bouton.
                //for (JButton piece : bouton){
                for ( int i=0 ; i<HAUTEUR*LARGEUR; i++){
                    if (listePiece[i]!=-1){
                        bouton[i].setVisible(false);
                   }
                }
                
            
            // Maintenant on peut replacer les pieces...
             for ( int i=0 ; i<HAUTEUR*LARGEUR; i++){
                if (listePiece[i] != -1){
                    placerPiece( listePosition[i], listePiece[i]);
                }                
             }
                // une solution n'est forc�e que si n�c�ssaire.
            if (!existeUneSolution()){
                forcerSolution();
            }
            // il existe un solution ou bien elle a �t� forc�e, on dessine
            // le tableau de jeu.
            for ( int i=0 ; i<HAUTEUR*LARGEUR; i++){
                if ( positionPiece[i] != -1){
                     String nom = "/images/"+listePiece[i]+".png";
                    dessinerPiece(listePosition[i],listePiece[i],nom);
            }
        }
    }
    public boolean existeCheminVertical(int lin1, int col1, int lin2, int col2){
        Intervalle intervalle1 = new Intervalle();
        intervalle1 = intervalle1.intervalleVertical(grille,lin1,col1);
        Intervalle intervalle2 = intervalle1.intervalleVertical(grille,lin2,col2);
        
        
        // on cr��e un intervalle de valeurs parmis lesquelles 
        // il pourrait exister une solution. Pas besoin de parcourir l'ensemble
        // des points de chaque intervalle.
        
        int ymin = Math.max(intervalle1.MIN, intervalle2.MIN);
        int ymax = Math.min(intervalle1.MAX, intervalle2.MAX);
            
        // Le chemin existe ?
        // Essai de "connecter" les deux intervalles.
        // on determine la colonne d'arriv�e et de depart pour s'affranchir de
        // l'ordre dans lequel les tiles ont �t� choisi.
        int colArrivee = Math.max(col1,col2);
        int colDepart = Math.min(col1,col2);
        
        for (int i = ymin ; i<=ymax; i++){
            // si on entre dans la boucle c'est qu'il existe un intervalle de
            // chemin horizontal possible entre les deux intervalles verticaux.
            // si les deux pieces sont sur le m�me colonne alors il y a une solution
            if (colArrivee == colDepart+1 || colArrivee == colDepart){
                return true;
            }
            // s'il ne sont pas sur la m�me colonne on essaye de connecter les deux 
            // intervalles.
            for (int j = colDepart ; j <= colArrivee && grille[i][j]; j++){ 
                // Nous sommes arriv� au bout du chemin, les deux intervalles
                // sont connect�... ?
                if (j+1==colArrivee){
                    return true;
                }
            }
         }
         // Toutes les positions de l'intervalle on �t� explor�es. il n'y
         // pas de connections possible, donc pas de chemin...
         return false;    
        }
   public boolean existeCheminHorizontal(int lin1, int col1, int lin2, int col2){
          //cette fonction est la sym�trique de existeCheminVerticale.
        
        //if (lin1 == lin2 &&( col1+1 == col2 || col1-1 == col2)){
          //      return true;
        //}
        Intervalle intervalle1 = new Intervalle(0,0);
        Intervalle intervalle2 = new Intervalle(0,0);
        intervalle1 = intervalle1.intervalleHorizontal(grille,lin1,col1);
        intervalle2 = intervalle2.intervalleHorizontal(grille,lin2,col2);
        
        int xmin = Math.max(intervalle1.MIN, intervalle2.MIN);
        int xmax = Math.min(intervalle1.MAX, intervalle2.MAX);
        
        int linArrivee = Math.max(lin1,lin2);
        int linDepart = Math.min(lin1,lin2);
       
        for (int j = xmin ; j<=xmax ; j++){
            if (linArrivee == linDepart+1 || linArrivee == linDepart){
                return true;
            }
            for (int i = linDepart ; i <= linArrivee && grille[i][j]; i++){ 
                if (i+1==linArrivee){
                    return true;
                }
            }
         }
         return false; 
    }
    public boolean existeUnChemin(int a, int b){
        
        // Extraction des coordonn�es � partir de la position de la piece.
        int col1 = colonne(a);
        int col2 = colonne(b);
        int lin1 = ligne(a);
        int lin2 = ligne(b);
        
        // on cherche un chemin
        return (existeCheminVertical(lin1, col1, lin2, col2)||existeCheminHorizontal(lin1, col1, lin2, col2));        
    }
    
    public boolean existeUneSolution(){
        // Pour chaque famille de piece on va regarder s'il existe un chemin
        // entre les different membres.
    	for (FamillePiece piece : typePiece){
            if (piece.existeChemin(piece,this)){
                return true;
            }
        }
        return false;
    }
    
    public boolean estVide(){
        for (int piece : positionPiece ){
            if (piece !=-1){
                return false;
            }
        }
        return true;
    }
    public int ligne (int position){
        // Renvoie la ligne � partir d'une position
        // dans le tableau grille.
        // ATTENTION : on oublie pas le +1 � cause du surdimensionement 
        // de la grille.
        return (position/LARGEUR)+1;
    }
    public int colonne(int position){
        // De m�me que pour la ligne
        return (position%LARGEUR)+1;
    }
}
    