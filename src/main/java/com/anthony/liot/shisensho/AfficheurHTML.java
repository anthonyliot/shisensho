package com.anthony.liot.shisensho;
/*
 * AfficheurHTML.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */


import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.*;

// AfficheurHTML herite du JScrollPane sera implementer de HyperlinkListener

class AfficheurHTML extends JScrollPane implements HyperlinkListener{
    
    protected JEditorPane editeur;

    public AfficheurHTML(ShisenSho shisensho){
        
        try{
            //Creation d'une URL, adreese de la page d'index'
            URL direction = shisensho.getClass().getResource("/html/index.html");
            editeur = new JEditorPane(direction);
            //Dimension du cadre 
            editeur.setPreferredSize(new Dimension(650, 450));
            //on rend l'editeur non editable
            editeur.setEditable(false);
            //on ajoute a l'editeur les liens
            editeur.addHyperlinkListener(this);
            
            JViewport jviewport = getViewport();
            jviewport.add(editeur);
        }
        catch (IOException exp){
        }
    }


    /**
     * Quelques types de contenu peut fournir des liens support� par le g�nerateur de liens. 
     * L'editeur HTML gen�re des liens si le JEditorPane n'est pas editable.
     * si le cadre HTML est incrust� dans le doc, la reponse devra changer une partie du doc
     * Le fragment de code rend possible l'action sur les liens.
     */
    public void hyperlinkUpdate(HyperlinkEvent e){
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            JEditorPane pane = (JEditorPane) e.getSource();
            if (e instanceof HTMLFrameHyperlinkEvent) {
                HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
                HTMLDocument doc = (HTMLDocument)pane.getDocument();
                doc.processHTMLFrameHyperlinkEvent(evt);
            }
            else {
                try {
                    pane.setPage(e.getURL());
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }
}
