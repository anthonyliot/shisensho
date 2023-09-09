/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.anthony.liot.shisensho;

import java.awt.Dimension;

/**
 *
 * @author anthony.liot
 */
public class Main {

    public static void main(String[] args) {
        ShisenSho shisensho = new ShisenSho();
        Dimension dimi = new Dimension(666,666);        
        shisensho.setSize(dimi);
        shisensho.setVisible(true);
    }
}
